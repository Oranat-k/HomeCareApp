package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPlannerActivity extends AppCompatActivity {

    Button btnBeforMed,btnAfterMed,btnMorning,btnAfternoon,btnEvening,btnBeforeBed;
    EditText nameMedicine,coutMedicine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_planner);

        // menu bar
        ActionBar actionBar = getSupportActionBar ();
                actionBar.setTitle ("เพิ่มยา");

        Createwidget();

    }

    public void InsertData(View view) {

        Log.d ("test","hhhhhhhh");
        DatabaseReference referenMedicine = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://homecare-90544.firebaseio.com");
        referenMedicine.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child("Medicines").child("Medicine").setValue(nameMedicine.getText ().toString ());

        referenMedicine.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child("Medicines").child("Medicine").setValue(coutMedicine.getText ().toString ());

    }

    private void Createwidget() {

        btnBeforMed = (Button) findViewById (R.id.btnBeforMed);
        btnAfterMed = (Button) findViewById (R.id.btnAfterMed);
        btnMorning = (Button) findViewById (R.id.btnMorning);
        btnAfternoon = (Button) findViewById (R.id.btnAfternoon);
        btnEvening = (Button) findViewById (R.id.btnEvening);
        btnBeforeBed = (Button) findViewById (R.id.btnBeforeBed);

        nameMedicine = (EditText) findViewById (R.id.nameMedicine);
        coutMedicine = (EditText) findViewById (R.id.coutMedicine);

        //สลับสีปุ่ม
        btnBeforMed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnBeforMed.setBackgroundColor (Color.rgb (254,176,98));
                btnAfterMed.setBackgroundColor (Color.WHITE);
            }
        });

        btnAfterMed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnAfterMed.setBackgroundColor (Color.rgb (254,176,98));
                btnBeforMed.setBackgroundColor (Color.WHITE);

            }
        });

        btnMorning.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundColor (Color.rgb (254,176,98));
                btnAfternoon.setBackgroundColor (Color.WHITE);
                btnEvening.setBackgroundColor (Color.WHITE);
                btnBeforeBed.setBackgroundColor (Color.WHITE);

            }
        });

        btnAfternoon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundColor (Color.WHITE);
                btnAfternoon.setBackgroundColor (Color.rgb (254,176,98));
                btnEvening.setBackgroundColor (Color.WHITE);
                btnBeforeBed.setBackgroundColor (Color.WHITE);

            }
        });

        btnEvening.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundColor (Color.WHITE);
                btnAfternoon.setBackgroundColor (Color.WHITE);
                btnEvening.setBackgroundColor (Color.rgb (254,176,98));
                btnBeforeBed.setBackgroundColor (Color.WHITE);

            }
        });

        btnBeforeBed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundColor (Color.WHITE);
                btnAfternoon.setBackgroundColor (Color.WHITE);
                btnEvening.setBackgroundColor (Color.WHITE);
                btnBeforeBed.setBackgroundColor (Color.rgb (254,176,98));

            }
        });

    }
}
