package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;
import java.util.Map;

import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.ProfileAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddDoctorActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddPatientActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddPressureActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddSugarActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddSymptomActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddTherapyActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.ProfileDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {


    ArrayList <ProfileDetail> medArrl = new ArrayList<> ();

    ListView lstHisPatient;
    Button b;

    TextView nameProfile;
    CircleImageView imgProfile;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3,floatingActionButton4,floatingActionButton5,floatingActionButton6;

    BottomNavigationView mBottomNavigation;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_profile);

//        getSupportActionBar().setTitle("Profile");
//        getSupportActionBar().hide();//Ocultar ActivityBar anterior

        nameProfile = (TextView)  findViewById (R.id.nameProfile);
        imgProfile = (CircleImageView)  findViewById (R.id.imgProfile);

        nameProfile.setText (UserDetail.userName);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        FirebaseAuth.getInstance().signOut();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomBar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        bottomNavigationView.setSelectedItemId (R.id.itemProfile);

        mBottomNavigation =(BottomNavigationView) findViewById(R.id.bottomBar);
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemHome:
                        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                        return true;
                    case R.id.itemChart:
                        startActivity(new Intent(ProfileActivity.this, ChartActivity.class));
                        return true;
                    case R.id.itemPlanner:
                        startActivity(new Intent(ProfileActivity.this, PlannerActivity.class));
                        return true;
                    case R.id.itemNoti:
                        startActivity(new Intent(ProfileActivity.this, NotificationActivity.class));
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
                Intent addPlanner = new Intent(ProfileActivity.this,  AddPressureActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(ProfileActivity.this,  AddSugarActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(ProfileActivity.this,  AddSymptomActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(ProfileActivity.this,  HistoryMedicineActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(ProfileActivity.this,  AddDoctorActivity.class);
                startActivity(addPlanner);

            }
        });
        floatingActionButton6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlanner = new Intent(ProfileActivity.this,  AddTherapyActivity.class);
                startActivity(addPlanner);

            }
        });//FloatingActionMenu


        if (ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(ProfileActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},1);
        }//ดัก

        //getDataToArr();

        lstHisPatient = (ListView) findViewById (R.id.lstHisPatient) ;

        lstHisPatient.setOnTouchListener(new View.OnTouchListener () {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        b = findViewById (R.id.call);

        b.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v)
            {
                Intent callIntent=new Intent (Intent.ACTION_CALL);
                callIntent.setData (Uri.parse ("tel:123"));
                startActivity(callIntent);
            }
        }); //call

        DatabaseReference reference1 = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://homecare-90544.firebaseio.com/users/"+UserDetail.userName+"");

        reference1.child ("patients").addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, String> map = (Map) ds.child ("ProfilePatient").getValue ();

                    ProfileDetail newMed = new ProfileDetail (
                            map.get ("Nickname").toString (),
                            map.get ("ImageUrl").toString (),
                            map.get ("Status").toString ());

                    medArrl.add (newMed);
                }
                ProfileAdapter medicineAdapter = new ProfileAdapter (medArrl,ProfileActivity.this);
                lstHisPatient.setAdapter(medicineAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        reference1.child ("profile").addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String img = snapshot.child ("ImageUrl").getValue ().toString ();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                        .child(img.substring (1,img.length ()));

                Glide.with(ProfileActivity.this)
                        .using(new FirebaseImageLoader ())
                        .load(storageReference)
                        .into(imgProfile);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                Toast.makeText(ProfileActivity.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                Toast.makeText(ProfileActivity.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                return true;

        }

        return super.onOptionsItemSelected(item);

    }//Android Options Menu Icon



    public void onClickaddPatient(View view){
        startActivity (new Intent(ProfileActivity.this,AddPatientActivity.class));
    }//back page



}
