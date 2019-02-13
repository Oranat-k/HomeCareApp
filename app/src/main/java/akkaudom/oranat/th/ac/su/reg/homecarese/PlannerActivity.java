package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.Calendar;

import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.PatientAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;

public class PlannerActivity extends AppCompatActivity {

    Activity mcontext = PlannerActivity.this;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3,floatingActionButton4,floatingActionButton5,floatingActionButton6;

    BottomNavigationView mBottomNavigation;
    Spinner patientNames;
    ImageButton btnback , btnnext;
    TextView Day;
    Calendar today, selectToday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_planner);


        Day = (TextView) findViewById (R.id.day);

        btnback = (ImageButton) findViewById (R.id.back);
        btnnext = (ImageButton) findViewById (R.id.next);

        today = Calendar.getInstance ();
        today.set (Calendar.HOUR_OF_DAY,0);
        today.set (Calendar.MINUTE,0);
        today.set (Calendar.SECOND,0);

        selectToday = Calendar.getInstance ();
        selectToday.set (Calendar.HOUR_OF_DAY,0);
        selectToday.set (Calendar.MINUTE,0);
        selectToday.set (Calendar.SECOND,0);

        showDay(0);


        btnback.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                showDay(-1);
            }
        });
        btnnext.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                showDay(1);
            }
        });

        patientNames = (Spinner) findViewById (R.id.patientNames);
        PatientAdapter weekAdt = new PatientAdapter(mcontext, UserDetail.patient);
        patientNames.setAdapter(weekAdt);
        weekAdt.notifyDataSetChanged();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomBar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        bottomNavigationView.setSelectedItemId (R.id.itemPlanner);

        mBottomNavigation =(BottomNavigationView) findViewById(R.id.bottomBar);
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemHome:
                        startActivity(new Intent (mcontext, HomeActivity.class));
                        return true;
                    case R.id.itemChart:
                        startActivity(new Intent(mcontext, ChartActivity.class));
                        return true;
                    case R.id.itemPlanner:
                        startActivity(new Intent(mcontext, PlannerListActivity.class));
                        return true;
                    case R.id.itemNoti:
                        startActivity(new Intent(mcontext, NotificationActivity.class));
                        return true;
                    case R.id.itemProfile:
//                        startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                        return true;
                }
                return false;
            }
        });

        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 28, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 28, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }//menu size



        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);
        floatingActionButton4 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item4);
        floatingActionButton5 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item5);
        floatingActionButton6 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item6);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(mcontext,  AddPressureActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(mcontext,  AddSugarActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(mcontext,  AddSymptomActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(mcontext,  HistoryMedicineActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(mcontext,  AddDoctorActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(mcontext,  AddTherapyActivity.class);
                startActivity(addPlanner);

            }
        });//FloatingActionMenu

    }

    public void showDay(int changeDay){
        int day = selectToday.get (Calendar.DAY_OF_MONTH);
        selectToday.set (Calendar.DAY_OF_MONTH,day + changeDay);
        int diffY = selectToday.get (Calendar.YEAR) - today.get (Calendar.YEAR);
        int diffM = today.get (Calendar.MONTH) - selectToday.get (Calendar.MONTH);
        int diffD = today.get (Calendar.DAY_OF_MONTH) - selectToday.get (Calendar.DAY_OF_MONTH);

        Log.d ("day", String.valueOf (diffD));

        if ( diffY == 0 && diffM ==0){
            switch (diffD){
                case 0:
                    Day.setText ("วันนี้");
                    break;
                case 1:
                    Day.setText ("พรุ่งนั้");
                    break;
                case -1:
                    Day.setText ("เมื่อวานนี้");
                    break;
                default:
                    Day.setText (selectToday.get (Calendar.DAY_OF_MONTH) + "-" +(selectToday.get (Calendar.MONTH)+1)+"-" +selectToday.get (Calendar.YEAR));
                    break;
            }


        }else {
            Day.setText (selectToday.get (Calendar.DAY_OF_MONTH) + "-" +(selectToday.get (Calendar.MONTH)+1)+"-" +selectToday.get (Calendar.YEAR));

        }


    }

}
