package com.example.numad20su_christophersims;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class FourthFragment extends Fragment {
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fourth, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final AlertDialog.Builder builder
                = new AlertDialog
                .Builder(this.getContext());
        builder.setMessage("Name: Christopher Sims\nEmail: sims.ch@husky.neu.edu");

        builder.setPositiveButton("Got it!", null);

        builder.setNegativeButton("Go Back", null);


        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(FourthFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);;
//                builder.create().show();
//                Log.v("T", "???");

            }
        });
    }
}
