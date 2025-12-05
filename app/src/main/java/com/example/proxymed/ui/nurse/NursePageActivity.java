package com.example.proxymed.ui.nurse;

import android.content.Intent;
import android.os.Bundle;

import com.example.proxymed.MainActivity;
import com.example.proxymed.R;
import com.example.proxymed.ui.auth.AuthentificationActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.proxymed.databinding.ActivityNursePageBinding;

public class NursePageActivity extends AppCompatActivity {

    private ActivityNursePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNursePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Associer le Toolbar comme ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Affichage de la flèche de retour dans le Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Affiche la flèche de retour

        BottomNavigationView navView = findViewById(R.id.nav_view);



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_nurseprofile, R.id.navigation_dashboard, R.id.navigation_deconnexion)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_nurse_page);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Ajouter un gestionnaire de clic pour le bouton de déconnexion
        navView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_deconnexion) {
                // Afficher une boîte de dialogue de confirmation
                new AlertDialog.Builder(NursePageActivity.this)
                        .setMessage("Êtes-vous sûr de vouloir vous déconnecter ?")
                        .setCancelable(false)
                        .setPositiveButton("Oui", (dialog, id) -> {
                            // Si l'utilisateur confirme, redirigez-le vers l'écran de connexion
                            Intent intent = new Intent(NursePageActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Supprime les activités précédentes
                            startActivity(intent);
                            finish(); // Termine l'activité actuelle pour éviter que l'utilisateur ne revienne ici en appuyant sur retour
                        })
                        .setNegativeButton("Non", null) // Si l'utilisateur annule, rien ne se passe
                        .show();
                return true;
            }
            else if (item.getItemId() == R.id.navigation_dashboard) {
                // Utiliser le NavController pour naviguer vers le fragment Dashboard
                navController.navigate(R.id.navigation_dashboard); // Redirige vers le fragment Dashboard
                return true;
            }
            else if (item.getItemId() == R.id.navigation_nurseprofile) {
                // Utiliser le NavController pour naviguer vers le fragment Profile
                navController.navigate(R.id.action_navigation_dashboard_to_profileFragment); // Redirige vers le fragment Profile
                return true;// Redirige vers le fragment Profile

            }
            return false;
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_nurse_page);
        return navController.navigateUp() || super.onSupportNavigateUp();  // Gérer la flèche de retour
    }

}