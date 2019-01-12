package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;

public class AddSymptomActivity extends AppCompatActivity {

    Button btnMorning,btnAfternoon,btnEvening,btnBeforeBed;
    EditText dataSymptom;

    String rangeSymptom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_symptom);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Symptom");
        //กดกลับ ตั้งชื่อหน้านั้น


        Createwidget();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(AddSymptomActivity.this, PlannerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    } //กดกลับ ตั้งชื่อหน้านั้น

    public void InsertData(View view) {

        DatabaseReference referenSymptom = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://homecare-90544.firebaseio.com");

        referenSymptom.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child("Symptoms").child(dataSymptom.getText ().toString ()).child ("Range").setValue(rangeSymptom);

        startActivity (new Intent(AddSymptomActivity.this,PlannerActivity.class)); //กดบันทึกเเล้วกลับไปหน้าก่อนหน้า

    }
    private void Createwidget() {


        btnMorning = (Button) findViewById (R.id.btnMorning);
        btnAfternoon = (Button) findViewById (R.id.btnAfternoon);
        btnEvening = (Button) findViewById (R.id.btnEvening);
        btnBeforeBed = (Button) findViewById (R.id.btnBeforeBed);

        dataSymptom = (EditText) findViewById (R.id.dataSymptom);


        //สลับสีปุ่ม

        btnMorning.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundColor (Color.rgb (254,176,98));
                btnAfternoon.setBackgroundColor (Color.WHITE);
                btnEvening.setBackgroundColor (Color.WHITE);
                btnBeforeBed.setBackgroundColor (Color.WHITE);
                rangeSymptom = "Morning";

            }
        });

        btnAfternoon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundColor (Color.WHITE);
                btnAfternoon.setBackgroundColor (Color.rgb (254,176,98));
                btnEvening.setBackgroundColor (Color.WHITE);
                btnBeforeBed.setBackgroundColor (Color.WHITE);
                rangeSymptom = "Afternoon";

            }
        });

        btnEvening.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundColor (Color.WHITE);
                btnAfternoon.setBackgroundColor (Color.WHITE);
                btnEvening.setBackgroundColor (Color.rgb (254,176,98));
                btnBeforeBed.setBackgroundColor (Color.WHITE);
                rangeSymptom = "Evening";

            }
        });

        btnBeforeBed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundColor (Color.WHITE);
                btnAfternoon.setBackgroundColor (Color.WHITE);
                btnEvening.setBackgroundColor (Color.WHITE);
                btnBeforeBed.setBackgroundColor (Color.rgb (254,176,98));
                rangeSymptom = "BeforeBed";

            }
        });

    }

}
