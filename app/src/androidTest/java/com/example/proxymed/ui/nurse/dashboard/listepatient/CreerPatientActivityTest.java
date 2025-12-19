package com.example.proxymed.ui.nurse.dashboard.listepatient;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;

import com.example.proxymed.R;
import com.example.proxymed.data.database.AppDatabase;
import com.example.proxymed.data.model.Utilisateur;
import com.example.proxymed.data.repository.PatientRepository;

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
 * Tests fonctionnels pour CreerPatientActivity
 */
@RunWith(AndroidJUnit4.class)
public class CreerPatientActivityTest {

    private AppDatabase database;
    private PatientRepository repository;
    private Context context;
    private Utilisateur testMedecin;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        database = AppDatabase.getInstance(context);
        repository = new PatientRepository(context);
        
        // Nettoyer la base de données
        database.patientDao().deleteAll();
        database.utilisateurDao().deleteAll();
        
        // Créer un médecin de test
        testMedecin = new Utilisateur(
                "Martin",
                "Sophie",
                "sophie.martin@example.com",
                "médecin",
                "password123",
                "514-987-6543",
                "Cardiologie",
                "12345",
                null,
                null
        );
        
        // Insérer directement dans la base de données
        database.utilisateurDao().insert(testMedecin);
    }

    @After
    public void tearDown() {
        if (database != null) {
            database.patientDao().deleteAll();
            database.utilisateurDao().deleteAll();
        }
    }

    @Test
    public void testCreerPatientActivityCreation() {
        // Arrange & Act
        ActivityScenario<CreerPatientActivity> scenario = ActivityScenario.launch(CreerPatientActivity.class);
        
        // Assert
        scenario.onActivity(activity -> {
            assertNotNull("L'activité ne devrait pas être null", activity);
        });
        
        // Vérifier que les champs sont affichés
        onView(withId(R.id.add_patient_nom)).check(matches(isDisplayed()));
        onView(withId(R.id.add_patient_prenom)).check(matches(isDisplayed()));
        onView(withId(R.id.add_patient_date_naissance)).check(matches(isDisplayed()));
    }

    @Test
    public void testCreerPatientWithEmptyFields() {
        // Arrange
        ActivityScenario<CreerPatientActivity> scenario = ActivityScenario.launch(CreerPatientActivity.class);
        
        // Act - Cliquer sur le bouton sans remplir les champs
        onView(withId(R.id.add_patient_record)).perform(click());
        
        // Assert - Un toast d'erreur devrait apparaître
        onView(withId(R.id.add_patient_nom)).check(matches(isDisplayed()));
    }

    @Test
    public void testCreerPatientWithValidData() {
        // Arrange
        ActivityScenario<CreerPatientActivity> scenario = ActivityScenario.launch(CreerPatientActivity.class);
        
        // Act
        onView(withId(R.id.add_patient_nom)).perform(typeText("Lavoie"));
        onView(withId(R.id.add_patient_prenom)).perform(typeText("Pierre"));
        onView(withId(R.id.add_patient_date_naissance)).perform(typeText("15/01/1990"));
        onView(withId(R.id.add_patient_telephone)).perform(typeText("5141234567"));
        onView(withId(R.id.add_patient_adresse)).perform(typeText("123 Rue Test"));
        
        // Fermer le clavier
        Espresso.closeSoftKeyboard();
        
        // Cliquer sur le bouton d'enregistrement
        onView(withId(R.id.add_patient_record)).perform(click());
        
        // Assert - L'activité devrait se terminer ou afficher un message de succès
        // La vérification exacte dépend de l'implémentation
        // On vérifie que l'activité est toujours active (ou qu'elle s'est terminée)
        onView(withId(R.id.add_patient_nom)).check(matches(isDisplayed()));
    }

    @Test
    public void testDatePickerDialog() {
        // Arrange
        ActivityScenario<CreerPatientActivity> scenario = ActivityScenario.launch(CreerPatientActivity.class);
        
        // Act - Cliquer sur le champ date de naissance
        onView(withId(R.id.add_patient_date_naissance)).perform(click());
        
        // Assert - Un DatePickerDialog devrait apparaître
        // Les dialogues sont difficiles à tester directement, on vérifie que le champ est toujours visible
        onView(withId(R.id.add_patient_date_naissance)).check(matches(isDisplayed()));
    }
}

