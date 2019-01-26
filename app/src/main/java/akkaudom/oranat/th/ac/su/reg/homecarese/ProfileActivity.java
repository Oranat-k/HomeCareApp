package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.MedicineAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.ProfileAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.MedicineDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.ProfileDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;

public class ProfileActivity extends AppCompatActivity {

//    ArrayList <ProfileDetail> patientlst = new ArrayList<> ();
ArrayList <ProfileDetail> medArrl = new ArrayList<ProfileDetail> ();

    ListView lstHisPatient;


    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        getSupportActionBar().setTitle("Profile");
        setContentView (R.layout.activity_profile);



        if (ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(ProfileActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},1);
        }//ดัก

        //getDataToArr();

        lstHisPatient = (ListView) findViewById (R.id.lstHisPatient) ;


        b = findViewById (R.id.call);

        b.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v)
            {
                Intent callIntent=new Intent (Intent.ACTION_CALL);
                callIntent.setData (Uri.parse ("tel:123"));
                startActivity(callIntent);
            }
        }); //call


        String url = "https://homecare-90544.firebaseio.com/users/"+UserDetail.userName+"/patients.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj = new JSONObject(s);

                    Iterator i = obj.keys();
                    String key = "";
                    while(i.hasNext()){
                        key = i.next().toString();
                        ProfileDetail newMed = new ProfileDetail (
                            obj.getJSONObject (key).getJSONObject ("ProfilePatient").getString ("Name"),
                            obj.getJSONObject (key).getJSONObject ("ProfilePatient").getString ("ImageUrl"),
                            obj.getJSONObject (key).getJSONObject ("ProfilePatient").getString ("Status"));

                        medArrl.add (newMed);


                    }

                    ProfileAdapter medicineAdapter = new ProfileAdapter (medArrl,ProfileActivity.this);
                    lstHisPatient.setAdapter(medicineAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(ProfileActivity.this);
        rQueue.add(request);




    }

    public void onClickaddPatient(View view){
        startActivity (new Intent(ProfileActivity.this,AddPatientActivity.class));
    }//back page



}
