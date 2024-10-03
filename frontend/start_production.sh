#!/bin/bash

echo "[1/4] Running ./npm.sh ci --omit dev"
./npm.sh ci --omit dev

echo "[2/4] Running ./npm.sh run build"
./npm.sh run build

echo "[3/4] Running stop_production.sh"
./stop_production.sh

export $(grep -v '^#' ../.env | xargs)

echo "[4/4] Running (node build) docker in detach mode"
docker run --tty --interactive \
    --volume ${PWD}:/var/www \
    --workdir /var/www \
    --user $(id -u ${USER}):$(id -g ${USER}) \
    --publish 3000:3000 \
    --detach \
    --name "workQuest_frontend" \
    --restart ${DOCKER_RESTART} \
    --network caddy \
    --label caddy=${APP_URL} \
    --label caddy.reverse_proxy="{{upstreams 3000}}" \
    node:21 node build