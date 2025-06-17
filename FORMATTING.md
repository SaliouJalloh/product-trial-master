# Configuration du Formatage Automatique

Ce projet utilise des outils de formatage automatique pour maintenir la cohérence du code.

## Outils Utilisés

### Backend (Java/Spring Boot)

- **Spotless Maven Plugin** : Formatage automatique du code Java
- **Google Java Format** : Style de formatage appliqué

### Frontend (TypeScript/Angular)

- **Biome** : Formateur et linter pour TypeScript/Angular

## Configuration des Hooks Git

### Installation Manuelle

1. **Backend** : Créez le fichier `back/ecommerce-backend/.git/hooks/pre-commit.ps1` avec le contenu suivant :

```powershell
# Hook pre-commit PowerShell pour formater automatiquement le code Java

Write-Host "🔧 Formatage automatique du code Java..." -ForegroundColor Green

# Vérifier si Maven est disponible
try {
    $null = Get-Command mvn -ErrorAction Stop
} catch {
    Write-Host "❌ Maven n'est pas installé ou n'est pas dans le PATH" -ForegroundColor Red
    exit 1
}

# Formater le code avec Spotless
Write-Host "Application du formatage Spotless..." -ForegroundColor Yellow
& mvn spotless:apply

if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Erreur lors du formatage avec Spotless" -ForegroundColor Red
    exit 1
}

# Vérifier s'il y a des changements non formatés
Write-Host "Vérification du formatage..." -ForegroundColor Yellow
& mvn spotless:check

if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Le code n'est pas correctement formaté. Exécutez 'mvn spotless:apply' pour corriger." -ForegroundColor Red
    exit 1
}

Write-Host "✅ Formatage terminé avec succès" -ForegroundColor Green
```

2. **Frontend** : Créez le fichier `front/.git/hooks/pre-commit.ps1` avec le contenu suivant :

```powershell
# Hook pre-commit PowerShell pour formater automatiquement le code TypeScript/Angular

Write-Host "🔧 Formatage automatique du code TypeScript/Angular..." -ForegroundColor Green

# Vérifier si Node.js et npm sont disponibles
try {
    $null = Get-Command npm -ErrorAction Stop
} catch {
    Write-Host "❌ npm n'est pas installé ou n'est pas dans le PATH" -ForegroundColor Red
    exit 1
}

# Formater le code avec Biome
Write-Host "Application du formatage Biome..." -ForegroundColor Yellow
& npm run format

if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Erreur lors du formatage avec Biome" -ForegroundColor Red
    exit 1
}

# Vérifier s'il y a des problèmes de linting
Write-Host "Vérification du linting..." -ForegroundColor Yellow
& npm run check

if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Problèmes de linting détectés. Exécutez 'npm run check:fix' pour corriger." -ForegroundColor Red
    exit 1
}

Write-Host "✅ Formatage et vérification terminés avec succès" -ForegroundColor Green
```

## Commandes Manuelles

### Backend

```bash
# Formater le code
mvn spotless:apply

# Vérifier le formatage
mvn spotless:check
```

### Frontend

```bash
# Formater le code
npm run format

# Vérifier le formatage
npm run format:check

# Vérifier le linting
npm run check

# Corriger automatiquement les problèmes de linting
npm run check:fix
```

## Fonctionnement

- **Lors d'un commit** : Les hooks Git s'exécutent automatiquement
- **Formatage** : Le code est formaté selon les règles définies
- **Vérification** : Si le formatage échoue, le commit est bloqué
- **Messages** : Des messages colorés indiquent le statut du formatage

## Prérequis

- **Backend** : Maven installé et dans le PATH
- **Frontend** : Node.js et npm installés et dans le PATH

## Dépannage

Si les hooks ne s'exécutent pas :

1. Vérifiez que les fichiers `.ps1` existent dans les dossiers `.git/hooks/`
2. Assurez-vous que PowerShell est configuré pour exécuter les scripts
3. Testez manuellement les commandes de formatage
