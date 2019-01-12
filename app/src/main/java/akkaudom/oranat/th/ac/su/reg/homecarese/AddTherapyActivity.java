package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;

public class AddTherapyActivity extends AppCompatActivity {

    Button btnMorning,btnAfternoon,btnEvening,btnBeforeBed;
    EditText dataTherapy;

    String rangeTherapy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_therapy);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Therapy");
        //กดกลับ ตั้งชื่อหน้านั้น


        Createwidget();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(AddTherapyActivity.this, PlannerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    } //กดกลับ ตั้งชื่อหน้านั้น

    public void InsertData(View view) {

        DatabaseReference referenTherapy = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://homecare-90544.firebaseio.com");

        referenTherapy.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child("Therapys").child(dataTherapy.getText ().toString ()).child ("Range").setValue(rangeTherapy);

        startActivity (new Intent(AddTherapyActivity.this,PlannerActivity.class));

    }

    private void Createwidget() {


        btnMorning = (Button) findViewById (R.id.btnMorning);
        btnAfternoon = (Button) findViewById (R.id.btnAfternoon);
        btnEvening = (Button) findViewById (R.id.btnEvening);
        btnBeforeBed = (Button) findViewById (R.id.btnBeforeBed);

        dataTherapy = (EditText) findViewById (R.id.dataTherapy);


        //สลับสีปุ่ม

        btnMorning.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundResource (R.drawable.border_box_active);
                btnAfternoon.setBackgroundResource (R.drawable.border_box);
                btnEvening.setBackgroundResource (R.drawable.border_box);
                btnBeforeBed.setBackgroundResource (R.drawable.border_box);
                rangeTherapy = "Morning";

            }
        });

        btnAfternoon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundResource (R.drawable.border_box);
                btnAfternoon.setBackgroundResource (R.drawable.border_box_active);
                btnEvening.setBackgroundResource (R.drawable.border_box);
                btnBeforeBed.setBackgroundResource (R.drawable.border_box);
                rangeTherapy = "Afternoon";

            }
        });

        btnEvening.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundResource (R.drawable.border_box);
                btnAfternoon.setBackgroundResource (R.drawable.border_box);
                btnEvening.setBackgroundResource (R.drawable.border_box_active);
                btnBeforeBed.setBackgroundResource (R.drawable.border_box);
                rangeTherapy = "Evening";

            }
        });

        btnBeforeBed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundResource (R.drawable.border_box);
                btnAfternoon.setBackgroundResource (R.drawable.border_box);
                btnEvening.setBackgroundResource (R.drawable.border_box);
                btnBeforeBed.setBackgroundResource (R.drawable.border_box_active);
                rangeTherapy = "BeforeBed";

            }
        });
    }

}
