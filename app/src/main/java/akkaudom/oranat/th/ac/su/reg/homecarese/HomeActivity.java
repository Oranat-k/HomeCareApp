package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddDoctorActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddPressureActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddSugarActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddSymptomActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddTherapyActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;


public class HomeActivity extends AppCompatActivity {

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3,floatingActionButton4,floatingActionButton5,floatingActionButton6;

    BottomNavigationView mBottomNavigation;

    RecyclerView mRecyclerView;
    String contentNoti = "";

    LinearLayout newsChild;
    LinearLayout manualChild;

    int widthDevice ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_home);

        //getSupportActionBar().setTitle("Home");
        getSupportActionBar().hide();
        //not nev bar

        widthDevice = getWindowManager().getDefaultDisplay().getWidth();
        manualChild = (LinearLayout) findViewById(R.id.recyclerview);
        newsChild = (LinearLayout) findViewById(R.id.newsChild);
        //content news


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
                            }//set show to Sting

                        }

                        if (!contentNoti.equals ("")){
                            NotiUitil.notiAlarm (HomeActivity.this , contentNoti);
                        }//notShowData เมื่อไม่มีค่า ว่าง



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


//        mRecyclerView = findViewById(R.id.recyclerview);


        DatabaseReference referenceWeek = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://homecare-90544.firebaseio.com/");

        Log.d ("test", String.valueOf (referenceWeek));

        referenceWeek.child ("contents").addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, String> map = (Map)ds.getValue();
                    createImg(
                            map.get("textCon").toString(),
                            map.get("imgCon").toString()


                    );
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        }); //content news



        referenceWeek.child("manual").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, String> map = (Map)ds.getValue();
                    createImg2(
                            map.get("title").toString(),
                            map.get("pic").toString(),
                            ds.getKey()
                    );
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });


    }

    private void createImg(String name , String img) {
        CardView cardView = new CardView(this);
        cardView.setRadius (5);
        CardView.LayoutParams params = new CardView.LayoutParams(
                CardView.LayoutParams.WRAP_CONTENT,
                CardView.LayoutParams.WRAP_CONTENT
        );
        int cardMargin = (int) (widthDevice * 0.028);
        params.setMargins(cardMargin, cardMargin, cardMargin, cardMargin);
        cardView.setLayoutParams(params);

        LinearLayout boder = new LinearLayout(this);
        LinearLayout.LayoutParams boderparams = new LinearLayout.LayoutParams(
                (int) (widthDevice * 0.6),
                (int) (widthDevice * 0.85)
        );
        boder.setLayoutParams(boderparams);
        boder.setBackgroundResource(R.drawable.box_content);
        boder.setOrientation(LinearLayout.VERTICAL);

        RoundedImageView roundedImageView = new RoundedImageView(this);
        LinearLayout.LayoutParams roundparams = new LinearLayout.LayoutParams(
                (int) (widthDevice * 0.77),
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int roundMargin = (int) (widthDevice * 0.015);
        roundparams.setMargins( roundMargin, roundMargin, roundMargin, roundMargin);
        roundparams.gravity = Gravity.CENTER;
        roundedImageView.setLayoutParams(roundparams);
        roundedImageView.setBorderColor(getResources().getColor(R.color.colorBox));
        roundedImageView.setBorderWidth(5);
        roundedImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        roundedImageView.setRadius(6);
        roundedImageView.setSquare(true);
        roundedImageView.setImageResource(R.drawable.camera3);

        Picasso.get()
                .load(img)
                .into(roundedImageView);


        TextView txtDetail = new TextView(this);
        RelativeLayout.LayoutParams txtDetailparams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        txtDetailparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        Typeface type = ResourcesCompat.getFont(this, R.font.sukhumvitset_medium);
        txtDetail.setTypeface(type);
        txtDetail.setText(name);
        txtDetail.setGravity(Gravity.CENTER);
        txtDetail.setLayoutParams(txtDetailparams);

        boder.addView(roundedImageView);
        boder.addView(txtDetail);
        cardView.addView(boder);
        newsChild.addView(cardView);
    }


    private void createImg2(String name , String img , final String key) {

        CardView cardView = new CardView(this);
        CardView.LayoutParams params = new CardView.LayoutParams(
                CardView.LayoutParams.WRAP_CONTENT,
                CardView.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(20, 20, 20, 170);
        cardView.setLayoutParams(params);

        LinearLayout boder = new LinearLayout(this);
        LinearLayout.LayoutParams boderparams = new LinearLayout.LayoutParams(
                (int) (widthDevice * 0.38),
                (int) (widthDevice * 0.45)
        );
        boder.setLayoutParams(boderparams);
        boder.setBackgroundResource(R.drawable.box_content);
        boder.setOrientation(LinearLayout.VERTICAL);

        RoundedImageView roundedImageView = new RoundedImageView(this);
        LinearLayout.LayoutParams roundparams = new LinearLayout.LayoutParams(
                (int) (widthDevice * 0.35),
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int roundMargin = (int) (widthDevice * 0.015);
        roundparams.setMargins( roundMargin, roundMargin, roundMargin, roundMargin);
        roundparams.gravity = Gravity.CENTER;
        roundedImageView.setLayoutParams(roundparams);
        roundedImageView.setBorderColor(getResources().getColor(R.color.colorBox));
        roundedImageView.setBorderWidth(5);
        roundedImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        roundedImageView.setRadius(6);
        roundedImageView.setSquare(true);
        roundedImageView.setImageResource(R.drawable.camera3);

        Picasso.get()
                .load(img)
                .into(roundedImageView);


        TextView txtDetail = new TextView(this);
        RelativeLayout.LayoutParams txtDetailparams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        txtDetailparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        Typeface type = ResourcesCompat.getFont(this, R.font.sukhumvitset_medium);
        txtDetail.setTypeface(type);
        txtDetail.setText(name);
        txtDetail.setGravity(Gravity.CENTER);
        txtDetail.setLayoutParams(txtDetailparams);

        boder.addView(roundedImageView);
        boder.addView(txtDetail);
        cardView.addView(boder);
        manualChild.addView(cardView);

        roundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDetail.selectContent = key;
                startActivity(new Intent(HomeActivity.this,DetailActivity.class));
            }
        });

//        CardView cardView = new CardView(this);
//        CardView.LayoutParams params = new CardView.LayoutParams(
//                CardView.LayoutParams.WRAP_CONTENT,
//                CardView.LayoutParams.WRAP_CONTENT
//        );
//        int cardMargin = (int) (widthDevice * 0.028);
//        params.setMargins(cardMargin, cardMargin, cardMargin, cardMargin);
//        cardView.setLayoutParams(params);
//
//        LinearLayout boder = new LinearLayout(this);
//        LinearLayout.LayoutParams boderparams = new LinearLayout.LayoutParams(
//                (int) (widthDevice * 0.5),
//                (int) (widthDevice * 0.4)
//        );
//        boder.setLayoutParams(boderparams);
//        boder.setBackgroundResource(R.drawable.box_content);
//        boder.setOrientation(LinearLayout.VERTICAL);
//
//        ImageView imageView = new ImageView(this);
//        LinearLayout.LayoutParams roundparams = new LinearLayout.LayoutParams(
//                (int) (widthDevice * 0.48),
//                (int) (widthDevice * 0.33)
//        );
//        int roundMargin = (int) (widthDevice * 0.01);
//        roundparams.setMargins( roundMargin, 0, roundMargin, 0);
//        imageView.setLayoutParams(roundparams);
//        Picasso.get()
//                .load(img)
//                .into(imageView);
//
//
//        TextView txtDetail = new TextView(this);
//        RelativeLayout.LayoutParams txtDetailparams = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT
//        );
//        txtDetailparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
//        Typeface type = ResourcesCompat.getFont(this, R.font.sukhumvitset_medium);
//        txtDetail.setTypeface(type);
//        txtDetail.setText(name);
//        txtDetail.setGravity(Gravity.CENTER);
//        txtDetail.setLayoutParams(txtDetailparams);
//
//        boder.addView(imageView);
//        boder.addView(txtDetail);
//        cardView.addView(boder);
//        manualChild.addView(cardView);
//
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UserDetail.selectContent = key;
//                startActivity(new Intent(HomeActivity.this,DetailActivity.class));
//            }
//        });

    }


}


