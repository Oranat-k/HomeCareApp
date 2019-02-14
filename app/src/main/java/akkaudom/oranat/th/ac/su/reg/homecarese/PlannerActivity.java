package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.app.Activity;
import android.app.LocalActivityManager;
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
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.PatientAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.PlannerAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.ProfileAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.PlannerDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.ProfileDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Tabs.AfternoonTab;
import akkaudom.oranat.th.ac.su.reg.homecarese.Tabs.MorningTab;

public class PlannerActivity extends AppCompatActivity {

    Activity mcontext = PlannerActivity.this;

    LocalActivityManager mLocalActivityManager;


    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3,floatingActionButton4,floatingActionButton5,floatingActionButton6;

    BottomNavigationView mBottomNavigation;
    Spinner patientNames;
    ImageButton btnback , btnnext;
    TextView Day;
    public static Calendar today, selectToday;

    public  static ArrayList<PlannerDetail> morningArrPlanner = new ArrayList<> ();
    public  static ArrayList<PlannerDetail> afternoonArrPlanner = new ArrayList<> ();
    public  static ArrayList<PlannerDetail> eveningArrPlanner = new ArrayList<> ();
    public  static ArrayList<PlannerDetail> befoebedArrPlanner = new ArrayList<> ();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_planner);

        getSupportActionBar().hide();
        //not nev bar

        mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);

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
        patientNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                UserDetail.selectPatient = arg2;
                Log.d ("patient select : ", "{"+arg2+"}");
                MorningTab.refreshData (mcontext);

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //optionally do something here
            }
        });



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

        setDafault(morningArrPlanner);
        setDafault(afternoonArrPlanner);
        setDafault(eveningArrPlanner);
        setDafault(befoebedArrPlanner);



        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup(mLocalActivityManager);

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tab1")
                .setIndicator("เช้า")
                .setContent(new Intent(mcontext, MorningTab.class));

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("tab2")
                .setIndicator("กลางวัน")
                .setContent(new Intent(mcontext, AfternoonTab.class));

        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("tab2")
                .setIndicator("เย็น")
                .setContent(new Intent(mcontext, AfternoonTab.class));

        TabHost.TabSpec tabSpec4 = tabHost.newTabSpec("tab2")
                .setIndicator("ก่อนนอน")
                .setContent(new Intent(mcontext, AfternoonTab.class));

        TabHost.TabSpec tabSpec5 = tabHost.newTabSpec("tab2")
                .setIndicator("วันนัดหมอ")
                .setContent(new Intent(mcontext, AfternoonTab.class));


        tabHost.addTab(tabSpec);
        tabHost.addTab(tabSpec2);
        tabHost.addTab(tabSpec3);
        tabHost.addTab(tabSpec4);
        tabHost.addTab(tabSpec5);



    }

    public  void  checkStatus(JSONObject obj , String during , ArrayList<PlannerDetail> arrPlanner){

        try {
            JSONObject objDuring =  obj.getJSONObject (during);
            for(int i = 0 ; i < arrPlanner.size () ; i++) {
                if (objDuring.has (arrPlanner.get (i).getTitle ())) {
                    arrPlanner.get (i).setStatus (objDuring.getString (arrPlanner.get (i).getTitle ()));

                }
            }
        } catch (JSONException e) {
            e.printStackTrace ();
        }

    }//Status Morning afternoon evening



//    public void getData(){
//        final String date = selectToday.get (Calendar.DAY_OF_MONTH) + "-" +selectToday.get (Calendar.MONTH)+1+"-" +selectToday.get (Calendar.YEAR);
//
//        String url = "https://homecare-90544.firebaseio.com/users/"+UserDetail.userName+"/patients/"
//                +UserDetail.patient.get (UserDetail.selectPatient).getId ()+"/Planners/"+date+".json";
//
//        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
//            @Override
//            public void onResponse(String s) {
//                try {
//                    JSONObject obj = new JSONObject(s);
//
//                    Iterator i = obj.keys();
//                    String key = "";
//
//                    while(i.hasNext()){
//                        key = i.next().toString();
//
//                        switch (key){
//                            case "Morning":
//                                checkStatus(obj,key,morningArrPlanner);
//
//                                break;
//                            case "Afternoon":
//                                checkStatus(obj,key,afternoonArrPlanner);
//                                break;
//                            case "Evening":
//                                checkStatus(obj,key,eveningArrPlanner);
//                                break;
//                            case "BeforBed":
//                                checkStatus(obj,key,befoebedArrPlanner);
//                                break;
//                        }
//
//
//                    }
//
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        },new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                System.out.println("" + volleyError);
//            }
//        });
//
//        RequestQueue rQueue = Volley.newRequestQueue(mcontext);
//        rQueue.add(request);
//    }

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


    private void setDafault(ArrayList<PlannerDetail> list) {


        PlannerDetail plan = new PlannerDetail ("พลิกตัว","default","false");
        PlannerDetail plan1 = new PlannerDetail ("กายภาพบำบัด","default","false");
        PlannerDetail plan2 = new PlannerDetail ("ตรวจเท้า","default","false");

        list.add (plan);
        list.add (plan1);
        list.add (plan2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocalActivityManager.dispatchPause(!isFinishing());

    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocalActivityManager.dispatchResume();
    }

}
