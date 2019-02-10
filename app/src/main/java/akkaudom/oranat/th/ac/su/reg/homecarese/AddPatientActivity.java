package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddPatientActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private CircleImageView ProPatient;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    Bitmap imageSelect;

    Button btnMale, btnFemale;
    EditText namePatient, staPatient, fullname, birthday, address, hospital,disease, allergic, numberHn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_patient);

        ProPatient = (CircleImageView) findViewById (R.id.proPatient);

        btnMale = (Button) findViewById (R.id.btnMale);
        btnFemale = (Button) findViewById (R.id.btnFemale);

        namePatient = (EditText) findViewById (R.id.namePatient);
//        staPatient = (EditText) findViewById (R.id.staPatient);
        fullname = (EditText) findViewById (R.id.fullname);
        birthday = (EditText) findViewById (R.id.birthday);
        address = (EditText) findViewById (R.id.address);
        hospital = (EditText) findViewById (R.id.hospital);
        disease = (EditText) findViewById (R.id.disease);

        Spinner spinner = findViewById(R.id.staPatient);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        allergic = (EditText) findViewById (R.id.allergic);
        numberHn = (EditText) findViewById (R.id.numberHn);



        if (ContextCompat.checkSelfPermission (AddPatientActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission (AddPatientActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission (AddPatientActivity.this, android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions (AddPatientActivity.this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA}, 1);
        }//รูป pop up take photo//galley

        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
        getSupportActionBar ().setDisplayShowHomeEnabled (true);
        getSupportActionBar ().setTitle ("ProfilePatient");
        //กดกลับ ตั้งชื่อหน้านั้น

        ProPatient = (CircleImageView) findViewById (R.id.proPatient);
        ProPatient.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {


                imgClick ();

            }
        });

    }

    public void imgClick() {


        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder (AddPatientActivity.this);
        builder.setTitle ("Add Photo!");
        builder.setItems (items, new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals ("Take Photo")) {
                    Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult (intent, 0);
                } else if (items[item].equals ("Choose from Library")) {
                    Intent intent = new Intent (
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult (intent, 1);
                } else if (items[item].equals ("Cancel")) {
                    dialog.dismiss ();
                }
            }
        });
        builder.show ();

    }//click img

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if (resultCode == RESULT_OK && null != data) {
            switch (requestCode) {
                case 0:
                    Bundle extras = data.getExtras ();
                    imageSelect = (Bitmap) extras.get ("data");
                    break;
                case 1:
                    Uri selectedImage = data.getData ();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver ().query (selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst ();

                    int columnIndex = cursor.getColumnIndex (filePathColumn[0]);
                    String picturePath = cursor.getString (columnIndex);
                    cursor.close ();
                    imageSelect = BitmapFactory.decodeFile (picturePath);
                    break;

            }

            imageSelect = Bitmap.createScaledBitmap (imageSelect, 100, 100, true);
            ProPatient.setImageBitmap (imageSelect);
        }

    }//เลือกรูป

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent (AddPatientActivity.this, ProfileActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity (intent);
                finish ();
                return true;

            default:
                return super.onOptionsItemSelected (item);
        }
    } //กดกลับ ตั้งชื่อหน้านั้น


    public void dataPatient(View view) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream ();
        imageSelect.compress (Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] dataPic = baos.toByteArray ();

        String id = UUID.randomUUID ().toString ();// ชื่อรูปไม่ซ้ำกัน Random

        FirebaseStorage storage = FirebaseStorage.getInstance ();
        StorageReference storageRef = storage.getReference ();
        StorageReference imagesRef = storageRef.child ("images/users/" + "proPatient" + "/" + id + ".jpg"); //พาทรูป
        UploadTask uploadTask = imagesRef.putBytes (dataPic);
        uploadTask.addOnFailureListener (new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText (AddPatientActivity.this, "incorrect ", Toast.LENGTH_LONG).show ();
            }
        }).addOnSuccessListener (new OnSuccessListener<UploadTask.TaskSnapshot> () {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText (AddPatientActivity.this, "correct ", Toast.LENGTH_LONG).show (); //บันทึกเข้าแล้ว
            }
        });


        DatabaseReference referenSymptom = FirebaseDatabase.getInstance ()
                .getReferenceFromUrl ("https://homecare-90544.firebaseio.com/users/");

        referenSymptom.child (UserDetail.userName).child ("patients").child ("ProfilePatient").child (id)
                .child ("ImageUrl").setValue (imagesRef.getPath ());

        referenSymptom.child (UserDetail.userName).child ("patients").child ("ProfilePatient").child (id)
                .child ("Nickname").setValue (namePatient.getText ().toString ());

        referenSymptom.child (UserDetail.userName).child ("patients").child ("ProfilePatient").child (id)
                .child ("Status").setValue (staPatient.getText ().toString ());

        referenSymptom.child (UserDetail.userName).child ("patients").child ("ProfilePatient").child (id)
                .child ("Name").setValue (fullname.getText ().toString ());

        referenSymptom.child (UserDetail.userName).child ("patients").child ("ProfilePatient").child (id)
                .child ("Birthday").setValue (birthday.getText ().toString ());

        referenSymptom.child (UserDetail.userName).child ("patients").child ("ProfilePatient").child (id)
                .child ("Address").setValue (address.getText ().toString ());

        referenSymptom.child (UserDetail.userName).child ("patients").child ("ProfilePatient").child (id)
                .child ("Hospital").setValue (hospital.getText ().toString ());

        referenSymptom.child (UserDetail.userName).child ("patients").child ("ProfilePatient").child (id)
                .child ("Disease").setValue (disease.getText ().toString ());

        referenSymptom.child (UserDetail.userName).child ("patients").child ("ProfilePatient").child (id)
                .child ("Allergic").setValue (allergic.getText ().toString ());

        referenSymptom.child (UserDetail.userName).child ("patients").child ("ProfilePatient").child (id)
                .child ("HN").setValue (numberHn.getText ().toString ());


        startActivity (new Intent (AddPatientActivity.this, ProfileActivity.class)); //กดบันทึกเเล้วกลับไปหน้าก่อนหน้า


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
