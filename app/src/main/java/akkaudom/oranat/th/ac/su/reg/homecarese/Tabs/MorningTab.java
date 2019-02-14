package akkaudom.oranat.th.ac.su.reg.homecarese.Tabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.PlannerAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.PlannerDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.HomeCareUitil;
import akkaudom.oranat.th.ac.su.reg.homecarese.PlannerActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.PlannerListActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.R;

public class MorningTab extends Activity {
    public  static ListView morningList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.morning_tab);

        morningList  = (ListView) findViewById (R.id.morningList);



//       HomeCareUitil.getMorning(this,PlannerActivity.selectToday,morningList);


    }

    public static  void refreshData(Context context){
        HomeCareUitil.getMorning(context,PlannerActivity.selectToday,morningList);

    }

}
