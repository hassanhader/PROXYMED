package com.example.proxymed.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.proxymed.data.model.PatientEntity;
import com.example.proxymed.data.model.VisiteEntity;

import java.util.List;

@Dao
public interface PatientDao {

    @Insert
    long insert(PatientEntity patient);

    @Update
    void update(PatientEntity patient);

    @Delete
    void delete(PatientEntity patient);

    // Récupérer tous les patients

    // Récupérer un patient par son ID
    @Query("SELECT * FROM patients")
    LiveData<List<PatientEntity>> getAllPatients();

    @Query("SELECT * FROM patients WHERE id = :patientId LIMIT 1")
    LiveData<PatientEntity> getPatientById(int patientId);

    // Récupérer les patients suivis par un médecin donné
    @Query("SELECT * FROM patients WHERE medecinId = :medecinId")
    List<PatientEntity> getPatientsByMedecin(long medecinId);

    // (Bonus) Compter les patients par médecin
    @Query("SELECT COUNT(*) FROM patients WHERE medecinId = :medecinId")
    int countPatientsByMedecin(long medecinId);

    // Supprimer tous les patients (utile pour les tests)
    @Query("DELETE FROM patients")
    void deleteAll();

}

