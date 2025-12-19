package com.example.proxymed.ui.nurse.dashboard.listepatient;

import android.content.Context;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.fragment.app.FragmentFactory;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.proxymed.R;
import com.example.proxymed.data.database.AppDatabase;
import com.example.proxymed.data.model.PatientEntity;
import com.example.proxymed.data.model.Utilisateur;
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
        database.utilisateurDao().deleteAll();
        
        // Créer un médecin pour satisfaire la contrainte de clé étrangère
        Utilisateur medecin = new Utilisateur(
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
        database.utilisateurDao().insert(medecin);
        
        // Récupérer le médecin inséré pour obtenir son ID
        Utilisateur insertedMedecin = database.utilisateurDao().getUtilisateurByEmail("sophie.martin@example.com");
        long medecinId = insertedMedecin.getId();
        
        // Créer un patient de test
        testPatient = new PatientEntity(
                "Lavoie",
                "Pierre",
                "1990-01-15",
                "123 Rue Test",
                "514-123-4567",
                medecinId // medecinId
        );
        
        // Insérer le patient directement dans la base de données
        // pour éviter les problèmes de synchronisation
        database.patientDao().insert(testPatient);
    }

    @After
    public void tearDown() {
        if (database != null) {
            database.patientDao().deleteAll();
            database.utilisateurDao().deleteAll();
        }
    }

    @Test
    public void testListePatientsFragmentCreation() {
        // Arrange & Act
        FragmentScenario<ListePatientsFragments> scenario = FragmentScenario.launchInContainer(
                ListePatientsFragments.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
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
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Assert - Vérifier que le RecyclerView est affiché
        onView(withId(R.id.rvPatients)).check(matches(isDisplayed()));
    }

    @Test
    public void testSearchViewDisplayed() {
        // Arrange
        FragmentScenario<ListePatientsFragments> scenario = FragmentScenario.launchInContainer(
                ListePatientsFragments.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Assert - Vérifier que la SearchView est affichée
        onView(withId(R.id.searchView)).check(matches(isDisplayed()));
    }

    @Test
    public void testFloatingActionButtonDisplayed() {
        // Arrange
        FragmentScenario<ListePatientsFragments> scenario = FragmentScenario.launchInContainer(
                ListePatientsFragments.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Assert - Vérifier que le FAB est affiché
        onView(withId(R.id.fab_add_patient)).check(matches(isDisplayed()));
    }

    @Test
    public void testFloatingActionButtonClick() {
        // Arrange
        FragmentScenario<ListePatientsFragments> scenario = FragmentScenario.launchInContainer(
                ListePatientsFragments.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Act - Cliquer sur le FAB
        onView(withId(R.id.fab_add_patient)).perform(click());
        
        // Assert - Le fragment devrait toujours être actif
        scenario.onFragment(fragment -> {
            assertNotNull("Le fragment devrait toujours être actif", fragment);
        });
    }
}

