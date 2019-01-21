FROM jboss/keycloak:4.8.3.Final

ADD target/keycloak-amqp-jar-with-dependencies.jar /opt/jboss/keycloak/providers/
ADD keycloak-server.json /opt/jboss/keycloak/standalone/configuration/

USER root

RUN set -x ;\
    chown jboss:jboss /opt/jboss/keycloak/providers/synaptix-keycloak-jar-with-dependencies.jar ;\
    chown jboss:jboss /opt/jboss/keycloak/standalone/configuration/keycloak-server.json

USER jboss

ADD synaptix-realm.json /opt/jboss/

CMD ["-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "-Dkeycloak.import=/opt/jboss/synaptix-realm.json"]
