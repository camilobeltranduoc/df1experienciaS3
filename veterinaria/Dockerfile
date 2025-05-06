FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/veterinaria-*.jar app.jar
COPY wallet /opt/wallet                          
ENV  SPRING_PROFILES_ACTIVE=cloud \
     WALLET_PATH=/opt/wallet \
     JAVA_OPTS=""
EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
