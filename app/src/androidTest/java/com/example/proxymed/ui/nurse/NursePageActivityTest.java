package com.example.proxymed.ui.nurse;

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
    public void testToolbarDisplayed() {
        // Arrange
        ActivityScenario<NursePageActivity> scenario = ActivityScenario.launch(NursePageActivity.class);
        
        // Assert - Vérifier que la toolbar est affichée
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void testBottomNavigationExists() {
        // Arrange
        ActivityScenario<NursePageActivity> scenario = ActivityScenario.launch(NursePageActivity.class);
        
        // Assert - Vérifier que la BottomNavigationView est affichée
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
        
        scenario.onActivity(activity -> {
            // Vérifier que l'activité est bien créée
            assertNotNull(activity);
        });
    }

    @Test
    public void testNavHostFragmentDisplayed() {
        // Arrange
        ActivityScenario<NursePageActivity> scenario = ActivityScenario.launch(NursePageActivity.class);
        
        // Assert - Vérifier que le NavHostFragment est affiché
        onView(withId(R.id.nav_host_fragment_activity_nurse_page)).check(matches(isDisplayed()));
    }
}

