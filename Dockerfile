FROM openjdk:15-jdk-alpine
RUN apk update && apk add curl openssl
COPY . .

ARG AKS_USERNAME
ARG AKS_PASSWORD
ARG STORE_PASS
ARG AUTH_APIKEY
ARG AWS_S3_ID
ARG AWS_S3_KEY

ENV AKS_USERNAME ${AKS_USERNAME}
ENV AKS_PASSWORD ${AKS_PASSWORD}
ENV STORE_PASS ${STORE_PASS}
ENV SPRING_PROFILES_ACTIVE prod
ENV AUTH_APIKEY ${AUTH_APIKEY}
ENV AWS_S3_ID ${AWS_S3_ID}
ENV AWS_S3_KEY ${AWS_S3_KEY}

RUN curl https://certs.secureserver.net/repository/sf-class2-root.crt -O && \
openssl x509 -outform der -in sf-class2-root.crt -out temp_file.der && \
keytool -import -alias cassandra -keystore cassandra_truststore.jks -file temp_file.der -storepass ${STORE_PASS} -trustcacerts -noprompt

RUN mv ./cassandra_truststore.jks ./src/main/resources
RUN ./gradlew openApiGenerate && ./gradlew build -x test

# entrypointでは変数展開されない
ENTRYPOINT ["/bin/sh", "-c", "java -jar build/libs/gameforum-0.0.1.jar -Djavax.net.ssl.trustStoreType=jks -Djavax.net.ssl.trustStore=src/main/resources/cassandra_truststore.jks -Djavax.net.ssl.trustStorePassword=${STORE_PASS} -Daws.accessKeyId=${AWS_S3_ID} -Daws.secretAccessKey=${AWS_S3_KEY}"]
