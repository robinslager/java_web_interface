<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass="App\Repository\ProjectRepository")
 */
class Project
{
    /**
     * @ORM\Id()
     * @ORM\GeneratedValue()
     * @ORM\Column(type="integer")
     */
    private $id;


    /**
     * @ORM\Column(type="string", length=255)
     */
    private $ProjectName;
    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     */
    private $DockerID;

    /**
     * @ORM\ManyToOne(targetEntity="App\Entity\User", inversedBy="Projects")
     * @ORM\JoinColumn(nullable=false)
     */
    private $User;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $DockerStatus;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getUserID(): ?int
    {
        return $this->UserID;
    }

    public function setUserID(int $UserID): self
    {
        $this->UserID = $UserID;

        return $this;
    }

    public function setID(int $ID): self
    {
        $this->ID = $ID;

        return $this;
    }

    public function getProjectName(): ?string
    {
        return $this->ProjectName;
    }

    public function setProjectName(string $ProjectName): self
    {
        $this->ProjectName = $ProjectName;

        return $this;
    }

    public function getDockerID(): ?string
    {
        return $this->DockerID;
    }

    public function setDockerID(?string $DockerID): self
    {
        $this->DockerID = $DockerID;

        return $this;
    }

    public function getUser(): ?User
    {
        return $this->User;
    }

    public function setUser(?User $User): self
    {
        $this->User = $User;

        return $this;
    }

    public function getDockerStatus(): ?string
    {
        return $this->DockerStatus;
    }

    public function setDockerStatus(string $DockerStatus): self
    {
        $this->DockerStatus = $DockerStatus;

        return $this;
    }

}
