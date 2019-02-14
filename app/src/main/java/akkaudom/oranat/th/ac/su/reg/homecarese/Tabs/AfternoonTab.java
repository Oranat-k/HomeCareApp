package akkaudom.oranat.th.ac.su.reg.homecarese.Tabs;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.Calendar;

import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.PlannerAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.PlannerActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.R;

public class AfternoonTab  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.morning_tab);

        ListView morningList = (ListView) findViewById (R.id.morningList);

        Calendar selectToday = PlannerActivity.selectToday;

        final String date = selectToday.get (Calendar.DAY_OF_MONTH) + "-" +selectToday.get (Calendar.MONTH)+1+"-" +selectToday.get (Calendar.YEAR);

        PlannerAdapter AP = new PlannerAdapter (PlannerActivity.afternoonArrPlanner,this,date,"morning");
        morningList.setAdapter (AP);
    }
}
