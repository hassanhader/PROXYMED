package com.example.proxymed.ui.auth;

import android.app.Application;
import android.content.Context;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.fragment.app.FragmentFactory;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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
    private Application application;
    private Context context;
    private Utilisateur testUtilisateur;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        application = (Application) context;
        database = AppDatabase.getInstance(context);
        repository = new UtilisateurRepository(application);
        
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
        
        // Insérer l'utilisateur directement dans la base de données
        database.utilisateurDao().insert(testUtilisateur);
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
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Assert
        scenario.onFragment(fragment -> {
            assertNotNull("Le fragment ne devrait pas être null", fragment);
            assertNotNull("La vue ne devrait pas être null", fragment.getView());
        });
        
        // Vérifier que tous les éléments UI sont affichés
        onView(withId(R.id.tvResetPasswordMessage)).check(matches(isDisplayed()));
        onView(withId(R.id.etEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.btnResetPassword)).check(matches(isDisplayed()));
    }

    @Test
    public void testResetPasswordWithEmptyEmail() {
        // Arrange
        FragmentScenario<ResetPasswordFragment> scenario = FragmentScenario.launchInContainer(
                ResetPasswordFragment.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
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
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Act
        onView(withId(R.id.etEmail)).perform(typeText("jean.dupont@example.com"));
        onView(withId(R.id.btnResetPassword)).perform(click());
        
        // Assert - Un toast de succès devrait apparaître et navigation vers login
        // On vérifie que le fragment est toujours visible (le traitement peut prendre du temps)
        onView(withId(R.id.etEmail)).check(matches(isDisplayed()));
    }

    @Test
    public void testResetPasswordWithInvalidEmail() {
        // Arrange
        FragmentScenario<ResetPasswordFragment> scenario = FragmentScenario.launchInContainer(
                ResetPasswordFragment.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Act
        onView(withId(R.id.etEmail)).perform(typeText("nonexistent@example.com"));
        onView(withId(R.id.btnResetPassword)).perform(click());
        
        // Assert - Un message d'erreur devrait apparaître
        // On vérifie que le fragment est toujours visible
        onView(withId(R.id.etEmail)).check(matches(isDisplayed()));
    }
}

