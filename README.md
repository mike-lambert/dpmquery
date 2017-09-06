# **DPMQuery**
### What is it?
Literally, by it own name, it's a "Dark Places Master Query".
A modern microservice, written in Java, and providing easy RESTful interface to query master servers of FPS games such as OpenArena and Quake 3.
It built on top of Spring Boot framework and produce one fat JAR.

### Building
You required to have JDK 8.x. and Docker. After cloning project, go to its's directory in shell, and just enter 
```shell
./gradlew clean build buildDocker
```

After that you will have JAR in build/libs directory and cyberspacelabs/dpmquery image installed in local repository.

### Running
DPMQuery is available as Docker image. Visit my [hub](https://hub.docker.com/r/cyberspacelabs/dpmquery/) to pull it.
If you not using Docker, don't forget **applicaton.properties**, placed in root directory of project. It contains default config, feel free to experiment.
If you using docker, don't forget to map port 8080.
```shell
docker pull cyberspacelabs/dpmquery
docker run -itd -p 80:8080 cyberspacelabs/dpmquery
```

### Usage
Really, it well self-documented and supported Swagger (OpenAPI) - just don't be lazy to check main page and then just play with Swagger UI, embedded into.
