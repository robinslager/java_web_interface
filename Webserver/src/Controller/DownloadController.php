<?php


namespace App\Controller;



use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\BinaryFileResponse;
use Symfony\Component\HttpFoundation\ResponseHeaderBag;
use Symfony\Component\Routing\Annotation\Route;

class DownloadController extends AbstractController
{

    /**
     * @Route("/download/{project_name}", name="dowload_project")
     * @param Request $request
     * @param $project_name
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function downloadProject($project_name){
        $response = new BinaryFileResponse('/project/Webserver/uploads/tar/' . $project_name. ".tar");
        $response->setContentDisposition(ResponseHeaderBag::DISPOSITION_ATTACHMENT,'project.tar');
        return $response;
    }
}