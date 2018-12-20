package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PlannerActivity extends AppCompatActivity {


    ListView simpleList;
    String  Item[] = {"Apple", "Banana", "Lemon", "Cherry", "Strawberry", "Avocado"};
    String  SubItem[] = {"1","2","3","4","5","6"};
    int flags[] = {R.drawable.medicine, R.drawable.sy, R.drawable.press, R.drawable.sugar, R.drawable.doctor, R.drawable.therapy};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_planner);

        // menu bar
        ActionBar actionBar = getSupportActionBar ();
        actionBar.setTitle ("เพิ่มหมายเหตุ");

        simpleList = (ListView)findViewById(R.id.ListViewMenu);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), Item,SubItem, flags);
        simpleList.setAdapter(customAdapter);

    }



    //กด next

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
    public void onClickHisSugar(View view){
        startActivity (new Intent(PlannerActivity.this,AddSugarActivity.class));
        // next page this หน้าปัจจุบัน
    }

    public void onClickHisPressure(View view){
        startActivity (new Intent(PlannerActivity.this,AddPressureActivity.class));
        // next page this หน้าปัจจุบัน
    }


}
