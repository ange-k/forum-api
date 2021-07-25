FROM openjdk:15-jdk-alpine
RUN apk update && apk add curl openssl
COPY . .

ARG AKS_USERNAME
ARG AKS_PASSWORD
ARG STORE_PASS

ENV AKS_USERNAME ${AKS_USERNAME}
ENV AKS_PASSWORD ${AKS_PASSWORD}
ENV STORE_PASS ${STORE_PASS}
ENV SPRING_PROFILES_ACTIVE prod

RUN curl https://certs.secureserver.net/repository/sf-class2-root.crt -O && \
openssl x509 -outform der -in sf-class2-root.crt -out temp_file.der && \
keytool -import -alias cassandra -keystore cassandra_truststore.jks -file temp_file.der -storepass ${STORE_PASS} -trustcacerts -noprompt

RUN mv ./cassandra_truststore.jks ./src/main/resources
RUN ./gradlew openApiGenerate && ./gradlew build -x test

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY ./build/libs/gameforum-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar","-Djavax.net.ssl.trustStore=src/main/resources/cassandra_truststore.jks", "-Djavax.net.ssl.trustStorePassword=${STORE_PASS}"]