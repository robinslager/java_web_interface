FROM gradle:4.3-jdk
USER root
ADD --chown=gradle . /java/Running
SHELL ["/bin/bash", "-c"]
RUN chmod a+rwX /java
RUN chmod a+rwX /java/*
CMD chmod +X Scripts/MoveProject.sh
CMD chmod +X Start.sh
CMD sed -i "s/\r//" Start.sh


CMD tail -f /dev/null


