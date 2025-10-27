package com.example.beans;

import javax.persistence.Entity;
import java.util.List;
import javax.persistence.OneToMany;
import java.util.Date;

@Entity
public class Homme extends Personne {

    @OneToMany(mappedBy = "homme")
    private List<Mariage> mariages;

    public Homme() {}

    public Homme(String nom, String prenom, String telephone, String adresse, String cin, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, cin, dateNaissance);
    }

    public List<Mariage> getMariages() { return mariages; }
    public void setMariages(List<Mariage> mariages) { this.mariages = mariages; }
}