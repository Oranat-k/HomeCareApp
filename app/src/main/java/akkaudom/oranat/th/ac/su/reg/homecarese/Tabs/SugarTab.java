package akkaudom.oranat.th.ac.su.reg.homecarese.Tabs;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;

import akkaudom.oranat.th.ac.su.reg.homecarese.R;

public class SugarTab extends Activity {

    LineChart mpLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sugar_tab);

//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);
//        linearLayout.setBackgroundColor(Color.parseColor("#00b06b"));

        mpLineChart = (LineChart) findViewById (R.id.chart);
        LineDataSet lineDataSet1 = new LineDataSet (dataValues1 (),"ค่าบน");
        LineDataSet lineDataSet2 = new LineDataSet (dataValues2 (),"ค่าล่าง");

        ArrayList<ILineDataSet> dataSets = new ArrayList<> ();
        dataSets.add (lineDataSet1);
        dataSets.add (lineDataSet2);

        LineData data = new LineData (dataSets);
        mpLineChart.setData (data);
        mpLineChart.invalidate ();

        mpLineChart.animateX (2000);

        lineDataSet2.setColor (Color.parseColor("#feb062"));
        lineDataSet1.setLineWidth (3f);
        lineDataSet2.setLineWidth (3f);
        lineDataSet1.setValueTextSize (15f);
        lineDataSet2.setValueTextSize (15f);
        lineDataSet2.setCircleColor (Color.parseColor("#feb062"));
    }

    private ArrayList<Entry> dataValues1()
    {
        ArrayList<Entry> dataVals = new ArrayList<Entry> ();
        dataVals.add (new Entry (0,200));
        dataVals.add (new Entry (1,150));
        dataVals.add (new Entry (2,50));
        dataVals.add (new Entry (3,100));
        dataVals.add (new Entry (4,55));

        return dataVals;
    }
    private ArrayList<Entry> dataValues2()

    {
        ArrayList<Entry> dataVals = new ArrayList<Entry> ();
        dataVals.add (new Entry (0,170));
        dataVals.add (new Entry (1,130));
        dataVals.add (new Entry (2,70));
        dataVals.add (new Entry (3,120));
        dataVals.add (new Entry (4,60));

        return dataVals;
    }

}