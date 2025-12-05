package com.example.proxymed.ui.auth;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proxymed.R;
import com.example.proxymed.data.model.Utilisateur;
import com.example.proxymed.data.repository.UtilisateurRepository;
import com.example.proxymed.databinding.FragmentSignUpBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;

    //Camera pour photo de profil
    public static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    private Bitmap photo;  // Déclarer ici pour qu'elle soit accessible partout



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);


        binding.btnSubmit.setOnClickListener(v -> {
            // Si tous les champs sont validés
            if (validateInputs()) {
                // Créer un objet Utilisateur avec les données de l'infirmier qui s'inscrit
                byte[] photoBytes = null;
                if (photo != null) {
                    photoBytes = convertBitmapToByteArray(photo);  // Récupère la photo sous forme de byte[]
                }
                Utilisateur utilisateur = new Utilisateur(
                        binding.etFullName.getText().toString().split(" ")[1],
                        //Prendre la partie prenom du fullname
                        binding.etFullName.getText().toString().split(" ")[0],
                        binding.etEmail.getText().toString(),
                        "infirmier",  // Type : "infirmier" ou "médecin" selon le cas
                        binding.etPassword.getText().toString(),  // Le mot de passe est bien inséré ici
                        binding.etPhone.getText().toString(),
                        null,
                        binding.etProfessionalNumber.getText().toString(),
                        binding.etWorkAddress.getText().toString(),
                        photoBytes
                );
                // Insérer l'utilisateur dans la base de données 💾
                UtilisateurRepository utilisateurRepository = new UtilisateurRepository(getActivity().getApplication());
               // utilisateurRepository.deleteAllUtilisateurs();  // Supprimer tous les utilisateurs

                utilisateurRepository.insertUtilisateur(utilisateur);  // Nouvelle méthode d'insertion

                // Vérification si l'utilisateur a bien été inséré
                utilisateurRepository.getAllUtilisateurs(utilisateurs -> {
                    // Cette méthode est appelée sur le thread principal une fois les utilisateurs récupérés
                    for (Utilisateur u : utilisateurs) {
                        Log.d("Utilisateur", "Nom : " + u.getNom() + ", Email : " + u.getEmail());
                    }
                });

                Log.d("Inscription", "Utilisateur ajouté : " + utilisateur.getNom());
                Toast.makeText(getContext(), "Utilisateur ajouté avec succès !", Toast.LENGTH_SHORT).show();

                // Après l'insertion, on redirige vers la page de login 🔑
                NavHostFragment.findNavController(SignUpFragment.this)
                        .navigate(R.id.action_signup_to_login);
            }
        });

        // Vérification des permissions
        checkPermissions();

        // Lancer la fonction pour prendre ou choisir une photo
        binding.ivProfilePicture.setOnClickListener(v -> showImageOptions());

        return binding.getRoot();
    }




    //PHOTO - CAMERA
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                    0);
        }
    }

    private void showImageOptions() {
        CharSequence[] options = {"Prendre une photo", "Choisir depuis la galerie"};
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choisir une image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    // Si l'utilisateur choisit "Prendre une photo"
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePictureIntent, REQUEST_CAMERA);
                } else if (which == 1) {
                    // Si l'utilisateur choisit "Choisir depuis la galerie"
                    Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhotoIntent, SELECT_FILE);
                }
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions accordées
            } else {
                showToast("Permissions nécessaires pour accéder à la caméra et aux fichiers.");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                // Si l'utilisateur a pris une photo avec la caméra
                photo = (Bitmap) data.getExtras().get("data");
                binding.ivProfilePicture.setImageBitmap(photo);  // Affiche la photo dans l'ImageView
            } else if (requestCode == SELECT_FILE) {
                // Si l'utilisateur a choisi une photo depuis la galerie
                Uri selectedImageUri = data.getData();
                try {
                    photo = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImageUri);  // Affecte la photo à la variable `photo`
                    binding.ivProfilePicture.setImageBitmap(photo);  // Affiche l'image dans l'ImageView
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Maintenant, tu peux enregistrer photoBytes dans la base de données
            } else if (requestCode == SELECT_FILE) {
                // Si l'utilisateur a choisi une photo depuis la galerie
                Uri selectedImageUri = data.getData();
                try {
                    Bitmap photo = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImageUri);
                    byte[] photoBytes = convertBitmapToByteArray(photo);  // Convertir l'image en byte[]
                    binding.ivProfilePicture.setImageURI(selectedImageUri);  // Affiche l'image sélectionnée

                    // Maintenant, tu peux enregistrer photoBytes dans la base de données
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /*VALIDATION DES INPUTS LORS DE L'INSCRIPTION*/

    private boolean validateInputs() {
        String nomEtPrenom = binding.etFullName.getText().toString();
        String email = binding.etEmail.getText().toString();
        String motDePasse = binding.etPassword.getText().toString();
        String confirmationMotDePasse = binding.etConfirmPassword.getText().toString();
        String telephone = binding.etPhone.getText().toString();

        // Vérification que tous les champs sont remplis
        if (nomEtPrenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty() ||
                confirmationMotDePasse.isEmpty() || telephone.isEmpty()) {
            showToast("Veuillez remplir tous les champs obligatoires 😅");
            return false;
        }

        // Vérification que l'email est valide 📧
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("L'email n'est pas valide, essayez encore ! 😬");
            return false;
        }

        // Vérification que les mots de passe correspondent 🔒
        if (!motDePasse.equals(confirmationMotDePasse)) {
            showToast("Les mots de passe ne correspondent pas 🙅‍♂️");
            return false;
        }

        // Vérification du numéro de téléphone 📞
        if (telephone.length() != 10) {
            showToast("Numéro de téléphone invalide, essaye encore 📱");
            return false;
        }

        // Vérification que l'utilisateur a bien pris une photo 📸
        if (photo == null) {
            showToast("Veuillez ajouter une photo de profil ! 😅");
            return false;
        }

        return true;  // Si tout est ok, on continue !
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();  // Affiche un message à l'utilisateur
    }


    //PHOTO PDP

    public byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);  // Choisis un format de compression
        return byteArrayOutputStream.toByteArray();
    }




}