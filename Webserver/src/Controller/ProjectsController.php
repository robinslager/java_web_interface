<?php

namespace App\Controller;

use App\Entity\Project;
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
                "UserID" => $this->getUser()->getId()]
        );
        if (!$projects) {
            $response = "You do not own any projects yet";
        }
        return $this->render('projects/index.html.twig', [
            'controller_name' => 'ProjectsController',
            'Responce' => $response,
            'Projects' => $projects
        ]);
    }

    /**
     * @Route("/uploadproject", name="uploadproject")
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function uploadProject()
    {
        return $this->render('projects/uploadproject.html.twig', []);
    }

    /**
     * @Route("/saveupload", name="saveupload")
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function saveupload(EntityManagerInterface $em)
    {
        if(new ProjectUploader($_FILES)){
            $project = new Project();
            $project->setUserID($this->getUser()->getID());
            $project->setProjectName($_POST['ProjectName']);
            $em->persist($project);
            $em->flush();

            // create tar file
            $pd = new \PharData('/project/Webserver/uploads/tar/' . $_POST['ProjectName'] . '.tar');
            $dir = realpath("/project/Webserver/uploads/raw/" . $_POST['ProjectName']);
            $pd->buildFromDirectory($dir);
            unset( $pd );
        }
    return $this->redirect('/projects');
    }

}
