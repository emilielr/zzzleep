package com.example.zzzleep.ui.settings;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.zzzleep.R;
import com.example.zzzleep.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    CheckBox monday, tuesday, wednesday, thursday, friday, saturday, sunday;

    public void onClick(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_monday:
                if (checked)
                    monday = (CheckBox)view.findViewById(R.id.checkbox_monday);
                    monday.setText("heihei");
                break;
            case R.id.checkbox_tuesday:
                if (checked)
                    tuesday = (CheckBox)view.findViewById(R.id.checkbox_tuesday);
                tuesday.setText("heihei tysdag");
                break;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CheckBox mon = root.findViewById(R.id.checkbox_monday);
        mon.setOnClickListener(this::onClick) ;
        CheckBox tus = root.findViewById(R.id.checkbox_tuesday);
        tus.setOnClickListener(this::onClick) ;
        CheckBox wed = root.findViewById(R.id.checkbox_wednesday);
        wed.setOnClickListener(this::onClick) ;
        CheckBox thur = root.findViewById(R.id.checkbox_thursday);
        thur.setOnClickListener(this::onClick);
        CheckBox fri = root.findViewById(R.id.checkbox_friday);
        fri.setOnClickListener(this::onClick) ;
        CheckBox sat = root.findViewById(R.id.checkbox_saturday);
        sat.setOnClickListener(this::onClick) ;
        CheckBox sun = root.findViewById(R.id.checkbox_sunday);
        sun.setOnClickListener(this::onClick) ;

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
