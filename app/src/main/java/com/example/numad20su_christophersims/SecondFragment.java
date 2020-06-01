package com.example.numad20su_christophersims;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class SecondFragment extends Fragment {
    TextView text;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_second, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView buttonPressedText =  (TextView)getView().findViewById(R.id.button_text_view);

//        view.findViewById(R.id.button_seventh)

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPressedText.setText("Pressed: A");

            }
        });

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPressedText.setText("Pressed: B");

            }
        });

        view.findViewById(R.id.button_third).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPressedText.setText("Pressed: C");

            }
        });

        view.findViewById(R.id.button_fourth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPressedText.setText("Pressed: D");


            }
        });

        view.findViewById(R.id.button_fifth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPressedText.setText("Pressed: E");

            }
        });

        view.findViewById(R.id.button_sixth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPressedText.setText("Pressed: E");

            }
        });

        view.findViewById(R.id.button_seventh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_ThirdFragment);

            }
        });



    }
}
