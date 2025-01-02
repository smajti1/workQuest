# WorkQuest

How to start

#### Start server

This project use [caddy](https://caddyserver.com/docs) as a server and reverse proxy

You may need to create external docker network (if not exist)

    docker network create caddy

To work with domain use caddy reverse_proxy [caddy-docker-proxy](https://github.com/lucaslorentz/caddy-docker-proxy)
on development server should run on https://workQuest.localhost next:

### Backend

Copy and edit `cp .env.example .env` with contain docker settings

Copy or create newrelic config file `newrelic/newrelic.yml` copy from `build/libs/newrelic` and set `license_key`, `app_name`

Project will be build when docker start `docker compose up --detach`

To attach to container use `./attach.sh` to exit `Ctrl+P, Ctrl+Q` (you may need to push enter to see `shell:>`) next you can use spring boot shell command:

- `runCronAll` to run all cron task
- `runCron JOB_PORTAL` to run only one (run `runCron` to list available options)

Backend run on http://localhost:8080

To see if docker run use `docker compose ps`, to see logs use `docker compose logs --follow`

### Frontend
Add `.env` in `frontend` directory with `PUBLIC_BASE_URL=https://workquest.localhost`

Frontend files are placed in [frontend](./frontend) directory
and use node/npm from docker see `npm.sh` file

    cd frontend

To run project in development mode

    ./npm.sh install
    ./npm.sh run dev -- --host

Frontend just run on http://localhost:5173 to change default port change it in [npm.sh](./frontend/npm.sh)

To create a production version of app just run:

    ./start_production.sh #it will run node server using docker in detached mode

Frontend just run on http://localhost:3000 to change default port change it
in [start_production.sh](./frontend/start_production.sh)
see more in https://kit.svelte.dev/docs/adapter-node

To check if server is running execute `docker ps` command and to stop just run `docker stop workQuest`

### Backend links:

- for scraping web project use selenium https://www.selenium.dev
- newrelic logs https://newrelic.com

### Frontend links:

- frontend framework https://svelte.dev
- utility-first CSS framework https://tailwindcss.com
- UI toolkit for Svelte and Tailwind https://www.skeleton.dev
- SVG icons, by the makers of Tailwind CSS https://heroicons.com

### Docs:
- [postgres](docs/postgres.md)