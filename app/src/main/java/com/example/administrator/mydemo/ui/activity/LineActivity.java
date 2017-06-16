package com.example.administrator.mydemo.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.mydemo.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LineActivity extends AppCompatActivity {

    @BindView(R.id.chart)
    LineChart mChart;
    private LineData mLineData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        ButterKnife.bind(this);
        //设置没有数据时展示的文本
        mChart.setNoDataText("不好意思，没数据");
        //设置数据
        mChart.setData(getLineData());
        //设置图表的值不从0开始， 可以为负数
        mChart.setStartAtZero(false);

        //启用触摸手势
        mChart.setTouchEnabled(true);
        // 缩放和拖拽是否可用
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // 如果禁用了，缩放可以分别在x和y轴上进行
        mChart.setPinchZoom(true);

        //设置图表上x/y轴的值和显示样式
        mChart.setDrawYLabels(true);
        YLabels yLabels = mChart.getYLabels();
        yLabels.setLabelCount(10);
        yLabels.setPosition(YLabels.YLabelPosition.BOTH_SIDED);

        XLabels xLabels = mChart.getXLabels();
        xLabels.setPosition(XLabels.XLabelPosition.BOTTOM);
        yLabels.setLabelCount(10);

        // enable/disable highlight indicators (the lines that indicate the
        // highlighted Entry)  指示器高亮显示
//        mChart.setHighlightIndicatorEnabled(false);

        //设置边界状态
//        mChart.setDrawBorder(true);
//        mChart.setBorderPositions(new BarLineChartBase.BorderPosition[]{
//                BarLineChartBase.BorderPosition.BOTTOM
//        });

        // 是否绘制垂直的网格
        mChart.setDrawVerticalGrid(false);
        // mChart.setDrawHorizontalGrid(false); 水平的网格

        // mChart.setDrawXLegend(false); y轴和x轴的说明
        // mChart.setDrawYLegend(false);

        // restrain the maximum scale-out factor  限制最大的尺度
//        mChart.setScaleMinima(3f, 3f);

        //设置高亮突出
        mChart.setHighlightEnabled(true);

        //设置表格描述信息
        mChart.setDescription("这是表格的描述");
        //设置是否绘制表格背景
        mChart.setDrawGridBackground(true);
        mChart.setGridColor(Color.BLACK);
//        mChart.setBackgroundColor(Color.BLACK);
        //设置动画事件
        mChart.animateXY(3000, 3000);

        mChart.invalidate();
    }

    public LineData getLineData() {

        int count = 10;
        ArrayList<Entry> xVals = new ArrayList<Entry>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            //设置每个点的数据 两个表示两条线
            Random random = new Random();
            int i1 = random.nextInt(10);
            Entry entry1 = new BarEntry(i * i1 - 20, i);
            xVals.add(entry1);
            Entry entry2 = new BarEntry(i * i1 + i1, i);
            yVals.add(entry2);
        }

        //创建LineDataSet对象， 表示每条线的数据
        LineDataSet set1 = new LineDataSet(xVals, "线条1");
        LineDataSet set2 = new LineDataSet(yVals, "线条2");

        //设置线条的宽度和颜色
        set1.setColor(Color.YELLOW);
        set1.setLineWidth(4);

        set2.setColor(Color.CYAN);
        set2.setLineWidth(4);
        //设置不绘制线条上的圆
//        set1.setDrawCircles(false);

        //创建集合，保存所有线条
        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);

        //生成x 坐标的默认位置
        ArrayList<String> strings = ChartData.generateXVals(0, count);

        // create a data object with the datasets
        LineData data = new LineData(strings, dataSets);

        return data;
    }
}
