FROM openjdk:16

ADD build/libs/content-upload-DEV.jar /data/content-upload.jar

COPY ./startup.sh /data/startup.sh

CMD ["/bin/bash", "/data/startup.sh"]

#HEALTHCHECK CMD wget --quiet --tries=1 --spider http://localhost:9090/api/status || exit 1

EXPOSE 9090
EXPOSE 9900
