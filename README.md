# java_web_interface
a webinterface for java projects using docker
this is a webinterface for java projects but i want to increase to this to multiple to more languages
this is a project where you can test your project safely and in a professional environment. 

# end goals
this project is meant to improve my skills in docker and symfony that will always stay as motivation to this project.
but i do want something with this project. i want this project to not only support java projects but more languages.
i want to make it that it is easy for me to import new languages docker files but also for users to import there custom docker files to work on their project.
i want a community that can help each other to make docker more accessible for everyone. this should be a way to test there work on a professional setup.

# on first startup
there are some things that are not set when you clone the project that are needed to be checked.
first:
* check if you have the custom image build. if you dont have the custom image buildt than the prodject will not work end will not create a new container for your project.
 if not use ```docker build /docker/java -t gradlejavacontainer:1.1```

# tips for docker
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





