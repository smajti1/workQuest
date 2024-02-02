# WorkInIt

How to start

### Backend

Copy and edit `cp .env.example .env` with contain docker settings

Copy or create newrelic config file `newrelic/newrelic.yml` copy from `build/libs/newrelic` and set `license_key`, `app_name`

Project will be build when docker start `docker compose up --detach`

To attach to container use `./attach.sh` script (you may need to push enter to see `shell:>`) next you can use spring boot shell command:

- `runCronAll` to run all cron task
- `runCron JOB_PORTAL` to run only one (run `runCron` to list available options)

Backend run on http://localhost:8080

To see if docker run use `docker compose ps`, to see logs use `docker compose logs --follow`

### Frontend

Frontend files are placed in [frontend](./frontend) directory
and use node/npm from docker see `npm.sh` file

    cd frontend

To run project in development mode

    ./npm.sh install
    ./npm.sh run dev -- --host

Frontend just run on http://localhost:5173 to change default port change it in [npm.sh](./frontend/npm.sh)

To create a production version of app just run:

    ./npm.sh ci --omit dev
    ./npm.sh run build
    ./start_production.sh #it will run node server using docker in detached mode

Frontend just run on http://localhost:3000 to change default port change it
in [start_production.sh](./frontend/start_production.sh)
see more in https://kit.svelte.dev/docs/adapter-node

To check if server is running execute `docker ps` command and to stop just run `docker stop workInIt`

### Postgresql - db

To connect use command `docker compose exec postgres-db psql --password --username=$DB_USERNAME`
with data from `.env` file

### Backend links:

- for scraping web project use selenium https://www.selenium.dev
- newrelic logs https://newrelic.com

### Frontend links:

- frontend framework https://svelte.dev
- utility-first CSS framework https://tailwindcss.com
- UI toolkit for Svelte and Tailwind https://www.skeleton.dev
- SVG icons, by the makers of Tailwind CSS https://heroicons.com
