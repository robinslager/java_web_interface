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
            'User' => $this->getUser(),
            'Error' => ""
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
		if($container !== null) {
            return $this->render('dockerdashboard/dockerContainer.html.twig', [
                'Container' => $container,
                'User' => $this->getUser(),

            ]);
        } else {
            return $this->render('dockerdashboard/dockerdashboard.html.twig', [
                'dockercontainers' => $docker,
                'User' => $this->getUser(),
                'Error' => "Container Does not Exists"
            ]);
        }
	}

}
