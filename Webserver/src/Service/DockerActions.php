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

                break;
            case 'stop-project':

                break;
        }
    }

    private function newProject(){
        $container = exec('curl --unix-socket /var/run/docker.sock -X POST http:/v1.30/containers/create -H "Content-Type: application/json" '.
        '--data @../docker_Postrequest/createcontainer.json');
        $containerID = json_decode($container)->Id;
        $result = exec("curl --unix-socket /var/run/docker.sock -X POST http:/v1.30/containers/" . $containerID . "/start");


        $project = new Project();
        $project->setUserID($this->user->getID());
        $project->setProjectName($_POST['ProjectName']);
        $project->setDockerID($containerID);
        $this->em->persist($project);
        $this->em->flush();

        // this needs after flushing project otherwise the entity project for this project does not exists
        $this->copyProject($containerID);
    }

    // this function is for copieng and eventually updating projects
    private function copyProject($dockerID){
    $execdownload = exec('curl --unix-socket /var/run/docker.sock -X POST http:/v1.30/containers/' . $dockerID . '/exec -H "Content-Type: application/json" --data @../docker_Postrequest/download.json');
    $execID = json_decode($execdownload)->Id;
    $this->startexec($execID);
    $execdownload = exec('curl --unix-socket /var/run/docker.sock -X POST http:/v1.30/containers/' . $dockerID . '/exec -H "Content-Type: application/json" --data @../docker_Postrequest/untar.json');
    $execID = json_decode($execdownload)->Id;
    $this->startexec($execID);
    $execdownload = exec('curl --unix-socket /var/run/docker.sock -X POST http:/v1.30/containers/' . $dockerID . '/exec -H "Content-Type: application/json" --data @../docker_Postrequest/gradle_run.json');
    $execID = json_decode($execdownload)->Id;
    $this->startdetachedexec($execID);
    }

    public function startProject($projectname, $containerID = null){
        if($containerID === null){
            $this->em->getRepository(Project::class)->findOneBy([
                'ProjectName' => $projectname
            ]);
        }

    }

    private function startexec($execID){
        exec('curl --unix-socket /var/run/docker.sock -X POST http:/v1.30/exec/' . $execID .'/start -H "Content-Type: application/json" --data @../docker_Postrequest/start.json');
    }
    private function startdetachedexec($execID){
        exec('curl --unix-socket /var/run/docker.sock -X POST http:/v1.30/exec/' . $execID .'/start -H "Content-Type: application/json" --data @../docker_Postrequest/start_detatched.json');
    }
}