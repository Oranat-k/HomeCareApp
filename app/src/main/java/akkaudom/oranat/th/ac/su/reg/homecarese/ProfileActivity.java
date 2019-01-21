package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    ArrayList<String> arrayPlanner = new ArrayList<> ();

    ListView listPlanner ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_profile);
//
//        getDataToArr();

        getSupportActionBar().setTitle("Profile");





    }
//    public void onClickButtonMenu(View view){
//        switch (view.getId()) {
//            case R.id.homepageM:
//                startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
//                break;
//            case R.id.chartM:
//                startActivity(new Intent(ProfileActivity.this, PlannerListActivity.class));
//                break;
//            case R.id.plannerM:
//                startActivity(new Intent(ProfileActivity.this, PlannerListActivity.class));
//                break;
//            case R.id.notiM:
//                startActivity(new Intent(ProfileActivity.this, PlannerListActivity.class));
//                break;
//            case R.id.profileM:
//
//                break;
//
//        }
//    }//back page

    public void onClickaddPatient(View view){
        startActivity (new Intent(ProfileActivity.this,AddPatientActivity.class));
    }//back page



}
