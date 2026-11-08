#!/bin/bash

echo "Running: npm $@"
docker run --tty --interactive --rm \
    --volume ${PWD}:/var/www \
    --workdir /var/www \
    --env npm_config_cache=/tmp/.npm \
    --user $(id -u ${USER}):$(id -g ${USER}) \
    --publish 5173:5173 \
    node:26 npm "$@"