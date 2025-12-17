package com.example.proxymed.data.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe Utilisateur
 */
public class UtilisateurTest {

    private Utilisateur utilisateur;

    @Before
    public void setUp() {
        utilisateur = new Utilisateur(
                "Dupont",
                "Jean",
                "jean.dupont@example.com",
                "infirmier",
                "password123",
                "514-123-4567",
                null,
                null,
                "123 Rue Test, Montréal",
                null
        );
    }

    @Test
    public void testConstructor() {
        assertNotNull(utilisateur);
        assertEquals("Dupont", utilisateur.getNom());
        assertEquals("Jean", utilisateur.getPrenom());
        assertEquals("jean.dupont@example.com", utilisateur.getEmail());
        assertEquals("infirmier", utilisateur.getType());
        assertEquals("password123", utilisateur.getMotDePasse());
        assertEquals("514-123-4567", utilisateur.getTelephone());
        assertEquals("", utilisateur.getSpecialite()); // Doit être vide pour un infirmier
        assertEquals("", utilisateur.getNumeroPraticien()); // Doit être vide pour un infirmier
        assertEquals("123 Rue Test, Montréal", utilisateur.getAdresse());
    }

    @Test
    public void testConstructorWithMedecin() {
        Utilisateur medecin = new Utilisateur(
                "Martin",
                "Sophie",
                "sophie.martin@example.com",
                "medecin",
                "password456",
                "514-987-6543",
                "Cardiologie",
                "12345",
                null,
                null
        );

        assertEquals("medecin", medecin.getType());
        assertEquals("Cardiologie", medecin.getSpecialite());
        assertEquals("12345", medecin.getNumeroPraticien());
        assertEquals("", medecin.getAdresse()); // Doit être vide pour un médecin
    }

    @Test
    public void testGettersAndSetters() {
        // Test des setters et getters
        utilisateur.setId(1L);
        assertEquals(Long.valueOf(1L), utilisateur.getId());

        utilisateur.setNom("Tremblay");
        assertEquals("Tremblay", utilisateur.getNom());

        utilisateur.setPrenom("Marie");
        assertEquals("Marie", utilisateur.getPrenom());

        utilisateur.setEmail("marie.tremblay@example.com");
        assertEquals("marie.tremblay@example.com", utilisateur.getEmail());

        utilisateur.setType("medecin");
        assertEquals("medecin", utilisateur.getType());

        utilisateur.setTelephone("514-555-1234");
        assertEquals("514-555-1234", utilisateur.getTelephone());

        utilisateur.setMotDePasse("newPassword");
        assertEquals("newPassword", utilisateur.getMotDePasse());

        utilisateur.setSpecialite("Neurologie");
        assertEquals("Neurologie", utilisateur.getSpecialite());

        utilisateur.setNumeroPraticien("67890");
        assertEquals("67890", utilisateur.getNumeroPraticien());

        utilisateur.setAdresse("456 Rue Nouvelle, Québec");
        assertEquals("456 Rue Nouvelle, Québec", utilisateur.getAdresse());

        byte[] photo = new byte[]{1, 2, 3};
        utilisateur.setPhotoProfil(photo);
        assertArrayEquals(photo, utilisateur.getPhotoProfil());
    }

    @Test
    public void testNullHandling() {
        // Test que les valeurs null sont gérées correctement dans le constructeur
        Utilisateur user = new Utilisateur(
                "Test",
                "User",
                "test@example.com",
                "infirmier",
                "pass",
                "514-000-0000",
                null,
                null,
                null,
                null
        );

        assertEquals("", user.getSpecialite());
        assertEquals("", user.getNumeroPraticien());
        assertEquals("", user.getAdresse());
        assertNull(user.getPhotoProfil());
    }
}

