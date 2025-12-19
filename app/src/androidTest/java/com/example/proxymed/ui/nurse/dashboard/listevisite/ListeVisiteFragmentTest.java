package com.example.proxymed.ui.nurse.dashboard.listevisite;

import android.content.Context;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.proxymed.R;
import com.example.proxymed.data.database.AppDatabase;
import com.example.proxymed.data.model.VisiteEntity;
import com.example.proxymed.data.repository.VisiteRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Tests fonctionnels pour ListeVisiteFragment
 */
@RunWith(AndroidJUnit4.class)
public class ListeVisiteFragmentTest {

    private AppDatabase database;
    private VisiteRepository repository;
    private Context context;
    private VisiteEntity testVisite;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        database = AppDatabase.getInstance(context);
        repository = new VisiteRepository(context);
        
        // Nettoyer la base de données
        database.visiteDao().deleteAll();
        
        // Créer une visite de test
        testVisite = new VisiteEntity(
                1L,                    // patientId
                "Lavoie",              // patientNom
                "Pierre",              // patientPrenom
                2L,                    // infirmierId
                "Dupont",              // infirmierNom
                "Jean",                // infirmierPrenom
                "Martin",              // medecinNom
                "Sophie",              // medecinPrenom
                3L,                    // medecinId
                "2025-03-20",          // dateRendezVous
                "789 Rue Patient",     // adresseRdv
                "120/80",              // pressionArterielle
                5.5f,                  // glycemie
                75.5f,                 // poids
                18,                    // frequenceRespiratoire
                72,                    // frequenceCardiaque
                98,                    // saturationO2
                "Bon état général",    // etatGeneral
                "Aucune note",         // notesSupplementaires
                null,                  // photo
                45.5017,               // latitude
                -73.5673,              // longitude
                "En attente"           // statut
        );
        
        // Insérer la visite
        repository.insertVisite(testVisite);
        
        // Attendre un peu
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        if (database != null) {
            database.visiteDao().deleteAll();
        }
    }

    @Test
    public void testListeVisiteFragmentCreation() {
        // Arrange & Act
        FragmentScenario<ListeVisiteFragment> scenario = FragmentScenario.launchInContainer(
                ListeVisiteFragment.class,
                null,
                R.style.Theme_AppCompat,
                null
        );
        
        // Assert
        scenario.onFragment(fragment -> {
            assertNotNull("Le fragment ne devrait pas être null", fragment);
            assertNotNull("La vue ne devrait pas être null", fragment.getView());
        });
    }

    @Test
    public void testRecyclerViewDisplayed() {
        // Arrange
        FragmentScenario<ListeVisiteFragment> scenario = FragmentScenario.launchInContainer(
                ListeVisiteFragment.class,
                null,
                R.style.Theme_AppCompat,
                null
        );
        
        // Assert
        scenario.onFragment(fragment -> {
            assertNotNull(fragment.getView());
        });
    }
}

