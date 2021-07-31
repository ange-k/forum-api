FROM openjdk:15-jdk-alpine
RUN apk update && apk add curl openssl
COPY . .

ARG AKS_USERNAME
ARG AKS_PASSWORD
ARG STORE_PASS

ENV AKS_USERNAME ${AKS_USERNAME:-forum-at-991180925719}
ENV AKS_PASSWORD ${AKS_PASSWORD:-I2djDu8QGc05sHH8u3uw8tDg21h5ataIg7s9afDShrM=}
ENV STORE_PASS ${STORE_PASS:-alphaforomega}
ENV SPRING_PROFILES_ACTIVE prod

RUN curl https://certs.secureserver.net/repository/sf-class2-root.crt -O && \
openssl x509 -outform der -in sf-class2-root.crt -out temp_file.der && \
keytool -import -alias cassandra -keystore cassandra_truststore.jks -file temp_file.der -storepass ${STORE_PASS} -trustcacerts -noprompt

RUN mv ./cassandra_truststore.jks ./src/main/resources
RUN ./gradlew openApiGenerate && ./gradlew build -x test

# entrypointでは変数展開されない
ENTRYPOINT ["/bin/sh", "-c", "java -jar build/libs/gameforum-0.0.1.jar -Djavax.net.ssl.trustStoreType=jks -Djavax.net.ssl.trustStore=src/main/resources/cassandra_truststore.jks -Djavax.net.ssl.trustStorePassword=${STORE_PASS}"]
