package com.example.proxymed.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.proxymed.data.model.VisiteEntity;

import java.util.List;

@Dao
public interface VisiteDao {

    @Insert
    long insert(VisiteEntity visite);

    @Update
    void update(VisiteEntity visite);

    @Delete
    void delete(VisiteEntity visite);

    // Supprimer toutes les visites (utile pour les tests)
    @Query("DELETE FROM visite")
    void deleteAll();

    // Récupérer toutes les visites
    @Query("SELECT * FROM visite")
    LiveData<List<VisiteEntity>> getAllVisites();

    // Récupérer une visite par son ID
    @Query("SELECT * FROM visite WHERE id = :visiteId")
    LiveData<VisiteEntity> getVisiteById(long visiteId);  // Retourner LiveData

    // Récupérer toutes les visites d’un patient
    @Query("SELECT * FROM visite WHERE patientId = :patientId")
    LiveData<List<VisiteEntity>> getVisitesByPatient(long patientId);  // Retourner LiveData

    // Récupérer toutes les visites effectuées par un infirmier donné
    @Query("SELECT * FROM visite WHERE infirmierId = :infirmierId")
    LiveData<List<VisiteEntity>> getVisitesByInfirmier(long infirmierId);  // Retourner LiveData

    // Récupérer toutes les visites assignées à un médecin
    @Query("SELECT * FROM visite WHERE medecinId = :medecinId")
    LiveData<List<VisiteEntity>> getVisitesByMedecin(long medecinId);  // Retourner LiveData

    // (Bonus) Récupérer les visites d’un patient pour un médecin spécifique
    @Query("SELECT * FROM visite WHERE patientId = :patientId AND medecinId = :medecinId")
    LiveData<List<VisiteEntity>> getVisitesByPatientAndMedecin(long patientId, long medecinId);  // Retourner LiveData

    // Méthode pour rechercher des visites par nom ou prénom du patient
    @Query("SELECT * FROM visite WHERE patientNom LIKE :query OR patientPrenom LIKE :query")
    LiveData<List<VisiteEntity>> searchVisites(String query); // Utilise LIKE pour effectuer la recherche

    // Pour avoir la visite courante
    @Query("SELECT * FROM visite WHERE id = (SELECT MAX(id) FROM visite)")
    VisiteEntity getCurrentVisite();
}

