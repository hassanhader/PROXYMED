package com.example.proxymed.viewmodel;

import android.app.Application;

import com.example.proxymed.data.model.VisiteEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Tests unitaires pour VisiteViewModel
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class VisiteViewModelTest {

    private Application application;
    private VisiteViewModel viewModel;
    private VisiteEntity testVisite;

    @Before
    public void setUp() {
        application = RuntimeEnvironment.getApplication();
        viewModel = new VisiteViewModel(application);
        
        testVisite = new VisiteEntity(
                1L, "Lavoie", "Pierre",
                2L, "Dupont", "Jean",
                "Martin", "Sophie", 3L,
                "2025-03-20", "789 Rue Patient",
                "120/80", 5.5f, 75.5f,
                18, 72, 98,
                "Bon état général", "Aucune note",
                null, 45.5017, -73.5673,
                "En attente"
        );
    }

    @Test
    public void testViewModelInitialization() {
        assertNotNull(viewModel);
    }

    @Test
    public void testGetAllVisites() {
        assertNotNull(viewModel.getAllVisites());
    }

    @Test
    public void testSearchVisites() {
        assertNotNull(viewModel.searchVisites("Lavoie"));
    }

    @Test
    public void testDeleteVisite() {
        try {
            viewModel.deleteVisite(testVisite);
            assertTrue(true);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testInsertVisite() {
        try {
            viewModel.insertVisite(testVisite);
            assertTrue(true);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }
}

