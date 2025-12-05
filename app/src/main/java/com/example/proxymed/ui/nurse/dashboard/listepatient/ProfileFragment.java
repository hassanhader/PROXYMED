package com.example.proxymed.ui.nurse.dashboard.listepatient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.proxymed.R;
import com.example.proxymed.databinding.FragmentProfileBinding;
import com.example.proxymed.data.model.Utilisateur;
import com.example.proxymed.ui.auth.AuthentificationActivity;
import com.example.proxymed.viewmodel.UtilisateurViewModel;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import android.graphics.Bitmap;
import android.util.Base64;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private com.example.proxymed.viewmodel.UtilisateurViewModel utilisateurViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Récupérer les informations de l'infirmier depuis SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_prefs", getContext().MODE_PRIVATE);
        String infirmierNom = sharedPreferences.getString("infirmier_nom", "Nom inconnu");
        String infirmierPrenom = sharedPreferences.getString("infirmier_prenom", "Prénom inconnu");
        String infirmierEmail = sharedPreferences.getString("infirmier_email", "Email inconnu");
        String photoByteArrayString = sharedPreferences.getString("infirmier_photo_byte", ""); // Récupérer le byte[] en tant que String

        // Afficher ces informations dans les TextViews correspondants
        TextView nomTextView = binding.profilNom; // Assure-toi que tu as ce TextView dans le layout XML
        TextView prenomTextView = binding.profilPrenom; // Pareil pour le prénom

        nomTextView.setText(infirmierNom);
        prenomTextView.setText(infirmierPrenom);

        // Si le byte[] de l'image est présent et valide, on le convertit et on charge l'image dans Glide
        if (!photoByteArrayString.isEmpty()) {
            try {
                byte[] decodedByteArray = Base64.decode(photoByteArrayString, Base64.DEFAULT); // Décoder le byte[] en base64

                Glide.with(this)
                        .load(decodedByteArray) // Charge le tableau d'octets
                        .placeholder(R.drawable.defaultprofile_image) // Image de remplacement pendant le chargement
                        .error(R.drawable.defaultprofile_image) // Image si le chargement échoue
                        .into(binding.profilImage); // ImageView où l'image de profil sera affichée
            } catch (IllegalArgumentException e) {
                // En cas d'erreur de décodage, on affiche l'image de remplacement
                Glide.with(this)
                        .load(R.drawable.defaultprofile_image)
                        .into(binding.profilImage);
            }
        } else {
            // Si aucune image n'est présente, on affiche une image par défaut
            Glide.with(this)
                    .load(R.drawable.defaultprofile_image)
                    .into(binding.profilImage);
        }

        binding.profilEditButtonTv.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_editProfileFragment));
        binding.profilChangePasswordButton.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_changePasswordFragment2));


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Libération du binding pour éviter les fuites de mémoire
    }
}
