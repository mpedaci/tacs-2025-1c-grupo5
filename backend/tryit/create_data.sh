#!/bin/bash

echo "Creating data for API..."
echo "\n"

echo "Creating users"
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "phone": "123456789",
    "firstName": "John",
    "lastName": "Doe",
    "username": "johndoe",
    "password": "securepassword"
  }'
echo "\n"
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "michael@example.com",
    "phone": "987654321",
    "firstName": "Michael",
    "lastName": "Scott",
    "username": "michaelscott",
    "password": "securepassword"
  }'
echo "\n"


echo "Creating game"
curl -X POST http://localhost:8080/api/games \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Pokemon",
    "description": "Pokemon"
  }'
echo "\n"


echo "Creating cards"
curl -X POST http://localhost:8080/api/cards \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Pikachu",
    "description": "Electric type Pokemon",
    "gameId": 1
  }'
echo "\n"
curl -X POST http://localhost:8080/api/cards \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Charizard",
    "description": "Fire type Pokemon",
    "gameId": 1
  }'
echo "\n"


echo "Creating post"
curl -X POST http://localhost:8080/api/posts \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "images": ["https://example.com/card.jpg"],
    "conservationStatus": "PERFECT",
    "estimatedValue": 45.00,
    "cardId": 1,
    "description": "Looking to trade my Pikachu for a Charizard",
    "wantedCardsIds": [2]
  }'
echo "\n"


echo "Creating offer"
curl -X POST http://localhost:8080/api/offers \
  -H "Content-Type: application/json" \
  -d '{
    "postId": 1,
    "offererId": 2,
    "money": 25.00,
    "offeredCards": [
        {
        "cardId": 2,
        "conservationStatus": "BAD"
        }
    ]
  }'

echo "\n\n"
echo "Data created successfully!"