# WorkInIt

How to start

### Backend

Copy and edit `cp .env.example .env` with contain docker settings

Backend run on http://localhost:8080

To see if docker run `docker compose ps`

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