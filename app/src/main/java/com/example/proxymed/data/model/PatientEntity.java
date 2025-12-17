package com.example.proxymed.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Cette classe représente les patients gérés par l'application.
 * Chaque patient est assigné à un médecin dès sa création.
 */

@Entity(
        tableName = "patients",
        indices = {@Index("medecinId")},
        foreignKeys = @ForeignKey(
                entity = Utilisateur.class,
                parentColumns = "id",
                childColumns = "medecinId",
                onDelete = ForeignKey.SET_NULL // Si le médecin est supprimé, le patient garde son dossier sans médecin
        )
)
public class PatientEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String adresse;
    private String telephone;

    @ColumnInfo(name = "medecinId")  // Médecin référent
    private Long medecinId;  // Peut être null si aucun médecin n’a été assigné (cas limite)

    // Constructeur
    public PatientEntity(String nom, String prenom, String dateNaissance, String adresse, String telephone, Long medecinId) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.telephone = telephone;
        this.medecinId = medecinId;  // Assigné au moment de la création
    }

    // Getters et Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(String dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public Long getMedecinId() { return medecinId; }
    public void setMedecinId(Long medecinId) { this.medecinId = medecinId; }

    @Override
    public String toString() {
        return nom + " " + prenom; // Retourne le nom et prénom du patient
    }

}
