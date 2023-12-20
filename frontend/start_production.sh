#!/bin/bash

echo "Running (node build) docker in detach mode"
docker run --tty --interactive --rm \
    --volume ${PWD}:/var/www \
    --workdir /var/www \
    --user $(id -u ${USER}):$(id -g ${USER}) \
    --publish 3000:3000 \
    --detach \
    --name "workInIt" \
    node:21 node build