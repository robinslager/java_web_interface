<?php

namespace App\Controller;

use App\Entity\Project;
use App\Service\DockerActions;
use App\Service\ProjectUploader;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ProjectsController extends AbstractController
{
    /**
     * @Route("/projects", name="projects")
     * @param EntityManagerInterface $entityManager
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function index(EntityManagerInterface $em)
    {
        $response = "";

        $projects = $em->getRepository(Project::class)->findby([
                "User" => $this->getUser()]
        );
        if (!$projects) {
            $response = "You do not own any projects yet";
        }
        return $this->render('projects/index.html.twig', [
            'controller_name' => 'ProjectsController',
            'Responce' => $response,
            'Projects' => $projects,
            'User' => $this->getUser(),
            'Error' => "",
        ]);
    }

    /**
     * @Route("/uploadproject", name="uploadproject")
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function uploadProject()
    {
        return $this->render('projects/uploadproject.html.twig', [
            'User' => $this->getUser(),
        ]);
    }

    /**
     * @Route("/saveupload", name="saveupload")
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function saveupload(EntityManagerInterface $em)
    {
        if (new ProjectUploader($_FILES)) {
            // create tar file
            $pd = new \PharData('/project/Webserver/uploads/tar/' . $_POST['ProjectName'] . '.tar');
            $dir = realpath("/project/Webserver/uploads/raw/" . $_POST['ProjectName']);
            $pd->buildFromDirectory($dir);
            unset($pd);

            // create project and container
            new DockerActions("new-project", $em, $this->getUser(), $_POST);

        }
        return $this->redirect('/projects');
    }

    /**
     * @Route("/project/{project_name}", name="Project")
     * @param $project_name
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function Project(EntityManagerInterface $em, $project_name)
    {

        $project = $em->getRepository(Project::class)->findOneBy(
            array(
                'ProjectName' => $project_name,
                'User' => $this->getUser()
            )
        );
        if ($project) {
            $dockerID = $project->getDockerID();
            $command = exec("curl --unix-socket /var/run/docker.sock http:/v1.30/containers/json");
            $docker = json_decode($command);
            $container = null;
            foreach ($docker as $dockercontainer) {
                if ($dockercontainer->Id === $dockerID) {
                    $container = $dockercontainer;
                }
            }

            return $this->render('projects/Project.html.twig', [
                'Container' => $container,
                'Project' => $project,
                'User' => $this->getUser(),
            ]);
        } else {
            $projects = $em->getRepository(Project::class)->findby([
                    "User" => $this->getUser()]
            );
            if (!$projects) {
                $response = "You do not own any projects yet";
            }
            return $this->render('projects/index.html.twig', [
                'controller_name' => 'ProjectsController',
                'Responce' => $response,
                'Projects' => $projects,
                'User' => $this->getUser(),
                'Error' => "Project does not exist",
            ]);
        }

    }

    /**
     * @Route("/project/{project_name}/{action}", name="Project_actions")
     * @param $project_name
     * @param $action
     *
     */
    public function projectActions(EntityManagerInterface $em, $project_name, $action)
    {
        // todo error handling
        $project = $em->getRepository(Project::class)->findOneBy(
            array(
                'ProjectName' => $project_name,
                'User' => $this->getUser()
            )
        );
        switch ($action) {
            case 'start-project':
            case 'unpause-project':
                if ($project->getDockerStatus() !== "running") {
                    new DockerActions(
                        $action,
                        $em,
                        $this->getUser(),
                        array(
                            'DockerID' => $project->getDockerID(),
                        )
                    );
                }
                break;
            case 'stop-project':
                if ($project->getDockerStatus() !== "Down") {
                    new DockerActions(
                        $action,
                        $em,
                        $this->getUser(),
                        array(
                            'DockerID' => $project->getDockerID(),
                        )
                    );
                }
                break;
            case 'pause-project':
                if ($project->getDockerStatus() !== "paused") {
                    new DockerActions(
                        $action,
                        $em,
                        $this->getUser(),
                        array(
                            'DockerID' => $project->getDockerID(),
                        )
                    );
                }
                break;
        }


    }

}
