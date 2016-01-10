docker build -t time-server .
docker run -it -p 8080:8080 --rm --name time-server  time-server