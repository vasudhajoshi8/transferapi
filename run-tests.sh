#!/bin/bash

# Build the application
mvn clean package

# Start Docker containers
docker-compose up -d --build

# Wait for the services to be ready
sleep 20

# Create accounts
curl -X POST -H "Content-Type: application/json" -d '{"owner":"Alice","balance":1000}' http://localhost:8080/api/accounts
curl -X POST -H "Content-Type: application/json" -d '{"owner":"Bob","balance":500}' http://localhost:8080/api/accounts

# Perform a transfer
curl -X POST -H "Content-Type: application/json" -d '{"fromAccountId":1,"toAccountId":2,"amount":200}' http://localhost:8080/api/transfer

# Check balances
curl -X GET http://localhost:8080/api/accounts/1
curl -X GET http://localhost:8080/api/accounts/2

# Stop Docker containers
docker-compose down
