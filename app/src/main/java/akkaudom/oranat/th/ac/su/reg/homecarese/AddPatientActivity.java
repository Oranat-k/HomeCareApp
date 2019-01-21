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
import java.util.Calendar;
import java.util.Date;

import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddPatientActivity extends AppCompatActivity {

    CircleImageView ProPatient;
    Button btnMale,btnFemale;
    EditText namePatient,staPatient,fullname,birthday,address,hospital,disease,allergic,numberHn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_patient);

        ProPatient = (CircleImageView) findViewById (R.id.proPatient);

        btnMale = (Button)  findViewById (R.id.btnMale);
        btnFemale = (Button)  findViewById (R.id.btnFemale);

        namePatient = (EditText) findViewById (R.id.namePatient);
        staPatient = (EditText) findViewById (R.id.staPatient);
        fullname = (EditText) findViewById (R.id.fullname);
        birthday = (EditText) findViewById (R.id.birthday);
        address = (EditText) findViewById (R.id.address);
        hospital = (EditText) findViewById (R.id.hospital);
        disease = (EditText) findViewById (R.id.disease);
        allergic = (EditText) findViewById (R.id.allergic);
        numberHn = (EditText) findViewById (R.id.numberHn);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("ProfilePatient");
        //กดกลับ ตั้งชื่อหน้านั้น
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(AddPatientActivity.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    } //กดกลับ ตั้งชื่อหน้านั้น

    public void dataPatient(View view) {


        DatabaseReference referenSymptom = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://homecare-90544.firebaseio.com");

        referenSymptom.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child ("ProfilePatient")
                .child("ชื่อเล่น").setValue(namePatient.getText ().toString ());

        referenSymptom.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child ("ProfilePatient")
                .child("สถานะ").setValue(staPatient.getText ().toString ());

        referenSymptom.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child ("ProfilePatient")
                .child("ชื่อจริง").setValue(fullname.getText ().toString ());

        referenSymptom.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child ("ProfilePatient")
                .child("เกิดเมื่อ").setValue(birthday.getText ().toString ());

        referenSymptom.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child ("ProfilePatient")
                .child("ที่อยู่").setValue(address.getText ().toString ());

        referenSymptom.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child ("ProfilePatient")
                .child("โรงพยาบาลประจำตัว").setValue(hospital.getText ().toString ());

        referenSymptom.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child ("ProfilePatient")
                .child("โรคประจำตัว").setValue(disease.getText ().toString ());

        referenSymptom.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child ("ProfilePatient")
                .child("ประวัติเเพ้ยา").setValue(allergic.getText ().toString ());

        referenSymptom.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child ("ProfilePatient")
                .child("HN").setValue(numberHn.getText ().toString ());

        startActivity (new Intent(AddPatientActivity.this,ProfileActivity.class)); //กดบันทึกเเล้วกลับไปหน้าก่อนหน้า

    }


}
