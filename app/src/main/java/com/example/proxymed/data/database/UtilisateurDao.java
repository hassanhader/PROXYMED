package com.example.proxymed.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.Update;
import androidx.room.Query;

import com.example.proxymed.data.model.Utilisateur;

import java.util.List;


/*
* Cette interface permet de définir les opérations
* de gestion des données pour la table Utilisateur
* (ajout, mise à jour, suppression, récupération),
* avec des méthodes adaptées à la fois pour les utilisateurs
* généraux et pour des types spécifiques (infirmiers et médecins).
* */

@Dao
public interface UtilisateurDao {

    @Insert
    void insert(Utilisateur utilisateur);



    @Update
    void update(Utilisateur utilisateur);

    @Delete
    void delete(Utilisateur utilisateur);

    @Query("DELETE FROM utilisateurs")
    void deleteAll();  // Supprimer tous les utilisateurs

    @Query("SELECT * FROM utilisateurs")
    List<Utilisateur> getAllUtilisateurs();

    @Query("SELECT * FROM utilisateurs WHERE id = :id")
    Utilisateur getUtilisateurById(int id);

    @Query("SELECT * FROM utilisateurs WHERE type = 'médecin'")
    List<Utilisateur> getAllMedecins();  // Récupérer tous les médecins

    @Query("SELECT * FROM utilisateurs WHERE type = 'infirmier'")
    List<Utilisateur> getAllInfirmiers();  // Récupérer tous les infirmiers

    @Query("SELECT * FROM utilisateurs WHERE email = :email AND motDePasse = :password LIMIT 1")
    Utilisateur getUtilisateurByEmailAndPassword(String email, String password);

    @Query("SELECT * FROM utilisateurs WHERE email = :email LIMIT 1")
    Utilisateur getUtilisateurByEmail(String email);

    @Query("SELECT * FROM utilisateurs WHERE email = :email LIMIT 1")
    LiveData<Utilisateur> getUtilisateurByEmailLive(String email);

    @Query("SELECT * FROM utilisateurs WHERE id = :userId LIMIT 1")
    LiveData<Utilisateur> getUtilisateurByIdLive(String userId);

    @Query("SELECT * FROM utilisateurs WHERE type = 'médecin'")
    List<Utilisateur> getMedecins();

}
