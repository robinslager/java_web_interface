<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Routing\Annotation\Route;

class DashboardController extends AbstractController
{
    /**
     * @Route("/dashboard", name="dashboard")
     */
    public function Dashboard()
    {
        return $this->render('dashboard/index.html.twig', [
	        'User' => $this->getUser(),
        ]);
    }
}
