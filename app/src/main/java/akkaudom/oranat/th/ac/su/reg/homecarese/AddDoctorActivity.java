package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AddDoctorActivity extends AppCompatActivity {

    EditText hospitalName, nameDoc;
    Button dateDoc, timeDoc;
    DatePicker datePicker;

    final Calendar myCalendar = Calendar.getInstance ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_doctor);

        hospitalName = (EditText) findViewById (R.id.hospitalName);
        nameDoc = (EditText) findViewById (R.id.nameDoc);

        dateDoc = (Button) findViewById (R.id.dateDoc);
        timeDoc = (Button) findViewById (R.id.timeDoc);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Doctor");
        //กดกลับ ตั้งชื่อหน้านั้น

    }



    public void setDate(View view) {

       new DatePickerDialog (AddDoctorActivity.this,date,
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

    };//date



    public void setTime(View view) {

        new TimePickerDialog (AddDoctorActivity.this,time,
                myCalendar.get (Calendar.HOUR_OF_DAY),myCalendar.get (Calendar.MINUTE),true).show ();
    }

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener () {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            myCalendar.set (Calendar.HOUR_OF_DAY,hourOfDay);
            myCalendar.set (Calendar.MINUTE,minute);
            showDateTime ();
        }
    };//time





    private void showDateTime() {

        dateDoc.setText (myCalendar.get (Calendar.DAY_OF_MONTH)+"/"+myCalendar.get (Calendar.MONTH)+"/"+myCalendar.get (Calendar.YEAR));
        timeDoc.setText (myCalendar.get (Calendar.HOUR_OF_DAY)+":"+myCalendar.get (Calendar.MINUTE));


    }


    public void dataDoctor(View view) {


        DatabaseReference referenPressure = FirebaseDatabase.getInstance ()
                .getReferenceFromUrl ("https://homecare-90544.firebaseio.com");
        referenPressure.child ("users").child (UserDetail.userName).child ("patients")
                .child (UserDetail.patient[UserDetail.selectPatient])
                .child ("Doctor").child ("Hospital").setValue (hospitalName.getText ().toString ());

        referenPressure.child ("users").child (UserDetail.userName).child ("patients").child (UserDetail.patient[UserDetail.selectPatient])
                .child ("Doctor").child ("DoctorName").setValue (nameDoc.getText ().toString ());

        referenPressure.child ("users").child (UserDetail.userName).child ("patients").child (UserDetail.patient[UserDetail.selectPatient])
                .child ("Doctor").child ("Date").setValue (dateDoc.getText ().toString ());


        startActivity (new Intent (AddDoctorActivity.this,PlannerActivity.class));
    }
}// input data firebase