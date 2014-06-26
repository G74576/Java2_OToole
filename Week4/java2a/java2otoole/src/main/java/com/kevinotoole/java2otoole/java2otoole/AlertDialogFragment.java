package com.kevinotoole.java2otoole.java2otoole;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by kevinotoole on 6/24/14.
 */
public class AlertDialogFragment extends DialogFragment {

    public enum  DialogType {SEARCH, INFO, FAVORITES, PREFERENCES};

    public static DialogType type;
    public static String userRatingFile = "RATING.txt";

    public CustomAdapter adapter = (CustomAdapter) MainActivityFragment.listView.getAdapter();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        switch (type){

            case SEARCH:
                //Get custom view:
                final View view = inflater.inflate(R.layout.search_alert_dialog, null);
                builder.setView(view)
                    //Add action buttons:
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EditText searchWord = (EditText)view.findViewById(R.id.searchEditText);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialogFragment.this.getDialog().cancel();
                        }
                    });

                break;

            case INFO:

                break;

            case FAVORITES:
                final View view2 = inflater.inflate(R.layout.fav_alert_dialog, null);

                //ListView to Hold Rating Array from Main Activity of photos favorited:
                ListView favList = (ListView)view2.findViewById(R.id.favListView);
                ArrayAdapter<String> ratingAdapter = new ArrayAdapter<String>(getActivity(), R.layout.fav_list_row, MainActivity.ratingArray);
                favList.setAdapter(ratingAdapter);

                builder.setView(view2)
                    //Add action buttons:
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialogFragment.this.getDialog().cancel();
                        }
                    });

                break;

            case PREFERENCES:
                final View view3 = inflater.inflate(R.layout.preferences_alert_dialog, null);
                builder.setView(view3)
                    //Add action buttons:
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int id) {
                            //Access the username edit text field:
                            EditText username = (EditText) view3.findViewById(R.id.preferenceDialogEdit);

                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("USERNAME", username.getText().toString());
                            editor.commit();

                            //Refresh Parent activity with current User Preference:
                            Intent refresh = new Intent(MainActivity.mContext, MainActivity.class);
                            getActivity().finish();
                            startActivity(refresh);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int id) {
                            AlertDialogFragment.this.getDialog().cancel();
                        }
                    });

                break;

            default:
                break;
        }
        return builder.create();
    }

    public static AlertDialogFragment newInstance(DialogType dialogType){
        type = dialogType;
        return new AlertDialogFragment();
    }
}
