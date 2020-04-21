<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;

class LoginController extends AbstractController
{
	/**
	 * @Route("/", name="login")
	 * @param Request $request
	 * @param AuthenticationUtils $utils
	 * @return \Symfony\Component\HttpFoundation\Response
	 */
    public function login(Request $request, AuthenticationUtils $utils)
    {
    	$error = $utils->getLastAuthenticationError();

    	$lastusername = $utils->getLastUsername();

    	if($this->getUser() !== null){
    		return $this->redirect('/dashboard');
	    } else {
		    return $this->render('login/login.html.twig', [
			    'error' => $error,
			    'Lastusername' => $lastusername
		    ]);
	    }
    }

    /**
     * @Route("/logout", name="logout")
     */
    public function logout(){

    }
}
