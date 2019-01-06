package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayPlanner = new ArrayList<> ();

    ListView listPlanner ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
//
//        getDataToArr();

        getSupportActionBar().setTitle("Planner");





    }

//    private void getDataToArr() {
//        arrayPlanner.add ("พลิกตัว");
//        arrayPlanner.add ("กายภาพบำบัด");
//        arrayPlanner.add ("ตรวจเท้า");
//
//        PlannerAdapter planArr = new PlannerAdapter (arrayPlanner,MainActivity.this);
//        listPlanner = findViewById (R.id.listPlanner);
//        listPlanner.setAdapter (planArr);
//
//    }//ดึงข้อมูลมาโชว์

    public void onClickaddPlanner(View view){
        startActivity (new Intent(MainActivity.this,PlannerActivity.class));
    }//back page

    public void onClickPlanner(View view){
        startActivity (new Intent(MainActivity.this,PlannerListActivity.class));
    }//back page
}
