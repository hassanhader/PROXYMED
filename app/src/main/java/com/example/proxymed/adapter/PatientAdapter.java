package com.example.proxymed.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proxymed.R;
import com.example.proxymed.data.model.PatientEntity;
import com.example.proxymed.databinding.PatientListItemBinding;

public class PatientAdapter extends ListAdapter<PatientEntity, PatientAdapter.PatientViewHolder> {

    public PatientAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public PatientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PatientListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.patient_list_item, parent, false);
        return new PatientViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(PatientViewHolder holder, int position) {
        PatientEntity patient = getItem(position);
        holder.bind(patient);
    }

    static class PatientViewHolder extends RecyclerView.ViewHolder {
        private final PatientListItemBinding  binding;

        public PatientViewHolder(PatientListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(v -> {
                // Exemple : Afficher un message lors du clic
                Toast.makeText(itemView.getContext(), "Clic sur le patient " + (getAdapterPosition() + 1), Toast.LENGTH_SHORT).show();
            });
        }

        public void bind(PatientEntity patient) {
            binding.setPatient(patient);
            binding.executePendingBindings();
        }
    }

    private static final DiffUtil.ItemCallback<PatientEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<PatientEntity>() {
        @Override
        public boolean areItemsTheSame(PatientEntity oldItem, PatientEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(PatientEntity oldItem, PatientEntity newItem) {
            return oldItem.equals(newItem);
        }
    };
}
