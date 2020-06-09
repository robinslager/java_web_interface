<?php

namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Security\Core\User\UserInterface;

/**
 * @ORM\Entity(repositoryClass="App\Repository\UserRepository")
 */
class User implements UserInterface, \Serializable
{
    /**
     * @ORM\Id()
     * @ORM\GeneratedValue()
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255, unique=true)
     */
    private $username;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $password;

    /**
     * @ORM\Column(type="string", length=255, unique=true)
     */
    private $email;

    /**
     * @ORM\OneToMany(targetEntity="App\Entity\Project", mappedBy="User")
     */
    private $projects;

    /**
     * @ORM\OneToMany(targetEntity="App\Entity\Project", mappedBy="User", orphanRemoval=true)
     */
    private $Projects;

    public function __construct()
    {
        $this->projects = new ArrayCollection();
        $this->Projects = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getUsername(): ?string
    {
        return $this->username;
    }

    public function setUsername(string $username): self
    {
        $this->username = $username;

        return $this;
    }

    public function getPassword(): ?string
    {
        return $this->password;
    }

    public function setPassword(string $password): self
    {
        $this->password = $password;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }

	/**
	 * @inheritDoc
	 */
	public function getRoles()
                              	{
                              		return [
                              			'ROLE_USER'
                              		];
                              	}

	/**
	 * @inheritDoc
	 */
	public function getSalt()
                              	{
                              		// TODO: Implement getSalt() method.
                              	}

	/**
	 * @inheritDoc
	 */
	public function eraseCredentials()
                              	{
                              		// TODO: Implement eraseCredentials() method.
                              	}

	/**
	 * @inheritDoc
	 */
	public function serialize()
                              	{
                              		return serialize( [
                              			$this->id,
                              			$this->username,
                              			$this->email,
                              			$this->password
                              			]
                              		);
                              	}

	/**
	 * @inheritDoc
	 */
	public function unserialize($serialized)
                              	{
                              		list(
                              			$this->id,
                              			$this->username,
                              			$this->email,
                              			$this->password
                              			) = unserialize($serialized, ['allowed_classes' => false]);
                              	}

    /**
     * @return Collection|Project[]
     */
    public function getProjects(): Collection
    {
        return $this->projects;
    }

    public function addProject(Project $project): self
    {
        if (!$this->projects->contains($project)) {
            $this->projects[] = $project;
            $project->setUser($this);
        }

        return $this;
    }

    public function removeProject(Project $project): self
    {
        if ($this->projects->contains($project)) {
            $this->projects->removeElement($project);
            // set the owning side to null (unless already changed)
            if ($project->getUser() === $this) {
                $project->setUser(null);
            }
        }

        return $this;
    }
}
