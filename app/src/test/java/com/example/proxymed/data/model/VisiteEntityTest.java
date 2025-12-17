package com.example.proxymed.data.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe VisiteEntity
 */
public class VisiteEntityTest {

    private VisiteEntity visite;

    @Before
    public void setUp() {
        visite = new VisiteEntity(
                1L,                    // patientId
                "Lavoie",              // patientNom
                "Pierre",              // patientPrenom
                2L,                    // infirmierId
                "Dupont",              // infirmierNom
                "Jean",                // infirmierPrenom
                "Martin",              // medecinNom
                "Sophie",              // medecinPrenom
                3L,                    // medecinId
                "2025-03-20",          // dateRendezVous
                "789 Rue Patient",     // adresseRdv
                "120/80",              // pressionArterielle
                5.5f,                  // glycemie
                75.5f,                 // poids
                18,                    // frequenceRespiratoire
                72,                    // frequenceCardiaque
                98,                    // saturationO2
                "Bon état général",    // etatGeneral
                "Aucune note",         // notesSupplementaires
                null,                  // photo
                45.5017,               // latitude
                -73.5673,              // longitude
                "En attente"           // statut
        );
    }

    @Test
    public void testConstructor() {
        assertNotNull(visite);
        assertEquals(1L, visite.getPatientId());
        assertEquals("Lavoie", visite.getPatientNom());
        assertEquals("Pierre", visite.getPatientPrenom());
        assertEquals(2L, visite.getInfirmierId());
        assertEquals("Dupont", visite.getInfirmierNom());
        assertEquals("Jean", visite.getInfirmierPrenom());
        assertEquals("Martin", visite.getMedecinNom());
        assertEquals("Sophie", visite.getMedecinPrenom());
        assertEquals(Long.valueOf(3L), visite.getMedecinId());
        assertEquals("2025-03-20", visite.getDateRendezVous());
        assertEquals("789 Rue Patient", visite.getAdresseRdv());
        assertEquals("120/80", visite.getPressionArterielle());
        assertEquals(5.5f, visite.getGlycemie(), 0.01f);
        assertEquals(75.5f, visite.getPoids(), 0.01f);
        assertEquals(18, visite.getFrequenceRespiratoire());
        assertEquals(72, visite.getFrequenceCardiaque());
        assertEquals(98, visite.getSaturationO2());
        assertEquals("Bon état général", visite.getEtatGeneral());
        assertEquals("Aucune note", visite.getNotesSupplementaires());
        assertEquals(45.5017, visite.getLatitude(), 0.0001);
        assertEquals(-73.5673, visite.getLongitude(), 0.0001);
        assertEquals("En attente", visite.getStatut());
    }

    @Test
    public void testGettersAndSetters() {
        visite.setId(100L);
        assertEquals(100L, visite.getId());

        visite.setPatientId(5L);
        assertEquals(5L, visite.getPatientId());

        visite.setPatientNom("Gagnon");
        assertEquals("Gagnon", visite.getPatientNom());

        visite.setPatientPrenom("Julie");
        assertEquals("Julie", visite.getPatientPrenom());

        visite.setInfirmierId(10L);
        assertEquals(10L, visite.getInfirmierId());

        visite.setMedecinId(15L);
        assertEquals(Long.valueOf(15L), visite.getMedecinId());

        visite.setDateRendezVous("2025-04-15");
        assertEquals("2025-04-15", visite.getDateRendezVous());

        visite.setPressionArterielle("130/85");
        assertEquals("130/85", visite.getPressionArterielle());

        visite.setGlycemie(6.2f);
        assertEquals(6.2f, visite.getGlycemie(), 0.01f);

        visite.setPoids(80.0f);
        assertEquals(80.0f, visite.getPoids(), 0.01f);

        visite.setFrequenceRespiratoire(20);
        assertEquals(20, visite.getFrequenceRespiratoire());

        visite.setFrequenceCardiaque(80);
        assertEquals(80, visite.getFrequenceCardiaque());

        visite.setSaturationO2(99);
        assertEquals(99, visite.getSaturationO2());

        visite.setEtatGeneral("État stable");
        assertEquals("État stable", visite.getEtatGeneral());

        visite.setNotesSupplementaires("Notes importantes");
        assertEquals("Notes importantes", visite.getNotesSupplementaires());

        visite.setLatitude(46.8139);
        assertEquals(46.8139, visite.getLatitude(), 0.0001);

        visite.setLongitude(-71.2080);
        assertEquals(-71.2080, visite.getLongitude(), 0.0001);

        visite.setStatut("Validée");
        assertEquals("Validée", visite.getStatut());

        byte[] photo = new byte[]{1, 2, 3, 4, 5};
        visite.setPhoto(photo);
        assertArrayEquals(photo, visite.getPhoto());
    }

    @Test
    public void testVisiteWithoutMedecin() {
        VisiteEntity visiteSansMedecin = new VisiteEntity(
                1L, "Lavoie", "Pierre",
                2L, "Dupont", "Jean",
                null, null, null,
                "2025-03-20", "789 Rue Patient",
                "120/80", 5.5f, 75.5f,
                18, 72, 98,
                "Bon état", "Aucune note",
                null, 45.5017, -73.5673,
                "En attente"
        );

        assertNull(visiteSansMedecin.getMedecinId());
        assertNull(visiteSansMedecin.getMedecinNom());
        assertNull(visiteSansMedecin.getMedecinPrenom());
    }

    @Test
    public void testStatutValues() {
        visite.setStatut("En attente");
        assertEquals("En attente", visite.getStatut());

        visite.setStatut("Validée");
        assertEquals("Validée", visite.getStatut());

        visite.setStatut("Rejetée");
        assertEquals("Rejetée", visite.getStatut());
    }
}

