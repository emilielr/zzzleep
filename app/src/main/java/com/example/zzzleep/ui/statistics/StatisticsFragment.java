package com.example.zzzleep.ui.statistics;

import android.os.Bundle;
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
import java.io.ObjectInputStream;
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

    // Function for stying the outlook of the bar chart
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
        @Override
        public String getFormattedValue(float value) {
            return (datesOnXAxis.get((int) value - 1));
        }
    }


    private void getBarEntries(ArrayList<SleepObject> data) {
        barEntriesArrayList = new ArrayList<>();
        List<String> xValues = Arrays.asList("1f", "2f", "3f", "4f", "5f", "6f", "7f");
        List<String> dates = new ArrayList<>(Arrays.asList("-", "-", "-", "-", "-", "-", "-"));
        int counter = 6;
        int dateCounter = 0;
        int dataSize = data.size();

        if (dataSize == 0) {
            int x = 0;
            while (x < 7) {
                barEntriesArrayList.add(new BarEntry(Float.parseFloat(xValues.get(x)), 0));
                x++;
            }
        }

        else {
            // We want to display the 7 latest days
            // If we have more than 7 objects, we want to take the dates from the end of the list
            // If we have less than 7 objects, we want to get them from the start of the list
            if (dataSize > 7) {
                for (int i = dataSize - 1; i >= dataSize - 7; i--) {
                    barEntriesArrayList.add(new BarEntry(Float.parseFloat(xValues.get(counter)), data.get(i).getHours()));
                    dates.add(dateCounter, formatDate(data.get(i).getDate()));
                    dateCounter++;
                    counter--;
                }
            } else {
                for (int i = 0; i < dataSize; i++) {
                    barEntriesArrayList.add(new BarEntry(Float.parseFloat(xValues.get(i)), data.get(i).getHours()));
                    dates.add(i, formatDate(data.get(i).getDate()));
                    counter--;
                    dateCounter++;
                }
            }

            while (counter > -1) {
                barEntriesArrayList.add(new BarEntry(Float.parseFloat(xValues.get(counter)), 0));
                counter--;
            }

        }

        datesOnXAxis = dates;
    }

    public String formatDate(String date) {
        String[] parts = date.split("-");
        String day = parts[0];
        String month = parts[1];
        return String.format("%s/%s", day, month);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}