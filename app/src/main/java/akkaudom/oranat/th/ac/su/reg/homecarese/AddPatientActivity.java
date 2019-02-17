package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddPatientActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private CircleImageView ProPatient;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    Bitmap imageSelect;

    Button btnMale, btnFemale, birthday;
    EditText namePatient, fullname, address, hospital, allergic, numberHn;

    Spinner staPatient, disease;
    String gender;

    private static final String TAG = "AddPatientActivity";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    Button mOrder;
    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_patient);

        ProPatient = (CircleImageView) findViewById (R.id.proPatient);

        btnMale = (Button) findViewById (R.id.btnMale);
        btnFemale = (Button) findViewById (R.id.btnFemale);


        namePatient = (EditText) findViewById (R.id.namePatient);
        staPatient = (Spinner) findViewById (R.id.staPatient);
        fullname = (EditText) findViewById (R.id.fullname);
        address = (EditText) findViewById (R.id.address);
        hospital = (EditText) findViewById (R.id.hospital);

//        disease = (Spinner) findViewById (R.id.disease);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource (this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        staPatient.setAdapter (adapter);
        staPatient.setOnItemSelectedListener (this);
        //dropdown status

//        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource (this,
//                R.array.numbers1, android.R.layout.simple_spinner_item);
//        adapter1.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
//        disease.setAdapter (adapter1);
//        disease.setOnItemSelectedListener (this);

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

        Createwidget ();
        gender = "";


        birthday = (Button) findViewById (R.id.birthday);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddPatientActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                birthday.setText(date);
            }
        };//Date Picker



        mOrder = (Button) findViewById(R.id.btnOrder);
        mItemSelected = (TextView) findViewById(R.id.tvItemSelected);

        listItems = getResources().getStringArray(R.array.shopping_item);
        checkedItems = new boolean[listItems.length];

        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddPatientActivity.this);
                mBuilder.setTitle(getString(R.string.dialog_title));

                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                        if(isChecked){
                            mUserItems.add(position);
                        }else{
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);

                mBuilder.setPositiveButton(getString(R.string.ok_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        mItemSelected.setText(item);
                    }
                });

                mBuilder.setNegativeButton(getString(R.string.dismiss_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(getString(R.string.clear_all_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            mItemSelected.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
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

        String idPatient = UUID.randomUUID ().toString ();

        DatabaseReference referenSymptom = FirebaseDatabase.getInstance ()
                .getReferenceFromUrl ("https://homecare-90544.firebaseio.com/users/" + UserDetail.userName
                        + "/patients/" + idPatient + "/ProfilePatient/");

        Map<String, String> map = new HashMap<String, String> ();
        map.put ("ImageUrl", imagesRef.getPath ());
        map.put ("Nickname", namePatient.getText ().toString ());
        map.put ("Status", staPatient.getSelectedItem ().toString ());
        map.put ("Name", fullname.getText ().toString ());
        map.put ("Birthday", birthday.getText ().toString ());
        map.put ("Address", address.getText ().toString ());
        map.put ("Gender", gender);
        map.put ("Hospital", hospital.getText ().toString ());

        map.put ("Disease", mItemSelected.getText ().toString ());
        map.put ("Allergic", allergic.getText ().toString ());
        map.put ("HN", numberHn.getText ().toString ());


        referenSymptom.setValue (map);


        startActivity (new Intent (AddPatientActivity.this, ProfileActivity.class)); //กดบันทึกเเล้วกลับไปหน้าก่อนหน้า


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition (position).toString ();
        Toast.makeText (parent.getContext (), text, Toast.LENGTH_SHORT).show ();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void Createwidget() {


        btnMale = (Button) findViewById (R.id.btnMale);
        btnFemale = (Button) findViewById (R.id.btnFemale);


        //สลับสีปุ่ม
        btnMale.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                btnMale.setBackgroundResource (R.drawable.border_box_active);
                btnFemale.setBackgroundResource (R.drawable.border_box);
                gender = "ชาย";
            }
        });

        btnFemale.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                btnFemale.setBackgroundResource (R.drawable.border_box_active);
                btnMale.setBackgroundResource (R.drawable.border_box);
                gender = "หญิง";

            }
        });

    }
}
