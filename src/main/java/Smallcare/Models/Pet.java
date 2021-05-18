package Smallcare.Models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Pet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "photoUrl")
    private String photoUrl;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    Set<PetComment> petComments;

    @ManyToOne(cascade = CascadeType.REFRESH)
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pet() {}

    public Pet(String name, String description, String photoUrl) {
        this.name = name;
        this.description = description;
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Set<PetComment> getPetComments() {
        return petComments;
    }

    public void setPetComments(Set<PetComment> petComments) {
        this.petComments = petComments;
    }

    public void addPetComment(PetComment petComment){
        this.petComments.add(petComment);
    }
}
