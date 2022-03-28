package com.example.zzzleep.ui.statistics;

import android.content.Context;
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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

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

        barDataSet = new BarDataSet(barEntriesArrayList, "Hours of sleep");

        barData = new BarData(barDataSet);

        barChart.setData(barData);
        barChart.invalidate();

        initBarDataSet(barDataSet);
        initBarChart();

        return root;
    }

    // function for stying the outlook of the bar chart
    private void initBarDataSet(BarDataSet barDataSet){
        barDataSet.setColors(R.color.dark_blue);
        //showing the value of the bar, default true if not set
        barDataSet.setDrawValues(false);
        //setting the text size of the value of the bar
        barDataSet.setValueTextSize(16f);
        barDataSet.setValueTextColor(R.color.black);
        barChart.getDescription().setEnabled(false);
        barData.setBarWidth(0.9f);
        barChart.setFitBars(true);
    }

    private void initBarChart() {

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(true);
        rightAxis.setAxisMinimum(0);
        rightAxis.setAxisMaximum(10);
        rightAxis.setGridLineWidth(1f);
        rightAxis.setGranularity(2f);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(false);
        leftAxis.setAxisMinimum(0);
        leftAxis.setAxisMaximum(10);
        leftAxis.setGranularity(2f);

        Legend legend = barChart.getLegend();
        //setting the shape of the legend form to line, default square shape
        legend.setForm(Legend.LegendForm.LINE);
        //setting the text size of the legend
        legend.setTextSize(11f);
        //setting the alignment of legend toward the chart
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        //setting the stacking direction of legend
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //setting the location of legend outside the chart, default false if not set
        legend.setDrawInside(false);

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