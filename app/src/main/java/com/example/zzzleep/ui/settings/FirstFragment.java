package com.example.zzzleep.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.zzzleep.databinding.FragmentFirstBinding;
import com.example.zzzleep.R;

public class FirstFragment extends Fragment {

private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = FragmentFirstBinding.inflate(inflater, container, false);
      return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        SharedPreferences.Editor editor = prefs.edit();
        EditText setName = view.findViewById(R.id.name_reg);

        binding.buttonContinue.setOnClickListener(view1 -> {
            editor.putString("name", setName.getText().toString());
            editor.apply();
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
        });
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}