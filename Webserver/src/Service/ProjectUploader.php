<?php


namespace App\Service;

class ProjectUploader
{
    public function __construct($uploads,$uploadDir='/project/Webserver/uploads/raw/'){

        // Split the string containing the list of file paths into an array
        $paths = explode("###",rtrim($_POST['paths'],"###"));

        // Loop through files sent
        foreach($uploads as $key => $current)
        {
            // Stores full destination path of file on server
            $this->uploadFile=$uploadDir.rtrim($paths[$key],"/.");
            // Stores containing folder path to check if dir later
            $this->folder = substr($this->uploadFile,0,strrpos($this->uploadFile,"/"));

            // Check whether the current entity is an actual file or a folder (With a . for a name)
            if(strlen($current['name'])!=1)
                // Upload current file
                if($this->upload($current,$this->uploadFile))
                    echo "The file ".$paths[$key]." has been uploadedn";
                else
                    echo "Error";
        }
    }

    private function upload($current,$uploadFile){
        // Checks whether the current file's containing folder exists, if not, it will create it.
        if(!is_dir($this->folder)){
            mkdir($this->folder,0700,true);
        }
        // Moves current file to upload destination
        if(move_uploaded_file($current['tmp_name'],$uploadFile))
            return true;
        else
            return false;
    }
}
