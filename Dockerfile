FROM openjdk:17-jdk-alpine
COPY target/car_reservation_api-0.0.1-SNAPSHOT.jar car_reservation.jar
ENTRYPOINT [ "java", "-jar", "car_reservation.jar"]
