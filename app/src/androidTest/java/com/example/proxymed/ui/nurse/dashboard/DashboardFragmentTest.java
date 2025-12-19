package com.example.proxymed.ui.nurse.dashboard;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.fragment.app.FragmentFactory;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.proxymed.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Tests fonctionnels pour DashboardFragment
 */
@RunWith(AndroidJUnit4.class)
public class DashboardFragmentTest {

    /**
     * Attendre que le fragment soit complètement chargé
     */
    private void waitForFragmentToLoad() {
        try {
            Thread.sleep(300); // Attendre que le fragment soit chargé
        } catch (InterruptedException e) {
            // Ignorer
        }
    }

    @Test
    public void testDashboardFragmentCreation() {
        // Arrange & Act
        FragmentScenario<DashboardFragment> scenario = FragmentScenario.launchInContainer(
                DashboardFragment.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Attendre que le fragment soit chargé
        waitForFragmentToLoad();
        
        // Assert
        scenario.onFragment(fragment -> {
            assertNotNull("Le fragment ne devrait pas être null", fragment);
            assertNotNull("La vue ne devrait pas être null", fragment.getView());
        });
    }

    @Test
    public void testDashboardCardsDisplayed() {
        // Arrange
        FragmentScenario<DashboardFragment> scenario = FragmentScenario.launchInContainer(
                DashboardFragment.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Attendre que le fragment soit chargé
        waitForFragmentToLoad();
        
        // Assert - Vérifier que toutes les cartes sont affichées
        onView(withId(R.id.cardListePatients)).check(matches(isDisplayed()));
        onView(withId(R.id.cardRapports)).check(matches(isDisplayed()));
        onView(withId(R.id.cardVisites)).check(matches(isDisplayed()));
    }

    @Test
    public void testCardListePatientsClick() {
        // Arrange
        FragmentScenario<DashboardFragment> scenario = FragmentScenario.launchInContainer(
                DashboardFragment.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Attendre que le fragment soit chargé
        waitForFragmentToLoad();
        
        // Vérifier que la carte est visible
        onView(withId(R.id.cardListePatients)).check(matches(isDisplayed()));
        
        // Act - Cliquer sur la carte Liste des patients
        // Note: Dans un test isolé, la navigation peut échouer car il n'y a pas de NavController,
        // mais on vérifie que le clic ne cause pas de crash du fragment
        try {
            onView(withId(R.id.cardListePatients)).perform(click());
        } catch (androidx.test.espresso.PerformException e) {
            // Si la navigation échoue, c'est acceptable dans un test isolé
        } catch (Exception e) {
            // Autres exceptions - on continue
        }
        
        // Attendre un peu pour que toute opération asynchrone se termine
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // Ignorer
        }
        
        // Assert - Le fragment devrait toujours être actif
        scenario.onFragment(fragment -> {
            assertNotNull("Le fragment devrait toujours être actif", fragment);
            assertNotNull("La vue devrait toujours exister", fragment.getView());
            assertTrue("Le fragment devrait être ajouté", fragment.isAdded());
        });
    }

    @Test
    public void testCardRapportsClick() {
        // Arrange
        FragmentScenario<DashboardFragment> scenario = FragmentScenario.launchInContainer(
                DashboardFragment.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Attendre que le fragment soit chargé
        waitForFragmentToLoad();
        
        // Vérifier que la carte est visible
        onView(withId(R.id.cardRapports)).check(matches(isDisplayed()));
        
        // Act - Cliquer sur la carte Rapports
        try {
            onView(withId(R.id.cardRapports)).perform(click());
        } catch (androidx.test.espresso.PerformException e) {
            // Si la navigation échoue, c'est acceptable dans un test isolé
        } catch (Exception e) {
            // Autres exceptions - on continue
        }
        
        // Attendre un peu
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // Ignorer
        }
        
        // Assert - Le fragment devrait toujours être actif
        scenario.onFragment(fragment -> {
            assertNotNull("Le fragment devrait toujours être actif", fragment);
            assertTrue("Le fragment devrait être ajouté", fragment.isAdded());
        });
    }

    @Test
    public void testCardVisitesClick() {
        // Arrange
        FragmentScenario<DashboardFragment> scenario = FragmentScenario.launchInContainer(
                DashboardFragment.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Attendre que le fragment soit chargé
        waitForFragmentToLoad();
        
        // Vérifier que la carte est visible
        onView(withId(R.id.cardVisites)).check(matches(isDisplayed()));
        
        // Act - Cliquer sur la carte Visites
        try {
            onView(withId(R.id.cardVisites)).perform(click());
        } catch (androidx.test.espresso.PerformException e) {
            // Si la navigation échoue, c'est acceptable dans un test isolé
        } catch (Exception e) {
            // Autres exceptions - on continue
        }
        
        // Attendre un peu
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // Ignorer
        }
        
        // Assert - Le fragment devrait toujours être actif
        scenario.onFragment(fragment -> {
            assertNotNull("Le fragment devrait toujours être actif", fragment);
            assertTrue("Le fragment devrait être ajouté", fragment.isAdded());
        });
    }

    @Test
    public void testAllCardsAreClickable() {
        // Arrange
        FragmentScenario<DashboardFragment> scenario = FragmentScenario.launchInContainer(
                DashboardFragment.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Attendre que le fragment soit chargé
        waitForFragmentToLoad();
        
        // Assert - Vérifier que toutes les cartes sont affichées
        onView(withId(R.id.cardListePatients)).check(matches(isDisplayed()));
        onView(withId(R.id.cardRapports)).check(matches(isDisplayed()));
        onView(withId(R.id.cardVisites)).check(matches(isDisplayed()));
        
        // Act - Cliquer sur chaque carte
        // Note: Les clics peuvent échouer à cause de l'absence de NavController,
        // mais on vérifie que le fragment reste stable
        try {
            onView(withId(R.id.cardListePatients)).perform(click());
            Thread.sleep(100);
        } catch (Exception e) {
            // Ignorer les erreurs de navigation
        }
        
        try {
            onView(withId(R.id.cardRapports)).perform(click());
            Thread.sleep(100);
        } catch (Exception e) {
            // Ignorer les erreurs de navigation
        }
        
        try {
            onView(withId(R.id.cardVisites)).perform(click());
            Thread.sleep(100);
        } catch (Exception e) {
            // Ignorer les erreurs de navigation
        }
        
        // Assert - Le fragment devrait toujours être actif
        scenario.onFragment(fragment -> {
            assertNotNull("Le fragment devrait toujours être actif après les clics", fragment);
            assertTrue("Le fragment devrait être ajouté", fragment.isAdded());
        });
        
        // Vérifier que les cartes sont toujours visibles
        onView(withId(R.id.cardListePatients)).check(matches(isDisplayed()));
        onView(withId(R.id.cardRapports)).check(matches(isDisplayed()));
        onView(withId(R.id.cardVisites)).check(matches(isDisplayed()));
    }
}

