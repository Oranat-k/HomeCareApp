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

public class AddSugarActivity extends AppCompatActivity {

    Button btnBeforSugar,btnAfterSugar,btnMorning,btnAfternoon,btnEvening,btnBeforeBed;
    EditText dataSugar;

    String timeSugar,rangeSugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_sugar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Sugar");
        //กดกลับ ตั้งชื่อหน้านั้น


        Createwidget();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(AddSugarActivity.this, PlannerActivity.class);
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
                .child("Sugars").child(dataSugar.getText ().toString ()).child ("Time").setValue(timeSugar);

        referenSymptom.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child("Sugars").child(dataSugar.getText ().toString ()).child ("Range").setValue(rangeSugar);

        startActivity (new Intent(AddSugarActivity.this,PlannerActivity.class)); //กดบันทึกเเล้วกลับไปหน้าก่อนหน้า

    }

    private void Createwidget() {

        btnBeforSugar = (Button) findViewById (R.id.btnBeforSugar);
        btnAfterSugar = (Button) findViewById (R.id.btnAfterSugar);

        btnMorning = (Button) findViewById (R.id.btnMorning);
        btnAfternoon = (Button) findViewById (R.id.btnAfternoon);
        btnEvening = (Button) findViewById (R.id.btnEvening);
        btnBeforeBed = (Button) findViewById (R.id.btnBeforeBed);

        dataSugar = (EditText) findViewById (R.id.dataSugar);


        //สลับสีปุ่ม
        btnBeforSugar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnBeforSugar.setBackgroundColor (Color.rgb (254,176,98));
                btnAfterSugar.setBackgroundColor (Color.WHITE);
                timeSugar = "BeforeFood";
            }
        });

        btnAfterSugar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnAfterSugar.setBackgroundColor (Color.rgb (254,176,98));
                btnBeforSugar.setBackgroundColor (Color.WHITE);
                timeSugar = "AfterFood";

            }
        });

        btnMorning.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundColor (Color.rgb (254,176,98));
                btnAfternoon.setBackgroundColor (Color.WHITE);
                btnEvening.setBackgroundColor (Color.WHITE);
                btnBeforeBed.setBackgroundColor (Color.WHITE);
                rangeSugar = "Morning";

            }
        });

        btnAfternoon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundColor (Color.WHITE);
                btnAfternoon.setBackgroundColor (Color.rgb (254,176,98));
                btnEvening.setBackgroundColor (Color.WHITE);
                btnBeforeBed.setBackgroundColor (Color.WHITE);
                rangeSugar = "Afternoon";

            }
        });

        btnEvening.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundColor (Color.WHITE);
                btnAfternoon.setBackgroundColor (Color.WHITE);
                btnEvening.setBackgroundColor (Color.rgb (254,176,98));
                btnBeforeBed.setBackgroundColor (Color.WHITE);
                rangeSugar = "Evening";

            }
        });

        btnBeforeBed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundColor (Color.WHITE);
                btnAfternoon.setBackgroundColor (Color.WHITE);
                btnEvening.setBackgroundColor (Color.WHITE);
                btnBeforeBed.setBackgroundColor (Color.rgb (254,176,98));
                rangeSugar = "BeforeBed";

            }
        });

    }

}
