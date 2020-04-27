FROM gradle:4.3-jdk
USER root
ADD --chown=gradle . /Java/workspace
SHELL ["/bin/bash", "-c"]
RUN chmod a+rwX /Java
RUN chmod a+rwX /java/*



CMD tail -f /dev/null


