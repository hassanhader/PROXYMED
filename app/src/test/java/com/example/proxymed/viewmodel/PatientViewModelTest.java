package com.example.proxymed.viewmodel;

import android.app.Application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Tests unitaires pour PatientViewModel
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class PatientViewModelTest {

    private Application application;
    private PatientViewModel viewModel;

    @Before
    public void setUp() {
        application = RuntimeEnvironment.getApplication();
        viewModel = new PatientViewModel(application);
    }

    @Test
    public void testViewModelInitialization() {
        assertNotNull(viewModel);
    }

    @Test
    public void testGetAllPatients() {
        // Act
        assertNotNull(viewModel.getAllPatients());
        // LiveData devrait toujours retourner un objet (même s'il est vide)
    }
}

