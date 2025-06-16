# Tecnologías Avanzadas en la Construcción de Software

## TP - Grupo 5

### **Backend**: Java (Spring Boot)

### **Frontend**: Typescript (Next.js, React, MaterialUI)

## Participants

- Molina, Francisco
- Pedaci, Marcos
- Romano, Santiago
- Tamborini, Agustín
- Vazquez, Juan Martin
- Villalba, Emmanuel Marcos

## Prerequisites

- **Docker** or **Docker Desktop** installed
- **Docker Compose** installed
- .env file provided via email

## Project Setup

#### 1. Provide .env file

Insert .env file in the root directory of the project, or know where it's located in your machine.

The content of the .env file should look like this:

```bash
SPRING_PROFILES_ACTIVE=dev
SPRING_DATA_MONGODB_URI=mongodb://mongo:27017/tacsdb
YUGIOH_API_URL=https://db.ygoprodeck.com/api/v7/
MAGIC_API_URL=https://api.magicthegathering.io/v1/
POKEMON_API_URL=https://api.pokemontcg.io/v2/
FRONTEND_URL=http://localhost:3000
BACKEND_URL=http://localhost:8080
BACKEND_URL_LOCAL=http://backend:8080
TELEGRAM_BOT_NAME=CardsTradingbot
TELEGRAM_BOT_TOKEN=CardsTradingbotToken
TELEGRAM_CREATOR_ID=1234567890
JWT_SECRET=wOzW8kFODQPht6SHvmjhg67dYutx3U+PEfV3izXlRnc=
```

---

#### 2. Build the app

Run the following command to create the development environment using docker:

```bash
docker compose --env-file ./.env up --build
```

Or this one if the .env file is not located at the root directory of the project (change the path to env file):

```bash
docker compose --env-file ./path/to/env/file/.env up --build
```

---

#### 3. Run the app

Run the following command to start the app:

```bash
docker-compose up
```

---

#### 4. Access the app

The backend will be available at `http://localhost:8080`\
The frontend will be available at `http://localhost:3000`

---

#### 5. Admin access

To create an admin user, run the following command:

```bash
curl 'http://localhost:8080/api/users' -H 'Content-Type: application/json' --data-raw $'{"name": "admin", "username": "admin", "password": "admin", "admin": true}'
```

## Documentation

To access api documentation visit the `/api/docs` url or click in this [link](http://localhost:8080/api/docs)
