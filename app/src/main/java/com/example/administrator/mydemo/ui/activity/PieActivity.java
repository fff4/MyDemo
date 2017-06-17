package com.example.administrator.mydemo.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.mydemo.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PieActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    @BindView(R.id.PieChart)
    PieChart mPieChart;
    private PieData mPieDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);
        ButterKnife.bind(this);
        initPie();
    }

    private void initPie() {
        mPieChart.setData(getPieDate());
        //设置描述
        mPieChart.setDescription("简单饼图");
        mPieChart.setDescriptionTextSize(20);
        //设置中心说明
        mPieChart.setCenterText("雪饼百分比");
        mPieChart.setCenterTextSize(20);

        // change the color of the center-hole 设置中心孔的颜色
        mPieChart.setHoleColor(Color.rgb(248, 157, 255));
        //设置半径
        mPieChart.setHoleRadius(60f);
        //开启内容、将对应的描述值绘制到切片中
        mPieChart.setDrawYValues(true);
        mPieChart.setDrawXValues(true);
        mPieChart.setDrawCenterText(true);

        mPieChart.setDrawHoleEnabled(true);
        //旋转的角度
        mPieChart.setRotationAngle(0);

        // enable rotation of the chart by touch 可以触摸使图表旋转
        mPieChart.setRotationEnabled(true);

        // display percentage values 可以使用百分比值
        mPieChart.setUsePercentValues(true);

        // mChart.setUnit(" €");  设置单位
        // mChart.setDrawUnitsInChart(true);  设置指示器

        // add a selection listener  添加选择监听
        mPieChart.setOnChartValueSelectedListener(this);

        // mChart.setTouchEnabled(false); 触摸设置

        mPieChart.animateXY(1500, 1500);

        mPieChart.invalidate();
    }

    public PieData getPieDate() {

        ArrayList<Entry> dataEntry = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Entry entry = new BarEntry(i+10, i);
            dataEntry.add(entry);
        }

        PieDataSet dataSet = new PieDataSet(dataEntry, "标签");

        //定义不同颜色设置
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        String[] xVals = {
                "雪饼1号",
                "雪饼2号",
                "雪饼3号",
                "雪饼4号",
                "雪饼5号"
        };
        PieData pieDate = new PieData(xVals, dataSet);

        return pieDate;
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }
}
