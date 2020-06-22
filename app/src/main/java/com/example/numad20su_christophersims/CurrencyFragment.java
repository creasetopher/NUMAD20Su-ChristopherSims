package com.example.numad20su_christophersims;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.numad20su_christophersims.services.CurrencyService;

public class CurrencyFragment extends Fragment {
    private TextView currencyTextView;
    private Handler textHandler = new Handler();
    private Spinner dropdown;
    private TextView currencyResultText;
    private EditText currencyAmountInput;
    private String currencyDest;
    private String amount;

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
        dropdown = view.findViewById(R.id.currency_dropdown);
        currencyResultText = (TextView)view.findViewById(R.id.currency_text);
        currencyAmountInput = (EditText) view.findViewById(R.id.currency_input);
        currencyAmountInput.setText("100");
        amount = currencyAmountInput.getText().toString();

        String[] currencies = new String[] {
                "Canadian Dollar (CAD)",
                "British Pound Sterling (GBP)",
                "Japanese Yen (JPY)",
                "Nigerian Naira (NGN)",
                "New Taiwan Dollar (TWD)",
                "United States Dollar (USD)"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                currencies);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                final Spinner dropdown = parentView.findViewById(R.id.currency_dropdown);
                final TextView currencyResultText = (TextView)parentView.findViewById(R.id.currency_text);

                String source = (String)dropdown.getItemAtPosition(position);
                currencyDest = source.substring(
                        source.indexOf('(') + 1,
                        source.length() - 1);


//                currencyResultText.setText(source);
                runnableThread runnableThread = new runnableThread();
                new Thread(runnableThread).start();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        dropdown.setAdapter(adapter);

        currencyAmountInput.addTextChangedListener(new TextWatcher() {
            final Spinner dropdown = view.findViewById(R.id.currency_dropdown);
            final TextView currencyResultText = (TextView)view.findViewById(R.id.currency_text);
            runnableThread runnableThread = new runnableThread();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                CurrencyFragment.this.amount = s.toString();
                new Thread(runnableThread).start();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String amount = s.toString();
                CurrencyFragment.this.amount = amount;
                new Thread(runnableThread).start();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    class runnableThread implements Runnable {
        @Override
        public void run() {
            RequestQueue queue = Volley.newRequestQueue(getContext());

            CurrencyService currencyService = new CurrencyService();
            float amount;

            try {
                amount = Float.parseFloat(CurrencyFragment.this.amount);

                currencyService.makeConversionRequest(amount, currencyDest, queue);
                String conversion = currencyService.getConversion();

                while (conversion == null) {
                    conversion = currencyService.getConversion();
                }
                String finalConversion = Float.toString(Float.parseFloat(conversion) * amount);
                textHandler.post(() -> currencyResultText.setText(String.format("%s  %s", finalConversion, currencyDest)));

            }
            catch (Exception e) {
                textHandler.post(() -> currencyResultText
                        .setText("Not a valid amount :( /n please enter a monetary value in USD"));

            }

        }
    }
}
