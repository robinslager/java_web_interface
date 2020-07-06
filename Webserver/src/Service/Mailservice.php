<?php


namespace App\Service;


use Symfony\Component\Mime\Email;

class Mailservice
{

    private $mail;

    private $personadded = false;

    /**
     * Mailservice constructor.
     * sets email.
     * sets from Email
     */
    public function __construct($fromMailadrress)
    {
        $this->mail = new Email();
        $this->mail->from($fromMailadrress);
}

    /**
     * @param $email
     * @return bool
     * validates every mailadres and returns a boolean if its valid or not
     */
    private function MailadressValidator($email){
        // checks if @exists
        if(strpos($email, "@") !== false){
            // default php email validator
            if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * this wil send the email to a single person with the specified email.
     * note: if you want to send this email to multiple people use the addpersons() function
     * @param $emaiL
     */
    public function addperson($email){
        // checks if person is already added otherwise this can produce a error this if is to prevent this.
        if($this->personadded === false) {
            if ($this->MailadressValidator($email)) {
                $this->mail->to($email);
                $this->personadded = true;
            }
        }
        //todo return error if not
    }

    /**
     * this will send the email to all specified emails if all the given emails are valid
     * it will return a list of all adresses that were send a mail to
     */
    public function addpersons(array $emails){
        // checks if person is already added otherwise this can produce a error this if is to prevent this.
        if($this->personadded === false) {
            $i = 0;
            foreach ($emails as $email) {
                if ($i === 0) {
                    if ($this->MailadressValidator($email)) {
                        $this->mail->to($email);
                    }
                }
                if ($this->MailadressValidator($email)) {
                    $this->mail->addTo($email);
                }
                $i++;
            }
            $this->personadded = true;
        }
        //todo return array of wrong emails
    }

    /**
     * @param $adress
     * add a CC to the email that will be send
     * note: if you want to add multiple cc`s use the function addMultipleCC()
     * returns error if mail is not valid
     */
    public function addCC($adress){
        // todo check if cc is already set
        if($this->MailadressValidator($adress)){
            $this->mail->cc($adress);
        }
        //todo return Error if fails
    }

    /**
     * @param array $adresses
     * will add multiple cc`s to this given email.
     * note: this function will only work if you have more than 1 adrress
     * this wil return a list of adresses that are in the cc list.
     * @return string
     */
    public function addMulitpleCC(array $adresses){
        if(count($adresses) === 1) {
            return "use AddCC instead of add multiple CC for this to work";
        }
        $i = 0;
        foreach ($adresses as $adress){
            if($i === 0){
                if($this->MailadressValidator($adress)) {
                    $this->mail->addCc($adress);
                }
            }
            if($this->MailadressValidator($adress)) {
                $this->mail->addBcc($adress);
            }
            $i++;
        }
        //todo return array of wrong emails
    }

    /**
 * gives a subject to this email. first it will check if subject is already set.
 * if subject is set it will return a error. else it will set the subject after stripping it from all html tags
 * @param $subject
 * @return string
 */
    public function setSubject($subject)
    {
        if($this->mail->getSubject() === null){
            $this->mail->subject(strip_tags($subject));
        } else {
            return "Subject is already set";
        }
    }

    /**
     * sets body of the mail. this function will try and remove all script reletad charackters--
     * @param string $text
     * @return string
     */
    public function setMailContext(string $text)
    {
        // text is not set if null
        if($this->mail->text() === null){
            $body=str_replace(array("&", "<", ">"), array("&amp;", "&lt;", "&gt;"), $text);
            $this->mail->text($body);
        } else {
            //todo custom throw error
            return "";
        }
    }

    /**
     * set content of of mail with html by string
     * will filter all script tags
     * 
     * @param string $html
     */
    public function setmailHTMLcontent(string $htmlString)
    {
        // text is not set if null
        if($this->mail->text() === null) {
            $body = str_replace(array('<script src=', '<script', 'script='), array("script tag with src", "script tag", "inline scriping"), $htmlString);
        }

    }

    /**
     * this function will send the email
     * checks for all components
     * it will first check on all components
     * if all components are set then it will send
     * if component is not set throw error
     */
    public function sendMail()
    {

    }
}