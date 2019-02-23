package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.MyAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddDoctorActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddPressureActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddSugarActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddSymptomActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddTherapyActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.FlowerData;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;


public class HomeActivity extends AppCompatActivity {

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3,floatingActionButton4,floatingActionButton5,floatingActionButton6;

    BottomNavigationView mBottomNavigation;

    RecyclerView mRecyclerView;
    List<FlowerData> mFlowerList;
    FlowerData mFlowerData;
    String contentNoti = "";

    TextView conTitle;
    ImageView conImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_home);

        //getSupportActionBar().setTitle("Home");
        getSupportActionBar().hide();
        //not nev bar
        conTitle = (TextView) findViewById (R.id.conTitle);
        conImage = (ImageView) findViewById (R.id.conImage);



        final Calendar now = Calendar.getInstance();

        String url = "https://homecare-90544.firebaseio.com/users/"+UserDetail.userName+"/patients/"
                +UserDetail.patient.get (UserDetail.selectPatient).getId ()+"/Doctors.json";

        Log.d ("url firebase", "{"+url+"}");
        final StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {

                if (!s.equals ("null")){
                    try {
                        JSONObject objData = new JSONObject (s);

                        Iterator i = objData.keys();
                        String key = "";
                        while(i.hasNext()) {
                            key = i.next ().toString ();

                            String dateStr = objData.getJSONObject (key).getString ("Date");
                            Calendar date = Calendar.getInstance ();
                            SimpleDateFormat sdf = new SimpleDateFormat ("dd-MM-yyyy hh:mm");
                            date.setTime (sdf.parse (dateStr));// all done

                            boolean sameDay = now.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR) &&
                                    now.get(Calendar.YEAR) == date.get(Calendar.YEAR);

                            if (sameDay){
                                JSONObject objDoctor = objData.optJSONObject (key);
                                contentNoti += objDoctor.getString ("Hospital") + ","
                                        + objDoctor.getString ("DoctorName") + "  "
                                        + objDoctor.getString ("Date") +"\n\n";
                            }

                        }

                        NotiUitil.notiAlarm (HomeActivity.this , contentNoti);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace ();
                    }

                }

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(HomeActivity.this);
        rQueue.add(request);
        //Notification




        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomBar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);


        mBottomNavigation =(BottomNavigationView) findViewById(R.id.bottomBar);
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemHome:
                      //startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                        return true;
                    case R.id.itemChart:
                        startActivity(new Intent(HomeActivity.this, ChartActivity.class));
                        return true;
                    case R.id.itemPlanner:
                        startActivity(new Intent(HomeActivity.this, PlannerActivity.class));
                        return true;
                    case R.id.itemNoti:
                        startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
                        return true;
                    case R.id.itemProfile:
                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
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
                Intent addPlanner = new Intent(HomeActivity.this,  AddPressureActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(HomeActivity.this,  AddSugarActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(HomeActivity.this,  AddSymptomActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(HomeActivity.this,  HistoryMedicineActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(HomeActivity.this,  AddDoctorActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(HomeActivity.this,  AddTherapyActivity.class);
                startActivity(addPlanner);

            }
        });//FloatingActionMenu


        mRecyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(HomeActivity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mFlowerList = new ArrayList<> ();
        mFlowerData = new FlowerData("ภาวะกลืนลำบาก", getString(R.string.description_flower_rose),
                R.drawable.pic_two);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("การติดเชื้อ", getString(R.string.description_flower_carnation),
                R.drawable.pic_one);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("แผลกดทับ", getString(R.string.description_flower_tulip),
                R.drawable.pic_tree);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("กายภาพ", getString(R.string.description_flower_daisy),
                R.drawable.pic_four);
        mFlowerList.add(mFlowerData);

//        mFlowerData = new FlowerData("Sunflower", getString(R.string.description_flower_sunflower),
//                R.drawable.pic_a);
//        mFlowerList.add(mFlowerData);
//        mFlowerData = new FlowerData("Daffodil", getString(R.string.description_flower_daffodil),
//                R.drawable.pic_a);
//        mFlowerList.add(mFlowerData);
//        mFlowerData = new FlowerData("Gerbera", getString(R.string.description_flower_gerbera),
//                R.drawable.pic_a);
//        mFlowerList.add(mFlowerData);
//        mFlowerData = new FlowerData("Orchid", getString(R.string.description_flower_orchid),
//                R.drawable.pic_a);
//        mFlowerList.add(mFlowerData);
//        mFlowerData = new FlowerData("Iris", getString(R.string.description_flower_iris),
//                R.drawable.pic_a);
//        mFlowerList.add(mFlowerData);
//        mFlowerData = new FlowerData("Lilac", getString(R.string.description_flower_lilac),
//                R.drawable.pic_a);
//        mFlowerList.add(mFlowerData);

        MyAdapter myAdapter = new MyAdapter(HomeActivity.this, mFlowerList);
        mRecyclerView.setAdapter(myAdapter);


    }


}


