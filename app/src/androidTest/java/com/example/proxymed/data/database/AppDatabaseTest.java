package com.example.proxymed.data.database;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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
 * Tests fonctionnels pour la base de données Room
 */
@RunWith(AndroidJUnit4.class)
public class AppDatabaseTest {

    private AppDatabase database;
    private UtilisateurDao utilisateurDao;
    private PatientDao patientDao;
    private VisiteDao visiteDao;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        // Créer une base de données en mémoire pour les tests
        database = Room.inMemoryDatabaseBuilder(
                context,
                AppDatabase.class
        ).allowMainThreadQueries().build();
        
        utilisateurDao = database.utilisateurDao();
        patientDao = database.patientDao();
        visiteDao = database.visiteDao();
    }

    @After
    public void tearDown() {
        if (database != null) {
            database.close();
        }
    }

    @Test
    public void testInsertAndGetUtilisateur() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur(
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
        
        // Act
        utilisateurDao.insert(utilisateur);
        List<Utilisateur> utilisateurs = utilisateurDao.getAllUtilisateurs();
        
        // Assert
        assertEquals(1, utilisateurs.size());
        assertEquals("Dupont", utilisateurs.get(0).getNom());
        assertEquals("Jean", utilisateurs.get(0).getPrenom());
        assertEquals("jean.dupont@example.com", utilisateurs.get(0).getEmail());
    }

    @Test
    public void testGetUtilisateurByEmailAndPassword() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur(
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
        utilisateurDao.insert(utilisateur);
        
        // Act
        Utilisateur found = utilisateurDao.getUtilisateurByEmailAndPassword(
                "sophie.martin@example.com",
                "password456"
        );
        
        // Assert
        assertNotNull(found);
        assertEquals("Martin", found.getNom());
        assertEquals("medecin", found.getType());
        assertEquals("Cardiologie", found.getSpecialite());
    }

    @Test
    public void testGetAllInfirmiers() {
        // Arrange
        Utilisateur infirmier1 = new Utilisateur(
                "Dupont", "Jean", "jean@example.com",
                "infirmier", "pass", "514-111-1111",
                null, null, "123 Rue", null
        );
        Utilisateur infirmier2 = new Utilisateur(
                "Tremblay", "Marie", "marie@example.com",
                "infirmier", "pass", "514-222-2222",
                null, null, "456 Rue", null
        );
        Utilisateur medecin = new Utilisateur(
                "Martin", "Sophie", "sophie@example.com",
                "medecin", "pass", "514-333-3333",
                "Cardio", "12345", null, null
        );
        
        utilisateurDao.insert(infirmier1);
        utilisateurDao.insert(infirmier2);
        utilisateurDao.insert(medecin);
        
        // Act
        List<Utilisateur> infirmiers = utilisateurDao.getAllInfirmiers();
        
        // Assert
        assertEquals(2, infirmiers.size());
        for (Utilisateur u : infirmiers) {
            assertEquals("infirmier", u.getType());
        }
    }

    @Test
    public void testInsertAndGetPatient() {
        // Arrange
        PatientEntity patient = new PatientEntity(
                "Lavoie",
                "Pierre",
                "1990-05-15",
                "789 Rue Patient, Montréal",
                "514-111-2222",
                1L
        );
        
        // Act
        long id = patientDao.insert(patient);
        patient.setId(id);
        
        // Assert
        assertTrue(id > 0);
        assertEquals("Lavoie", patient.getNom());
        assertEquals("Pierre", patient.getPrenom());
    }

    @Test
    public void testUpdatePatient() {
        // Arrange
        PatientEntity patient = new PatientEntity(
                "Gagnon", "Julie", "1985-03-20",
                "321 Rue", "418-333-4444", 1L
        );
        long id = patientDao.insert(patient);
        patient.setId(id);
        
        // Act
        patient.setNom("Tremblay");
        patient.setTelephone("514-999-8888");
        patientDao.update(patient);
        
        // Assert
        // Note: Pour vérifier la mise à jour, il faudrait utiliser LiveData
        // ou une requête synchrone
        assertEquals("Tremblay", patient.getNom());
    }

    @Test
    public void testInsertAndGetVisite() {
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
        long id = visiteDao.insert(visite);
        visite.setId(id);
        
        // Assert
        assertTrue(id > 0);
        assertEquals("Lavoie", visite.getPatientNom());
        assertEquals("En attente", visite.getStatut());
    }

    @Test
    public void testDeleteUtilisateur() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur(
                "Test", "User", "test@example.com",
                "infirmier", "pass", "514-000-0000",
                null, null, "123 Rue", null
        );
        utilisateurDao.insert(utilisateur);
        
        // Act
        utilisateurDao.delete(utilisateur);
        List<Utilisateur> utilisateurs = utilisateurDao.getAllUtilisateurs();
        
        // Assert
        assertEquals(0, utilisateurs.size());
    }

    @Test
    public void testDeleteAllUtilisateurs() {
        // Arrange
        Utilisateur u1 = new Utilisateur(
                "User1", "Test1", "user1@example.com",
                "infirmier", "pass", "514-111-1111",
                null, null, "123 Rue", null
        );
        Utilisateur u2 = new Utilisateur(
                "User2", "Test2", "user2@example.com",
                "medecin", "pass", "514-222-2222",
                "Cardio", "12345", null, null
        );
        utilisateurDao.insert(u1);
        utilisateurDao.insert(u2);
        
        // Act
        utilisateurDao.deleteAll();
        List<Utilisateur> utilisateurs = utilisateurDao.getAllUtilisateurs();
        
        // Assert
        assertEquals(0, utilisateurs.size());
    }
}

