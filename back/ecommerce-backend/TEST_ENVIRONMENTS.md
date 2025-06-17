# Environnements de Test

Ce projet utilise différents environnements de test selon le contexte d'exécution.

## Environnements Disponibles

### 1. Environnement Local (test)

- **Profil** : `test`
- **Base de données** : H2 (en mémoire)
- **Configuration** : `application-test.yml`
- **Utilisation** : Tests locaux rapides
- **Commande** : `mvn test`

### 2. Environnement CI (intégration)

- **Profil** : `ci`
- **Base de données** : PostgreSQL
- **Configuration** : `application-ci.yml`
- **Utilisation** : Tests d'intégration dans GitHub Actions
- **Commande** : `mvn test -Dspring.profiles.active=ci`

## Configuration des Bases de Données

### H2 (Local)

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    driver-class-name: org.h2.Driver
    username: sa
    password:
```

### PostgreSQL (CI)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/testdb
    driver-class-name: org.postgresql.Driver
    username: postgres
    password: postgres
```

## Tests Disponibles

### Tests Unitaires

- **Environnement** : H2 (local)
- **Profil** : `test`
- **Exécution** : `mvn test`

### Tests d'Intégration

- **Environnement** : PostgreSQL
- **Profil** : `ci`
- **Exécution** : `mvn test -Dspring.profiles.active=ci`

## Scripts Utiles

### Test Local avec H2

```bash
mvn test
```

### Test Local avec PostgreSQL (simulation CI)

```bash
./test-ci-local.sh
```

### Test CI dans GitHub Actions

Le workflow GitHub Actions exécute automatiquement :

1. Tests unitaires avec H2
2. Tests d'intégration avec PostgreSQL

## Résolution de Problèmes

### Erreur de Connexion PostgreSQL

- Vérifier que PostgreSQL est démarré
- Vérifier les paramètres de connexion
- Utiliser `pg_isready` pour diagnostiquer

### Erreur de Profil

- S'assurer que le bon profil est activé
- Vérifier que le fichier de configuration existe
- Utiliser `-Dspring.profiles.active=ci` pour forcer le profil

### Différences entre Local et CI

- **Local** : H2 en mémoire (rapide)
- **CI** : PostgreSQL réel (plus réaliste)
- Les tests peuvent se comporter différemment

## Bonnes Pratiques

1. **Tests Locaux** : Utiliser H2 pour la rapidité
2. **Tests CI** : Utiliser PostgreSQL pour la réalité
3. **Configuration** : Séparer les environnements clairement
4. **Documentation** : Maintenir cette documentation à jour
