package com.example.proxymed.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.proxymed.data.model.PatientEntity;
import com.example.proxymed.data.model.Utilisateur;
import com.example.proxymed.data.model.VisiteEntity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* Cette classe abstraite gère la base de données
en spécifiant les entités (tables) utilisées,
ici Utilisateur, et en fournissant des méthodes
d'accès aux DAO.
*/

@Database(entities = {Utilisateur.class, PatientEntity.class, VisiteEntity.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    // Executor pour exécuter les requêtes en arrière-plan (Room recommande d'éviter les requêtes directes sur le thread principal)
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    // Déclare les DAO
    public abstract UtilisateurDao utilisateurDao();
    public abstract PatientDao patientDao();
    public abstract VisiteDao visiteDao();


    // Méthode Singleton pour obtenir la base de données
    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "proxymed_database"
                    )
                    .fallbackToDestructiveMigration()  // ⚠️ En développement seulement ! En prod, il faut gérer les migrations manuelles.
                    .allowMainThreadQueries() // A éviter en production
                    .build();
        }
        return INSTANCE;
    }
}
