package com.example.zzzleep.ui.goodmorning;

import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zzzleep.R;

public class GoodMorningFragment extends Fragment {

    private GoodMorningViewModel mViewModel;

    public static GoodMorningFragment newInstance() {
        return new GoodMorningFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.good_morning_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        TextView sleep = view.findViewById(R.id.numberSleepHours);
        String newSleep = prefs.getString("sleepHours", "0");
        sleep.setText("You got "+newSleep+" hours of sleep");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GoodMorningViewModel.class);
        // TODO: Use the ViewModel
    }

}