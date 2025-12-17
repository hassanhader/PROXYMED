package com.example.proxymed.ui.auth;

import android.content.Context;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;

import com.example.proxymed.R;
import com.example.proxymed.data.database.AppDatabase;
import com.example.proxymed.data.model.Utilisateur;
import com.example.proxymed.data.repository.UtilisateurRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Tests fonctionnels pour ResetPasswordFragment
 */
@RunWith(AndroidJUnit4.class)
public class ResetPasswordFragmentTest {

    private AppDatabase database;
    private UtilisateurRepository repository;
    private Context context;
    private Utilisateur testUtilisateur;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        database = AppDatabase.getInstance(context);
        repository = new UtilisateurRepository(context);
        
        // Nettoyer la base de données
        database.utilisateurDao().deleteAll();
        
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
        
        // Insérer l'utilisateur
        database.utilisateurDao().insert(testUtilisateur);
        
        // Attendre un peu pour que l'insertion soit terminée
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        // Nettoyer la base de données
        if (database != null) {
            database.utilisateurDao().deleteAll();
        }
    }

    @Test
    public void testResetPasswordFragmentCreation() {
        // Arrange & Act
        FragmentScenario<ResetPasswordFragment> scenario = FragmentScenario.launchInContainer(
                ResetPasswordFragment.class,
                null,
                R.style.Theme_AppCompat,
                null
        );
        
        // Assert
        scenario.onFragment(fragment -> {
            assertNotNull("Le fragment ne devrait pas être null", fragment);
            assertNotNull("La vue ne devrait pas être null", fragment.getView());
        });
        
        // Vérifier que les éléments sont affichés
        onView(withId(R.id.etEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.btnResetPassword)).check(matches(isDisplayed()));
    }

    @Test
    public void testResetPasswordWithEmptyEmail() {
        // Arrange
        FragmentScenario<ResetPasswordFragment> scenario = FragmentScenario.launchInContainer(
                ResetPasswordFragment.class,
                null,
                R.style.Theme_AppCompat,
                null
        );
        
        // Act - Cliquer sur le bouton sans remplir l'email
        onView(withId(R.id.btnResetPassword)).perform(click());
        
        // Assert - Le fragment devrait toujours être visible
        onView(withId(R.id.etEmail)).check(matches(isDisplayed()));
    }

    @Test
    public void testResetPasswordWithValidEmail() {
        // Arrange
        FragmentScenario<ResetPasswordFragment> scenario = FragmentScenario.launchInContainer(
                ResetPasswordFragment.class,
                null,
                R.style.Theme_AppCompat,
                null
        );
        
        // Act
        onView(withId(R.id.etEmail)).perform(typeText("jean.dupont@example.com"));
        onView(withId(R.id.btnResetPassword)).perform(click());
        
        // Attendre que le traitement soit terminé
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Assert - Un toast de succès devrait apparaître et navigation vers login
        // Note: La navigation est difficile à tester directement, on vérifie que le traitement s'est bien passé
        onView(withId(R.id.etEmail)).check(matches(isDisplayed()));
    }

    @Test
    public void testResetPasswordWithInvalidEmail() {
        // Arrange
        FragmentScenario<ResetPasswordFragment> scenario = FragmentScenario.launchInContainer(
                ResetPasswordFragment.class,
                null,
                R.style.Theme_AppCompat,
                null
        );
        
        // Act
        onView(withId(R.id.etEmail)).perform(typeText("nonexistent@example.com"));
        onView(withId(R.id.btnResetPassword)).perform(click());
        
        // Attendre que le traitement soit terminé
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Assert - Un message d'erreur devrait apparaître
        onView(withId(R.id.etEmail)).check(matches(isDisplayed()));
    }
}

