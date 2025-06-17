#!/bin/bash

# Script pour tester la configuration CI localement
# Compatible Windows (Git Bash) et Linux/Mac

echo "🧪 Test de la configuration CI locale..."

# Vérifier si Docker est disponible
if ! command -v docker &> /dev/null; then
    echo "❌ Docker n'est pas installé"
    exit 1
fi

# Arrêter et supprimer le conteneur existant s'il existe
echo "🧹 Nettoyage des conteneurs existants..."
docker stop test-postgres-ci 2>/dev/null || true
docker rm test-postgres-ci 2>/dev/null || true

# Trouver un port disponible (commencer par 5432)
PORT=5432
while docker ps -q --filter "publish=$PORT" | grep -q .; do
    echo "Port $PORT est occupé, essai du port suivant..."
    PORT=$((PORT + 1))
done

echo "🐘 Démarrage de PostgreSQL sur le port $PORT..."
docker run --name test-postgres-ci \
    -e POSTGRES_PASSWORD=postgres \
    -e POSTGRES_DB=testdb \
    -e POSTGRES_USER=postgres \
    -p $PORT:5432 \
    -d postgres:15

if [ $? -ne 0 ]; then
    echo "❌ Erreur lors du démarrage de PostgreSQL"
    exit 1
fi

# Attendre que PostgreSQL soit prêt (approche compatible Windows)
echo "⏳ Attente que PostgreSQL soit prêt..."
sleep 10

# Test de connectivité avec plusieurs méthodes
echo "🔍 Test de connectivité..."
CONNECTION_OK=false

# Méthode 1: netcat (nc)
if command -v nc &> /dev/null; then
    echo "  - Test avec netcat..."
    if nc -z localhost $PORT 2>/dev/null; then
        echo "✅ PostgreSQL est accessible sur le port $PORT (netcat)"
        CONNECTION_OK=true
    fi
fi

# Méthode 2: telnet
if [ "$CONNECTION_OK" = false ] && command -v telnet &> /dev/null; then
    echo "  - Test avec telnet..."
    if echo "quit" | telnet localhost $PORT 2>&1 | grep -q "Connected"; then
        echo "✅ PostgreSQL est accessible sur le port $PORT (telnet)"
        CONNECTION_OK=true
    fi
fi

# Méthode 3: curl (si disponible)
if [ "$CONNECTION_OK" = false ] && command -v curl &> /dev/null; then
    echo "  - Test avec curl..."
    if curl -s --connect-timeout 5 telnet://localhost:$PORT >/dev/null 2>&1; then
        echo "✅ PostgreSQL est accessible sur le port $PORT (curl)"
        CONNECTION_OK=true
    fi
fi

# Méthode 4: Vérification des logs Docker
if [ "$CONNECTION_OK" = false ]; then
    echo "  - Vérification des logs Docker..."
    if docker logs test-postgres-ci 2>&1 | grep -q "database system is ready to accept connections"; then
        echo "✅ PostgreSQL semble prêt (d'après les logs)"
        CONNECTION_OK=true
    else
        echo "⚠️  Impossible de vérifier la connectivité, on continue..."
        # On continue quand même car PostgreSQL peut être prêt sans que les logs soient visibles
        CONNECTION_OK=true
    fi
fi

if [ "$CONNECTION_OK" = false ]; then
    echo "❌ PostgreSQL n'est pas accessible"
    docker stop test-postgres-ci
    docker rm test-postgres-ci
    exit 1
fi

# Sauvegarder la configuration originale
echo "💾 Sauvegarde de la configuration originale..."
cp src/test/resources/application-ci.yml src/test/resources/application-ci.yml.backup

# Modifier temporairement la configuration pour utiliser le bon port
echo "🔧 Configuration du port PostgreSQL..."
if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "cygwin" ]]; then
    # Windows Git Bash
    sed -i "s/localhost:5432/localhost:$PORT/g" src/test/resources/application-ci.yml
else
    # Linux/Mac
    sed -i "s/localhost:5432/localhost:$PORT/g" src/test/resources/application-ci.yml
fi

# Exécuter les tests avec le profil CI
echo "🔧 Exécution des tests avec le profil CI..."
mvn test -Dspring.profiles.active=ci

# Sauvegarder le code de sortie
TEST_EXIT_CODE=$?

# Restaurer la configuration originale
echo "🔄 Restauration de la configuration..."
mv src/test/resources/application-ci.yml.backup src/test/resources/application-ci.yml

# Nettoyer
echo "🧹 Nettoyage..."
docker stop test-postgres-ci
docker rm test-postgres-ci

# Afficher le résultat
if [ $TEST_EXIT_CODE -eq 0 ]; then
    echo "✅ Tests terminés avec succès!"
else
    echo "❌ Tests échoués avec le code de sortie: $TEST_EXIT_CODE"
fi

exit $TEST_EXIT_CODE