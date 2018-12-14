package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddPlannerActivity extends AppCompatActivity {

    private CircleImageView ProfileImage;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    Bitmap imageSelect;


    Button btnBeforMed,btnAfterMed,btnMorning,btnAfternoon,btnEvening,btnBeforeBed;
    EditText nameMedicine,coutMedicine;

    String timeMedicine,rangeMedicine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_planner);

        if (ContextCompat.checkSelfPermission(AddPlannerActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(AddPlannerActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(AddPlannerActivity.this, android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddPlannerActivity.this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA},1);
        }//รูป pop up take photo//galley


        ActionBar actionBar = getSupportActionBar ();
        actionBar.setTitle ("เพิ่มยา");
        // menu bar

        Createwidget();

        ProfileImage = (CircleImageView) findViewById (R.id.profile_Medicine);
        ProfileImage.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {


                imgClick();

            }
        });

    }

    public void imgClick(){


        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddPlannerActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,1);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if(resultCode == RESULT_OK && null != data){
            switch(requestCode) {
                case 0:
                    Bundle extras = data.getExtras();
                    imageSelect = (Bitmap) extras.get("data");
                    break;
                case 1:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    imageSelect = BitmapFactory.decodeFile(picturePath);
                    break;

            }

            imageSelect = Bitmap.createScaledBitmap(imageSelect, 100,100,true);
            ProfileImage.setImageBitmap(imageSelect);
        }

    }//เลือกรูป

    public void InsertData(View view) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageSelect.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] dataPic = baos.toByteArray();

        String id = UUID.randomUUID().toString();// ชื่อรูปไม่ซ้ำกัน Random

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images/users/"+UserDetail.patient[UserDetail.selectPatient]+"/"+id+".jpg"); //พาทรูป
        UploadTask uploadTask = imagesRef.putBytes(dataPic);
        uploadTask.addOnFailureListener(new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(AddPlannerActivity.this, "incorrect ", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot> () {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddPlannerActivity.this, "correct ", Toast.LENGTH_LONG).show(); //บันทึกเข้าแล้ว
            }
        });


        DatabaseReference referenMedicine = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://homecare-90544.firebaseio.com");
        referenMedicine.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child("Medicines").child(nameMedicine.getText ().toString ()).child ("Amount").setValue(coutMedicine.getText ().toString ());

        referenMedicine.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child("Medicines").child(nameMedicine.getText ().toString ()).child ("Time").setValue(timeMedicine);

        referenMedicine.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child("Medicines").child(nameMedicine.getText ().toString ()).child ("Range").setValue(rangeMedicine);

        referenMedicine.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child("Medicines").child(nameMedicine.getText ().toString ()).child ("ImageUrl").setValue(imagesRef.getPath ());

        referenMedicine.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                .child("Medicines").child(nameMedicine.getText ().toString ()).child ("Status").setValue("true");

        startActivity (new Intent(AddPlannerActivity.this,HistoryMedicineActivity.class));


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
                timeMedicine = "BeforeFood";
            }
        });

        btnAfterMed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnAfterMed.setBackgroundColor (Color.rgb (254,176,98));
                btnBeforMed.setBackgroundColor (Color.WHITE);
                timeMedicine = "AfterFood";

            }
        });

        btnMorning.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundColor (Color.rgb (254,176,98));
                btnAfternoon.setBackgroundColor (Color.WHITE);
                btnEvening.setBackgroundColor (Color.WHITE);
                btnBeforeBed.setBackgroundColor (Color.WHITE);
                rangeMedicine = "Morning";

            }
        });

        btnAfternoon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundColor (Color.WHITE);
                btnAfternoon.setBackgroundColor (Color.rgb (254,176,98));
                btnEvening.setBackgroundColor (Color.WHITE);
                btnBeforeBed.setBackgroundColor (Color.WHITE);
                rangeMedicine = "Afternoon";

            }
        });

        btnEvening.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundColor (Color.WHITE);
                btnAfternoon.setBackgroundColor (Color.WHITE);
                btnEvening.setBackgroundColor (Color.rgb (254,176,98));
                btnBeforeBed.setBackgroundColor (Color.WHITE);
                rangeMedicine = "Evening";

            }
        });

        btnBeforeBed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnMorning.setBackgroundColor (Color.WHITE);
                btnAfternoon.setBackgroundColor (Color.WHITE);
                btnEvening.setBackgroundColor (Color.WHITE);
                btnBeforeBed.setBackgroundColor (Color.rgb (254,176,98));
                rangeMedicine = "BeforeBed";

            }
        });

    }




}

