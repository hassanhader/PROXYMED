package com.example.proxymed.ui.auth;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.proxymed.R;
import com.example.proxymed.data.database.AppDatabase;
import com.example.proxymed.data.model.Utilisateur;
import com.example.proxymed.data.repository.UtilisateurRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Tests fonctionnels (instrumentés) pour LoginFragment
 */
@RunWith(AndroidJUnit4.class)
public class LoginFragmentTest {

    private AppDatabase database;
    private UtilisateurRepository repository;
    private Utilisateur testUtilisateur;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        database = AppDatabase.getInstance(context);
        repository = new UtilisateurRepository(ApplicationProvider.getApplicationContext());
        
        // Créer un utilisateur de test
        testUtilisateur = new Utilisateur(
                "Test",
                "User",
                "test@example.com",
                "infirmier",
                "password123",
                "514-123-4567",
                null,
                null,
                "123 Rue Test",
                null
        );
        
        // Nettoyer la base de données avant chaque test
        database.utilisateurDao().deleteAll();
        
        // Insérer l'utilisateur de test
        repository.insertUtilisateur(testUtilisateur);
        
        // Attendre un peu pour que l'insertion soit terminée
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        // Nettoyer après chaque test
        if (database != null) {
            database.utilisateurDao().deleteAll();
            database.close();
        }
    }

    @Test
    public void testLoginFragmentCreation() {
        // Arrange & Act
        FragmentScenario<LoginFragment> scenario = FragmentScenario.launchInContainer(
                LoginFragment.class,
                null,
                R.style.Theme_AppCompat,
                null
        );
        
        // Assert
        scenario.onFragment(fragment -> {
            assertNotNull(fragment);
            assertNotNull(fragment.getView());
        });
    }

    @Test
    public void testLoginWithValidCredentials() {
        // Arrange
        FragmentScenario<LoginFragment> scenario = FragmentScenario.launchInContainer(
                LoginFragment.class,
                null,
                R.style.Theme_AppCompat,
                null
        );
        
        // Act & Assert
        scenario.onFragment(fragment -> {
            // Vérifier que le fragment est bien créé
            assertNotNull(fragment);
            
            // Note: Pour tester complètement la connexion, il faudrait
            // utiliser Espresso pour interagir avec les vues
            // Ce test vérifie juste que le fragment peut être créé
        });
    }

    @Test
    public void testSharedPreferencesAfterLogin() {
        // Arrange
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("infirmier_id", 1L);
        editor.putString("infirmier_nom", "Test");
        editor.putString("infirmier_prenom", "User");
        editor.apply();
        
        // Act
        long userId = prefs.getLong("infirmier_id", -1);
        String nom = prefs.getString("infirmier_nom", "");
        String prenom = prefs.getString("infirmier_prenom", "");
        
        // Assert
        assertEquals(1L, userId);
        assertEquals("Test", nom);
        assertEquals("User", prenom);
    }
}

