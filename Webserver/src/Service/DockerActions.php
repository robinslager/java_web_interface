<?php


namespace App\Service;


use App\Entity\Project;
use Doctrine\ORM\EntityManagerInterface;

class DockerActions
{
    private $params;
    private $em;
    private $user;

    /**
     * DockerActions constructor.
     * @param $action
     * @param EntityManagerInterface $em
     * @param $params
     */
    public function __construct($action, EntityManagerInterface $em, $user, $params )
    {
        $this->params = $params;
        $this->em = $em;
        $this->user = $user;

        switch ($action){
            case 'new-project':
                $this->newProject();
                break;
            case 'start-project':
                $dockerID = $this->params['DockerID'];
                $this->startProject(null, null, $dockerID);
                break;
            case 'stop-project':
                $dockerID = $this->params['DockerID'];
                $projectname = $this->params['Project_Name'];
                $this->stopProject(null, null, $dockerID);
                break;
        }
    }

    private function newProject(){
        // checks if project already exists with project name and user
        if($this->em->getRepository(Project::class)->findOneBy(array('ProjectName' => $_POST['ProjectName'], 'User' => $this->user)) === null) {
            $container = exec('curl --unix-socket /var/run/docker.sock -X POST http:/v1.30/containers/create -H "Content-Type: application/json" ' .
                '--data @../docker_Postrequest/createcontainer.json');
            $containerID = json_decode($container)->Id;
            $result = exec("curl --unix-socket /var/run/docker.sock -X POST http:/v1.30/containers/" . $containerID . "/start");

            $project = new Project();
            $project->setUser($this->user);
            $project->setProjectName($_POST['ProjectName']);
            $project->setDockerID($containerID);
            $project->setDockerStatus("Running");
            $this->em->persist($project);
            $this->em->flush();

            // this needs after flushing project otherwise the entity project for this project does not exists
            $this->copyProject($containerID);
        }
    }

    // this function is for copieng and eventually updating projects
    private function copyProject($dockerID){
    $execdownload = exec('curl --unix-socket /var/run/docker.sock -X POST http:/v1.30/containers/' . $dockerID . '/exec -H "Content-Type: application/json" --data @../docker_Postrequest/download.json');
    $execID = json_decode($execdownload)->Id;
    $this->startexec($execID);
    $execdownload = exec('curl --unix-socket /var/run/docker.sock -X POST http:/v1.30/containers/' . $dockerID . '/exec -H "Content-Type: application/json" --data @../docker_Postrequest/untar.json');
    $execID = json_decode($execdownload)->Id;
    $this->startexec($execID);
    $this->startProject(null, null, $dockerID);
    }

    private function startexec($execID){
        exec('curl --unix-socket /var/run/docker.sock -X POST http:/v1.30/exec/' . $execID .'/start -H "Content-Type: application/json" --data @../docker_Postrequest/start.json');
    }
    private function startdetachedexec($execID){
        exec('curl --unix-socket /var/run/docker.sock -X POST http:/v1.30/exec/' . $execID .'/start -H "Content-Type: application/json" --data @../docker_Postrequest/start_detatched.json');
    }

    public function startProject($projectname = null, $user = null,  $containerID = null){
        if($containerID === null){
            $project  = $this->em->getRepository(Project::class)->findOneBy([
                'ProjectName' => $projectname,
                'User' => $user
            ]);
        }
        $execdownload = exec('curl --unix-socket /var/run/docker.sock -X POST http:/v1.30/containers/' . $containerID . '/exec -H "Content-Type: application/json" --data @../docker_Postrequest/gradle_run.json');
        $execID = json_decode($execdownload)->Id;
        $this->startdetachedexec($execID);
    }

    public function stopProject($projectname = null, $user = null,  $containerID = null){
        if($containerID === null){
            $project  = $this->em->getRepository(Project::class)->findOneBy([
                'ProjectName' => $projectname,
                'User' => $user
            ]);
        }
        $execdownload = exec('curl --unix-socket /var/run/docker.sock -X POST http:/v1.30/containers/' . $containerID . '/exec -H "Content-Type: application/json" --data @../docker_Postrequest/gradle_stop.json');
        $execID = json_decode($execdownload)->Id;
        $this->startdetachedexec($execID);
    }
}