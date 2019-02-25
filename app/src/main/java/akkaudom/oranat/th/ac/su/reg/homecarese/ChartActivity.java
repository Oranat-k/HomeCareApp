package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddDoctorActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddPressureActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddSugarActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddSymptomActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddTherapyActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.Tabs.PressureTab;

public class ChartActivity extends AppCompatActivity {
    Activity mcontext = ChartActivity.this;

    LocalActivityManager mLocalActivityManager;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3,floatingActionButton4,floatingActionButton5,floatingActionButton6;

    BottomNavigationView mBottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_chart);

        getSupportActionBar().setTitle("Chart");


        mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        // Tab


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomBar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        bottomNavigationView.setSelectedItemId (R.id.itemChart);

        mBottomNavigation =(BottomNavigationView) findViewById(R.id.bottomBar);
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemHome:
                    startActivity(new Intent(ChartActivity.this, HomeActivity.class));
                        return true;
                    case R.id.itemChart:
//                        startActivity(new Intent(ChartActivity.this, ChartActivity.class));
                        return true;
                    case R.id.itemPlanner:
                        startActivity(new Intent(ChartActivity.this, PlannerActivity.class));
                        return true;
                    case R.id.itemNoti:
                        startActivity(new Intent(ChartActivity.this, NotificationActivity.class));
                        return true;
                    case R.id.itemProfile:
                        startActivity(new Intent(ChartActivity.this, ProfileActivity.class));
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
                Intent addPlanner = new Intent(ChartActivity.this,  AddPressureActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(ChartActivity.this,  AddSugarActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(ChartActivity.this,  AddSymptomActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(ChartActivity.this,  HistoryMedicineActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(ChartActivity.this,  AddDoctorActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(ChartActivity.this,  AddTherapyActivity.class);
                startActivity(addPlanner);

            }
        });//FloatingActionMenu


        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup(mLocalActivityManager);

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tab1")
                .setIndicator("ค่าความดัน")
                .setContent(new Intent(mcontext, PressureTab.class));

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("tab2")
                .setIndicator("ค่าน้ำตาล")
                .setContent(new Intent(mcontext, PressureTab.class));

        tabHost.addTab(tabSpec);
        tabHost.addTab(tabSpec2);
        //tab chart
    }
}