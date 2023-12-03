#!/bin/bash

docker-compose down
docker stop $(docker ps -qa)
docker container prune --force && docker volume prune --force && docker network prune --force
docker-compose up