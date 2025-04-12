# TACS Project

## Prerequisites

- **Docker** or **Docker Desktop** installed

## Project Setup

#### 1. Build the app

Place the provided properties file in the `/src/main/resources`. It should be named `application-dev.properties`.

Run the following command to create the development environment using docker:

```bash
docker build -t tacs-app .
```

#### 2. Run the app

To run the image previously created on port 8080, run the following command:

```bash
docker run --name tacs-app -p 8080:8080 tacs-app
```

## Documentation

To access api documentation visit the `/api-docs-ui` url or click in this [link](http://localhost:8080/api-docs-ui)
