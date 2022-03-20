package com.example.zzzleep.ui.statistics;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.zzzleep.R;
import com.example.zzzleep.databinding.FragmentStatisticsBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class StatisticsFragment extends Fragment {

    private FragmentStatisticsBinding binding;

    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;

    ArrayList barEntriesArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StatisticsViewModel homeViewModel =
                new ViewModelProvider(this).get(StatisticsViewModel.class);

        binding = FragmentStatisticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        barChart = root.findViewById(R.id.barChart);

        createFileData();
        ArrayList<SleepObject> data = getFileData();
        getBarEntries(data);

        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "Sleep pattern");

        // creating a new bar data and passing our bar data set.
        barData = new BarData(barDataSet);

        barChart.setData(barData);

        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);

        return root;
    }


    private void createFileData() {
        ArrayList<SleepObject> sleepObjectsList = new ArrayList<SleepObject>();
        SleepObject obj1 = new SleepObject("11-03-2022", 8);
        SleepObject obj2 = new SleepObject("12-03-2022", 3);
        SleepObject obj3 = new SleepObject("13-03-2022", 6);
        SleepObject obj4 = new SleepObject("14-03-2022", 5);
        SleepObject obj5 = new SleepObject("15-03-2022", 8);
        SleepObject obj6 = new SleepObject("16-03-2022", 7);
        SleepObject obj7 = new SleepObject("17-03-2022", 7);

        sleepObjectsList.add(obj1);
        sleepObjectsList.add(obj2);
        sleepObjectsList.add(obj3);
        sleepObjectsList.add(obj4);
        sleepObjectsList.add(obj5);
        sleepObjectsList.add(obj6);
        sleepObjectsList.add(obj7);

        try (FileOutputStream fs = (getContext().openFileOutput("data.ser", Context.MODE_PRIVATE));
             ObjectOutputStream os = new ObjectOutputStream(fs)) {
            os.writeObject(sleepObjectsList);
        } catch (Exception e) {
            Log.e(this.getActivity().getLocalClassName(), "Exception writing file", e);
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


    private void getBarEntries(ArrayList<SleepObject> data) {
        barEntriesArrayList = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntriesArrayList.add(new BarEntry(1f, data.get(0).getHours()));
        barEntriesArrayList.add(new BarEntry(2f, data.get(1).getHours()));
        barEntriesArrayList.add(new BarEntry(3f, data.get(2).getHours()));
        barEntriesArrayList.add(new BarEntry(4f, data.get(3).getHours()));
        barEntriesArrayList.add(new BarEntry(5f, data.get(4).getHours()));
        barEntriesArrayList.add(new BarEntry(6f, data.get(5).getHours()));
        barEntriesArrayList.add(new BarEntry(7f, data.get(6).getHours()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}