# java_web_interface
a webinterface for java projects using docker

to start project type in:  
**docker-compose run -e TERM --service-ports server**

to see all containers docker:  
**docker container ls**

console log:  
CONTAINER ID  ||   IMAGE                   ||     COMMAND   ||        CREATED    ||   STATUS  ||  PORTS ||  NAMES  
ac19853c765f  ||  gradlejavacontainer:1.1  || "/bin/bash -c 'tail …" ||  27 seconds ago   ||   Up 27 seconds  || **clever_noether**(projectname)  

to kill open java containers:  
**docker kill (project_name)**

to close project:  
**docker-compose down**





