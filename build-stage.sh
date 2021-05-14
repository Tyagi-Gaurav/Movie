#!/bin/bash

./gradlew clean check build && docker-compose up -d --build

