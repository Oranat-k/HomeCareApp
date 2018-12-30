package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PlannerActivity extends AppCompatActivity {


    ListView simpleList;
    String  Item[] = {"ยา", "อาการ", "ความดัน", "ค่าน้ำตาลในเลือด", "วันนัดหมอ", "เพิ่มเติม"};
    String  SubItem[] = {"บันทึกยาที่ต้องทานประจำ","อาการผิดปกติ","","","","การดูเเลเพิ่มเติมที่ไม่มีในนี้"};
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

        simpleList.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position==0){
                    Intent myintent = new Intent (view.getContext (),HistoryMedicineActivity.class);
                    startActivityForResult (myintent,0);
                }
                if (position==1){
                    Intent myintent = new Intent (view.getContext (),AddSymptomActivity.class);
                    startActivityForResult (myintent,1);
                }
                if (position==2){
                    Intent myintent = new Intent (view.getContext (),AddPressureActivity.class);
                    startActivityForResult (myintent,2);
                }
                if (position==3){
                    Intent myintent = new Intent (view.getContext (),AddSugarActivity.class);
                    startActivityForResult (myintent,3);
                }
                if (position==4){
                    Intent myintent = new Intent (view.getContext (),AddDoctorActivity.class);
                    startActivityForResult (myintent,4);
                }
                if (position==5){
                    Intent myintent = new Intent (view.getContext (),AddTherapyActivity.class);
                    startActivityForResult (myintent,5);
                }
            }
        });

    }

}
