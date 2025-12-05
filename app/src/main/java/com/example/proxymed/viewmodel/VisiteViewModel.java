package com.example.proxymed.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proxymed.data.model.VisiteEntity;
import com.example.proxymed.data.repository.VisiteRepository;

import java.util.ArrayList;
import java.util.List;

public class VisiteViewModel extends AndroidViewModel {

    private final VisiteRepository visiteRepository;
    public VisiteViewModel(Application application) {
        super(application);
        visiteRepository = new VisiteRepository(application);
        //allVisites = visiteRepository.getAllVisites(); // Récupérer toutes les visites
    }


    // Récupérer toutes les visites
    public LiveData<List<VisiteEntity>> getAllVisites() {
        return visiteRepository.getAllVisites();
    }

    // Récupérer les visites filtrées (si tu veux filtrer depuis la base de données)
    public LiveData<List<VisiteEntity>> searchVisites(String query) {
        return visiteRepository.searchVisites(query);
    }

    //Pour supprimer uen visite
    public void deleteVisite(VisiteEntity visite) {
        visiteRepository.delete(visite);
    }

    // Méthode pour insérer une visite
    public void insertVisite(VisiteEntity visite) {
        visiteRepository.insertVisite(visite);
    }

    public VisiteEntity getCurrentVisite() {
        return visiteRepository.getCurrentVisite();
    }
}
