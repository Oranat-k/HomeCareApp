package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_planner);

        // menu bar
        ActionBar actionBar = getSupportActionBar ();
        actionBar.setTitle ("เพิ่มหมายเหตุ");
        // menu bar

    }

    public void onClickHisMed(View view){
        startActivity (new Intent(PlannerActivity.this,HistoryMedicineActivity.class));
        // next page this หน้าปัจจุบัน
    }

    public void onClickHisTherapy(View view){
        startActivity (new Intent(PlannerActivity.this,AddTherapyActivity.class));
        // next page this หน้าปัจจุบัน
    }
    public void onClickHisSymptom(View view){
        startActivity (new Intent(PlannerActivity.this,AddSymptomActivity.class));
        // next page this หน้าปัจจุบัน
    }


}
