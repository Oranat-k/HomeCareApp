package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;

public class AddPressureActivity extends AppCompatActivity {

    Button btnMorning,btnAfternoon,btnEvening,btnBeforeBed;
    EditText topPressure,belowPressure;
    Button datePress, timePress;

    String rangePressure;

    ArrayList<Boolean> checkRangePress = new ArrayList<> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_pressure);

        topPressure = (EditText) findViewById (R.id.topPressure);
        belowPressure = (EditText) findViewById (R.id.belowPressure);


        btnMorning = (Button) findViewById (R.id.btnMorning);
        btnAfternoon = (Button) findViewById (R.id.btnAfternoon);
        btnEvening = (Button) findViewById (R.id.btnEvening);
        btnBeforeBed = (Button) findViewById (R.id.btnBeforeBed);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Pressure");
        //กดกลับ ตั้งชื่อหน้านั้น


        Createwidget();

        checkRangePress.add (false);
        checkRangePress.add (false);
        checkRangePress.add (false);
        checkRangePress.add (false);


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



    public void dataPress(View view) {

        Calendar dateNow = Calendar.getInstance ();
        //String time = CheckTime(dateNow);

        DateFormat formater = new SimpleDateFormat ("dd-MM-yyyy");
        String datetime = formater.format (new Date ());

        DatabaseReference referenPressure = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://homecare-90544.firebaseio.com");
        referenPressure.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child("Pressures").child(datetime)
                .child("Top").setValue(topPressure.getText ().toString ());

        referenPressure.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child("Pressures").child(datetime)
                .child("Below").setValue (belowPressure.getText ().toString ());

        for (int i = 0 ; i < checkRangePress.size ();i++){
            String range = "";
            switch (i){
                case 0: range = "Morning"; break;
                case 1: range = "Afternoon"; break;
                case 2: range = "Evening"; break;
                case 3: range = "Beforbed"; break;
            }
            referenPressure.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                    .child("Pressures").child(datetime).child ("Range")
                    .child (range).setValue (checkRangePress.get (i).toString ());
        }



        startActivity (new Intent(AddPressureActivity.this,PlannerActivity.class)); //กดบันทึกเเล้วกลับไปหน้าก่อนหน้า

    }//input ข้อมูล จากปุ่ม onclick

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
                btnActive(btnMorning,0);

            }
        });

        btnAfternoon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnActive(btnAfternoon,1);

            }
        });

        btnEvening.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnActive(btnEvening,2);
            }
        });

        btnBeforeBed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnActive(btnBeforeBed,3);

            }
        });

    }

    public void btnActive(Button btnSelect,int index){

        if (!checkRangePress.get (index)){
            btnSelect.setBackgroundResource (R.drawable.border_box_active);
            checkRangePress.set (index,true);
        }else{
            btnSelect.setBackgroundResource (R.drawable.border_box);
            checkRangePress.set (index,false);
        }


    }


}

