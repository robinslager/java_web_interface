<?php

namespace App\Controller;

use App\Entity\User;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
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

        if ($this->getUser() !== null) {
            return $this->redirect('/dashboard');
        } else {
            return $this->render('login/login.html.twig', [
                'error' => $error,
                'Lastusername' => $lastusername,
                'Request' => $request,
            ]);
        }
    }

    /**
     * @Route("/logout", name="logout")
     */
    public function logout()
    {

    }

    /**
     * @Route("/signup", name="signup")
     */
    public function signup(Request $request, EntityManagerInterface $em, UserPasswordEncoderInterface $encoder)
    {
        if ($_POST['Username'] !== null || $_POST['password'] !== null || $_POST['email'] !== null) {
            $username = $_POST['Username'];
            $password = $_POST['password'];
            $email = $_POST['email'];

            $responce = "";

            if ($em->getRepository(User::class)->findOneBy(
                    array(
                        'username' => $username,
                    )
                ) === null) {
                if ($em->getRepository(User::class)->findOneBy(
                        array(
                            'email' => $email,
                        )
                    ) === null) {
                    // user does not exists yet
                    $user = new User();
                    $user->setUsername($username);
                    $user->setPassword($encoder->encodePassword($user, $password));
                    $user->setEmail($email);
                    $em->persist($user);
                    $em->flush();

                    return $this->redirect('/');
                } else {
                    $responce = "email already exists";
                }
            } else {
                $responce = "username already exists";
            }
        }
        return $this->render('login/signup.html.twig', [
            'Request' => $request,
            'Responce' => $responce
        ]);
    }
}
