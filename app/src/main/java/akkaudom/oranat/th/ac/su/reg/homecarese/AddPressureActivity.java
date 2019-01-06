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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddPressureActivity extends AppCompatActivity {

    Button btnMorning,btnAfternoon,btnEvening,btnBeforeBed;
    EditText topPressure,belowPressure;

    String rangePressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_pressure);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Pressure");
        //กดกลับ ตั้งชื่อหน้านั้น


        Createwidget();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(AddPressureActivity.this, PlannerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    } //กดกลับ ตั้งชื่อหน้านั้น


    public void InsertData(View view) {

        Calendar dateNow = Calendar.getInstance ();
        String time = CheckTime(dateNow);
        DateFormat formater = new SimpleDateFormat ("dd-MM-yyyy");
        String datetime = formater.format (new Date ());


        DatabaseReference referenPressure = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://homecare-90544.firebaseio.com");
        referenPressure.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child("Pressures").child(datetime).child (time)
                .child("Top").setValue(topPressure.getText ().toString ());

        referenPressure.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child("Pressures").child(datetime).child (time)
                .child("Below").setValue (belowPressure.getText ().toString ());

//        referenPressure.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
//                .child("Pressures").child(datetime).child (time)
//                .child ("Range").setValue(rangePressure);



        startActivity (new Intent(AddPressureActivity.this,PlannerActivity.class)); //กดบันทึกเเล้วกลับไปหน้าก่อนหน้า

    }//input ข้อมูล จากปุ่ม onclick

    private String CheckTime(Calendar dateNow) {
       if(dateNow.get (Calendar.HOUR_OF_DAY) >= 1&& dateNow.get (Calendar.HOUR_OF_DAY) <= 10){
           return "Morning";
       }else if (dateNow.get (Calendar.HOUR_OF_DAY) >= 11&& dateNow.get (Calendar.HOUR_OF_DAY) <= 15){
           return "Afternoon";
       }else if (dateNow.get (Calendar.HOUR_OF_DAY) >= 16&& dateNow.get (Calendar.HOUR_OF_DAY) <= 18){
           return "Evening";
       }else {
           return "Before Bed";
       }

    }

    private void Createwidget() {


        btnMorning = (Button) findViewById (R.id.btnMorning);
        btnAfternoon = (Button) findViewById (R.id.btnAfternoon);
        btnEvening = (Button) findViewById (R.id.btnEvening);
        btnBeforeBed = (Button) findViewById (R.id.btnBeforeBed);

        topPressure = (EditText) findViewById (R.id.topPressure);
        belowPressure = (EditText) findViewById (R.id.belowPressure);


        //สลับสีปุ่ม

        btnMorning.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundResource (R.drawable.border_box_active);
                btnAfternoon.setBackgroundResource (R.drawable.border_box);
                btnEvening.setBackgroundResource (R.drawable.border_box);
                btnBeforeBed.setBackgroundResource (R.drawable.border_box);
                rangePressure = "Morning";

            }
        });

        btnAfternoon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundResource (R.drawable.border_box);
                btnAfternoon.setBackgroundResource (R.drawable.border_box_active);
                btnEvening.setBackgroundResource (R.drawable.border_box);
                btnBeforeBed.setBackgroundResource (R.drawable.border_box);
                rangePressure = "Afternoon";

            }
        });

        btnEvening.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundResource (R.drawable.border_box);
                btnAfternoon.setBackgroundResource (R.drawable.border_box);
                btnEvening.setBackgroundResource (R.drawable.border_box_active);
                btnBeforeBed.setBackgroundResource (R.drawable.border_box);
                rangePressure = "Evening";

            }
        });

        btnBeforeBed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundResource (R.drawable.border_box);
                btnAfternoon.setBackgroundResource (R.drawable.border_box);
                btnEvening.setBackgroundResource (R.drawable.border_box);
                btnBeforeBed.setBackgroundResource (R.drawable.border_box_active);
                rangePressure = "BeforeBed";

            }
        });

    }
}
