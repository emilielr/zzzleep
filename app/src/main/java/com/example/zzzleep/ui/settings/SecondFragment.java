package com.example.zzzleep.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.zzzleep.databinding.FragmentSecondBinding;
import com.example.zzzleep.R;

public class SecondFragment extends Fragment {

private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = FragmentSecondBinding.inflate(inflater, container, false);
      return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        SharedPreferences.Editor editor = prefs.edit();

        CheckBox mon = view.findViewById(R.id.checkbox_monday2);
        CheckBox tue = view.findViewById(R.id.checkbox_tuesday2);
        CheckBox wed = view.findViewById(R.id.checkbox_wednesday2);
        CheckBox thur = view.findViewById(R.id.checkbox_thursday2);
        CheckBox fri = view.findViewById(R.id.checkbox_friday2);
        CheckBox sat = view.findViewById(R.id.checkbox_saturday2);
        CheckBox sun = view.findViewById(R.id.checkbox_sunday2);

        TimePicker time = view.findViewById(R.id.bedtime_reg);

        binding.buttonSecond.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_ThirdFragment);

            editor.putBoolean("monday", mon.isChecked());
            editor.putBoolean("tuesday", tue.isChecked());
            editor.putBoolean("wednesday", wed.isChecked());
            editor.putBoolean("thursday", thur.isChecked());
            editor.putBoolean("friday", fri.isChecked());
            editor.putBoolean("saturday", sat.isChecked());
            editor.putBoolean("sunday", sun.isChecked());
            editor.putInt("minute",time.getMinute());
            editor.putInt("hour",time.getHour());
            editor.apply();


        });
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}