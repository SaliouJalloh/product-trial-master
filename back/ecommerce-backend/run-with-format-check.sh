#!/bin/bash

# Script bash pour lancer l'application avec vérification du formatage

echo "🔍 Vérification du formatage du code..."

# Vérifier le formatage avec Spotless
echo "Exécution de mvn spotless:check..."
mvn spotless:check

if [ $? -ne 0 ]; then
    echo ""
    echo "⚠️  Le code n'est pas correctement formaté !"
    echo ""
    echo "🔧 Pour corriger le formatage, exécutez :"
    echo "   mvn spotless:apply"
    echo ""
    echo "📝 Puis relancez l'application :"
    echo "   mvn spring-boot:run"
    echo ""
    echo "❌ Application non lancée - formatage requis"
    exit 1
fi

echo "✅ Formatage OK - Lancement de l'application..."
echo ""

# Lancer l'application Spring Boot
echo "🚀 Démarrage de l'application Spring Boot..."
mvn spring-boot:run 