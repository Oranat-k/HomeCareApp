package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
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

public class AddSymptomActivity extends AppCompatActivity {

    Activity mcontext = AddSymptomActivity.this;

    Button btnMorning,btnAfternoon,btnEvening,btnBeforeBed;
    EditText dataSymptom;
    Button dateSym;

    String rangeSymptom;

    ArrayList<Boolean> checkRangeSymptom = new ArrayList<> ();

    final Calendar myCalendar = Calendar.getInstance ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_symptom);

        dateSym = (Button) findViewById (R.id.dateSym);

        dataSymptom = (EditText) findViewById (R.id.dataSymptom);

        btnMorning = (Button) findViewById (R.id.btnMorning);
        btnAfternoon = (Button) findViewById (R.id.btnAfternoon);
        btnEvening = (Button) findViewById (R.id.btnEvening);
        btnBeforeBed = (Button) findViewById (R.id.btnBeforeBed);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        getSupportActionBar().setTitle("Symptom");
        //กดกลับ ตั้งชื่อหน้านั้น

        showDateTime ();

        Createwidget();

        checkRangeSymptom.add (false);
        checkRangeSymptom.add (false);
        checkRangeSymptom.add (false);
        checkRangeSymptom.add (false);

    }


    public void setDate(View view) {

        new DatePickerDialog (mcontext,date,
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

    private void showDateTime() {

        dateSym.setText (myCalendar.get (Calendar.DAY_OF_MONTH)+" / "+(myCalendar.get (Calendar.MONTH)+1)+" / "+myCalendar.get (Calendar.YEAR));


    }//show

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    } //กดกลับ ตั้งชื่อหน้านั้น




    public void dataSym(View view) {

        DateFormat formater = new SimpleDateFormat ("dd-MM-yyyy");
        String datetime = formater.format (myCalendar.getTime ());


        DatabaseReference referenSymptom = FirebaseDatabase.getInstance ()
                .getReferenceFromUrl ("https://homecare-90544.firebaseio.com");

        for (int i = 0; i < checkRangeSymptom.size (); i++) {
            String range = "";
            switch (i) {
                case 0:
                    range = "morning";
                    break;
                case 1:
                    range = "afternoon";
                    break;
                case 2:
                    range = "evening";
                    break;
                case 3:
                    range = "beforbed";
                    break;
            }
            if (checkRangeSymptom.get (i)) {


                referenSymptom.child ("users").child (UserDetail.userName).child ("patients")
                        .child (UserDetail.patient.get (UserDetail.selectPatient).getId ())
                        .child ("Symptoms").child (datetime).child (range)
                        .child ("Value").setValue (dataSymptom.getText ().toString ());

            }

        }

            startActivity (new Intent (AddSymptomActivity.this, PlannerListActivity.class)); //กดบันทึกเเล้วกลับไปหน้าก่อนหน้า


    }

        private void Createwidget() {


            btnMorning = (Button) findViewById (R.id.btnMorning);
            btnAfternoon = (Button) findViewById (R.id.btnAfternoon);
            btnEvening = (Button) findViewById (R.id.btnEvening);
            btnBeforeBed = (Button) findViewById (R.id.btnBeforeBed);


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

            if (!checkRangeSymptom.get (index)){
                btnSelect.setBackgroundResource (R.drawable.border_box_active);
                checkRangeSymptom.set (index,true);
            }else{
                btnSelect.setBackgroundResource (R.drawable.border_box);
                checkRangeSymptom.set (index,false);
            }


        }


    }