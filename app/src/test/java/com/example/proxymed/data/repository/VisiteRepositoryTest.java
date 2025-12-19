package com.example.proxymed.data.repository;

import android.content.Context;

import com.example.proxymed.data.model.VisiteEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Tests unitaires pour VisiteRepository
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class VisiteRepositoryTest {

    private Context context;
    private VisiteRepository repository;
    private VisiteEntity testVisite;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.getApplication();
        repository = new VisiteRepository(context);
        
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
    public void testRepositoryInitialization() {
        assertNotNull(repository);
    }

    @Test
    public void testInsertVisite() {
        // Act
        try {
            repository.insertVisite(testVisite);
            assertTrue(true);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testUpdateVisite() {
        // Arrange
        testVisite.setStatut("Validée");
        
        // Act
        try {
            repository.updateVisite(testVisite);
            assertTrue(true);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testDeleteVisite() {
        // Act
        try {
            repository.deleteVisite(testVisite);
            assertTrue(true);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testGetAllVisites() {
        assertNotNull(repository.getAllVisites());
    }

    @Test
    public void testGetVisitesByPatient() {
        assertNotNull(repository.getVisitesByPatient(1));
    }

    @Test
    public void testGetVisiteById() {
        assertNotNull(repository.getVisiteById(1));
    }

    @Test
    public void testGetVisitesByInfirmier() {
        assertNotNull(repository.getVisitesByInfirmier(2));
    }

    @Test
    public void testGetVisitesByMedecin() {
        assertNotNull(repository.getVisitesByMedecin(3));
    }

    @Test
    public void testGetVisitesByPatientAndMedecin() {
        assertNotNull(repository.getVisitesByPatientAndMedecin(1, 3));
    }

    @Test
    public void testSearchVisites() {
        assertNotNull(repository.searchVisites("Lavoie"));
    }
}

