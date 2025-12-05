package com.example.proxymed.viewmodel;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.proxymed.data.model.Utilisateur;
import com.example.proxymed.data.repository.UtilisateurRepository;

public class UtilisateurViewModel extends AndroidViewModel {

    private UtilisateurRepository utilisateurRepository;

    public UtilisateurViewModel(Application application) {
        super(application);
        utilisateurRepository = new UtilisateurRepository(application);
    }



    public void update(Utilisateur utilisateur) {
        new Thread(() -> utilisateurRepository.update(utilisateur)).start();
    }


    // Méthode pour mettre à jour l'utilisateur
    public void updateUtilisateur(Utilisateur utilisateur) {
        utilisateurRepository.update(utilisateur);
    }

    public LiveData<Utilisateur> getUtilisateurByEmailLive(String email) {
        return utilisateurRepository.getUtilisateurByEmailLive(email);
    }

    public LiveData<Utilisateur> getUtilisateurById(String userId) {
        return utilisateurRepository.getUtilisateurByIdLive(userId);
    }

}


