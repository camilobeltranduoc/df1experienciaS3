# Imagen de runtime: JRE 21 ligero
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos el JAR que genera Maven en target/
COPY target/envios-*.jar app.jar

# Wallet de Oracle: copia la carpeta completa (opcional ‑ puedes montarla como volumen)
COPY wallet /opt/wallet

# Variables de entorno
ENV SPRING_PROFILES_ACTIVE=cloud \
    WALLET_PATH=/opt/wallet \
    JAVA_OPTS=""

EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
