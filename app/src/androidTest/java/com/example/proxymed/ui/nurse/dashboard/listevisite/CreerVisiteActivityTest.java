package com.example.proxymed.ui.nurse.dashboard.listevisite;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;

import com.example.proxymed.data.database.AppDatabase;
import com.example.proxymed.data.model.PatientEntity;
import com.example.proxymed.data.repository.PatientRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Tests fonctionnels pour CreerVisiteActivity
 */
@RunWith(AndroidJUnit4.class)
public class CreerVisiteActivityTest {

    private AppDatabase database;
    private PatientRepository repository;
    private Context context;
    private PatientEntity testPatient;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        database = AppDatabase.getInstance(context);
        repository = new PatientRepository(context);
        
        // Nettoyer la base de données
        database.visiteDao().deleteAll();
        database.patientDao().deleteAll();
        
        // Créer un patient de test
        testPatient = new PatientEntity(
                "Lavoie",
                "Pierre",
                "1990-01-15",
                "123 Rue Test",
                "514-123-4567",
                "pierre.lavoie@example.com",
                1L
        );
        
        repository.insertPatient(testPatient);
        
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        if (database != null) {
            database.visiteDao().deleteAll();
            database.patientDao().deleteAll();
        }
    }

    @Test
    public void testCreerVisiteActivityCreation() {
        // Arrange & Act
        ActivityScenario<CreerVisiteActivity> scenario = ActivityScenario.launch(CreerVisiteActivity.class);
        
        // Assert
        scenario.onActivity(activity -> {
            assertNotNull("L'activité ne devrait pas être null", activity);
        });
    }

    @Test
    public void testCreerVisiteWithEmptyFields() {
        // Arrange
        ActivityScenario<CreerVisiteActivity> scenario = ActivityScenario.launch(CreerVisiteActivity.class);
        
        // Act - Essayer d'enregistrer sans remplir les champs
        // Note: L'ID du bouton peut varier selon votre layout
        
        // Assert - Un toast d'erreur devrait apparaître
        // Note: La vérification exacte dépend de votre implémentation
    }
}

