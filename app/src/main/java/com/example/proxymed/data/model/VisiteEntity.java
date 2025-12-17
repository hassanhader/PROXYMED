package com.example.proxymed.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "visite",
        indices = {
                @androidx.room.Index(value = "patientId"),
                @androidx.room.Index(value = "infirmierId"),
                @androidx.room.Index(value = "medecinId")
        },
        foreignKeys = {
                @ForeignKey(
                        entity = PatientEntity.class,
                        parentColumns = "id",
                        childColumns = "patientId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Utilisateur.class,  // Infirmier
                        parentColumns = "id",
                        childColumns = "infirmierId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Utilisateur.class,  // Médecin
                        parentColumns = "id",
                        childColumns = "medecinId",
                        onDelete = ForeignKey.SET_NULL  // Si le médecin est supprimé, on garde la visite (optionnel)
                )
        }
)
public class VisiteEntity {

    //autoincrementer automatiquement l'id : PrimaryKey(autoGenerate = true)
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "patientId")
    private long patientId;

    @ColumnInfo(name = "patientNom")
    private String patientNom;

    @ColumnInfo(name = "patientPrenom")
    private String patientPrenom;

    @ColumnInfo(name = "infirmierId")
    private long infirmierId;  // L'infirmier qui a effectué la visite

    @ColumnInfo(name = "infirmierNom")
    private String infirmierNom;

    @ColumnInfo(name = "infirmierPrenom")
    private String infirmierPrenom;

    @ColumnInfo(name = "medecinNom")
    private String medecinNom;

    @ColumnInfo(name = "medecinPrenom")
    private String medecinPrenom;

    @ColumnInfo(name = "medecinId")
    private Long medecinId;  // Le médecin qui valide, peut être NULL au début

    @ColumnInfo(name = "date_rendezvous")
    private String dateRendezVous;

    //adresse du rdv (celui du patient)
    @ColumnInfo(name = "adresse_rdv")
    private String adresseRdv;

    @ColumnInfo(name = "pression_arterielle")
    private String pressionArterielle;

    @ColumnInfo(name = "glycemie")
    private float glycemie;

    @ColumnInfo(name = "poids")
    private float poids;

    @ColumnInfo(name = "frequence_respiratoire")
    private int frequenceRespiratoire;

    @ColumnInfo(name = "frequence_cardiaque")
    private int frequenceCardiaque;

    @ColumnInfo(name = "saturation_o2")
    private int saturationO2;

    @ColumnInfo(name = "etat_general")
    private String etatGeneral;

    @ColumnInfo(name = "notes_supplementaires")
    private String notesSupplementaires;

    public byte[] getPhoto() {
        return photo;
    }

    @ColumnInfo(name = "photo")
    private byte[] photo; // Ajouter un champ pour stocker l'image

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

    @ColumnInfo(name = "statut")
    private String statut;  // Ex : "En attente", "Validée", "Rejetée"

    public VisiteEntity(long patientId, String patientNom, String patientPrenom, long infirmierId, String infirmierNom, String infirmierPrenom, String medecinNom, String medecinPrenom, Long medecinId, String dateRendezVous, String adresseRdv, String pressionArterielle, float glycemie, float poids, int frequenceRespiratoire, int frequenceCardiaque, int saturationO2, String etatGeneral, String notesSupplementaires, byte[] photo, double latitude, double longitude, String statut) {
        this.patientId = patientId;
        this.patientNom = patientNom;
        this.patientPrenom = patientPrenom;
        this.infirmierId = infirmierId;
        this.infirmierNom = infirmierNom;
        this.infirmierPrenom = infirmierPrenom;
        this.medecinNom = medecinNom;
        this.medecinPrenom = medecinPrenom;
        this.medecinId = medecinId;
        this.dateRendezVous = dateRendezVous;
        this.adresseRdv = adresseRdv;
        this.pressionArterielle = pressionArterielle;
        this.glycemie = glycemie;
        this.poids = poids;
        this.frequenceRespiratoire = frequenceRespiratoire;
        this.frequenceCardiaque = frequenceCardiaque;
        this.saturationO2 = saturationO2;
        this.etatGeneral = etatGeneral;
        this.notesSupplementaires = notesSupplementaires;
        this.photo = photo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.statut = statut;
    }

    public String getAdresseRdv() {
        return adresseRdv;
    }

    public void setAdresseRdv(String adresseRdv) {
        this.adresseRdv = adresseRdv;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getPatientNom() {
        return patientNom;
    }

    public void setPatientNom(String patientNom) {
        this.patientNom = patientNom;
    }

    public String getPatientPrenom() {
        return patientPrenom;
    }

    public void setPatientPrenom(String patientPrenom) {
        this.patientPrenom = patientPrenom;
    }

    public long getInfirmierId() {
        return infirmierId;
    }

    public void setInfirmierId(long infirmierId) {
        this.infirmierId = infirmierId;
    }

    public String getInfirmierNom() {
        return infirmierNom;
    }

    public void setInfirmierNom(String infirmierNom) {
        this.infirmierNom = infirmierNom;
    }

    public String getInfirmierPrenom() {
        return infirmierPrenom;
    }

    public void setInfirmierPrenom(String infirmierPrenom) {
        this.infirmierPrenom = infirmierPrenom;
    }

    public String getMedecinNom() {
        return medecinNom;
    }

    public void setMedecinNom(String medecinNom) {
        this.medecinNom = medecinNom;
    }

    public String getMedecinPrenom() {
        return medecinPrenom;
    }

    public void setMedecinPrenom(String medecinPrenom) {
        this.medecinPrenom = medecinPrenom;
    }

    public Long getMedecinId() {
        return medecinId;
    }

    public void setMedecinId(Long medecinId) {
        this.medecinId = medecinId;
    }

    public String getDateRendezVous() {
        return dateRendezVous;
    }

    public void setDateRendezVous(String dateRendezVous) {
        this.dateRendezVous = dateRendezVous;
    }

    public String getPressionArterielle() {
        return pressionArterielle;
    }

    public void setPressionArterielle(String pressionArterielle) {
        this.pressionArterielle = pressionArterielle;
    }

    public float getGlycemie() {
        return glycemie;
    }

    public void setGlycemie(float glycemie) {
        this.glycemie = glycemie;
    }

    public float getPoids() {
        return poids;
    }

    public void setPoids(float poids) {
        this.poids = poids;
    }

    public int getFrequenceRespiratoire() {
        return frequenceRespiratoire;
    }

    public void setFrequenceRespiratoire(int frequenceRespiratoire) {
        this.frequenceRespiratoire = frequenceRespiratoire;
    }

    public int getFrequenceCardiaque() {
        return frequenceCardiaque;
    }

    public void setFrequenceCardiaque(int frequenceCardiaque) {
        this.frequenceCardiaque = frequenceCardiaque;
    }

    public int getSaturationO2() {
        return saturationO2;
    }

    public void setSaturationO2(int saturationO2) {
        this.saturationO2 = saturationO2;
    }

    public String getEtatGeneral() {
        return etatGeneral;
    }

    public void setEtatGeneral(String etatGeneral) {
        this.etatGeneral = etatGeneral;
    }

    public String getNotesSupplementaires() {
        return notesSupplementaires;
    }

    public void setNotesSupplementaires(String notesSupplementaires) {
        this.notesSupplementaires = notesSupplementaires;
    }


    public void setPhoto(byte[] photos) {
        this.photo = photos;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
