FROM openjdk:11
EXPOSE 8081
ADD target/springboot-security-readingisgood-docker-jenkins-integration.jar springboot-security-readingisgood-docker-jenkins-integration.jar
ENTRYPOINT ["java", "-jar", "/springboot-security-readingisgood-docker-jenkins-integration.jar"]