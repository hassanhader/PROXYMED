package com.example.proxymed.ui.nurse.dashboard.listevisite;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.databinding.DataBindingUtil;
import com.example.proxymed.R;
import com.example.proxymed.databinding.FragmentListeVisiteBinding;
import com.example.proxymed.data.model.VisiteEntity;
import com.example.proxymed.adapter.VisiteAdapter;
import com.example.proxymed.viewmodel.VisiteViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class ListeVisiteFragment extends Fragment {

    private VisiteViewModel visitViewModel;
    private VisiteAdapter adapter;
    private SearchView searchView; // Barre de recherche pour filtrer les visites



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Utilisation de DataBinding
        FragmentListeVisiteBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_liste_visite, container, false);
        View root = binding.getRoot();

        // Initialiser le ViewModel
        visitViewModel = new ViewModelProvider(this).get(VisiteViewModel.class);

        // Configurer le RecyclerView
        RecyclerView recyclerView = binding.rvVisites;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Adapter pour afficher la liste des visites
        adapter = new VisiteAdapter();
        recyclerView.setAdapter(adapter);

        // Observer les visites et mettre à jour l'UI quand les données changent
        visitViewModel.getAllVisites().observe(getViewLifecycleOwner(), visits -> {
            if (visits != null) {
                adapter.submitList(visits); // Mettre à jour la liste dans l'adapter dès que les données sont recues
            }
        });

        // Clic sur le bouton flottant pour être rediriger vers la création d'une nouvelle activité visite
        binding.fabAddVisite.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreerVisiteActivity.class);
            startActivity(intent);
        });


        // Configurer la barre de recherche
        searchView = binding.searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // On ne fait rien ici
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtrer la liste en fonction de la requête
                filterList(newText);
                return false;
            }
        });

        // Ajouter un écouteur de clic long pour supprimer une visite
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                                                @Override
                                                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                                                    if (e.getAction() == MotionEvent.ACTION_DOWN) {
                                                        View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                                                        if (childView != null) {
                                                            int position = recyclerView.getChildAdapterPosition(childView);
                                                            showDeleteConfirmationDialog(position);
                                                        }
                                                    }
                                                    return false;
                                                }
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

        return root;
    }

    // Fonction pour filtrer la liste des visites
    private void filterList(String query) {
        visitViewModel.getAllVisites().observe(getViewLifecycleOwner(), visits -> {
            if (visits != null) {
                // Filtrer les visites en fonction du nom ou prénom du patient
                List<VisiteEntity> filteredList = visits.stream()
                        .filter(visite -> visite.getPatientNom().toLowerCase().contains(query.toLowerCase()) ||
                                visite.getPatientPrenom().toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList());

                // Mettre à jour la liste filtrée dans l'adaptateur
                adapter.submitList(filteredList);
            }
        });
    }

    // Affiche le dialogue de confirmation pour la suppression
    private void showDeleteConfirmationDialog(int position) {
        new AlertDialog.Builder(getContext())
                .setMessage("Êtes-vous sûr de vouloir supprimer cette visite ?")
                .setPositiveButton("Oui", (dialog, which) -> deleteVisiteFromDatabase(position))
                .setNegativeButton("Annuler", null)
                .show();
    }

    // Supprimer la visite de la base de données et mettre à jour la liste
    private void deleteVisiteFromDatabase(int position) {
        VisiteEntity visite = adapter.getCurrentList().get(position);
        visitViewModel.deleteVisite(visite); // Appelle la méthode dans le ViewModel pour supprimer la visite
        adapter.notifyItemRemoved(position); // Met à jour la RecyclerView
    }
}
