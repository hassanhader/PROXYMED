package com.example.proxymed.ui.nurse.dashboard.listepatient;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import com.example.proxymed.R;
import com.example.proxymed.databinding.FragmentEditProfileBinding; // Import de ViewBinding

import com.example.proxymed.data.model.Utilisateur;
import com.example.proxymed.viewmodel.UtilisateurViewModel;

import android.graphics.BitmapFactory;
import android.widget.ImageView;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;  // Référence à ViewBinding
    private UtilisateurViewModel utilisateurViewModel;
    private SharedPreferences sharedPreferences;

    public EditProfileFragment() {
        super(R.layout.fragment_edit_profile);  // Assurez-vous que le layout est bien votre fichier XML
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);

        // Initialisation du ViewModel
        utilisateurViewModel = new ViewModelProvider(this).get(UtilisateurViewModel.class);

        // Initialiser SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user_email", null);  // Récupérer l'email

        // Observer les données de l'utilisateur via le ViewModel
        if (email != null) {
            utilisateurViewModel.getUtilisateurByEmailLive(email).observe(getViewLifecycleOwner(), utilisateur -> {
                if (utilisateur != null) {
                    // Afficher les informations de l'utilisateur dans les champs via Binding
                    binding.editProfileNom.setText(utilisateur.getNom());
                    binding.editProfilePrenom.setText(utilisateur.getPrenom());
                    binding.editProfileTelephone.setText(utilisateur.getTelephone());
                    binding.editProfileNumeroPraticien.setText(utilisateur.getNumeroPraticien());
                    binding.editProfileAdresse.setText(utilisateur.getAdresse());

                    // Afficher l'image de profil si elle existe
                    if (utilisateur.getPhotoProfil() != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(utilisateur.getPhotoProfil(), 0, utilisateur.getPhotoProfil().length);
                        binding.editProfileImage.setImageBitmap(bitmap);
                    }
                }
            });
        }

        // Action du bouton "Enregistrer"
        binding.profilRecordButton.setOnClickListener(v -> {
            // Récupérer les valeurs modifiées
            String nom = binding.editProfileNom.getText().toString();
            String prenom = binding.editProfilePrenom.getText().toString();
            String telephone = binding.editProfileTelephone.getText().toString();
            String numeroPraticien = binding.editProfileNumeroPraticien.getText().toString();
            String adresse = binding.editProfileAdresse.getText().toString();
            byte[] photoProfil = imageToByteArray(binding.editProfileImage); // Convertir l'image en byte[] si nécessaire

            // Créer un nouvel utilisateur avec les données modifiées (email supprimé pour infirmier)
            Utilisateur updatedUtilisateur = new Utilisateur(nom, prenom, null, "infirmier", "", telephone,
                    null, numeroPraticien, adresse, photoProfil);

            // Mettre à jour l'utilisateur dans la base de données
            utilisateurViewModel.update(updatedUtilisateur);
            Toast.makeText(getContext(), "Profil mis à jour", Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }

    // Méthode pour convertir l'image en byte[] si nécessaire
    private byte[] imageToByteArray(ImageView imageView) {
        // Assurez-vous que l'image est bien convertie avant de la sauvegarder
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }
        return null;  // Si aucune image n'est présente, retourner null
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Assurez-vous de libérer les ressources du ViewBinding
        binding = null;
    }
}
