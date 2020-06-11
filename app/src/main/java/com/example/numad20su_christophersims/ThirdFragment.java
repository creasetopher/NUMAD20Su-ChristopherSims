package com.example.numad20su_christophersims;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ThirdFragment extends Fragment {
    List<LinkGroup> links = new ArrayList<>();
    ArrayAdapter<String> listAdapter;
    ListView listView;
    AlertDialog.Builder urlpop;

    private class LinkGroup {
        private String name;
        private String url;

        public LinkGroup (String name, String url) {
            this.name = name;
            this.url = url;
        }

        @Override
        public String toString() {
            return String.format("Name: %s | URL: %s", this.name, this.url);
        }

        public String getName() {
            return this.name;
        }

        public String getUrl() {
            return this.url;
        }

    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false);
    }

    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listAdapter = new ArrayAdapter(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                links);

        listView = (ListView)view.findViewById(R.id.listViewForLinks);

        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinkGroup element = (LinkGroup)listView.getItemAtPosition(position);
                Intent browserWindow = new Intent(Intent.ACTION_VIEW, Uri.parse(element.getUrl()));
                startActivity(browserWindow);
            }
        });

        urlpop = new AlertDialog
                .Builder(this.getContext());
        urlpop.setTitle("Add a Link");



//        final EditText nameInput = new EditText(this.getContext());
//        final EditText urlInput = new EditText(this.getContext());
//
//        nameInput.setInputType(InputType.TYPE_CLASS_TEXT);
//        urlInput.setInputType(InputType.TYPE_CLASS_TEXT);
//
//        nameInput.setHint("name");
//        urlInput.setHint("URL");







        view.findViewById(R.id.floatingButton).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        LinearLayout layoutForLinkInput = new LinearLayout(getContext());
                        layoutForLinkInput.setOrientation(LinearLayout.VERTICAL);

                        final EditText nameInput = new EditText(getContext());
                        final EditText urlInput = new EditText(getContext());

                        nameInput.setInputType(InputType.TYPE_CLASS_TEXT);
                        urlInput.setInputType(InputType.TYPE_CLASS_TEXT);

                        nameInput.setHint("name");
                        urlInput.setHint("URL");

                        layoutForLinkInput.addView(nameInput);
                        layoutForLinkInput.addView(urlInput);
                        final Snackbar snackbar =  Snackbar.make(view, "Link successfully added!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);

                        urlpop.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = nameInput.getText().toString();
                                String link = urlInput.getText().toString();

                                if (!link.startsWith("http://") && !link.startsWith("https://"))
                                    link = "http://" + link;


                                links.add(new LinkGroup(name, link));

                                listAdapter.notifyDataSetChanged();
                                snackbar.show();

                            }
                        });


                        urlpop.setView(layoutForLinkInput);
                        urlpop.show();




//                        final AlertDialog openURLpop = urlpop.show();




//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//                builder.create().show();
//                Log.v("T", "???");

            }
        });
    }


//    @Override
//    public void onDestroyView(View view) {
//        super.onDestroyView();
//        if (view != null) {
//            ViewGroup parent = (ViewGroup) view.getParent();
//            if (parent != null) {
//                parent.removeAllViews();
//            }
//        }
//    }


}
