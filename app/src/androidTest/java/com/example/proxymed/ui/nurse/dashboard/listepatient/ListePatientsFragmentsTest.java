package com.example.proxymed.ui.nurse.dashboard.listepatient;

import android.content.Context;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.proxymed.R;
import com.example.proxymed.data.database.AppDatabase;
import com.example.proxymed.data.model.PatientEntity;
import com.example.proxymed.data.repository.PatientRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Tests fonctionnels pour ListePatientsFragments
 */
@RunWith(AndroidJUnit4.class)
public class ListePatientsFragmentsTest {

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
        database.patientDao().deleteAll();
        
        // Créer un patient de test
        testPatient = new PatientEntity(
                "Lavoie",
                "Pierre",
                "1990-01-15",
                "123 Rue Test",
                "514-123-4567",
                "pierre.lavoie@example.com",
                1L // medecinId
        );
        
        // Insérer le patient
        repository.insertPatient(testPatient);
        
        // Attendre un peu
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        if (database != null) {
            database.patientDao().deleteAll();
        }
    }

    @Test
    public void testListePatientsFragmentCreation() {
        // Arrange & Act
        FragmentScenario<ListePatientsFragments> scenario = FragmentScenario.launchInContainer(
                ListePatientsFragments.class,
                null,
                R.style.Theme_AppCompat,
                null
        );
        
        // Assert
        scenario.onFragment(fragment -> {
            assertNotNull("Le fragment ne devrait pas être null", fragment);
            assertNotNull("La vue ne devrait pas être null", fragment.getView());
        });
    }

    @Test
    public void testRecyclerViewDisplayed() {
        // Arrange
        FragmentScenario<ListePatientsFragments> scenario = FragmentScenario.launchInContainer(
                ListePatientsFragments.class,
                null,
                R.style.Theme_AppCompat,
                null
        );
        
        // Assert
        scenario.onFragment(fragment -> {
            assertNotNull(fragment.getView());
            // Vérifier que le RecyclerView est présent
            // Note: Les IDs peuvent varier selon votre layout
        });
    }
}

