#!/bin/sh

BLUECOLOR='\033[0;34;47m'
NOCOLOR='\033[0m' # No Color

DOCKER_IMAGE=my-jenkins-image
DOCKER_CONTAINER=myJenkins

command="docker ps -a -f name=$DOCKER_CONTAINER -q"
echo $command
dockerContainers=$(eval $command)
echo $dockerContainers
for f in $dockerContainers;
do
    echo "Removing docker container: $f"
    docker rm -f $f;
done

echo -e "\e[0;34;47m--- Deleting docker containers: $1 ...DONE\e[0m"

command="docker images *$DOCKER_IMAGE* -q "
dockerImages=$(eval $command)
for f in $dockerImages;
do
    echo "Removing docker image: $f"
    docker rmi -f $f;
done

echo -e "\e[0;34;47m--- Deleting docker images: $1 ...Done\e[0m" 

docker build -f Dockerfile -t $DOCKER_IMAGE .

echo -e "\e[0;34;47m--- Creating docker images: $1 ...Done\e[0m" 
docker run -d -p 8081:8080 --name $DOCKER_CONTAINER $DOCKER_IMAGE