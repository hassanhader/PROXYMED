package com.example.proxymed.ui.nurse.dashboard.listerapportsoumis;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proxymed.R;
import com.example.proxymed.databinding.FragmentListeRapportFragmentsBinding;

public class ListeRapportFragments extends Fragment {

    private FragmentListeRapportFragmentsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListeRapportFragmentsBinding.inflate(inflater, container, false);
        // Retour au fragment précédent lorsque la flèche de retour est pressée
        binding.getRoot().setOnClickListener(v -> {
            NavHostFragment.findNavController(ListeRapportFragments.this).navigateUp();  // Retour
        });
        return binding.getRoot();
    }
}