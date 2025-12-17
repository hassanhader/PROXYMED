package com.example.proxymed.ui.auth;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Tests fonctionnels pour AuthentificationActivity
 */
@RunWith(AndroidJUnit4.class)
public class AuthentificationActivityTest {

    @Test
    public void testAuthentificationActivityCreation() {
        // Arrange & Act
        ActivityScenario<AuthentificationActivity> scenario = ActivityScenario.launch(AuthentificationActivity.class);
        
        // Assert
        scenario.onActivity(activity -> {
            assertNotNull("L'activité ne devrait pas être null", activity);
            assertNotNull("La vue ne devrait pas être null", activity.findViewById(android.R.id.content));
        });
    }

    @Test
    public void testNavigationContainerExists() {
        // Arrange
        ActivityScenario<AuthentificationActivity> scenario = ActivityScenario.launch(AuthentificationActivity.class);
        
        // Assert
        scenario.onActivity(activity -> {
            // Vérifier que le NavHostFragment est présent
            assertNotNull(activity.getSupportFragmentManager().findFragmentById(android.R.id.content));
        });
    }
}

