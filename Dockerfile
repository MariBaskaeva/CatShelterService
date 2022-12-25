FROM openjdk:17-alpine3.14
EXPOSE 8081
ADD target/CatShelterService-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "CatShelterService-0.0.1-SNAPSHOT.jar"]