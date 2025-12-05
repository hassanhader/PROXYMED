package com.example.proxymed.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proxymed.R;
import com.example.proxymed.data.model.VisiteEntity;
import com.example.proxymed.databinding.ItemVisiteBinding;

public class VisiteAdapter extends ListAdapter<VisiteEntity, VisiteAdapter.VisiteViewHolder> {

    public VisiteAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public VisiteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemVisiteBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_visite, parent, false);
        return new VisiteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(VisiteViewHolder holder, int position) {
        VisiteEntity visite = getItem(position); // Récupérer la visite à partir de la liste
        holder.bind(visite); // Mettre à jour la vue avec les données de la visite
    }


    /*
        Cette classe interne représente un ViewHolder pour un élément de la liste des visites.
        Elle est utilisé par le RecyclerView pour afficher les visites dans la liste.
        Elle utilise DataBinding pour lier les données de la visite au layout item_visite.xml.
     */
    static class VisiteViewHolder extends RecyclerView.ViewHolder {
        private final ItemVisiteBinding binding;

        public VisiteViewHolder(ItemVisiteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(v -> {
                // Gestion du clic : passer à un autre fragment/activité par exemple
                //Juste pour tester, afficher un popup
                Toast.makeText(itemView.getContext(), "Clic sur l'élément " + (getAdapterPosition() + 1), Toast.LENGTH_SHORT).show();
            });
        }


        //bind : Sert à lier les données du modèle à la vue en utilisant DataBinding
        public void bind(VisiteEntity visite) {
            binding.setVisite(visite); // Lier la visite au layout
            binding.executePendingBindings();
        }
    }

    // DiffUtil callback pour gérer les changements dans la liste
    private static final DiffUtil.ItemCallback<VisiteEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<VisiteEntity>() {
        @Override
        public boolean areItemsTheSame(VisiteEntity oldItem, VisiteEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(VisiteEntity oldItem, VisiteEntity newItem) {
            return oldItem.equals(newItem);
        }
    };
}
