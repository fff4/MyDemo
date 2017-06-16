package com.example.administrator.mydemo.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.mydemo.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BarActivity extends AppCompatActivity {

    @BindView(R.id.BarChart)
    BarChart mBarChart;
    private BarData mBarDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        ButterKnife.bind(this);
        initBarChat();
    }

    private void initBarChat() {
        //启动值的绘制
        mBarChart.setDrawYValues(true);
        //
        mBarChart.setDrawValueAboveBar(true);
        //设置描述信息
        mBarChart.setDescription("这是模拟柱状图");
        //设置最大的条目值
        mBarChart.setMaxVisibleValueCount(60);
        // disable 3D 不使用3d
        mBarChart.set3DEnabled(false);
        // scaling can now only be done on x- and y-axis separately
        //只在x y 上进行缩放
        mBarChart.setPinchZoom(false);
        //设置单位
        mBarChart.setUnit("￥");

        //设置阴影--不要显示阴影
        mBarChart.setDrawBarShadow(false);

        // mChart.setDrawXLabels(false); 设置不画x 的值
        mBarChart.setDrawGridBackground(false); //设置不绘制网格图的背景
        mBarChart.setDrawHorizontalGrid(true);//设置显示水平网格
        mBarChart.setDrawVerticalGrid(false);//不显示数值网格

        // sets the text size of the values inside the chart
        mBarChart.setValueTextSize(10f); //设置文本的大小

        mBarChart.setStartAtZero(false);

        //设置x y的网格数值显示字体
        XLabels xl = mBarChart.getXLabels();
        xl.setPosition(XLabels.XLabelPosition.BOTTOM);
        xl.setCenterXLabelText(true);
//        xl.setTypeface(tf);

        YLabels yl = mBarChart.getYLabels();
//        yl.setTypeface(tf);
        yl.setLabelCount(8);
        yl.setPosition(YLabels.YLabelPosition.BOTH_SIDED);

        mBarChart.setData(getBarDate());

        mBarChart.animateXY(3000, 3000);
    }

    public BarData getBarDate() {

        int maxX = 9;

        //设置柱子的数据
        ArrayList<BarEntry> barEntries1 = new ArrayList<>();
        ArrayList<BarEntry> barEntries2 = new ArrayList<>();

        for (int i = 0; i < maxX; i++) {
            Random random = new Random();
            int i1 = random.nextInt(10);
            BarEntry barEntry1 = new BarEntry(i1 * i - 20, i);
            barEntries1.add(barEntry1);
            BarEntry barEntry2 = new BarEntry(i + 20, i);
            barEntries2.add(barEntry2);
        }

        BarDataSet set1 = new BarDataSet(barEntries1, "Android");
        BarDataSet set2 = new BarDataSet(barEntries2, "java");

        set1.setColor(Color.YELLOW);  //柱子颜色
        set2.setColor(Color.CYAN);  //柱子颜色
//        set1.setBarShadowColor(Color.GRAY);  //设置阴影颜色
//        set2.setBarShadowColor(Color.GRAY);  //设置阴影颜色


        //设置条目数据----每个条目的数据
        ArrayList<BarDataSet> dataSet = new ArrayList<>();
        dataSet.add(set1);
        dataSet.add(set2);

        //设置x轴的显示
        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("一月");
        xVals.add("二月");
        xVals.add("三月");
        xVals.add("四月");
        xVals.add("五月");
        xVals.add("六月");
        xVals.add("七月");
        xVals.add("八月");
        xVals.add("九月");
        BarData mBarDate = new BarData(xVals, dataSet);
        return mBarDate;
    }
}
