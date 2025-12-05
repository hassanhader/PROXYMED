package com.example.proxymed.ui.nurse.dashboard.listevisite;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.proxymed.MainActivity;
import com.example.proxymed.R;
import com.example.proxymed.data.model.PatientEntity;
import com.example.proxymed.data.model.VisiteEntity;
import com.example.proxymed.databinding.ActivityCreerVisiteBinding;
import com.example.proxymed.viewmodel.PatientViewModel;
import com.example.proxymed.viewmodel.VisiteViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;

public class CreerVisiteActivity extends AppCompatActivity {

    private ActivityCreerVisiteBinding binding;
    private double latitude, longitude;

    private VisiteViewModel visiteViewModel;
    private PatientViewModel patientViewModel;

    // Codes de requête pour la caméra et la galerie
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;

    private static final int REQUEST_LOCATION_PERMISSION = 3;

    private MapView mapView;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;



    // Pour stocker la photo capturée ou sélectionnée
    private Bitmap photo;
    private byte[] photoBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreerVisiteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialisation des ViewModels
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);
        visiteViewModel = new ViewModelProvider(this).get(VisiteViewModel.class);

        // Observer la liste des patients et agir en conséquence
        patientViewModel.getAllPatients().observe(this, patients -> {
            if (patients.isEmpty()) {
                showNoPatientsDialog();
            } else {
                chargerListePatients();
            }
        });

        //Initialisation de la carte
        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                googleMap = map;
            }
        });






        setUpListeners();
    }


    /*GESTION GEOLOCALISATION*/



    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            getCurrentLocation();
        }
    }

    private void getCurrentLocation() {
        Log.d("DEBUG", "getCurrentLocation() appelée");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.d("DEBUG", "Position récupérée : Lat=" + latitude + ", Lng=" + longitude);

                            // Si tu veux afficher la position sur la carte
                            LatLng latLng = new LatLng(latitude, longitude);
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                            googleMap.addMarker(new MarkerOptions().position(latLng).title("Ma position"));
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("DEBUG", "Erreur géolocalisation", e);
                        Toast.makeText(this, "Erreur lors de la géolocalisation", Toast.LENGTH_SHORT).show();
                    });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }

//        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            Log.d("DEBUG", "Permission localisation confirmée, récupération de la position...");
//
//            fusedLocationClient.getCurrentLocation(com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY, null)
//                    .addOnSuccessListener(this, location -> {
//                        if (location != null) {
//                            double latitude = location.getLatitude();
//                            double longitude = location.getLongitude();
//                            String locText = "Lat: " + latitude + ", Lng: " + longitude;
//                            Log.d("DEBUG", "Position obtenue: " + locText);
//
//                            binding.editAdresse.setText(locText);
//                        } else {
//                            Log.d("DEBUG", "Location est null !");
//                            Toast.makeText(this, "Impossible d'obtenir la localisation", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnFailureListener(e -> {
//                        Log.e("DEBUG", "Erreur lors de la récupération de la localisation", e);
//                    });
//
//        } else {
//            Log.d("DEBUG", "Permission non accordée, demande requise");
//        }
    }



    // -------------------------------
    // Gestion de la caméra et de la galerie
    // -------------------------------

    // Méthode pour vérifier les permissions nécessaires
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CAMERA);
        }
    }

    // Afficher un popup d'options pour choisir entre caméra et galerie
    private void showImageOptions() {
        CharSequence[] options = {"Prendre une photo", "Choisir depuis la galerie"};
        new AlertDialog.Builder(this)
                .setTitle("Choisir une image")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            // Prendre une photo avec la caméra
                            requestCameraPermission();
                        } else if (which == 1) {
                            // Choisir une image depuis la galerie
                            openGallery();
                        }
                    }
                })
                .show();
    }

    // Demande la permission et ouvre la caméra si accordée
    private void requestCameraPermission() {
        Log.d("DEBUG", "Demande de permission pour la caméra");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        } else {
            openCamera();
        }
    }

    // Ouvre la caméra
    private void openCamera() {
        Log.d("DEBUG", "Ouverture de la caméra");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE); // Intent pour ouvrir la caméra
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            Log.d("DEBUG", "Lancement de l'intent de capture d'image");
            startActivityForResult(takePictureIntent, REQUEST_CAMERA);
        } else {
            Log.d("DEBUG", "Aucune application caméra trouvée ! Intent resolvable: " + takePictureIntent.resolveActivity(getPackageManager()));
            Toast.makeText(this, "Aucune application appareil photo trouvée", Toast.LENGTH_LONG).show();
        }
    }



    // Gérer la réponse de la demande de permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {
                openCamera();
        }
        else if (requestCode == REQUEST_LOCATION_PERMISSION)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Gérer le résultat de la caméra et de la galerie
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("DEBUG", "onActivityResult appelé - requestCode: " + requestCode + ", resultCode: " + resultCode);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                // Image prise avec la caméra
                if (data != null && data.getExtras() != null) {
                    Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                    if (imageBitmap != null) {
                        Log.d("DEBUG", "Photo prise, conversion en byte[]");
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        photoBytes = stream.toByteArray();
                        photo = imageBitmap; // Sauvegarder l'image en Bitmap aussi si besoin
                        binding.image1.setImageBitmap(imageBitmap);
                    } else {
                        Log.d("DEBUG", "L'image capturée est nulle");
                    }
                } else {
                    Log.d("DEBUG", "Les extras de l'intent sont nuls");
                }
            } else if (requestCode == SELECT_FILE) {
                // Image choisie depuis la galerie
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    try {
                        Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                        Log.d("DEBUG", "Image sélectionnée depuis la galerie, conversion en byte[]");
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        photoBytes = stream.toByteArray();
                        photo = imageBitmap;
                        binding.image1.setImageBitmap(imageBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("DEBUG", "Erreur lors de la conversion de l'image sélectionnée");
                    }
                }
            }
        } else {
            Log.d("DEBUG", "Capture annulée ou échouée");
        }
    }


    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // Choisir le format et la qualité de compression
        return stream.toByteArray();
    }


    // -------------------------------
    // Gestion de la liste des patients et des autres fonctionnalités
    // -------------------------------
    private void chargerListePatients() {
        patientViewModel.getAllPatients().observe(this, patients -> {
            // Trier la liste des patients par nom
            Collections.sort(patients, (p1, p2) -> {
                String nom1 = (p1.getNom() != null) ? p1.getNom() : "";
                String nom2 = (p2.getNom() != null) ? p2.getNom() : "";
                return nom1.compareToIgnoreCase(nom2);
            });
            ArrayAdapter<PatientEntity> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, patients);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinnerPatient.setAdapter(adapter);
        });
    }

    private void setUpListeners() {
        binding.spinnerPatient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                PatientEntity selectedPatient = (PatientEntity) parentView.getItemAtPosition(position);
                if (selectedPatient != null) {
                    binding.editAdresse.setText(selectedPatient.getAdresse());
                    Log.d("DEBUG", "Patient sélectionné : " + selectedPatient.getNom());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                binding.editAdresse.setText("");
                Log.d("DEBUG", "Aucun patient sélectionné");
            }
        });
        binding.editDateHeure.setOnClickListener(v -> showDatePicker());
        // Pour le bouton photo, on affiche un popup avec options (caméra / galerie)
        binding.btnPrendrePhoto.setOnClickListener(v -> showImageOptions());
        binding.btnGeolocaliser.setOnClickListener(v -> {
            // Code pour la géolocalisation (à implémenter ultérieurement)
        });
        binding.btnEnregistrer.setOnClickListener(v -> enregistrerVisite());
        binding.btnGeolocaliser.setOnClickListener( v -> {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Demander la localisation
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            // Déplacer la caméra à la nouvelle position
                            LatLng latLng = new LatLng(latitude, longitude);
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                            // Ajouter un marqueur à la position
                            googleMap.addMarker(new MarkerOptions().position(latLng).title("Ma position"));
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Erreur lors de la géolocalisation", Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Demander la permission si elle n'est pas accordée
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    });

    }

    private void openGallery() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, SELECT_FILE);
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String date = dayOfMonth + "/" + (month + 1) + "/" + year;
            binding.editDateHeure.setText(date);
            showTimePicker();
        }, 2025, 2, 11);
        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            String time = hourOfDay + ":" + minute;
            binding.editDateHeure.append(" " + time);
        }, 10, 0, true);
        timePickerDialog.show();
    }


    private void enregistrerVisite(){
        Log.d("DEBUG", "enregistrerVisite() appelée");

        String adresse = binding.editAdresse.getText().toString();
        String dateHeure = binding.editDateHeure.getText().toString();
        String pression = binding.editPression.getText().toString();
        String glycemieStr = binding.editGlycemie.getText().toString();
        String poidsStr = binding.editPoids.getText().toString();
        String frequenceCardiaqueStr = binding.editFreqCardiaque.getText().toString();
        String frequenceRespiratoireStr = binding.editFreqResp.getText().toString();
        String notesInfirmier = binding.editNotesInfirmier.getText().toString();
        String notesMedecin = binding.editObservationsMedecin.getText().toString();
        String saturationStr = binding.editSaturation.getText().toString();

        PatientEntity selectedPatient = (PatientEntity) binding.spinnerPatient.getSelectedItem();
        if (selectedPatient == null) {
            Log.d("DEBUG", "Aucun patient sélectionné");
            Toast.makeText(this, "Veuillez sélectionner un patient", Toast.LENGTH_SHORT).show();
            return;
        }

        long patientId = selectedPatient.getId();
        long medecinId = selectedPatient.getMedecinId();

        if (adresse.isEmpty() || dateHeure.isEmpty() || pression.isEmpty() || glycemieStr.isEmpty() || poidsStr.isEmpty() ||
                frequenceCardiaqueStr.isEmpty() || frequenceRespiratoireStr.isEmpty() || notesInfirmier.isEmpty()) {
            Log.d("DEBUG", "Champs obligatoires manquants");
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        if (photo == null) {
            Log.d("DEBUG", "Aucune photo prise");
            Toast.makeText(this, "Veuillez prendre une photo", Toast.LENGTH_SHORT).show();
            return;
        }

        byte[] photoBytes = convertBitmapToByteArray(photo); // Conversion de la photo en byte[]
        float glycemie = parseFloat(glycemieStr, "glycémie");
        int poids = parseInt(poidsStr, "poids");
        int saturation = parseInt(saturationStr, "saturation");
        int frequenceCardiaque = parseInt(frequenceCardiaqueStr, "fréquence cardiaque");
        int frequenceRespiratoire = parseInt(frequenceRespiratoireStr, "fréquence respiratoire");

        VisiteEntity visite = new VisiteEntity(
                patientId, selectedPatient.getNom(), selectedPatient.getPrenom(), 1,
                null, null, null, null, medecinId,
                dateHeure, selectedPatient.getAdresse(),
                pression, glycemie, poids, frequenceCardiaque, frequenceRespiratoire, saturation,
                notesInfirmier, notesMedecin, photoBytes, latitude, longitude, "En attente"
        );

        Log.d("DEBUG", "Visite à enregistrer : " + visite.toString());
        visiteViewModel.insertVisite(visite);
        // Afficher un message de confirmation
        Toast.makeText(this, "Visite enregistrée", Toast.LENGTH_SHORT).show();
    }


    private float parseFloat(String value, String fieldName) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            Log.d("DEBUG", "Erreur format pour " + fieldName + ": " + e.getMessage());
            Toast.makeText(this, "Valeur non valide pour " + fieldName, Toast.LENGTH_SHORT).show();
            return 0f;
        }
    }

    private int parseInt(String value, String fieldName) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            Log.d("DEBUG", "Erreur format pour " + fieldName + ": " + e.getMessage());
            Toast.makeText(this, "Valeur non valide pour " + fieldName, Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    // Afficher le popup si aucun patient n'est trouvé
    private void showNoPatientsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Aucun patient")
                .setMessage("Il n'y a actuellement aucun patient. Vous devez en créer un avant de continuer.")
                .setPositiveButton("OK", (dialog, which) -> {
                    Intent intent = new Intent(CreerVisiteActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

}
