package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_home);

        getSupportActionBar().setTitle("Home");

    }

        public void onClickButtonMenu(View view){
        switch (view.getId()) {
            case R.id.homepageM:
                break;
            case R.id.chartM:
                startActivity(new Intent(HomeActivity.this, PlannerListActivity.class));
                break;
            case R.id.plannerM:
                startActivity(new Intent(HomeActivity.this, PlannerListActivity.class));
                break;
            case R.id.notiM:
                startActivity(new Intent(HomeActivity.this, PlannerListActivity.class));
                break;
            case R.id.profileM:
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                break;

        }
    }//back page

}
