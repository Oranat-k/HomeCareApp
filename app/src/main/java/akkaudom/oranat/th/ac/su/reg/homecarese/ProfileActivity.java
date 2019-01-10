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

        getSupportActionBar().setTitle("Home");


//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
//
//        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
//        for (int i = 0; i < menuView.getChildCount(); i++) {
//            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
//            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
//            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
//            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
//            iconView.setLayoutParams(layoutParams);
//        }
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.itemHome:
//                        return true;
//                    case R.id.itemChart:
//                        //startActivity(new Intent(ProfileActivity.this, MomActivity.class));
//                        return true;
//                    case R.id.itemPlanner:
//                        startActivity(new Intent(ProfileActivity.this, PlannerListActivity.class));
//                        return true;
//                }
//                return false;
//            }
//        });
//        bottomNavigationView.setSelectedItemId(R.id.itemProfile);



    }
    public void onClickButtonMenu(View view){
        switch (view.getId()) {
            case R.id.homepageM:
                break;
            case R.id.chartM:
                break;
            case R.id.plannerM:
                startActivity(new Intent(ProfileActivity.this, PlannerListActivity.class));
                break;
            case R.id.notiM:
                startActivity(new Intent(ProfileActivity.this, PlannerListActivity.class));
                break;
            case R.id.profileM:
                startActivity(new Intent(ProfileActivity.this, PlannerListActivity.class));
                break;

        }
    }//back page

    public void onClickaddPlanner(View view){
        startActivity (new Intent(ProfileActivity.this,PlannerActivity.class));
    }//back page

    public void onClickPlanner(View view){
        startActivity (new Intent(ProfileActivity.this,PlannerListActivity.class));
    }//back page

}
