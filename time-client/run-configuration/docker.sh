docker build -t time-client .
docker run -it --rm --link time-server time-client