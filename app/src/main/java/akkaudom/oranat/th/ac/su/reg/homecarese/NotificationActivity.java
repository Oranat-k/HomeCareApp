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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.DoctorAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddDoctorActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddPressureActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddSugarActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddSymptomActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddTherapyActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.DoctorDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;

public class NotificationActivity extends AppCompatActivity {

//    ArrayList<DoctorDetail> medArrl = new ArrayList<DoctorDetail> ();
//    ListView doctorList;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3,floatingActionButton4,floatingActionButton5,floatingActionButton6;

    BottomNavigationView mBottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_notification);

        getSupportActionBar ().setTitle ("Notification");


//        doctorList = (ListView) findViewById (R.id.lstHisPatient) ;
//
//        doctorList.setOnTouchListener(new View.OnTouchListener () {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });
//
//
//        DatabaseReference reference1 = FirebaseDatabase.getInstance()
//                .getReferenceFromUrl("https://homecare-90544.firebaseio.com/users/"+UserDetail.userName+"");
//
//        reference1.child ("patients").addValueEventListener(new ValueEventListener () {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//
//                for(DataSnapshot ds : snapshot.getChildren()) {
//                    Map<String, String> map = (Map) ds.child ("Doctors").getValue ();
//
//                    DoctorDetail newMed = new DoctorDetail (
//                            map.get ("DoctorName").toString (),
//                            map.get ("Hospital").toString ());
//
//                    medArrl.add (newMed);
//                }
//                DoctorAdapter medicineAdapter = new DoctorAdapter (medArrl,NotificationActivity.this);
//                doctorList.setAdapter(medicineAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getMessage());
//            }
//        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomBar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        bottomNavigationView.setSelectedItemId (R.id.itemNoti);

        mBottomNavigation =(BottomNavigationView) findViewById(R.id.bottomBar);
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemHome:
                        startActivity(new Intent (NotificationActivity.this, HomeActivity.class));
                        return true;
                    case R.id.itemChart:
                        startActivity(new Intent(NotificationActivity.this, ChartActivity.class));
                        return true;
                    case R.id.itemPlanner:
                        startActivity(new Intent(NotificationActivity.this, PlannerActivity.class));
                        return true;
                    case R.id.itemNoti:
//                        startActivity(new Intent(NotificationActivity.this, NotificationActivity.class));
                        return true;
                    case R.id.itemProfile:
                        startActivity(new Intent(NotificationActivity.this, ProfileActivity.class));
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
                Intent addPlanner = new Intent(NotificationActivity.this,  AddPressureActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(NotificationActivity.this,  AddSugarActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(NotificationActivity.this,  AddSymptomActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(NotificationActivity.this,  HistoryMedicineActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(NotificationActivity.this,  AddDoctorActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(NotificationActivity.this,  AddTherapyActivity.class);
                startActivity(addPlanner);

            }
        });//FloatingActionMenu

    }


}
