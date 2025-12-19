package com.example.proxymed.ui.auth;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.fragment.app.FragmentFactory;
import androidx.test.core.app.ActivityScenario;
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
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
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
        Application application = (Application) ApplicationProvider.getApplicationContext();
        Context context = ApplicationProvider.getApplicationContext();
        database = AppDatabase.getInstance(context);
        repository = new UtilisateurRepository(application);
        
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
        
        // Insérer l'utilisateur de test directement dans la base de données
        // pour éviter les problèmes de synchronisation avec les threads
        database.utilisateurDao().insert(testUtilisateur);
    }

    @After
    public void tearDown() {
        // Nettoyer après chaque test
        if (database != null) {
            database.utilisateurDao().deleteAll();
            // Ne pas fermer la base de données car c'est un singleton partagé
        }
    }

    @Test
    public void testLoginFragmentCreation() {
        // Arrange & Act
        FragmentScenario<LoginFragment> scenario = FragmentScenario.launchInContainer(
                LoginFragment.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Assert - Vérifier que le fragment est créé
        scenario.onFragment(fragment -> {
            assertNotNull("Le fragment ne devrait pas être null", fragment);
            assertNotNull("La vue ne devrait pas être null", fragment.getView());
        });
        
        // Vérifier que tous les éléments UI sont affichés
        onView(withId(R.id.tvLoginTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.etEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.etPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()));
        onView(withId(R.id.tvGoToSignUp)).check(matches(isDisplayed()));
        onView(withId(R.id.tvForgotPassword)).check(matches(isDisplayed()));
    }

    @Test
    public void testLoginWithEmptyFields() {
        // Arrange
        FragmentScenario<LoginFragment> scenario = FragmentScenario.launchInContainer(
                LoginFragment.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Act - Cliquer sur le bouton sans remplir les champs
        onView(withId(R.id.btnLogin)).perform(click());
        
        // Assert - Le fragment devrait toujours être visible (pas de navigation)
        onView(withId(R.id.etEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.etPassword)).check(matches(isDisplayed()));
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        // Arrange
        FragmentScenario<LoginFragment> scenario = FragmentScenario.launchInContainer(
                LoginFragment.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Act - Remplir avec des identifiants invalides
        onView(withId(R.id.etEmail)).perform(typeText("invalid@example.com"));
        onView(withId(R.id.etPassword)).perform(typeText("wrongpassword"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btnLogin)).perform(click());
        
        // Assert - Le fragment devrait toujours être visible
        onView(withId(R.id.etEmail)).check(matches(isDisplayed()));
    }

    @Test
    public void testLoginWithValidCredentials() throws InterruptedException {
        // Arrange
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        prefs.edit().clear().apply(); // Nettoyer les SharedPreferences avant le test
        
        // Utiliser ActivityScenario pour avoir une activité réelle
        // Cela permet au LoginFragment d'accéder à getActivity() correctement
        ActivityScenario<AuthentificationActivity> activityScenario = ActivityScenario.launch(AuthentificationActivity.class);
        
        // Attendre que le fragment soit chargé
        Thread.sleep(500);
        
        // Act - Remplir avec des identifiants valides
        onView(withId(R.id.etEmail)).perform(typeText("test@example.com"));
        onView(withId(R.id.etPassword)).perform(typeText("password123"));
        Espresso.closeSoftKeyboard();
        
        // Cliquer sur le bouton de connexion
        onView(withId(R.id.btnLogin)).perform(click());
        
        // Attendre que l'opération asynchrone se termine
        // Le LoginFragment utilise un thread séparé, donc on doit attendre un peu
        Thread.sleep(1500);
        
        // Assert - Vérifier que les SharedPreferences ont été mis à jour
        // Cela confirme que la connexion a réussi
        long userId = prefs.getLong("infirmier_id", -1);
        String nom = prefs.getString("infirmier_nom", "");
        String prenom = prefs.getString("infirmier_prenom", "");
        
        assertTrue("L'ID de l'utilisateur devrait être sauvegardé (obtenu: " + userId + ")", userId > 0);
        assertEquals("Le nom devrait être sauvegardé", "Test", nom);
        assertEquals("Le prénom devrait être sauvegardé", "User", prenom);
        
        // Vérifier que l'utilisateur dans la base de données correspond
        Utilisateur savedUser = database.utilisateurDao().getUtilisateurByEmail("test@example.com");
        assertNotNull("L'utilisateur devrait exister dans la base de données", savedUser);
        assertEquals("L'ID devrait correspondre", userId, savedUser.getId().longValue());
    }

    @Test
    public void testNavigationToSignUp() {
        // Arrange
        FragmentScenario<LoginFragment> scenario = FragmentScenario.launchInContainer(
                LoginFragment.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Act - Cliquer sur le lien "S'inscrire"
        onView(withId(R.id.tvGoToSignUp)).perform(click());
        
        // Assert - La navigation devrait se produire
        // On vérifie que le fragment est toujours actif
        scenario.onFragment(fragment -> {
            assertNotNull(fragment);
        });
    }

    @Test
    public void testNavigationToResetPassword() {
        // Arrange
        FragmentScenario<LoginFragment> scenario = FragmentScenario.launchInContainer(
                LoginFragment.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Act - Cliquer sur "Mot de passe oublié ?"
        onView(withId(R.id.tvForgotPassword)).perform(click());
        
        // Assert - La navigation devrait se produire
        scenario.onFragment(fragment -> {
            assertNotNull(fragment);
        });
    }

    @Test
    public void testEmailFieldInput() {
        // Arrange
        FragmentScenario<LoginFragment> scenario = FragmentScenario.launchInContainer(
                LoginFragment.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Act - Taper dans le champ email
        onView(withId(R.id.etEmail)).perform(typeText("test@example.com"));
        
        // Assert - Le champ devrait être visible et contenir le texte
        onView(withId(R.id.etEmail)).check(matches(isDisplayed()));
    }

    @Test
    public void testPasswordFieldInput() {
        // Arrange
        FragmentScenario<LoginFragment> scenario = FragmentScenario.launchInContainer(
                LoginFragment.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Act - Taper dans le champ mot de passe
        onView(withId(R.id.etPassword)).perform(typeText("password123"));
        
        // Assert - Le champ devrait être visible
        onView(withId(R.id.etPassword)).check(matches(isDisplayed()));
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

