package myapp.net.inspire.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

import myapp.net.inspire.R;

/**
 * Created by deadlydragger on 11/4/18.
 */

public class ProgressGraphFragment extends Fragment {
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LineChart chart = (LineChart) view.findViewById(R.id.chart);
        chart.getDescription().setEnabled(false);


        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 2));
        entries.add(new Entry(1, 1));
        entries.add(new Entry(2, 2));
        entries.add(new Entry(3, 4));
        entries.add(new Entry(4, 2));
        entries.add(new Entry(5, 0));

        LineDataSet dataSet = new LineDataSet(entries, "label");
        dataSet.setColor(ContextCompat.getColor(getContext(), R.color.planBackground));
        dataSet.setCircleColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        dataSet.setCircleHoleColor(ContextCompat.getColor(getContext(), R.color.white));


        dataSet.setCircleRadius(3.0f);
        dataSet.setLineWidth(2.0f);
        dataSet.setValueTextColor(ContextCompat.getColor(getContext(), R.color.transparent));

        //****
        // Controlling X axis
        XAxis xAxis = chart.getXAxis();
        // Set the xAxis position to bottom. Default is top
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        //Customizing activity_graph axis value
        final String[] months = new String[]{"9/03/18", "9/09/18", "8/27/18", "9/02/18", "8/20/18", "8/26/18"};

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return months[(int) value];
            }
        };
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

        //***
        // Controlling right side of y axis
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        //***
        // Controlling left side of y axis
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setGranularity(1f);

        // Setting Data
        LineData data = new LineData(dataSet);
        chart.setData(data);
//        chart.animateX(2500);
        //refresh
        chart.invalidate();
        firstOne();
//        chartSecond();
//        chartSecondBlue();
//        chartThird();
//        chartThirdBlue();
    }

    private void firstOne() {
        LineChart chart = (LineChart) getView().findViewById(R.id.chart1);
        chart.getDescription().setEnabled(false);


        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 2));
        entries.add(new Entry(1, 4));
        entries.add(new Entry(2, 2));
        entries.add(new Entry(3, 2));
        entries.add(new Entry(4, 4));
        entries.add(new Entry(5, 0));

        LineDataSet dataSet = new LineDataSet(entries, "label");
        dataSet.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        dataSet.setCircleColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        dataSet.setCircleHoleColor(ContextCompat.getColor(getContext(), R.color.white));


        dataSet.setCircleRadius(3.0f);
        dataSet.setLineWidth(2.0f);
        dataSet.setValueTextColor(ContextCompat.getColor(getContext(), R.color.transparent));

        //****
        // Controlling X axis
        XAxis xAxis = chart.getXAxis();
        // Set the xAxis position to bottom. Default is top
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        //Customizing activity_graph axis value
        final String[] months = new String[]{"9/03/18", "9/09/18", "8/27/18", "9/02/18", "8/20/18", "8/26/18"};


        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return months[(int) value];
            }
        };
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

        //***
        // Controlling right side of y axis
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        //***
        // Controlling left side of y axis
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setGranularity(1f);

        // Setting Data
        LineData data = new LineData(dataSet);
        chart.setData(data);
//        chart.animateX(2500);
        //refresh
        chart.invalidate();
    }

    private void chartSecond() {
        LineChart chart = (LineChart) getView().findViewById(R.id.chartSecond);
        chart.getDescription().setEnabled(false);


        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 1));
        entries.add(new Entry(1, 3));
        entries.add(new Entry(2, 7));
        entries.add(new Entry(3, 4));
        entries.add(new Entry(4, 2));
        entries.add(new Entry(5, 1));

        LineDataSet dataSet = new LineDataSet(entries, "label");
        dataSet.setColor(ContextCompat.getColor(getContext(), R.color.planBackground));
        dataSet.setCircleColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        dataSet.setCircleHoleColor(ContextCompat.getColor(getContext(), R.color.white));

        dataSet.setCircleRadius(3.0f);
        dataSet.setLineWidth(2.0f);
        dataSet.setValueTextColor(ContextCompat.getColor(getContext(), R.color.transparent));

        //****
        // Controlling X axis
        XAxis xAxis = chart.getXAxis();
        // Set the xAxis position to bottom. Default is top
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //Customizing activity_graph axis value
        final String[] months = new String[]{"9/03/18", "9/09/18", "8/27/18", "9/02/18", "8/20/18", "8/26/18"};

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return months[(int) value];
            }
        };
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

        //***
        // Controlling right side of y axis
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        //***
        // Controlling left side of y axis
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setGranularity(1f);

        // Setting Data
        LineData data = new LineData(dataSet);
        chart.setData(data);
//        chart.animateX(2500);
        //refresh
        chart.invalidate();
    }

    private void chartSecondBlue() {
        LineChart chart = (LineChart) getView().findViewById(R.id.chartSecond1);
        chart.getDescription().setEnabled(false);


        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 1));
        entries.add(new Entry(1, 7));
        entries.add(new Entry(2, 3));
        entries.add(new Entry(3, 2));
        entries.add(new Entry(4, 4));
        entries.add(new Entry(5, 1));

        LineDataSet dataSet = new LineDataSet(entries, "label");
        dataSet.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        dataSet.setCircleColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        dataSet.setCircleHoleColor(ContextCompat.getColor(getContext(), R.color.white));

        dataSet.setCircleRadius(3.0f);
        dataSet.setLineWidth(2.0f);
        dataSet.setValueTextColor(ContextCompat.getColor(getContext(), R.color.transparent));

        //****
        // Controlling X axis
        XAxis xAxis = chart.getXAxis();
        // Set the xAxis position to bottom. Default is top
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //Customizing activity_graph axis value
        final String[] months = new String[]{"9/03/18", "9/09/18", "8/27/18", "9/02/18", "8/20/18", "8/26/18"};

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return months[(int) value];
            }
        };
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

        //***
        // Controlling right side of y axis
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        //***
        // Controlling left side of y axis
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setGranularity(1f);

        // Setting Data
        LineData data = new LineData(dataSet);
        chart.setData(data);
//        chart.animateX(2500);
        //refresh
        chart.invalidate();
    }

//    private void chartThird() {
//        LineChart chart = (LineChart) getView().findViewById(R.id.chartThird);
//        chart.getDescription().setEnabled(false);
//
//
//        ArrayList<Entry> entries = new ArrayList<>();
//        entries.add(new Entry(0, 3));
//        entries.add(new Entry(1, 1));
//        entries.add(new Entry(2, 4));
//        entries.add(new Entry(3, 4));
//        entries.add(new Entry(4, 2));
//        entries.add(new Entry(5, 1));
//
//        LineDataSet dataSet = new LineDataSet(entries, "label");
//        dataSet.setColor(ContextCompat.getColor(getContext(), R.color.planBackground));
//        dataSet.setCircleColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
//        dataSet.setCircleHoleColor(ContextCompat.getColor(getContext(), R.color.white));
//
//        dataSet.setCircleRadius(3.0f);
//        dataSet.setLineWidth(2.0f);
//        dataSet.setValueTextColor(ContextCompat.getColor(getContext(), R.color.transparent));
//
//        //****
//        // Controlling X axis
//        XAxis xAxis = chart.getXAxis();
//        // Set the xAxis position to bottom. Default is top
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        //Customizing activity_graph axis value
//        final String[] months = new String[]{"9/03/18", "9/09/18", "8/27/18", "9/02/18", "8/20/18", "8/26/18"};
//
//        IAxisValueFormatter formatter = new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return months[(int) value];
//            }
//        };
//        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
//        xAxis.setValueFormatter(formatter);
//
//        //***
//        // Controlling right side of y axis
//        YAxis yAxisRight = chart.getAxisRight();
//        yAxisRight.setEnabled(false);
//
//        //***
//        // Controlling left side of y axis
//        YAxis yAxisLeft = chart.getAxisLeft();
//        yAxisLeft.setGranularity(1f);
//
//        // Setting Data
//        LineData data = new LineData(dataSet);
//        chart.setData(data);
////        chart.animateX(2500);
//        //refresh
//        chart.invalidate();
//    }
//
//
//    private void chartThirdBlue() {
//        LineChart chart = (LineChart) getView().findViewById(R.id.chartThird1);
//        chart.getDescription().setEnabled(false);
//
//
//        ArrayList<Entry> entries = new ArrayList<>();
//        entries.add(new Entry(0, 1));
//        entries.add(new Entry(1, 4));
//        entries.add(new Entry(2, 3));
//        entries.add(new Entry(3, 2));
//        entries.add(new Entry(4, 2));
//        entries.add(new Entry(5, 1));
//
//        LineDataSet dataSet = new LineDataSet(entries, "label");
//        dataSet.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
//        dataSet.setCircleColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
//        dataSet.setCircleHoleColor(ContextCompat.getColor(getContext(), R.color.white));
//
//        dataSet.setCircleRadius(3.0f);
//        dataSet.setLineWidth(2.0f);
//        dataSet.setValueTextColor(ContextCompat.getColor(getContext(), R.color.transparent));
//
//        //****
//        // Controlling X axis
//        XAxis xAxis = chart.getXAxis();
//        // Set the xAxis position to bottom. Default is top
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        //Customizing activity_graph axis value
//        final String[] months = new String[]{"9/03/18", "9/09/18", "8/27/18", "9/02/18", "8/20/18", "8/26/18"};
//
//        IAxisValueFormatter formatter = new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return months[(int) value];
//            }
//        };
//        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
//        xAxis.setValueFormatter(formatter);
//
//        //***
//        // Controlling right side of y axis
//        YAxis yAxisRight = chart.getAxisRight();
//        yAxisRight.setEnabled(false);
//
//        //***
//        // Controlling left side of y axis
//        YAxis yAxisLeft = chart.getAxisLeft();
//        yAxisLeft.setGranularity(1f);
//
//        // Setting Data
//        LineData data = new LineData(dataSet);
//        chart.setData(data);
////        chart.animateX(2500);
//        //refresh
//        chart.invalidate();
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_graph, container, false);
    }
}
