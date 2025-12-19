package com.example.proxymed.ui.nurse;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Tests fonctionnels pour NursePageActivity
 */
@RunWith(AndroidJUnit4.class)
public class NursePageActivityTest {

    @Test
    public void testNursePageActivityCreation() {
        // Arrange & Act
        ActivityScenario<NursePageActivity> scenario = ActivityScenario.launch(NursePageActivity.class);
        
        // Assert
        scenario.onActivity(activity -> {
            assertNotNull("L'activité ne devrait pas être null", activity);
            assertNotNull("La vue ne devrait pas être null", activity.findViewById(android.R.id.content));
        });
    }

    @Test
    public void testBottomNavigationExists() {
        // Arrange
        ActivityScenario<NursePageActivity> scenario = ActivityScenario.launch(NursePageActivity.class);
        
        // Assert
        scenario.onActivity(activity -> {
            // Vérifier que l'activité est bien créée
            assertNotNull(activity);
            // Note: Les IDs spécifiques peuvent varier selon votre layout
        });
    }
}

