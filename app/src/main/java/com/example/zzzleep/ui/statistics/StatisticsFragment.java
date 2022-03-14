package com.example.zzzleep.ui.statistics;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    TextView output;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StatisticsViewModel homeViewModel =
                new ViewModelProvider(this).get(StatisticsViewModel.class);

        binding = FragmentStatisticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        barChart = root.findViewById(R.id.barChart);

        // calling method to get bar entries.
        getBarEntries();

        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "Geeks for Geeks");

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);

        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);

        createFileData();

        // Read file data
        output = root.findViewById(R.id.output);

        ArrayList<SleepObject> dataList = new ArrayList<>();
        try (FileInputStream fi = (getContext().openFileInput("data.ser"));
             ObjectInputStream os = new ObjectInputStream(fi)) {
            dataList = (ArrayList<SleepObject>)os.readObject();

            String output_text = "";
            for (SleepObject obj : dataList) {
                output_text += String.format("Dato: %s. Antall timer: %o", obj.getDate(), obj.getHours());
            }

            TextView textView = root.findViewById(R.id.output);
            textView.setText(output_text);

        } catch (Exception e) {
            Log.e(this.getActivity().getLocalClassName(), "Exception reading file", e);
        }

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


    private void getBarEntries() {
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntriesArrayList.add(new BarEntry(1f, 4));
        barEntriesArrayList.add(new BarEntry(2f, 6));
        barEntriesArrayList.add(new BarEntry(3f, 8));
        barEntriesArrayList.add(new BarEntry(4f, 2));
        barEntriesArrayList.add(new BarEntry(5f, 4));
        barEntriesArrayList.add(new BarEntry(6f, 1));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}