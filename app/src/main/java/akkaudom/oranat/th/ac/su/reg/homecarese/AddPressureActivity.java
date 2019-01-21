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
import java.util.Calendar;
import java.util.Date;

import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;

public class AddPressureActivity extends AppCompatActivity {

    Button btnMorning,btnAfternoon,btnEvening,btnBeforeBed;
    EditText topPressure,belowPressure;
    Button datePress, timePress;

    String rangePressure;

    final Calendar myCalendar = Calendar.getInstance ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_pressure);

        topPressure = (EditText) findViewById (R.id.topPressure);
        belowPressure = (EditText) findViewById (R.id.belowPressure);

        datePress = (Button) findViewById (R.id.datePress);
        timePress = (Button) findViewById (R.id.timePress);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Pressure");
        //กดกลับ ตั้งชื่อหน้านั้น


        //Createwidget();

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

    public void setDate(View view) {

        new DatePickerDialog (AddPressureActivity.this,date,
                myCalendar.get (Calendar.YEAR),myCalendar.get (Calendar.MONTH),
                myCalendar.get (Calendar.DAY_OF_MONTH)).show ();

    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            showDateTime ();
        }

    };//Calendar date



    public void setTime(View view) {

        new TimePickerDialog (AddPressureActivity.this,time,
                myCalendar.get (Calendar.HOUR_OF_DAY),myCalendar.get (Calendar.MINUTE),true).show ();
    }

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener () {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            myCalendar.set (Calendar.HOUR_OF_DAY,hourOfDay);
            myCalendar.set (Calendar.MINUTE,minute);
            showDateTime ();
        }
    };//Calendar time





    private void showDateTime() {

        datePress.setText (myCalendar.get (Calendar.DAY_OF_MONTH)+"/"+myCalendar.get (Calendar.MONTH)+"/"+myCalendar.get (Calendar.YEAR));
        timePress.setText (myCalendar.get (Calendar.HOUR_OF_DAY)+":"+myCalendar.get (Calendar.MINUTE));


    }//show


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

        referenPressure.child ("users").child (UserDetail.userName).child ("patients").child (UserDetail.patient[UserDetail.selectPatient])
                .child ("Pressures").child(datetime)
                .child ("Date").setValue (datePress.getText ().toString ());

        referenPressure.child ("users").child (UserDetail.userName).child ("patients").child (UserDetail.patient[UserDetail.selectPatient])
                .child ("Pressures").child(datetime)
                .child ("Time").setValue (timePress.getText ().toString ());


//        referenPressure.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
//                .child("Pressures").child(datetime).child (time)
//                .child ("Range").setValue(rangePressure);



        startActivity (new Intent(AddPressureActivity.this,PlannerActivity.class)); //กดบันทึกเเล้วกลับไปหน้าก่อนหน้า

    }//input ข้อมูล จากปุ่ม onclick


}
