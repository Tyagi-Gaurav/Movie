FROM openjdk:16

ADD build/libs/ui.war /data/ui.war

COPY ./startup.sh /data/startup.sh

CMD ["/bin/bash", "/data/startup.sh"]

HEALTHCHECK CMD wget --quiet --tries=1 --spider http://localhost:8080/status || exit 1

EXPOSE 8080
EXPOSE 8900
