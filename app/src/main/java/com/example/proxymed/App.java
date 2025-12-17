package com.example.proxymed;

/*
* Projet : Proxymed, travail de session 1
* Auteurs : Abderraouf Guessoum, Lily Occhibelli, Océane Rakotoarisoa
* Cours : INF1030
* Session : Hiver 2025
* Date : 13 mars 2025*/

import android.app.Application;

import com.example.proxymed.data.repository.UtilisateurRepository;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UtilisateurRepository utilisateurRepository = new UtilisateurRepository(this);
    }
}
