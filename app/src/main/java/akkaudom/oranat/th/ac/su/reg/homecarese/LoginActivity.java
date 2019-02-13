package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Map;

import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.ProfileAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.PatientDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.ProfileDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;

public class LoginActivity extends AppCompatActivity {
    private CallbackManager callbackManager;

    EditText edtUser,edtPass;
    String userStr,passStr;
    Button btnLogin;
    LoginButton btnFBLogin;
    TextView nextReg;

    ImageButton fb;

    DatabaseReference referencePatient;
    String userF,nameF,genderF,birthdayF,emailF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        getSupportActionBar().hide();
        //not nev bar

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView (R.layout.activity_login);

        edtUser = (EditText) findViewById (R.id.edtUser);
        edtPass = (EditText) findViewById (R.id.edtPass);

        btnLogin = (Button) findViewById (R.id.btnLogin);


        btnLogin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                checkCondition();
            }
        });

        btnFBLogin = (LoginButton) findViewById (R.id.btnFBLogin);

        fb = (ImageButton)findViewById (R.id.fbLogin) ;

        btnFBLogin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                loginWithFB();
            }
        });

        fb.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                btnFBLogin.performClick ();
            }
        });

        callbackManager = CallbackManager.Factory.create();
        btnFBLogin.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email", "user_birthday"));

        nextReg = (TextView) findViewById (R.id.nextReg);
        nextReg .setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (LoginActivity.this,RegisterActivity.class));
            }
        });

        getSupportActionBar().setTitle("Login");
        //กดกลับ ตั้งชื่อหน้านั้น


    }

    private void loginWithFB(){
        btnFBLogin.setReadPermissions(Arrays.asList("public_profile","user_birthday","email"));
        btnFBLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult> () {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("onSuccess");
                GraphRequest mGraphRequest = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error
                                } else {
                                    userF = me.optString("id");
                                    nameF = me.optString("name");
                                    genderF = me.optString("gender");
                                    birthdayF = me.optString("birthday");
                                    emailF = me.optString ("email");

                                    fb.setImageResource (R.drawable.logoutfb);
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,gender,birthday,email");
                mGraphRequest.setParameters(parameters);
                mGraphRequest.executeAsync();


                String url = "https://homecare-90544.firebaseio.com/users.json";
                StringRequest requestFace = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        DatabaseReference reference = FirebaseDatabase.getInstance()
                                .getReferenceFromUrl("https://homecare-90544.firebaseio.com/users");
                        if (s.equals("null")) {
                            reference.child(userF).child ("profile").child("email").setValue(emailF);
                        } else {
                            try {
                                JSONObject obj = new JSONObject(s);

                                if (!obj.has(userF)) {
                                    reference.child(userF).child ("profile").child("email").setValue(emailF);
                                }
                                UserDetail.userName = userF;
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("" + volleyError);

                    }
                });

                RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
                rQueue.add(requestFace);

            }

            @Override
            public void onCancel() {
                System.out.println("onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println("onError");
                Log.v("LoginActivity", exception.getCause().toString());
            }
        });

    }



    private void checkCondition() {

        boolean status = true;
        userStr = edtUser.getText().toString();
        passStr = edtPass.getText().toString();

        if(userStr.equals("")){
            edtUser.setError("can't be blank");
            status = false;
        }

        if(passStr.equals("")){
            edtPass.setError("can't be blank");
            status = false;
        }

        if (status){
            final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Loading...");
            pd.show();

            String url = "https://homecare-90544.firebaseio.com//users.json";

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                @Override
                public void onResponse(String s) {


                    try {
                        JSONObject obj = new JSONObject(s);

                        if (obj.has(userStr)) {

                            if (obj.getJSONObject (userStr).getJSONObject ("profile").getString ("password").equals (passStr)) {
                                UserDetail.userName = userStr;
                                getPatients(userStr);
                                startActivity (new Intent (LoginActivity.this, HomeActivity.class));
                            } else {

                                edtPass.setError ("Password not correct");

                            }

                        }else {

                            edtUser.setError("not found user");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    pd.dismiss();
                }

            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    System.out.println("" + volleyError );
                    pd.dismiss();
                }
            });

            RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
            rQueue.add(request);
            }

    }

    public void getPatients(String user){

        referencePatient = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://homecare-90544.firebaseio.com/users/"+user+"/patients/");


        referencePatient.addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()) {
                    Map<String,String> map = (Map) ds.child ("ProfilePatient").getValue ();
                    PatientDetail newPatient = new PatientDetail (
                            ds.getKey (),
                            map.get ("Name"),
                            map.get ("ImageUrl")
                    );
                  UserDetail.patient.add (newPatient);
                }
                Log.d ("Patient", String.valueOf (UserDetail.patient));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}