package com.example.proxymed.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.proxymed.data.database.VisiteDao;
import com.example.proxymed.data.database.AppDatabase;
import com.example.proxymed.data.model.VisiteEntity;

import java.util.List;

public class VisiteRepository {

    private final VisiteDao visiteDao;

    public VisiteRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        this.visiteDao = database.visiteDao();
    }

    public void insertVisite(VisiteEntity visite) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            long id = visiteDao.insert(visite);  // Insère et récupère l'ID de la nouvelle visite
            Log.d("VisiteRepository", "Visite insérée avec ID : " + id);
        });
    }


    public void updateVisite(VisiteEntity visite) {
        AppDatabase.databaseWriteExecutor.execute(() -> visiteDao.update(visite));
    }

    public void deleteVisite(VisiteEntity visite) {
        AppDatabase.databaseWriteExecutor.execute(() -> visiteDao.delete(visite));
    }

    // Récupérer toutes les visites (LiveData pour observer les changements)
    public LiveData<List<VisiteEntity>> getAllVisites() {
        return visiteDao.getAllVisites();  // Retourner LiveData
    }

    // Récupérer les visites d'un patient
    public LiveData<List<VisiteEntity>> getVisitesByPatient(int patientId) {
        return visiteDao.getVisitesByPatient(patientId);  // Retourner LiveData
    }

    // Récupérer une visite par son ID
    public LiveData<VisiteEntity> getVisiteById(int visiteId) {
        return visiteDao.getVisiteById(visiteId);  // Retourner LiveData
    }

    // Récupérer les visites effectuées par un infirmier donné
    public LiveData<List<VisiteEntity>> getVisitesByInfirmier(int infirmierId) {
        return visiteDao.getVisitesByInfirmier(infirmierId);  // Retourner LiveData
    }

    // Récupérer les visites assignées à un médecin
    public LiveData<List<VisiteEntity>> getVisitesByMedecin(int medecinId) {
        return visiteDao.getVisitesByMedecin(medecinId);  // Retourner LiveData
    }

    // Récupérer les visites d'un patient pour un médecin spécifique
    public LiveData<List<VisiteEntity>> getVisitesByPatientAndMedecin(int patientId, int medecinId) {
        return visiteDao.getVisitesByPatientAndMedecin(patientId, medecinId);  // Retourner LiveData
    }

    // Méthode pour rechercher des visites filtrées
    public LiveData<List<VisiteEntity>> searchVisites(String query) {
        return visiteDao.searchVisites("%" + query + "%"); // Le "%" est un joker dans la requête SQL
    }


    public void delete(VisiteEntity visite) {
        // Suppression de la visite via DAO
        new Thread(() -> visiteDao.delete(visite)).start();
    }

    public VisiteEntity getCurrentVisite() {
        return visiteDao.getCurrentVisite();
    }
}
