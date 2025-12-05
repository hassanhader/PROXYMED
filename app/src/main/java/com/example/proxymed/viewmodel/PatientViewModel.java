package com.example.proxymed.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.proxymed.data.model.PatientEntity;
import com.example.proxymed.data.model.VisiteEntity;
import com.example.proxymed.data.repository.PatientRepository;

import java.util.List;

//Classe responsable de récupérer la liste des patients depuis la base de données Room et de les afficher dans l'interface utilisateur.
public class PatientViewModel extends AndroidViewModel {
    private PatientRepository repository;
    private LiveData<List<PatientEntity>> allPatients;

    public PatientViewModel(Application application) {
        super(application);
        repository = new PatientRepository(application);
        allPatients = repository.getAllPatients();
    }

    public LiveData<List<PatientEntity>> getAllPatients() {
        return allPatients;
    }


}

