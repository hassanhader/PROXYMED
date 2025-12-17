package com.example.proxymed.ui.nurse.dashboard.listepatient;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proxymed.R;
import com.example.proxymed.adapter.PatientAdapter;
import com.example.proxymed.data.model.PatientEntity;
import com.example.proxymed.databinding.FragmentListePatientsFragmentsBinding;
import com.example.proxymed.viewmodel.PatientViewModel;

import java.util.List;
import java.util.stream.Collectors;

/*
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proxymed.R;
import com.example.proxymed.databinding.FragmentListePatientsFragmentsBinding;

public class ListePatientsFragments extends Fragment {

    private FragmentListePatientsFragmentsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListePatientsFragmentsBinding.inflate(inflater, container, false);
        // Retour au fragment précédent lorsque la flèche de retour est pressée
        binding.getRoot().setOnClickListener(v -> {
            NavHostFragment.findNavController(ListePatientsFragments.this).navigateUp();  // Retour
        });
        return binding.getRoot();
    }
}*/
public class ListePatientsFragments extends Fragment {

    private PatientViewModel patientViewModel;
    private PatientAdapter adapter;
    private SearchView searchView;
    private List<PatientEntity> allPatients; // Conserver la liste complète des patients

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Utilisation de DataBinding
        FragmentListePatientsFragmentsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_liste_patients_fragments, container, false);
        View root = binding.getRoot();

        // Initialiser le ViewModel
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);

        // Configurer le RecyclerView
        RecyclerView recyclerView = binding.rvPatients;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Adapter pour afficher la liste des patients
        adapter = new PatientAdapter();
        recyclerView.setAdapter(adapter);

        // Observer les patients et mettre à jour l'UI
        patientViewModel.getAllPatients().observe(getViewLifecycleOwner(), patients -> {
            if (patients != null) {
                allPatients = patients;  // Garder une copie des patients complets
                adapter.submitList(patients); // Mettre à jour la liste dans l'adapter dès que les données sont reçues
            }
        });

        // Clic sur le bouton flottant pour être rediriger vers la création d'un nouveau patient
        binding.fabAddPatient.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreerPatientActivity.class);
            startActivity(intent);
        });

        // Configurer la barre de recherche
        searchView = binding.searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtrer la liste en fonction de la requête
                filterList(newText);
                return false;
            }
        });

        return root;
    }

    // Fonction pour filtrer la liste des patients
    private void filterList(String query) {
        if (allPatients != null) {
            List<PatientEntity> filteredList = allPatients.stream()
                    .filter(patient -> patient.getNom().toLowerCase().contains(query.toLowerCase()) ||
                            patient.getPrenom().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());

            // Mettre à jour la liste filtrée dans l'adaptateur
            adapter.submitList(filteredList);
        }
    }
}
