<?php

namespace App\Controller;

use App\Entity\User;
use App\Service\Mailservice;
use Doctrine\ORM\EntityManagerInterface;
use Doctrine\ORM\Mapping\Entity;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
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
            'User' => $this->getUser(),
        ]);
    }

    /**
     * @Route("/test-casus/js-worker", name="js-worker")
     */
    public function jsworker()
    {
        // todo make page with all tests
        return $this->render('test_casus/jsworker.html.twig', [
            'User' => $this->getUser(),
        ]);
    }

    /**
     * @Route("/test-casus/js-worker/getdata", name="js-getdata")
     * @return JsonResponse
     */
    public function getdata(EntityManagerInterface $em)
    {
        $user = $em->getRepository(User::class)->findOneBy(array('username' => $this->getUser()->getUsername()));
        $response = new Response(json_encode(array('name' => $user->getEmail())));
        $response->headers->set('Content-Type', 'application/json');
        return $response;
    }
}
