package com.example.proxymed.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.proxymed.R;
import com.example.proxymed.data.model.Utilisateur;
import com.example.proxymed.data.repository.UtilisateurRepository;
import com.example.proxymed.databinding.FragmentLoginBinding;
import com.example.proxymed.ui.nurse.NursePageActivity;

public class LoginFragment extends Fragment {

    // Variable pour le binding
    private FragmentLoginBinding binding;
    private UtilisateurRepository utilisateurRepository;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialisation du binding
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        // Initialisation du repository
        utilisateurRepository = new UtilisateurRepository(requireActivity().getApplication());

        // Bouton de connexion
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                // Lancer la vérification dans un thread séparé
                new Thread(() -> {
                    Utilisateur utilisateur = utilisateurRepository.getUtilisateurByEmailAndPassword(email, password);
                    if (utilisateur != null) {
                        // Utilisateur trouvé et mot de passe valide
                        getActivity().runOnUiThread(() -> {
                            Log.d("Login", "Connexion réussie pour " + utilisateur.getNom());


                            // Sauvegarder l'ID de l'utilisateur dans SharedPreferences
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putLong("infirmier_id", utilisateur.getId()); // Enregistrer l'ID de l'infirmier
                            editor.putString("infirmier_nom", utilisateur.getNom()); // Enregistrer le nom
                            editor.putString("infirmier_prenom", utilisateur.getPrenom()); // Enregistrer le prénom
                            editor.apply();


                            // Naviguer vers la page d'accueil en fonction du type d'utilisateur
                            if (utilisateur.getType().equals("infirmier")) {
                                Intent intent = new Intent(getActivity(), NursePageActivity.class);
                                startActivity(intent);
                                getActivity().finish(); // Terminer l'activité de connexion pour empêcher de revenir en arrière
                            }
                            else if (utilisateur.getType().equals("medecin")) {
                                Intent intent = new Intent(getActivity(), NursePageActivity.class);
                                startActivity(intent);
                                getActivity().finish(); // Terminer l'activité de connexion pour empêcher de revenir en arrière
                            }
                        });
                    } else {
                        // Utilisateur ou mot de passe incorrect
                        getActivity().runOnUiThread(() -> {
                            Toast.makeText(getContext(), "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                        });
                    }
                }).start();
            } else {
                // Email ou mot de passe vide
                Toast.makeText(getContext(), "Veuillez entrer votre email et mot de passe", Toast.LENGTH_SHORT).show();
            }
        });


        // Naviguer vers SignUpFragment
        binding.tvGoToSignUp.setOnClickListener(v -> {
            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.action_login_to_signup);
        });

        // Naviguer vers ResetPasswordFragment
        binding.tvForgotPassword.setOnClickListener(v -> {
            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.action_login_to_reset_password);
        });


        // Retourner la vue root (layout)
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Assurez-vous de libérer le binding pour éviter les fuites de mémoire
        binding = null;
    }
}