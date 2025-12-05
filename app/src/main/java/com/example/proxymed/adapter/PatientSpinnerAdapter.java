package com.example.proxymed.adapter;

import android.content.Context;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.proxymed.data.model.PatientEntity;

import java.util.List;

//Pour afficher le nom des patients dans la liste déroulante
public class PatientSpinnerAdapter extends ArrayAdapter<PatientEntity> {

    public PatientSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<PatientEntity> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        PatientEntity patient = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(patient.getNom() + " " + patient.getPrenom());  // Affiche le nom complet du patient
        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        PatientEntity patient = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(patient.getNom() + " " + patient.getPrenom());  // Affiche le nom complet du patient
        return view;
    }
}

