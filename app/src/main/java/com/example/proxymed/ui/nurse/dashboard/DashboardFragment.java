package com.example.proxymed.ui.nurse.dashboard;

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

import com.example.proxymed.R;
import com.example.proxymed.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        binding.setDashboardFragment(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Clique sur la carte Liste des patients
        binding.cardListePatients.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_dashboardFragment_to_listePatientsFragment);
        });

        // Clique sur la carte Rapports soumis
        binding.cardRapports.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_dashboardFragment_to_listeRapportFragment);
        });

        // Clique sur la carte Visites à effectuer
        binding.cardVisites.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_dashboardFragment_to_listeVisiteFragment);
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Eviter les memory leaks
    }
}
