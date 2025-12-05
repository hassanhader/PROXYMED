package com.example.proxymed.data.repository;

import com.example.proxymed.data.model.Utilisateur;

import java.util.List;

// interface Callback pour récupérer les résultats
        public interface Callback {
        void onResult(List<Utilisateur> utilisateurs);
        }
