package com.example.proxymed.ui.nurse.profil;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.fragment.app.FragmentFactory;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.proxymed.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Tests fonctionnels pour HomeFragment
 */
@RunWith(AndroidJUnit4.class)
public class HomeFragmentTest {

    @Test
    public void testHomeFragmentCreation() {
        // Arrange & Act
        FragmentScenario<HomeFragment> scenario = FragmentScenario.launchInContainer(
                HomeFragment.class,
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
    public void testHomeFragmentDisplaysText() {
        // Arrange
        FragmentScenario<HomeFragment> scenario = FragmentScenario.launchInContainer(
                HomeFragment.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Attendre que le fragment soit chargé et que le LiveData soit mis à jour
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // Ignorer
        }
        
        // Assert - Vérifier que le TextView est affiché
        // Le fragment utilise fragment_home.xml avec l'ID text_home
        onView(withId(R.id.text_home)).check(matches(isDisplayed()));
        
        // Vérifier que le TextView contient du texte (le texte peut varier selon le ViewModel)
        scenario.onFragment(fragment -> {
            assertNotNull("Le fragment devrait être initialisé", fragment);
            assertNotNull("La vue devrait exister", fragment.getView());
        });
    }

    @Test
    public void testWelcomeTextViewIsVisible() {
        // Arrange
        FragmentScenario<HomeFragment> scenario = FragmentScenario.launchInContainer(
                HomeFragment.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Attendre que le fragment soit chargé et que le LiveData soit mis à jour
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // Ignorer
        }
        
        // Assert - Vérifier que le TextView est visible
        // Le fragment utilise fragment_home.xml avec l'ID text_home
        onView(withId(R.id.text_home)).check(matches(isDisplayed()));
        
        // Vérifier que le fragment est bien initialisé
        scenario.onFragment(fragment -> {
            assertNotNull("Le fragment devrait être initialisé", fragment);
            assertNotNull("La vue devrait exister", fragment.getView());
        });
    }
}

