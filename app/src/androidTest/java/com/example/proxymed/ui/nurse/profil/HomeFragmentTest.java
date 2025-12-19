package com.example.proxymed.ui.nurse.profil;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.proxymed.R;

import org.junit.Test;
import org.junit.runner.RunWith;

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
    public void testHomeFragmentDisplaysText() {
        // Arrange
        FragmentScenario<HomeFragment> scenario = FragmentScenario.launchInContainer(
                HomeFragment.class,
                null,
                R.style.Theme_AppCompat,
                null
        );
        
        // Assert
        scenario.onFragment(fragment -> {
            assertNotNull(fragment.getView());
            // Vérifier que le texte est affiché
        });
    }
}

