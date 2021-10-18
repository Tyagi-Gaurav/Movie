FROM openjdk:16

ADD build/libs/testOauth.jar /data/testOauth.jar

COPY ./startup.sh /data/startup.sh

CMD ["/bin/bash", "/data/startup.sh"]

HEALTHCHECK CMD wget --quiet --tries=1 --spider http://localhost:10080/status || exit 1

EXPOSE 10080
