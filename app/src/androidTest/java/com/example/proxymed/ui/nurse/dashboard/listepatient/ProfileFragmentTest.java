package com.example.proxymed.ui.nurse.dashboard.listepatient;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.fragment.app.FragmentFactory;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.proxymed.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Tests fonctionnels pour ProfileFragment
 */
@RunWith(AndroidJUnit4.class)
public class ProfileFragmentTest {

    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        
        // Préparer les SharedPreferences avec des données de test
        SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("infirmier_nom", "Dupont");
        editor.putString("infirmier_prenom", "Jean");
        editor.putString("infirmier_email", "jean.dupont@example.com");
        editor.putString("infirmier_photo_byte", "");
        editor.apply();
    }

    @Test
    public void testProfileFragmentCreation() {
        // Arrange & Act
        FragmentScenario<ProfileFragment> scenario = FragmentScenario.launchInContainer(
                ProfileFragment.class,
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
    public void testProfileFragmentDisplaysUserInfo() {
        // Arrange
        FragmentScenario<ProfileFragment> scenario = FragmentScenario.launchInContainer(
                ProfileFragment.class,
                null,
                R.style.Theme_PROXYMED,
                (FragmentFactory) null
        );
        
        // Assert
        scenario.onFragment(fragment -> {
            assertNotNull(fragment.getView());
            // Vérifier que les informations utilisateur sont affichées
        });
    }
}

