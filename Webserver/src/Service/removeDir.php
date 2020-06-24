<?php


namespace App\Service;

use Symfony\Component\Finder\Finder;

class removeDir
{

    /**
     * removeDir constructor.
     */
    public function __construct($dirname)
    {
        $this->rrmdir($dirname);
    }


    function rrmdir($dir)
    {
        if (is_dir($dir))
        {
            $objects = scandir($dir);
            foreach ($objects as $object)
            {

                if ($object != '.' && $object != '..')
                {
                    dump($object);
                    dump(is_dir($object));
                    if(!is_dir($object)){
                        dump(unlink($dir . "/" . $object));
                    } else {
                        
                        $this->rrmdir($dir . "/" . $object);
                    }
                }
            }
            rmdir($dir);
        }
    }


}