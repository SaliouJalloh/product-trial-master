#!/bin/bash
# Script de configuration des hooks Git pour le formatage automatique (Unix/Linux)

GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

echo -e "${GREEN}🔧 Configuration des hooks Git pour le formatage automatique...${NC}"

# Fonction pour créer un hook pre-commit
create_pre_commit_hook() {
  local hook_path="$1"
  local script_content="$2"
  local hook_dir
  hook_dir=$(dirname "$hook_path")
  mkdir -p "$hook_dir"
  echo "$script_content" > "$hook_path"
  chmod +x "$hook_path"
  echo -e "${GREEN}✅ Hook créé: $hook_path${NC}"
}

# Hook pour le backend (Java)
backend_hook_content='#!/bin/bash
# Hook pre-commit pour formater automatiquement le code Java

echo -e "\033[0;32m🔧 Formatage automatique du code Java...\033[0m"

# Vérifier si Maven est disponible
if ! command -v mvn &> /dev/null; then
  echo -e "\033[0;31m❌ Maven n\'est pas installé ou n\'est pas dans le PATH\033[0m"
  exit 1
fi

echo -e "\033[1;33mApplication du formatage Spotless...\033[0m"
mvn spotless:apply
if [ $? -ne 0 ]; then
  echo -e "\033[0;31m❌ Erreur lors du formatage avec Spotless\033[0m"
  exit 1
fi

echo -e "\033[1;33mVérification du formatage...\033[0m"
mvn spotless:check
if [ $? -ne 0 ]; then
  echo -e "\033[0;31m❌ Le code n\'est pas correctement formaté. Exécutez 'mvn spotless:apply' pour corriger.\033[0m"
  exit 1
fi

echo -e "\033[0;32m✅ Formatage terminé avec succès\033[0m"
'

# Hook pour le frontend (TypeScript/Angular)
frontend_hook_content='#!/bin/bash
# Hook pre-commit pour formater automatiquement le code TypeScript/Angular

echo -e "\033[0;32m🔧 Formatage automatique du code TypeScript/Angular...\033[0m"

# Vérifier si npm est disponible
if ! command -v npm &> /dev/null; then
  echo -e "\033[0;31m❌ npm n\'est pas installé ou n\'est pas dans le PATH\033[0m"
  exit 1
fi

echo -e "\033[1;33mApplication du formatage Biome...\033[0m"
npm run format
if [ $? -ne 0 ]; then
  echo -e "\033[0;31m❌ Erreur lors du formatage avec Biome\033[0m"
  exit 1
fi

echo -e "\033[1;33mVérification du linting...\033[0m"
npm run check
if [ $? -ne 0 ]; then
  echo -e "\033[0;31m❌ Problèmes de linting détectés. Exécutez 'npm run check:fix' pour corriger.\033[0m"
  exit 1
fi

echo -e "\033[0;32m✅ Formatage et vérification terminés avec succès\033[0m"
'

# Créer les hooks
create_pre_commit_hook "back/ecommerce-backend/.git/hooks/pre-commit" "$backend_hook_content"
create_pre_commit_hook "front/.git/hooks/pre-commit" "$frontend_hook_content"

echo -e "${GREEN}🎉 Configuration terminée !${NC}"
echo -e "${YELLOW}Les hooks Git sont maintenant configurés pour formater automatiquement le code lors des commits.${NC}"
echo -e ""
echo -e "${CYAN}Pour tester le formatage manuellement :${NC}"
echo -e "  Backend: cd back/ecommerce-backend; mvn spotless:apply"
echo -e "  Frontend: cd front; npm run format"
