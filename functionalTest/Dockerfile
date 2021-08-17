FROM ubuntu:latest

ARG  GRADLE_VERSION=7.0.2
SHELL ["/bin/bash", "-c"]
VOLUME /data
RUN apt-get update -y && \
    apt-get -y install unzip zip curl wget && \
    curl -s "https://get.sdkman.io" | bash && \
    source "$HOME/.sdkman/bin/sdkman-init.sh" && \
    sdk install java 16.0.1.hs-adpt && \
    wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp && \
    unzip -d /opt /tmp/gradle-${GRADLE_VERSION}-bin.zip

ENV JAVA_HOME /root/.sdkman/candidates/java/current
ADD . /data
RUN export JAVA_HOME && chmod +x /data/runFunctionalTest.sh
WORKDIR /data

CMD ["/data/runFunctionalTest.sh"]
