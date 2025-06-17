# Script de configuration des hooks Git pour le formatage automatique

Write-Host "🔧 Configuration des hooks Git pour le formatage automatique..." -ForegroundColor Green

# Fonction pour créer un hook pre-commit
function Create-PreCommitHook {
    param(
        [string]$HookPath,
        [string]$ScriptContent
    )
    
    $hookDir = Split-Path $HookPath -Parent
    if (!(Test-Path $hookDir)) {
        New-Item -ItemType Directory -Path $hookDir -Force | Out-Null
    }
    
    Set-Content -Path $HookPath -Value $ScriptContent -Encoding UTF8
    Write-Host "✅ Hook créé: $HookPath" -ForegroundColor Green
}

# Hook pour le backend (Java)
$backendHookContent = @'
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
'@

# Hook pour le frontend (TypeScript/Angular)
$frontendHookContent = @'
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
'@

# Créer les hooks
Create-PreCommitHook -HookPath "back/ecommerce-backend/.git/hooks/pre-commit.ps1" -ScriptContent $backendHookContent
Create-PreCommitHook -HookPath "front/.git/hooks/pre-commit.ps1" -ScriptContent $frontendHookContent

Write-Host "🎉 Configuration terminée !" -ForegroundColor Green
Write-Host "Les hooks Git sont maintenant configurés pour formater automatiquement le code lors des commits." -ForegroundColor Yellow
Write-Host "" -ForegroundColor White
Write-Host "Pour tester le formatage manuellement :" -ForegroundColor Cyan
Write-Host "  Backend: cd back/ecommerce-backend; mvn spotless:apply" -ForegroundColor White
Write-Host "  Frontend: cd front; npm run format" -ForegroundColor White 