FROM gradle:4.3-jdk
USER root
ADD --chown=gradle . /Java
SHELL ["/bin/bash", "-c"]
RUN chmod a+rwX /Java



CMD tail -f /dev/null


