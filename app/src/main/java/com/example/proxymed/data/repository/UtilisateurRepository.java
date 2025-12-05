package com.example.proxymed.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.proxymed.data.database.AppDatabase;
import com.example.proxymed.data.database.UtilisateurDao;
import com.example.proxymed.data.model.Utilisateur;

import java.util.List;

/*Cette classe agit comme une couche intermédiaire
entre la base de données et l'interface utilisateur,
fournissant des méthodes pour interagir avec les données
utilisateur, comme l'insertion d'un utilisateur ou
la récupération de la liste des utilisateurs.
*/
public class UtilisateurRepository {
    private UtilisateurDao utilisateurDao; //DAO :

    //Constructeur
    public UtilisateurRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application); //Récupérer la BD
        utilisateurDao = db.utilisateurDao();   // Accéder au DAO pour pouvoir interagir avec les données
    }


    // Insérer un utilisateur
    public void insertUtilisateur(Utilisateur utilisateur) {
        new Thread(() -> {
            try {
                Log.d("Inscription", "Début de l'insertion de l'utilisateur : " + utilisateur.getNom());
                utilisateurDao.insert(utilisateur);
                Log.d("Inscription", "Utilisateur inséré : " + utilisateur.getNom());
            } catch (Exception e) {
                Log.e("DatabaseError", "Erreur lors de l'insertion de l'utilisateur", e);
            }
        }).start();
    }


    // Récupérer tous les utilisateurs
    public void getAllUtilisateurs(Callback callback) {
        new Thread(() -> {
            List<Utilisateur> utilisateurs = utilisateurDao.getAllUtilisateurs();
            callback.onResult(utilisateurs);  // Appel du callback pour notifier le résultat
        }).start();
    }

    // Récupérer tous les infirmiers
    public List<Utilisateur> getAllInfirmiers() {
        return utilisateurDao.getAllInfirmiers();
    }

    // Récupérer tous les médecins
    public List<Utilisateur> getAllMedecins() {
        return utilisateurDao.getAllMedecins();
    }

    public void deleteAllUtilisateurs() {
        new Thread(() -> {
            try {
                Log.d("Supprimer", "Suppression de tous les utilisateurs");
                utilisateurDao.deleteAll();  // Appelle la méthode deleteAll dans le DAO
                Log.d("Supprimer", "Tous les utilisateurs ont été supprimés");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    //Pour la connexion
    public Utilisateur getUtilisateurByEmailAndPassword(String email, String password) {
        Log.d("Repository", "Vérification utilisateur avec email: " + email);
        return utilisateurDao.getUtilisateurByEmailAndPassword(email, password);
    }


    public Utilisateur getUtilisateurByEmail(String email) {
        Utilisateur utilisateur = utilisateurDao.getUtilisateurByEmail(email);
        return utilisateur;
    }

    public LiveData<Utilisateur> getUtilisateurByEmailLive(String email) {
        return utilisateurDao.getUtilisateurByEmailLive(email); // Cette méthode doit retourner un LiveData<Utilisateur>
    }

    public void update(Utilisateur utilisateur) {
        new Thread(() -> utilisateurDao.update(utilisateur)).start();
    }


    public LiveData<Utilisateur> getUtilisateurByIdLive(String userId) {
        return utilisateurDao.getUtilisateurByIdLive(userId);
    }


}

