package akkaudom.oranat.th.ac.su.reg.homecarese.Tabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import akkaudom.oranat.th.ac.su.reg.homecarese.HomeCareUitil;
import akkaudom.oranat.th.ac.su.reg.homecarese.PlannerActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.R;

public class EveningTab extends Activity {

    public  static ListView eveningList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evening_tab);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);
        linearLayout.setBackgroundColor(Color.parseColor("#edf7f9"));

        eveningList = (ListView) findViewById (R.id.eveningList);

    }
    public static  void refreshData(Context context){
        HomeCareUitil.getPlanerData(context,PlannerActivity.selectToday,"evening",eveningList);

    }
}
