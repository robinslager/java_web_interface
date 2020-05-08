<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

class DockerdashboardController extends AbstractController
{
    /**
     * @Route("/docker", name="docker")
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function docker()
    {
    	$command = exec("curl --unix-socket /var/run/docker.sock http:/v1.30/containers/json");
    	$docker = json_decode($command);
        return $this->render('dockerdashboard/dockerdashboard.html.twig', [
			'dockercontainers' => $docker,
        ]);
    }

	/**
	 * @Route("/docker/{containerID}", name="dockerContainer")
	 * @param $containerID
	 * @return \Symfony\Component\HttpFoundation\Response
	 */
	public function dockercontainer($containerID)
	{
		$command = exec("curl --unix-socket /var/run/docker.sock http:/v1.30/containers/json");
		$docker = json_decode($command);
		$container = null;
		foreach ($docker as $dockercontainer){
				if ($dockercontainer->Id === $containerID) {
					$container = $dockercontainer;
				}
		}
		return $this->render('dockerdashboard/dockerContainer.html.twig', [
			'Container' => $container,
		]);
	}

	/**
	 * @Route("/docker/{action}/{container}", name="DockerActions")
	 * @param $action
	 * @param $containerimage
	 * @return void
	 */
	public function dockeractions($action, $containerimage)
	{
		// get all containers
		$command = exec("curl --unix-socket /var/run/docker.sock http:/v1.30/containers/json");
		$docker = json_decode($command);

		$baseCommand = "curl --unix-socket /var/run/docker.sock -X POST http:/v1.30/containers/";
		if($action === "pause" || $action === "unpause" || $action === "stop" || $action === "start" || $action === "restart") {
			$Command = $baseCommand + "/" + $container + "/" + $action;
			$result = exec($command);
			$container = null;
			foreach ($docker as $dockercontainer) {
				if ($dockercontainer->image === $containerimage) {
					$container = $dockercontainer;
				}
			}
		}
	}
}
