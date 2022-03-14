package com.example.zzzleep.ui.dashboard;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.zzzleep.R;
import com.example.zzzleep.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment implements View.OnClickListener {
    private View mView;
    private Button btnTimerStart, btnTimerEnd;
    private Chronometer timerHere;

    private FragmentDashboardBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        mView = inflater.inflate(R.layout.fragment_dashboard, container,false);
        btnTimerStart=(Button)mView.findViewById(R.id.counter_button_start);
        btnTimerEnd=(Button)mView.findViewById(R.id.counter_button_end);
        timerHere=(Chronometer) mView.findViewById(R.id.timerHere);
        btnTimerStart.setOnClickListener(this);
        btnTimerEnd.setOnClickListener(this);
        return mView;

        //return root;
    }


    public void startTimer(){
        timerHere.setBase(SystemClock.elapsedRealtime());
        timerHere.start();
        btnTimerEnd.animate().alpha(1).translationY(-80).setDuration(300).start();
        btnTimerStart.animate().alpha(0).setDuration(300).start();
    }

    public void stopTimer(){
        timerHere.stop();
        btnTimerStart.animate().alpha(1);
        btnTimerEnd.animate().alpha(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.counter_button_start:
                startTimer();
                break;
            case R.id.counter_button_end:
                stopTimer();
                break;
        }
    }




        //Start timer
        //timerHere.setBase(SystemClock.elapsedRealtime());
        //timerHere.start();
        //btnTimerEnd.animate().alpha(1).translationY(-80).setDuration(300).start();
        //btnTimerStart.animate().alpha(0).setDuration(300).start();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}