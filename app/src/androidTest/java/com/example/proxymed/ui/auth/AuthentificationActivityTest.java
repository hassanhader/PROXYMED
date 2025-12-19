package com.example.proxymed.ui.auth;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.proxymed.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
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
    public void testToolbarDisplayed() {
        // Arrange
        ActivityScenario<AuthentificationActivity> scenario = ActivityScenario.launch(AuthentificationActivity.class);
        
        // Assert - Vérifier que la toolbar est affichée
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void testNavigationContainerExists() {
        // Arrange
        ActivityScenario<AuthentificationActivity> scenario = ActivityScenario.launch(AuthentificationActivity.class);
        
        // Assert - Vérifier que le NavHostFragment est présent
        onView(withId(R.id.nav_host_fragment)).check(matches(isDisplayed()));
        
        scenario.onActivity(activity -> {
            // Vérifier que le NavHostFragment est présent dans le FragmentManager
            assertNotNull(activity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment));
        });
    }
}

