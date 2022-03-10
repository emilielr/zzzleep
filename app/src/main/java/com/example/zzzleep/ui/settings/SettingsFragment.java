package com.example.zzzleep.ui.settings;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.zzzleep.R;
import com.example.zzzleep.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(root.getContext());
        SharedPreferences.Editor editor = prefs.edit();

        Button save = root.findViewById(R.id.button);

        CheckBox mon = root.findViewById(R.id.checkbox_monday);
        CheckBox tue = root.findViewById(R.id.checkbox_tuesday);
        CheckBox wed = root.findViewById(R.id.checkbox_wednesday);
        CheckBox thur = root.findViewById(R.id.checkbox_thursday);
        CheckBox fri = root.findViewById(R.id.checkbox_friday);
        CheckBox sat = root.findViewById(R.id.checkbox_saturday);
        CheckBox sun = root.findViewById(R.id.checkbox_sunday);

        mon.setChecked(prefs.getBoolean("monday", false));
        tue.setChecked(prefs.getBoolean("tuesday", false));
        wed.setChecked(prefs.getBoolean("wednesday", false));
        thur.setChecked(prefs.getBoolean("thursday", false));
        fri.setChecked(prefs.getBoolean("friday", false));
        sun.setChecked(prefs.getBoolean("sunday", false));


        EditText editName = root.findViewById(R.id.name);
        editName.setText(prefs.getString("name", ""));

        TimePicker time = root.findViewById(R.id.bedtime_subject);


        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editor.putString("name", editName.getText().toString());

                editor.putBoolean("monday", mon.isChecked());
                editor.putBoolean("tuesday", tue.isChecked());
                editor.putBoolean("wednesday", wed.isChecked());
                editor.putBoolean("thursday", thur.isChecked());
                editor.putBoolean("friday", fri.isChecked());
                editor.putBoolean("saturday", sat.isChecked());
                editor.putBoolean("sunday", sun.isChecked());

                editor.apply();

            };

        });

        return root;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
