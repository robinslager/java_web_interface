<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Routing\Annotation\Route;

class TestCasusController extends AbstractController
{
    /**
     * @Route("/test-casus", name="test_casus")
     */
    public function tests()
    {
        // todo make page with all tests
        return $this->render('test_casus/index.html.twig', [
            'controller_name' => 'TestCasusController',
        ]);
    }
}
