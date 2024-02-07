#!/bin/bash

export $(xargs < "../.env")

echo "Running (node build) docker in detach mode"
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