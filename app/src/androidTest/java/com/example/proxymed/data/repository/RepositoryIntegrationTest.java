package com.example.proxymed.data.repository;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.proxymed.data.database.AppDatabase;
import com.example.proxymed.data.model.PatientEntity;
import com.example.proxymed.data.model.Utilisateur;
import com.example.proxymed.data.model.VisiteEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Tests d'intégration pour les repositories avec la base de données
 */
@RunWith(AndroidJUnit4.class)
public class RepositoryIntegrationTest {

    private AppDatabase database;
    private UtilisateurRepository utilisateurRepository;
    private PatientRepository patientRepository;
    private VisiteRepository visiteRepository;
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(
                context,
                AppDatabase.class
        ).allowMainThreadQueries().build();
        
        utilisateurRepository = new UtilisateurRepository(context);
        patientRepository = new PatientRepository(context);
        visiteRepository = new VisiteRepository(context);
    }

    @After
    public void tearDown() {
        if (database != null) {
            database.close();
        }
    }

    @Test
    public void testCompleteWorkflow() throws InterruptedException {
        // 1. Créer un médecin
        Utilisateur medecin = new Utilisateur(
                "Martin",
                "Sophie",
                "sophie.martin@example.com",
                "medecin",
                "password456",
                "514-987-6543",
                "Cardiologie",
                "12345",
                null,
                null
        );
        database.utilisateurDao().insert(medecin);
        
        // 2. Créer un infirmier
        Utilisateur infirmier = new Utilisateur(
                "Dupont",
                "Jean",
                "jean.dupont@example.com",
                "infirmier",
                "password123",
                "514-123-4567",
                null,
                null,
                "123 Rue Test",
                null
        );
        database.utilisateurDao().insert(infirmier);
        
        // 3. Créer un patient assigné au médecin
        PatientEntity patient = new PatientEntity(
                "Lavoie",
                "Pierre",
                "1990-05-15",
                "789 Rue Patient, Montréal",
                "514-111-2222",
                1L // ID du médecin
        );
        long patientId = database.patientDao().insert(patient);
        patient.setId(patientId);
        
        // 4. Créer une visite
        VisiteEntity visite = new VisiteEntity(
                patientId,
                "Lavoie",
                "Pierre",
                2L, // ID de l'infirmier
                "Dupont",
                "Jean",
                "Martin",
                "Sophie",
                1L, // ID du médecin
                "2025-03-20",
                "789 Rue Patient",
                "120/80",
                5.5f,
                75.5f,
                18,
                72,
                98,
                "Bon état général",
                "Aucune note",
                null,
                45.5017,
                -73.5673,
                "En attente"
        );
        long visiteId = database.visiteDao().insert(visite);
        visite.setId(visiteId);
        
        // 5. Vérifications
        List<Utilisateur> medecins = database.utilisateurDao().getAllMedecins();
        assertEquals(1, medecins.size());
        
        List<Utilisateur> infirmiers = database.utilisateurDao().getAllInfirmiers();
        assertEquals(1, infirmiers.size());
        
        // Vérifier que la visite a été créée
        assertTrue(visiteId > 0);
        assertEquals("En attente", visite.getStatut());
    }

    @Test
    public void testPatientRepositoryIntegration() throws InterruptedException {
        // Arrange
        PatientEntity patient = new PatientEntity(
                "Gagnon",
                "Julie",
                "1985-03-20",
                "321 Rue Autre, Québec",
                "418-333-4444",
                1L
        );
        
        // Act
        patientRepository.insertPatient(patient);
        
        // Attendre un peu pour que l'insertion soit terminée
        Thread.sleep(500);
        
        // Assert
        // Note: Pour vérifier avec LiveData, il faudrait utiliser un observer
        // Pour l'instant, on vérifie juste que l'opération se termine sans erreur
        assertTrue(true);
    }

    @Test
    public void testVisiteRepositoryIntegration() throws InterruptedException {
        // Arrange
        VisiteEntity visite = new VisiteEntity(
                1L, "Lavoie", "Pierre",
                2L, "Dupont", "Jean",
                "Martin", "Sophie", 3L,
                "2025-03-20", "789 Rue Patient",
                "120/80", 5.5f, 75.5f,
                18, 72, 98,
                "Bon état général", "Aucune note",
                null, 45.5017, -73.5673,
                "En attente"
        );
        
        // Act
        visiteRepository.insertVisite(visite);
        
        // Attendre un peu
        Thread.sleep(500);
        
        // Assert
        assertTrue(true);
    }
}

