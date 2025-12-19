package com.example.proxymed.ui.auth;

import android.content.Context;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import com.example.proxymed.R;
import com.example.proxymed.data.database.AppDatabase;
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
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Tests fonctionnels pour SignUpFragment
 */
@RunWith(AndroidJUnit4.class)
public class SignUpFragmentTest {

    private AppDatabase database;
    private UtilisateurRepository repository;
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        database = AppDatabase.getInstance(context);
        repository = new UtilisateurRepository(context);
        
        // Nettoyer la base de données avant chaque test
        database.utilisateurDao().deleteAll();
    }

    @After
    public void tearDown() {
        // Nettoyer la base de données après chaque test
        if (database != null) {
            database.utilisateurDao().deleteAll();
        }
    }

    @Test
    public void testSignUpFragmentCreation() {
        // Arrange & Act
        FragmentScenario<SignUpFragment> scenario = FragmentScenario.launchInContainer(
                SignUpFragment.class,
                null,
                R.style.Theme_AppCompat,
                null
        );
        
        // Assert
        scenario.onFragment(fragment -> {
            assert fragment != null;
            assert fragment.getView() != null;
        });
        
        // Vérifier que les champs sont affichés
        onView(withId(R.id.etFullName)).check(matches(isDisplayed()));
        onView(withId(R.id.etEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.etPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.etConfirmPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.etPhone)).check(matches(isDisplayed()));
        onView(withId(R.id.btnSubmit)).check(matches(isDisplayed()));
    }

    @Test
    public void testSignUpWithEmptyFields() {
        // Arrange
        FragmentScenario<SignUpFragment> scenario = FragmentScenario.launchInContainer(
                SignUpFragment.class,
                null,
                R.style.Theme_AppCompat,
                null
        );
        
        // Act - Cliquer sur le bouton sans remplir les champs
        onView(withId(R.id.btnSubmit)).perform(click());
        
        // Assert - Un toast d'erreur devrait apparaître (testé via l'absence de navigation)
        // Note: Les toasts sont difficiles à tester directement, on vérifie que la navigation n'a pas eu lieu
        onView(withId(R.id.etFullName)).check(matches(isDisplayed()));
    }

    @Test
    public void testSignUpWithInvalidEmail() {
        // Arrange
        FragmentScenario<SignUpFragment> scenario = FragmentScenario.launchInContainer(
                SignUpFragment.class,
                null,
                R.style.Theme_AppCompat,
                null
        );
        
        // Act
        onView(withId(R.id.etFullName)).perform(typeText("Jean Dupont"));
        onView(withId(R.id.etEmail)).perform(typeText("email-invalide"));
        onView(withId(R.id.etPassword)).perform(typeText("password123"));
        onView(withId(R.id.etConfirmPassword)).perform(typeText("password123"));
        onView(withId(R.id.etPhone)).perform(typeText("5141234567"));
        onView(withId(R.id.btnSubmit)).perform(click());
        
        // Assert - Le fragment devrait toujours être visible (pas de navigation)
        onView(withId(R.id.etFullName)).check(matches(isDisplayed()));
    }

    @Test
    public void testSignUpWithPasswordMismatch() {
        // Arrange
        FragmentScenario<SignUpFragment> scenario = FragmentScenario.launchInContainer(
                SignUpFragment.class,
                null,
                R.style.Theme_AppCompat,
                null
        );
        
        // Act
        onView(withId(R.id.etFullName)).perform(typeText("Jean Dupont"));
        onView(withId(R.id.etEmail)).perform(typeText("jean@example.com"));
        onView(withId(R.id.etPassword)).perform(typeText("password123"));
        onView(withId(R.id.etConfirmPassword)).perform(typeText("password456")); // Mots de passe différents
        onView(withId(R.id.etPhone)).perform(typeText("5141234567"));
        onView(withId(R.id.btnSubmit)).perform(click());
        
        // Assert - Le fragment devrait toujours être visible
        onView(withId(R.id.etFullName)).check(matches(isDisplayed()));
    }

    @Test
    public void testSignUpWithInvalidPhone() {
        // Arrange
        FragmentScenario<SignUpFragment> scenario = FragmentScenario.launchInContainer(
                SignUpFragment.class,
                null,
                R.style.Theme_AppCompat,
                null
        );
        
        // Act
        onView(withId(R.id.etFullName)).perform(typeText("Jean Dupont"));
        onView(withId(R.id.etEmail)).perform(typeText("jean@example.com"));
        onView(withId(R.id.etPassword)).perform(typeText("password123"));
        onView(withId(R.id.etConfirmPassword)).perform(typeText("password123"));
        onView(withId(R.id.etPhone)).perform(typeText("123")); // Numéro trop court
        onView(withId(R.id.btnSubmit)).perform(click());
        
        // Assert
        onView(withId(R.id.etFullName)).check(matches(isDisplayed()));
    }

    @Test
    public void testProfilePictureClick() {
        // Arrange
        FragmentScenario<SignUpFragment> scenario = FragmentScenario.launchInContainer(
                SignUpFragment.class,
                null,
                R.style.Theme_AppCompat,
                null
        );
        
        // Act - Cliquer sur l'image de profil
        onView(withId(R.id.ivProfilePicture)).perform(click());
        
        // Assert - Un dialogue devrait apparaître (difficile à tester directement)
        // On vérifie que le fragment est toujours actif
        onView(withId(R.id.etFullName)).check(matches(isDisplayed()));
    }
}

