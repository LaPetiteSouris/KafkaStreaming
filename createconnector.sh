#!/bin/bash

curl -X POST -H “Accept:application/json” -H “Content-Type:application/json” localhost:8083/connectors/ -d ‘
{
 “name”: “inventory-connector”,
 “config”: {
 “connector.class”: “io.debezium.connector.postgresql.PostgresConnector”,
 “tasks.max”: “1”,
 “database.hostname”: “postgres”,
 “database.port”: “5432”,
 “database.user”: “postgres”,
 “database.password”: “postgres”,
 “database.dbname” : “inventory”,
 “database.server.name”: “dbserver1”,
 “database.whitelist”: “inventory”,
 “database.history.kafka.bootstrap.servers”: “kafka:9092”,
 “database.history.kafka.topic”: “schema-changes.inventory”
 }
}’