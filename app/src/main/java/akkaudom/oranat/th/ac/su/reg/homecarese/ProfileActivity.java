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

    Button b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_profile);


        if (ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(ProfileActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},1);
        }//รูป pop up take photo//galley
//
//        getDataToArr();

        getSupportActionBar().setTitle("Profile");

    b = findViewById (R.id.call);

    b.setOnClickListener (new View.OnClickListener () {
        @Override
        public void onClick(View v)
        {
            Intent callIntent=new Intent (Intent.ACTION_CALL);
            callIntent.setData (Uri.parse ("tel:123"));
            startActivity(callIntent);
        }
    });



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
