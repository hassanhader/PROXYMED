package com.example.proxymed.data.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe PatientEntity
 */
public class PatientEntityTest {

    private PatientEntity patient;

    @Before
    public void setUp() {
        patient = new PatientEntity(
                "Lavoie",
                "Pierre",
                "1990-05-15",
                "789 Rue Patient, Montréal",
                "514-111-2222",
                1L
        );
    }

    @Test
    public void testConstructor() {
        assertNotNull(patient);
        assertEquals("Lavoie", patient.getNom());
        assertEquals("Pierre", patient.getPrenom());
        assertEquals("1990-05-15", patient.getDateNaissance());
        assertEquals("789 Rue Patient, Montréal", patient.getAdresse());
        assertEquals("514-111-2222", patient.getTelephone());
        assertEquals(Long.valueOf(1L), patient.getMedecinId());
    }

    @Test
    public void testGettersAndSetters() {
        patient.setId(10L);
        assertEquals(10L, patient.getId());

        patient.setNom("Gagnon");
        assertEquals("Gagnon", patient.getNom());

        patient.setPrenom("Julie");
        assertEquals("Julie", patient.getPrenom());

        patient.setDateNaissance("1985-03-20");
        assertEquals("1985-03-20", patient.getDateNaissance());

        patient.setAdresse("321 Rue Autre, Québec");
        assertEquals("321 Rue Autre, Québec", patient.getAdresse());

        patient.setTelephone("418-333-4444");
        assertEquals("418-333-4444", patient.getTelephone());

        patient.setMedecinId(2L);
        assertEquals(Long.valueOf(2L), patient.getMedecinId());

        // Test avec medecinId null
        patient.setMedecinId(null);
        assertNull(patient.getMedecinId());
    }

    @Test
    public void testToString() {
        String result = patient.toString();
        assertEquals("Lavoie Pierre", result);

        patient.setNom("Tremblay");
        patient.setPrenom("Marc");
        assertEquals("Tremblay Marc", patient.toString());
    }

    @Test
    public void testPatientWithoutMedecin() {
        PatientEntity patientSansMedecin = new PatientEntity(
                "Dupont",
                "Alice",
                "2000-01-01",
                "123 Rue Test",
                "514-999-8888",
                null
        );

        assertNull(patientSansMedecin.getMedecinId());
    }
}

