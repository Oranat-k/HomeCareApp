package akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity;

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
import java.util.HashMap;
import java.util.Map;

import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.PlannerActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.R;

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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        getSupportActionBar().setTitle("Doctor");
        //กดกลับ ตั้งชื่อหน้านั้น

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    } //กดกลับ ตั้งชื่อหน้านั้น




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

    };//Calendar date



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
    };//Calendar time





    private void showDateTime() {

        dateDoc.setText (myCalendar.get (Calendar.DAY_OF_MONTH)+"/"+(myCalendar.get (Calendar.MONTH)+1)+"/"+myCalendar.get (Calendar.YEAR));
        timeDoc.setText (myCalendar.get (Calendar.HOUR_OF_DAY)+":"+myCalendar.get (Calendar.MINUTE));


    }//show


    public void dataDoctor(View view) {


        SimpleDateFormat sdf = new SimpleDateFormat ("dd-MM-yyyy hh:mm");
        String datetime = sdf.format (myCalendar.getTimeInMillis ());

        DatabaseReference referenPressure = FirebaseDatabase.getInstance ()
                .getReferenceFromUrl ("https://homecare-90544.firebaseio.com/users/"+UserDetail.userName+"/patients/"
                        +UserDetail.patient.get (UserDetail.selectPatient).getId ()+"/Doctors");

        Map<String, String> map = new HashMap<String, String> ();
        map.put ("Hospital", hospitalName.getText ().toString ());
        map.put ("DoctorName", nameDoc.getText ().toString ());
        map.put ("Date", datetime);


        referenPressure.push ().setValue (map);
//        referenPressure.child ("users").child (UserDetail.userName).child ("patients")
//                .child (UserDetail.patient.get (UserDetail.selectPatient).getId ())
//                .child ("Doctors").child(nameDoc.getText ().toString ()).child(datetime)
//                .child ("Hospital").setValue (hospitalName.getText ().toString ());
//
//        referenPressure.child ("users").child (UserDetail.userName).child ("patients")
//                .child (UserDetail.patient.get (UserDetail.selectPatient).getId ())
//                .child ("Doctors").child(nameDoc.getText ().toString ()).child(datetime)
//                .child ("DoctorName").setValue (nameDoc.getText ().toString ());
//
//        referenPressure.child ("users").child (UserDetail.userName).child ("patients")
//                .child (UserDetail.patient.get (UserDetail.selectPatient).getId ())
//                .child ("Doctors").child(nameDoc.getText ().toString ()).child(datetime)
//                .child ("Date").setValue (dateDoc.getText ().toString ()+" "+timeDoc.getText ().toString ());


//        referenPressure.child ("users").child (UserDetail.userName).child ("patients").child (UserDetail.patient[UserDetail.selectPatient])
//                .child ("Doctors").child ("Date").setValue (dateDoc.getText ().toString ());


        startActivity (new Intent (AddDoctorActivity.this,PlannerActivity.class));
    }





}// input data firebase