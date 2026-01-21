package com.example.listycity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    interface AddCityDialogueListener {
        void addCity(City city);
        void editCity(City city, int position);
    }

    // Add fields for editing
    private boolean editMode = false;
    private int editPosition = -1;
    private City editCity = null;

    // Method for adding
    public static AddCityFragment newInstance() {
        return new AddCityFragment();
    }

    // Method for editing
    public static AddCityFragment newInstance(City city, int position) {
        AddCityFragment fragment = new AddCityFragment();
        fragment.editMode = true;
        fragment.editPosition = position;
        fragment.editCity = city;
        return fragment;
    }

    private AddCityDialogueListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // check if our context is an instance of our interface
        if (context instanceof AddCityDialogueListener) {
            listener = (AddCityDialogueListener) context;
        } else {
            throw new RuntimeException(context + "must implement AddCityDialogueListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // If editing, pre-fill the fields
        if (editMode && editCity != null) {
            editCityName.setText(editCity.getName());
            editProvinceName.setText(editCity.getProvince());
        }

        // Set different title and button text for edit mode
        String title;
        String buttonText;

        if (editMode) {
            title = "Edit City";
            buttonText = "Save";
        } else {
            title = "Add City";
            buttonText = "Add";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(title)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(buttonText, (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    City city = new City(cityName, provinceName);

                    if (editMode) {
                        listener.editCity(city, editPosition);  // Call edit method
                    } else {
                        listener.addCity(city);  // Call add method
                    }
                })
                .create();
    }
}