package com.example.proxymed.ui.nurse.dashboard.listepatient;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proxymed.R;
import com.example.proxymed.databinding.FragmentChangePasswordBinding;
import com.example.proxymed.viewmodel.UtilisateurViewModel;

public class ChangePasswordFragment extends Fragment {

    private FragmentChangePasswordBinding binding;
    private UtilisateurViewModel utilisateurViewModel;
    private SharedPreferences sharedPreferences;

    public ChangePasswordFragment() {
        super(R.layout.fragment_change_password);  // Assurez-vous d'utiliser le bon layout XML
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);

        // Initialisation du ViewModel
        utilisateurViewModel = new ViewModelProvider(this).get(UtilisateurViewModel.class);

        // Initialiser SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user_email", null);  // Récupérer l'email de l'utilisateur connecté

        if (email != null) {
            utilisateurViewModel.getUtilisateurByEmailLive(email).observe(getViewLifecycleOwner(), utilisateur -> {
                if (utilisateur != null) {
                    binding.changePasswordLast.setText("");  // Ne pas afficher le mot de passe actuel
                }
            });
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Bouton pour changer le mot de passe
        binding.changePasswordRecord.setOnClickListener(v -> changePassword(view));
    }


    public void changePassword(View view) {
        String ancienMotDePasse = binding.changePasswordLast.getText().toString();
        String nouveauMotDePasse = binding.changePasswordNew.getText().toString();
        String confirmationMotDePasse = binding.changePasswordConfirm.getText().toString();

        if (ancienMotDePasse.isEmpty() || nouveauMotDePasse.isEmpty() || confirmationMotDePasse.isEmpty()) {
            Toast.makeText(getContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        } else if (!nouveauMotDePasse.equals(confirmationMotDePasse)) {
                Toast.makeText(getContext(), "Les nouveaux mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
            } else {
                    String email = sharedPreferences.getString("user_email", null);
                    if (email != null) {
                        utilisateurViewModel.getUtilisateurByEmailLive(email).observe(getViewLifecycleOwner(), utilisateur -> {
                            if (utilisateur != null) {
                                // Vérifier si l'ancien mot de passe est correct
                                if (utilisateur.getMotDePasse().equals(ancienMotDePasse)) {
                                    utilisateur.setMotDePasse(nouveauMotDePasse); // Mise à jour du mot de passe
                                    utilisateurViewModel.update(utilisateur);
                                    Toast.makeText(getContext(), "Mot de passe modifié avec succès", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(view).navigate(R.id.action_changePasswordFragment2_to_profileFragment);
                                } else {
                                    Toast.makeText(getContext(), "Ancien mot de passe incorrect", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
            }
        }

        @Override
        public void onDestroyView ()
        {
            super.onDestroyView();
            binding = null;  // Libérer la référence de binding pour éviter les fuites mémoire
        }
}


