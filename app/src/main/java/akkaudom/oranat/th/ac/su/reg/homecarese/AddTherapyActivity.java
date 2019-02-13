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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;

public class AddTherapyActivity extends AppCompatActivity {

    Button btnMorning,btnAfternoon,btnEvening,btnBeforeBed;
    EditText dataTherapy;

    String rangeTherapy;

    ArrayList<Boolean> checkRangeTherapy = new ArrayList<> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_therapy);

        dataTherapy = (EditText) findViewById (R.id.dataTherapy);

        btnMorning = (Button) findViewById (R.id.btnMorning);
        btnAfternoon = (Button) findViewById (R.id.btnAfternoon);
        btnEvening = (Button) findViewById (R.id.btnEvening);
        btnBeforeBed = (Button) findViewById (R.id.btnBeforeBed);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        getSupportActionBar().setTitle("Therapy");
        //กดกลับ ตั้งชื่อหน้านั้น


        Createwidget();

        checkRangeTherapy.add (false);
        checkRangeTherapy.add (false);
        checkRangeTherapy.add (false);
        checkRangeTherapy.add (false);

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


    public void InsertData(View view) {

        Calendar dateNow = Calendar.getInstance ();
        //String time = CheckTime(dateNow);

        DateFormat formater = new SimpleDateFormat ("dd-MM-yyyy");
        String datetime = formater.format (new Date ());

        DatabaseReference referenTherapy = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://homecare-90544.firebaseio.com");

        for (int i = 0; i < checkRangeTherapy.size (); i++) {
            String range = "";
            switch (i) {
                case 0:
                    range = "Morning";
                    break;
                case 1:
                    range = "Afternoon";
                    break;
                case 2:
                    range = "Evening";
                    break;
                case 3:
                    range = "Beforbed";
                    break;
            }
            if (checkRangeTherapy.get (i)) {


                referenTherapy.child ("users").child (UserDetail.userName).child ("patients")
                        .child (UserDetail.patient.get (UserDetail.selectPatient).getId ())
                        .child ("Therapys").child (datetime).child (range)
                        .child ("Value").setValue (dataTherapy.getText ().toString ());

            }
        }


        startActivity (new Intent(AddTherapyActivity.this,PlannerListActivity.class));

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

        if (!checkRangeTherapy.get (index)){
            btnSelect.setBackgroundResource (R.drawable.border_box_active);
            checkRangeTherapy.set (index,true);
        }else{
            btnSelect.setBackgroundResource (R.drawable.border_box);
            checkRangeTherapy.set (index,false);
        }


    }

}
