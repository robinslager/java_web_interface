<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

class DashboardController extends AbstractController
{
    /**
     * @Route("/dashboard", name="dashboard")
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function Dashboard(Request $request)
    {
        return $this->render('dashboard/dashboard.html.twig', [
	        'User' => $this->getUser(),
            'Request' => $request,
        ]);
    }
}
