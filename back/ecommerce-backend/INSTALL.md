# Guide d'installation - ECommerce Backend

Ce guide explique comment configurer et démarrer le projet ECommerce Backend pour les nouveaux membres de l'équipe.

## Prérequis

- Java 21 (Eclipse Temurin/AdoptOpenJDK recommandé)
- Maven 3.8+
- PostgreSQL 14+
- Docker et Docker Compose (optionnel, pour le déploiement containerisé)
- Un IDE Java (IntelliJ IDEA, Eclipse, VS Code avec les extensions appropriées)

## Configuration du projet

### 1. Cloner le dépôt

```bash
git clone <URL_DU_REPO>
cd ecommerce-backend
```

### 2. Configuration des variables d'environnement

Copiez le fichier `.env.example` vers `.env` et modifiez les valeurs selon votre environnement :

```bash
cp .env.example .env
```

Éditez le fichier `.env` pour configurer :

- Les paramètres de connexion à la base de données
- La clé secrète JWT et autres paramètres de sécurité

### 3. Configuration de la base de données

Deux options sont disponibles :

#### Option A : PostgreSQL local

1. Créez une base de données PostgreSQL nommée `ecommerce_db`
2. Vérifiez que les informations de connexion dans `.env` correspondent à votre installation PostgreSQL

#### Option B : PostgreSQL avec Docker

```bash
docker-compose up -d postgres
```

Cette commande démarre uniquement le service PostgreSQL défini dans `docker-compose.yml`.

### 4. Compilation du projet

```bash
./mvnw clean install -DskipTests
```

### 5. Exécution de l'application

#### Option A : Démarrage avec Maven

```bash
./mvnw spring-boot:run
```

#### Option B : Démarrage avec Docker Compose

### Compilez d'abord l'application pour générer le JAR

```bash
./mvnw clean package -DskipTests
```

```bash
docker-compose up -d
```

## Déploiement en production

Pour un déploiement en production, assurez-vous de :

1. Changer les valeurs dans `.env` pour l'environnement de production
2. Définir une clé JWT sécurisée (au moins 64 caractères)
3. Configurer correctement les paramètres CORS

```bash
# Démarrer en mode production
ENV=prod docker-compose up -d
```

## Accès à l'application

- Backend API : http://localhost:8085
- Documentation API : http://localhost:8085/swagger-ui/index.html
- PgAdmin (si activé dans docker-compose) : http://localhost:5050

## Résolution des problèmes courants

1. **Problèmes de connexion à la base de données**
    - Vérifiez que PostgreSQL est démarré
    - Vérifiez les informations de connexion dans `.env`
    - Assurez-vous que l'utilisateur a les permissions nécessaires

2. **Problèmes CORS**
    - Vérifiez que l'origine du frontend est bien listée dans la variable `ORIGINS`

## Gestion des migrations de base de données avec Liquibase

Le projet utilise Liquibase pour gérer les évolutions du schéma de la base de données de manière contrôlée et
versionnée.

### Création d'une nouvelle migration

### Fonctionnement des migrations

### Bonnes pratiques

## Ressources utiles

- [Documentation Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Documentation PostgreSQL](https://www.postgresql.org/docs/)
- [Documentation Liquibase](https://docs.liquibase.com/)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
