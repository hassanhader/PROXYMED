package com.example.proxymed.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.proxymed.data.database.PatientDao;
import com.example.proxymed.data.database.AppDatabase;
import com.example.proxymed.data.model.PatientEntity;
import com.example.proxymed.data.model.VisiteEntity;

import java.util.List;

public class PatientRepository {

    private final PatientDao patientDao;
    public PatientRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        this.patientDao = database.patientDao();
    }

    // Ajouter un patient
    public void insertPatient(PatientEntity patient) {
        AppDatabase.databaseWriteExecutor.execute(() -> patientDao.insert(patient));
    }

    // Mettre à jour un patient
    public void updatePatient(PatientEntity patient) {
        AppDatabase.databaseWriteExecutor.execute(() -> patientDao.update(patient));
    }

    // Supprimer un patient
    public void deletePatient(PatientEntity patient) {
        AppDatabase.databaseWriteExecutor.execute(() -> patientDao.delete(patient));
    }

    // Récupérer tous les patients (LiveData pour observer les changements)
    public LiveData<List<PatientEntity>> getAllPatients() {
        return patientDao.getAllPatients();
    }

    // Récupérer un patient par son ID
    public LiveData<PatientEntity> getPatientById(int patientId) {
        return patientDao.getPatientById(patientId);


    }

}
