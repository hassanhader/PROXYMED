package com.example.proxymed.ui.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proxymed.R;
import com.example.proxymed.data.model.Utilisateur;
import com.example.proxymed.data.repository.UtilisateurRepository;
import com.example.proxymed.databinding.FragmentResetPasswordBinding;



public class ResetPasswordFragment extends Fragment {

    private FragmentResetPasswordBinding binding;
    private UtilisateurRepository utilisateurRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialisation du binding
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false);

        // Initialisation du repository
        utilisateurRepository = new UtilisateurRepository(requireActivity().getApplication());







        /*-----------------------------------
        *
        * -----------------------------------*/
        // Lors du clic sur le bouton de soumission
        binding.btnResetPassword.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();

            if (!email.isEmpty()) {
                // Vérification de l'existence de l'email dans la base de données
                new Thread(() -> {
                    Utilisateur utilisateur = utilisateurRepository.getUtilisateurByEmail(email);
                    if (utilisateur != null) {
                        // Email trouvé, popup de succès
                        getActivity().runOnUiThread(() -> {
                            // Affichage du message de succès
                            Toast.makeText(getContext(), "Email trouvé. Instructions envoyées.", Toast.LENGTH_SHORT).show();
                            // Redirection vers la page de login
                            NavHostFragment.findNavController(ResetPasswordFragment.this)
                                    .navigate(R.id.action_reset_password_to_login);
                        });
                    } else {
                        // Email non trouvé, popup d'erreur
                        getActivity().runOnUiThread(() -> {
                            binding.tvResetPasswordMessage.setText("Email non trouvé. Veuillez vérifier.");
                            binding.tvResetPasswordMessage.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        });
                    }
                }).start();
            } else {
                // Champ email vide
                Toast.makeText(getContext(), "Veuillez entrer un email.", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    // Méthode pour afficher un toast avec un message
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}

