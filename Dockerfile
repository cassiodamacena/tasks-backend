FROM tomcat:8.5.50-jdk8-openjdk

ARG WAR_FILE
ARG CONTEXT

USER root

COPY ${WAR_FILE} /usr/local/tomcat/webapps/${CONTEXT}.war