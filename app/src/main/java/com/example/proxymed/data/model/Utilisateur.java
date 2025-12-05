package com.example.proxymed.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
* Cette classe représente les utilisateurs génériques
*  dans l'application, avec des champs communs à tous (
* nom, prénom, email, etc.) et des champs spécifiques
* pour chaque type d'utilisateur (infirmier ou médecin).
* */

@Entity(tableName = "utilisateurs")
public class Utilisateur {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String nom;

    private String prenom;
    private String email;
    private String type; // "infirmier" ou "médecin"
    private String telephone;
    private String motDePasse;
    private String specialite;  // Seulement pour les médecins
    private String numeroPraticien;
    private byte[] photoProfil;  // Champ ajouté pour l'image
    private String adresse;  // Seulement pour les infirmiers



    // Constructeur
    public Utilisateur(String nom, String prenom, String email, String type, String motDePasse, String telephone,
                       String specialite, String numeroPraticien, String adresse, byte[] photoProfil) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.type = type;
        this.motDePasse = motDePasse;
        this.telephone = telephone;
        this.specialite = specialite != null ? specialite : ""; // Valeur par défaut si null
        this.numeroPraticien = numeroPraticien != null ? numeroPraticien : ""; // Valeur par défaut si null
        this.adresse = adresse != null ? adresse : ""; // Valeur par défaut si null
        this.photoProfil = photoProfil;
    }

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    public String getNumeroPraticien() { return numeroPraticien; }
    public void setNumeroPraticien(String numeroPraticien) { this.numeroPraticien = numeroPraticien; }

    public byte[] getPhotoProfil() { return photoProfil; }
    public void setPhotoProfil(byte[] photoProfil) { this.photoProfil = photoProfil; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

}

