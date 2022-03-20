/*
package com.example.zzzleep.ui.settings;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.zzzleep.R;

public class CustomClickListener implements View.OnClickListener {

    View view;

    public CustomClickListener(View view) {
        this.view = view;
    }


    @Override
    public void onClick(View view)
    {
        //boolean checked = ((CheckBox) view).isChecked();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        SharedPreferences.Editor editor = prefs.edit();

        Button save = ((Button) view).findViewById(R.id.button);

        editor.putString("name", name.getText().toString());
        editor.apply();

                /*

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_monday:
                if (checked)
                    monday = (CheckBox)view.findViewById(R.id.checkbox_monday);
                monday.setText("heihei");
                editor.putBoolean("monday", true);
                editor.apply();
                break;
            case R.id.checkbox_tuesday:
                if (checked)
                    tuesday = (CheckBox)view.findViewById(R.id.checkbox_tuesday);
                tuesday.setText("heihei tysdag");
                editor.putBoolean("tuesday", true);
                editor.apply();
                break;
        }


    }

}
*/