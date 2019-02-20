package akkaudom.oranat.th.ac.su.reg.homecarese.Tabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import akkaudom.oranat.th.ac.su.reg.homecarese.HomeCareUitil;
import akkaudom.oranat.th.ac.su.reg.homecarese.PlannerActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.R;

public class DoctorTab extends Activity {

    public  static ListView doctorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_tab);

        doctorList = (ListView) findViewById (R.id.doctorList);

    }
    public static  void refreshData(Context context){
        HomeCareUitil.getPlanerData(context,PlannerActivity.selectToday,"Doctors",doctorList);

    }
}
