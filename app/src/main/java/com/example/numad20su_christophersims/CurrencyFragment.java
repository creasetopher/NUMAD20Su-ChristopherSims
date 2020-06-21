package com.example.numad20su_christophersims;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class CurrencyFragment extends Fragment {
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_currency, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Spinner dropdown = view.findViewById(R.id.currency_dropdown);
        final TextView currencyResultText = (TextView)view.findViewById(R.id.currency_text);
        String[] currencies = new String[]{
                "Canadian Dollar (CAD",
                "British Pound Sterling (GBP)",
                "Japanese Yen (JPY)",
                "Nigerian Naira (NGN)",
                "New Taiwan Dollar (TWD)",
                "United States Dollar (USD)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                currencies);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String source = (String)dropdown.getItemAtPosition(position);
                currencyResultText.setText(source);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        dropdown.setAdapter(adapter);



    }
}
