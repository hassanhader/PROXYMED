package com.example.proxymed.ui.nurse.dashboard.listepatient;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private final Calendar c = Calendar.getInstance();

    public interface OnDateSelectedListener {
        void onDateSelected(String formattedDate);
    }

    private OnDateSelectedListener myListener;

    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        myListener = listener;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it.
        return new DatePickerDialog(requireContext(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String formattedDate = formatDate(year, month, day);

        if (myListener != null) {
            myListener.onDateSelected(formattedDate);
        }
    }

    public String formatDate(int year, int month, int day) {
        // 2. Définir les valeurs (ATTENTION: le mois commence à 0 en Calendar)
        c.set(year, month - 1, day); // Month is 0-indexed (January is 0, February is 1, etc.)

        // 3. Obtenir la date
        Date date = c.getTime();

        // 4. Créer un SimpleDateFormat
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()); // Modifier le format selon tes besoins

        // 5. Formatter la date en String
        return sdf.format(date);
    }
}