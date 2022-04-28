package com.example.zzzleep.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.zzzleep.R;
import com.example.zzzleep.databinding.FragmentDashboardBinding;
import com.example.zzzleep.ui.statistics.SleepObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DashboardFragment extends Fragment implements View.OnClickListener {
    private View mView;
    private Button btnTimerStart, btnTimerEnd;
    private Chronometer timerHere;

    private FragmentDashboardBinding binding;
    private String datePattern = "dd-MM-yyyy";


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
        SleepObject o1 = new SleepObject(new SimpleDateFormat(datePattern).format(new Date()), (int) ((SystemClock.elapsedRealtime() - timerHere.getBase()) /1000));
        createFileData(o1);

        ArrayList<SleepObject> dataList = getFileData();
        for (SleepObject object : dataList) {
            Log.d("Hentet objekt:", String.valueOf(object.getHours()));

        }

    }

    private ArrayList<SleepObject> getFileData() {
        ArrayList<SleepObject> dataList = new ArrayList<>();

        try (FileInputStream fi = (getContext().openFileInput("data.ser"));
             ObjectInputStream os = new ObjectInputStream(fi)) {
            dataList = (ArrayList<SleepObject>)os.readObject();

        } catch (Exception e) {
            Log.e(this.getActivity().getLocalClassName(), "Exception reading file", e);
        }
        return dataList;
    }


    private void createFileData(SleepObject object){  //funksjonen som lagrer timer til data.ser filen
        ArrayList<SleepObject> sleepObjectsList = getFileData();
        sleepObjectsList.add(object);

        try (FileOutputStream fs = (getContext().openFileOutput("data.ser", Context.MODE_PRIVATE));
             ObjectOutputStream os = new ObjectOutputStream(fs)) {
            os.writeObject(sleepObjectsList);
        } catch (Exception e) {
            Log.e(this.getActivity().getLocalClassName(), "Exception writing file", e);
        }

    //SleepObject o1 = new SleepObject(new SimpleDateFormat(datePattern).format(new Date()), (int) ((SystemClock.elapsedRealtime() - timerHere.getBase()) /1000));
    //createFileData(o1);
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



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}