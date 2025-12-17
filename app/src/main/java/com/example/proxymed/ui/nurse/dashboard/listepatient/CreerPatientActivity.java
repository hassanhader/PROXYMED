package com.example.proxymed.ui.nurse.dashboard.listepatient;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.proxymed.data.database.AppDatabase;
import com.example.proxymed.data.model.PatientEntity;
import com.example.proxymed.data.model.Utilisateur;
import com.example.proxymed.databinding.ActivityCreerPatientBinding;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class CreerPatientActivity extends AppCompatActivity {

    private ActivityCreerPatientBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreerPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Ajouter un DatePickerDialog au champ date de naissance
        binding.addPatientDateNaissance.setOnClickListener(v -> showDatePickerDialog());

        // On clique sur le bouton Enregistrer
        binding.addPatientRecord.setOnClickListener(v -> enregistrerPatient());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, yearSelected, monthSelected, daySelected) -> {
                    String date = daySelected + "/" + (monthSelected + 1) + "/" + yearSelected;
                    binding.addPatientDateNaissance.setText(date);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void enregistrerPatient() {
        String nom = binding.addPatientNom.getText().toString().trim();
        String prenom = binding.addPatientPrenom.getText().toString().trim();
        String dateNaissance = binding.addPatientDateNaissance.getText().toString().trim();
        String telephone = binding.addPatientTelephone.getText().toString().trim();
        String adresse = binding.addPatientAdresse.getText().toString().trim();

        // Vérification des champs vides
        if (nom.isEmpty() || prenom.isEmpty() || dateNaissance.isEmpty() || telephone.isEmpty() || adresse.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validation du numéro de téléphone
        if (!telephone.matches("^\\d{10}$")) {
            Toast.makeText(this, "Numéro de téléphone invalide", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Récupérer tous les médecins
            List<Utilisateur> medecins = AppDatabase.getInstance(getApplicationContext()).utilisateurDao().getMedecins();

            // Vérifier s'il y a des médecins disponibles
            if (medecins.isEmpty()) {
                Toast.makeText(this, "Aucun médecin disponible", Toast.LENGTH_SHORT).show();
                return;
            }

            // Sélectionner un médecin au hasard
            Utilisateur medecinAssigné = medecins.get(new Random().nextInt(medecins.size()));

            // Créer un objet PatientEntity
            PatientEntity patient = new PatientEntity(nom, prenom, dateNaissance, telephone, adresse, medecinAssigné.getId());

            // Log du patient avant insertion
            Log.d("DB_TEST", "Insertion du patient : " + patient.toString());

            // Sauvegarde du patient dans la base de données
            AppDatabase.getInstance(getApplicationContext()).patientDao().insert(patient);

            // Vérification des patients après insertion
            AppDatabase.getInstance(getApplicationContext()).patientDao().getAllPatients().observe(this, patients -> {
                if (patients != null) {
                    Log.d("DB_TEST", "Patients en base après insertion : " + patients.size());
                }
            });


            Toast.makeText(this, "Patient ajouté avec succès", Toast.LENGTH_SHORT).show();
            finish(); // Fermer l'activité
        } catch (Exception e) {
            Log.e("DB_ERROR", "Erreur lors de l'insertion du patient", e);
            Toast.makeText(this, "Erreur lors de l'ajout du patient", Toast.LENGTH_SHORT).show();
        }
    }
}
