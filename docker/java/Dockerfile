FROM gradle:4.3-jdk
ADD . /java
ADD --chown=gradle . /java
WORKDIR /java
CMD ["gradle", "--stacktrace", "run"]

