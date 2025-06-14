# Configuration CI/CD

Ce projet utilise GitHub Actions pour exécuter automatiquement les tests à chaque push sur toutes les branches.

## Workflow CI

Le workflow se trouve dans `.github/workflows/ci.yml` et exécute les étapes suivantes :

### 1. Tests Backend (Java/Spring Boot)

- Compilation du code Java
- Exécution des tests unitaires avec Maven
- Vérification du formatage du code avec Spotless

### 2. Tests Frontend (Angular)

- Installation des dépendances Node.js
- Linting du code TypeScript
- Exécution des tests unitaires avec Karma
- Build de production
- Audit de sécurité des dépendances

### 3. Qualité du Code

- Vérification du formatage du code (Backend et Frontend)
- Audit de sécurité des dépendances npm

## Déclenchement

Le workflow se déclenche automatiquement sur :

- **Push** sur n'importe quelle branche
- **Pull Request** vers n'importe quelle branche

## Scripts Disponibles

### Frontend (dans `front/package.json`)

```bash
npm run test          # Tests en mode watch
npm run test:ci       # Tests pour CI (headless)
npm run lint          # Linting du code
npm run lint:fix      # Linting avec auto-correction
npm run format        # Formatage du code
npm run format:check  # Vérification du formatage
```

### Backend (dans `back/ecommerce-backend/`)

```bash
mvn test              # Exécution des tests
mvn spotless:check    # Vérification du formatage
mvn spotless:apply    # Application du formatage
```

## Artifacts

Le workflow génère des artifacts :

- **backend-test-results** : Rapports de tests du backend
- **frontend-coverage** : Rapports de couverture de code du frontend

## Configuration Requise

### Frontend

- Node.js 18+
- Angular CLI
- Karma pour les tests
- Biome pour le linting/formatage

### Backend

- Java 21
- Maven
- Spotless pour le formatage

## Résolution des Problèmes

### Si les tests échouent

1. Vérifiez les logs dans l'onglet "Actions" de GitHub
2. Exécutez les tests localement avec les mêmes commandes
3. Corrigez les erreurs de linting/formatage
4. Assurez-vous que tous les tests passent localement

### Si le build échoue

1. Vérifiez que toutes les dépendances sont installées
2. Assurez-vous que le code compile localement
3. Vérifiez les erreurs de syntaxe

## Bonnes Pratiques

1. **Toujours tester localement** avant de pousser
2. **Corriger les erreurs de linting** avant de créer une PR
3. **Maintenir une bonne couverture de tests**
4. **Utiliser des messages de commit descriptifs**
