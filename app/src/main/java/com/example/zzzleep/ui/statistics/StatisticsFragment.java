package com.example.zzzleep.ui.statistics;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.zzzleep.R;
import com.example.zzzleep.databinding.FragmentStatisticsBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatisticsFragment extends Fragment {


    private FragmentStatisticsBinding binding;

    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;

    ArrayList<BarEntry> barEntriesArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        barChart = root.findViewById(R.id.barChart);

        //createFileData();
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
        barChart.setScaleEnabled(false);
        barChart.setTouchEnabled(false);
        barDataSet.setDrawValues(false);
        barDataSet.setColor(getContext().getColor(R.color.blue_background));
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);

    }

    private void initBarChart() {

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(new XAxisFormatter());
        xAxis.setTextSize(14f);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(true);
        rightAxis.setAxisMinimum(0);
        rightAxis.setAxisMaximum(10);
        rightAxis.setGridLineWidth(1f);
        rightAxis.setLabelCount(6);
        rightAxis.setValueFormatter(new RightYAxisFormatter());
        rightAxis.setGranularity(2f);
        rightAxis.setTextSize(14f);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(false);
        leftAxis.setAxisMinimum(0);
        leftAxis.setAxisMaximum(10);
        leftAxis.setGranularity(2f);

        Legend legend = barChart.getLegend();
        legend.setEnabled(false);

    }


    private void createFileData() {
        ArrayList<SleepObject> sleepObjectsList = new ArrayList<>();
        SleepObject obj1 = new SleepObject("11-03-2022", 8);
        SleepObject obj2 = new SleepObject("12-03-2022", 3);
        SleepObject obj3 = new SleepObject("13-03-2022", 6);
        SleepObject obj4 = new SleepObject("14-03-2022", 5);
        SleepObject obj5 = new SleepObject("15-03-2022", 8);
        SleepObject obj6 = new SleepObject("16-03-2022", 7);
        //SleepObject obj7 = new SleepObject("17-03-2022", 7);

        sleepObjectsList.add(obj1);
        sleepObjectsList.add(obj2);
        sleepObjectsList.add(obj3);
        sleepObjectsList.add(obj4);
        sleepObjectsList.add(obj5);
        sleepObjectsList.add(obj6);
        //sleepObjectsList.add(obj7);

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


    static public class RightYAxisFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            return ((int) value + "h");
        }
    }

    public List<String> datesOnXAxis;

    public class XAxisFormatter extends ValueFormatter {
        //final List<String> weekdays = Arrays.asList("mon.", "tue.", "wed.", "thur.", "fri", "sat.", "sun.");
        @Override
        public String getFormattedValue(float value) {
            return (datesOnXAxis.get((int) value - 1));
        }
    }


    private void getBarEntries(ArrayList<SleepObject> data) {
        barEntriesArrayList = new ArrayList<>();
        List<String> xValues = Arrays.asList("1f", "2f", "3f", "4f", "5f", "6f", "7f");
        List<String> dates = new ArrayList<>();
        int counter = 6;
        int dateCounter = 0;
        int dataSize = data.size();

        Log.d("date", String.valueOf(dataSize));

        if (dataSize == 0) {
            int i = 0;
            while (i < 7) {
                barEntriesArrayList.add(new BarEntry(Float.parseFloat(xValues.get(i)), 0));
                dates.add("-");
                i++;
            }
        } else {
            // We want to display the 7 latest days
            // If we have more than 7 objects, we want to take the dates from the end of the list
            // If we have less than 7 objects, we want to get them from the start of the list
            if (dataSize > 6) {
                for (int i = dataSize - 1; i >= dataSize - 7; i--) {
                    barEntriesArrayList.add(new BarEntry(Float.parseFloat(xValues.get(counter)), data.get(i).getHours()));
                    dates.add(dateCounter, data.get(i).getDate());
                    dateCounter++;
                    counter--;
                }
            }
            else {
                for (int i = 0; i < 7; i++) {
                    barEntriesArrayList.add(new BarEntry(Float.parseFloat(xValues.get(counter)), data.get(i).getHours()));
                    dates.add(dateCounter, data.get(i).getDate());
                    counter--;
                    dateCounter++;
                }
            }


            while (counter > -1) {
                barEntriesArrayList.add(new BarEntry(Float.parseFloat(xValues.get(counter)), 0));
                dates.add(dateCounter, "");
                dateCounter--;
                counter--;
            }

        }

        datesOnXAxis = dates;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}