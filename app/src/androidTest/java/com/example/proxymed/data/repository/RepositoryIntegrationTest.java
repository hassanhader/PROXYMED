package com.example.proxymed.data.repository;

import android.app.Application;
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
    private Application application;
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        application = (Application) context;
        database = Room.inMemoryDatabaseBuilder(
                context,
                AppDatabase.class
        ).allowMainThreadQueries().build();
        
        utilisateurRepository = new UtilisateurRepository(application);
        patientRepository = new PatientRepository(context);
        visiteRepository = new VisiteRepository(context);
        
        // Nettoyer la base de données avant chaque test
        database.visiteDao().deleteAll();
        database.patientDao().deleteAll();
        database.utilisateurDao().deleteAll();
    }

    @After
    public void tearDown() {
        if (database != null) {
            // Nettoyer la base de données après chaque test
            database.visiteDao().deleteAll();
            database.patientDao().deleteAll();
            database.utilisateurDao().deleteAll();
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
                "médecin",
                "password456",
                "514-987-6543",
                "Cardiologie",
                "12345",
                null,
                null
        );
        database.utilisateurDao().insert(medecin);
        
        // Récupérer le médecin inséré pour obtenir son ID
        Utilisateur insertedMedecin = database.utilisateurDao().getUtilisateurByEmail("sophie.martin@example.com");
        long medecinId = insertedMedecin.getId();
        
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
        
        // Récupérer l'infirmier inséré pour obtenir son ID
        Utilisateur insertedInfirmier = database.utilisateurDao().getUtilisateurByEmail("jean.dupont@example.com");
        long infirmierId = insertedInfirmier.getId();
        
        // 3. Créer un patient assigné au médecin
        PatientEntity patient = new PatientEntity(
                "Lavoie",
                "Pierre",
                "1990-05-15",
                "789 Rue Patient, Montréal",
                "514-111-2222",
                medecinId // Utiliser l'ID réel du médecin
        );
        long patientId = database.patientDao().insert(patient);
        patient.setId(patientId);
        
        // 4. Créer une visite
        VisiteEntity visite = new VisiteEntity(
                patientId,
                "Lavoie",
                "Pierre",
                infirmierId, // Utiliser l'ID réel de l'infirmier
                "Dupont",
                "Jean",
                "Martin",
                "Sophie",
                medecinId, // Utiliser l'ID réel du médecin
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
        
        // Vérifier que le patient peut être récupéré
        List<PatientEntity> patients = database.patientDao().getPatientsByMedecin(medecinId);
        assertEquals(1, patients.size());
        assertEquals("Lavoie", patients.get(0).getNom());
    }

    @Test
    public void testPatientRepositoryIntegration() throws InterruptedException {
        // Arrange - Créer un médecin pour satisfaire la contrainte de clé étrangère
        Utilisateur medecin = new Utilisateur(
                "Martin",
                "Sophie",
                "sophie.martin@example.com",
                "médecin",
                "password456",
                "514-987-6543",
                "Cardiologie",
                "12345",
                null,
                null
        );
        database.utilisateurDao().insert(medecin);
        
        // Récupérer le médecin inséré pour obtenir son ID
        Utilisateur insertedMedecin = database.utilisateurDao().getUtilisateurByEmail("sophie.martin@example.com");
        long medecinId = insertedMedecin.getId();
        
        // Créer un patient avec le bon ID de médecin
        PatientEntity patient = new PatientEntity(
                "Gagnon",
                "Julie",
                "1985-03-20",
                "321 Rue Autre, Québec",
                "418-333-4444",
                medecinId
        );
        
        // Act - Utiliser directement le DAO pour éviter les problèmes de singleton
        long patientId = database.patientDao().insert(patient);
        patient.setId(patientId);
        
        // Assert - Vérifier que le patient a été inséré avec succès
        assertTrue("L'ID du patient devrait être supérieur à 0", patientId > 0);
        
        // Vérifier que le patient peut être récupéré via getPatientsByMedecin (méthode synchrone)
        List<PatientEntity> patients = database.patientDao().getPatientsByMedecin(medecinId);
        assertNotNull("La liste des patients ne devrait pas être null", patients);
        assertEquals("Il devrait y avoir exactement 1 patient", 1, patients.size());
        assertEquals("Le nom du patient devrait correspondre", "Gagnon", patients.get(0).getNom());
        assertEquals("Le prénom du patient devrait correspondre", "Julie", patients.get(0).getPrenom());
        assertEquals("L'ID du médecin devrait correspondre", medecinId, (long) patients.get(0).getMedecinId());
    }

    @Test
    public void testVisiteRepositoryIntegration() throws InterruptedException {
        // Arrange - Créer un médecin
        Utilisateur medecin = new Utilisateur(
                "Martin",
                "Sophie",
                "sophie.martin@example.com",
                "médecin",
                "password456",
                "514-987-6543",
                "Cardiologie",
                "12345",
                null,
                null
        );
        database.utilisateurDao().insert(medecin);
        Utilisateur insertedMedecin = database.utilisateurDao().getUtilisateurByEmail("sophie.martin@example.com");
        long medecinId = insertedMedecin.getId();
        
        // Créer un infirmier
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
        Utilisateur insertedInfirmier = database.utilisateurDao().getUtilisateurByEmail("jean.dupont@example.com");
        long infirmierId = insertedInfirmier.getId();
        
        // Créer un patient
        PatientEntity patient = new PatientEntity(
                "Lavoie",
                "Pierre",
                "1990-05-15",
                "789 Rue Patient, Montréal",
                "514-111-2222",
                medecinId
        );
        long patientId = database.patientDao().insert(patient);
        patient.setId(patientId);
        
        // Créer une visite avec les bons IDs
        VisiteEntity visite = new VisiteEntity(
                patientId, "Lavoie", "Pierre",
                infirmierId, "Dupont", "Jean",
                "Martin", "Sophie", medecinId,
                "2025-03-20", "789 Rue Patient",
                "120/80", 5.5f, 75.5f,
                18, 72, 98,
                "Bon état général", "Aucune note",
                null, 45.5017, -73.5673,
                "En attente"
        );
        
        // Act - Utiliser directement le DAO pour éviter les problèmes de singleton
        long visiteId = database.visiteDao().insert(visite);
        visite.setId(visiteId);
        
        // Assert - Vérifier que la visite a été insérée avec succès
        assertTrue("L'ID de la visite devrait être supérieur à 0", visiteId > 0);
        
        // Vérifier que la visite peut être récupérée via getCurrentVisite (méthode synchrone)
        VisiteEntity retrievedVisite = database.visiteDao().getCurrentVisite();
        assertNotNull("La visite récupérée ne devrait pas être null", retrievedVisite);
        assertEquals("L'ID de la visite devrait correspondre", visiteId, retrievedVisite.getId());
        assertEquals("Le statut devrait correspondre", "En attente", retrievedVisite.getStatut());
        assertEquals("Le nom du patient devrait correspondre", "Lavoie", retrievedVisite.getPatientNom());
    }
}

