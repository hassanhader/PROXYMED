package com.example.proxymed.data.repository;

import android.app.Application;

import com.example.proxymed.data.database.AppDatabase;
import com.example.proxymed.data.model.Utilisateur;
import com.example.proxymed.data.repository.Callback;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Tests unitaires pour UtilisateurRepository
 * Note: Ces tests utilisent Robolectric pour simuler l'environnement Android
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class UtilisateurRepositoryTest {

    private Application application;
    private UtilisateurRepository repository;
    private Utilisateur testUtilisateur;

    @Before
    public void setUp() {
        application = RuntimeEnvironment.getApplication();
        
        // Créer le repository avec l'application
        repository = new UtilisateurRepository(application);
        
        // Créer un utilisateur de test
        testUtilisateur = new Utilisateur(
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
        
        // Nettoyer la base de données avant chaque test
        try {
            AppDatabase.getInstance(application).utilisateurDao().deleteAll();
        } catch (Exception e) {
            // Ignorer les erreurs de nettoyage
        }
    }

    @After
    public void tearDown() {
        // Nettoyer la base de données après chaque test
        try {
            AppDatabase.getInstance(application).utilisateurDao().deleteAll();
        } catch (Exception e) {
            // Ignorer les erreurs de nettoyage
        }
        // Ne pas fermer la base de données car c'est un singleton partagé
    }

    @Test
    public void testInsertUtilisateur() throws InterruptedException {
        // Arrange & Act
        repository.insertUtilisateur(testUtilisateur);
        
        // Attendre que l'insertion soit terminée
        Thread.sleep(500);
        
        // Assert - Vérifier que l'utilisateur a été inséré
        Utilisateur retrieved = AppDatabase.getInstance(application).utilisateurDao().getUtilisateurByEmail("jean.dupont@example.com");
        assertNotNull("L'utilisateur devrait être inséré", retrieved);
        assertEquals("Le nom devrait correspondre", "Dupont", retrieved.getNom());
        assertEquals("Le prénom devrait correspondre", "Jean", retrieved.getPrenom());
        assertEquals("L'email devrait correspondre", "jean.dupont@example.com", retrieved.getEmail());
    }

    @Test
    public void testGetAllUtilisateurs() throws InterruptedException {
        // Arrange
        // Insérer d'abord un utilisateur
        AppDatabase.getInstance(application).utilisateurDao().insert(testUtilisateur);
        
        CountDownLatch latch = new CountDownLatch(1);
        List<Utilisateur> result = new ArrayList<>();
        
        Callback callback = new Callback() {
            @Override
            public void onResult(List<Utilisateur> utilisateurs) {
                result.addAll(utilisateurs);
                latch.countDown();
            }
        };
        
        // Act
        repository.getAllUtilisateurs(callback);
        
        // Attendre que le callback soit appelé
        boolean completed = latch.await(2, TimeUnit.SECONDS);
        
        // Assert
        assertTrue("Le callback devrait être appelé", completed);
        assertNotNull("La liste des utilisateurs ne devrait pas être null", result);
        assertFalse("La liste devrait contenir au moins un utilisateur", result.isEmpty());
        assertEquals("Le nombre d'utilisateurs devrait être 1", 1, result.size());
        assertEquals("L'email devrait correspondre", "jean.dupont@example.com", result.get(0).getEmail());
    }

    @Test
    public void testGetUtilisateurByEmailAndPassword() throws InterruptedException {
        // Arrange
        String email = "jean.dupont@example.com";
        String password = "password123";
        
        // Insérer l'utilisateur dans la base de données
        AppDatabase.getInstance(application).utilisateurDao().insert(testUtilisateur);
        Thread.sleep(200);
        
        // Act
        Utilisateur retrieved = repository.getUtilisateurByEmailAndPassword(email, password);
        
        // Assert
        assertNotNull("L'utilisateur devrait être trouvé avec les bons identifiants", retrieved);
        assertEquals("L'email devrait correspondre", email, retrieved.getEmail());
        assertEquals("Le mot de passe devrait correspondre", password, retrieved.getMotDePasse());
        
        // Test avec de mauvais identifiants
        Utilisateur wrongUser = repository.getUtilisateurByEmailAndPassword(email, "wrongpassword");
        assertNull("L'utilisateur ne devrait pas être trouvé avec un mauvais mot de passe", wrongUser);
    }

    @Test
    public void testGetUtilisateurByEmail() throws InterruptedException {
        // Arrange
        String email = "jean.dupont@example.com";
        
        // Insérer l'utilisateur dans la base de données
        AppDatabase.getInstance(application).utilisateurDao().insert(testUtilisateur);
        Thread.sleep(200);
        
        // Act
        Utilisateur retrieved = repository.getUtilisateurByEmail(email);
        
        // Assert
        assertNotNull("L'utilisateur devrait être trouvé par email", retrieved);
        assertEquals("L'email devrait correspondre", email, retrieved.getEmail());
        assertEquals("Le nom devrait correspondre", "Dupont", retrieved.getNom());
        
        // Test avec un email inexistant
        Utilisateur notFound = repository.getUtilisateurByEmail("nonexistent@example.com");
        assertNull("Aucun utilisateur ne devrait être trouvé avec un email inexistant", notFound);
    }

    @Test
    public void testUpdateUtilisateur() throws InterruptedException {
        // Arrange
        // Insérer l'utilisateur dans la base de données
        AppDatabase.getInstance(application).utilisateurDao().insert(testUtilisateur);
        Thread.sleep(200);
        
        // Récupérer l'utilisateur pour obtenir son ID
        Utilisateur userToUpdate = AppDatabase.getInstance(application).utilisateurDao().getUtilisateurByEmail("jean.dupont@example.com");
        assertNotNull("L'utilisateur devrait exister", userToUpdate);
        
        // Modifier le nom
        userToUpdate.setNom("Tremblay");
        
        // Act
        repository.update(userToUpdate);
        Thread.sleep(200);
        
        // Assert
        Utilisateur updated = AppDatabase.getInstance(application).utilisateurDao().getUtilisateurByEmail("jean.dupont@example.com");
        assertNotNull("L'utilisateur mis à jour devrait exister", updated);
        assertEquals("Le nom devrait être mis à jour", "Tremblay", updated.getNom());
        assertEquals("L'email ne devrait pas changer", "jean.dupont@example.com", updated.getEmail());
    }

    @Test
    public void testDeleteAllUtilisateurs() throws InterruptedException {
        // Arrange
        // Insérer plusieurs utilisateurs
        AppDatabase db = AppDatabase.getInstance(application);
        db.utilisateurDao().insert(testUtilisateur);
        
        Utilisateur secondUser = new Utilisateur(
                "Martin",
                "Marie",
                "marie.martin@example.com",
                "médecin",
                "password456",
                "514-987-6543",
                "Cardiologie",
                "12345",
                null,
                null
        );
        db.utilisateurDao().insert(secondUser);
        Thread.sleep(200);
        
        // Vérifier qu'il y a des utilisateurs
        List<Utilisateur> beforeDelete = db.utilisateurDao().getAllUtilisateurs();
        assertEquals("Il devrait y avoir 2 utilisateurs avant la suppression", 2, beforeDelete.size());
        
        // Act
        repository.deleteAllUtilisateurs();
        Thread.sleep(300);
        
        // Assert
        List<Utilisateur> afterDelete = db.utilisateurDao().getAllUtilisateurs();
        assertTrue("La liste devrait être vide après la suppression", afterDelete.isEmpty());
    }
}

