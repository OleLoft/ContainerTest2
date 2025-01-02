# Building as an image
Either use the Spring Boot-provided `mvnw` or your locally installed Maven `mvn` to run the `build-image` goal. In this descripton we have used `mvn`.
E.g.:
```
sudo mvn spring-boot:build-image -Dspring-boot.build-image.imageName=poc/divservice
```

# Running image with Docker
If you want to make the setup of having a division with an additional BFF,
you can do this with Docker by running the following (assuming backend is running on port 8082):
```
sudo docker run -e SERVER_PORT=8081 -e APP_IS_BFF="true" -e SERVICE_ENDPOINT=${BACKEND_IP_ADDRESS}:8082 -p 8081:8081 -t poc/division
sudo docker run -e SERVICE_ENDPOINT=${BFF_IP_ADDRESS}:8081 -p 8080:8080 -t poc/division
```

# Run on local K8s
To make the division/BFF setup on your local K8s (e.g. minikube)
run the following:
```
kubectl apply -f k8s
```
This will make a division and a BFF deployment along with matching
services. The BFF deployment expects a backend service to be in place
that it can send requests to, so make sure not to forget that.

# To run setup in docker
Remember to build the backend project as well. Find backend project in bitbucket and follow the project instructions.

First build the image of the division.
```
sudo mvn spring-boot:build-image -Dspring-boot.build-image.imageName=poc-image-division
```
Next, in a terminal either go the folder where the docker-compose.yml(Included in this project) file or run command with the path to the docker-compose file
```
sudo docker-compose up
```
or with path to file:
```
sudo docker compose -f /DockerSetup/docker-compose.yml up
```

Now it should be possible in a terminal to type
```
sudo docker ps
```
and get at list of the running containers.

To see it work it should be possible to use the following in a tool like postman or curl

In postman
```
localhost:8080/division?num1=200&num2=5
```
or in curl
```
curl -v "http://localhost:8080/division?num1=200&num2=5"
```

NB! If num2 is 0, then the system will get a division by zero error. This will trigger an error in all modules division, bff and backend.
Please note, it is a get operation.

