FROM openjdk:16

ADD build/libs/user-all.jar /data/user.jar

COPY ./startup.sh /data/startup.sh

CMD ["/bin/bash", "/data/startup.sh"]

HEALTHCHECK CMD wget --quiet --tries=1 --spider http://localhost:6060/status || exit 1

EXPOSE 6060
