#!/bin/bash

echo "Running: node $@"
docker run --tty --interactive --rm \
    --volume ${PWD}:/var/www \
    --workdir /var/www \
    --user $(id -u ${USER}):$(id -g ${USER}) \
    --publish 5173:5173 \
    node:21 "$@"