<?php


namespace App\Exeption;


class MailExeption extends \Exception
{
    const EXEPTION_NONE = 0;
    const EXEPTION_INVAID_EMAIL = 1;
    const EXEPTION_INVALID_EMAILS = 2;
    const EXEPTION_CONTEXT_ISSET = 3;
    const EXEPTION_ = 4;

    public function __construct($type)
    {

    }

}