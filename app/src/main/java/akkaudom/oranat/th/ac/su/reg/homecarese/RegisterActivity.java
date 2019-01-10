package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    EditText emailid,usernameid,passwordid;
    String usernameStr,emailStr,passwordStr;
    Button btnRegis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_register);


        usernameid = (EditText) findViewById (R.id.usernameid);
        emailid = (EditText) findViewById (R.id.emailid);
        passwordid = (EditText) findViewById (R.id.passwordid);

        btnRegis = (Button) findViewById (R.id.btnRegis);

        btnRegis.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                checkCondition();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Register");
        //กดกลับ ตั้งชื่อหน้านั้น
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    } //กดกลับ ตั้งชื่อหน้านั้น

    public final static boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void checkCondition() {
        boolean status = true;
        usernameStr = usernameid.getText ().toString ();
        emailStr = emailid.getText ().toString ();
        passwordStr = passwordid.getText ().toString ();

        if(usernameStr.equals("")){
            usernameid.setError("can't be blank");
            status = false;
        }else if(!usernameStr.matches("[A-Za-z0-9]+")){
            usernameid.setError("only alphabet or number allowed");
            status = false;
        }


        if(emailStr.equals("")){
            emailid.setError("can't be blank");
            status = false;
        }else if (!isValidEmail(emailStr)){
            emailid.setError("only email pattern");
            status = false;
        }

        if(passwordStr.equals("")){
            passwordid.setError("can't be blank");
            status = false;

        }  else if(passwordStr.length()<5){
            passwordid.setError("at least 5 characters long");
            status = false;
        }

        if (status){
            final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
            pd.setMessage("Loading...");
            pd.show();

            String url = "https://homecare-90544.firebaseio.com/users.json";

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){

                @Override
                public void onResponse(String s) {

                    DatabaseReference reference = FirebaseDatabase.getInstance()
                            .getReferenceFromUrl("https://homecare-90544.firebaseio.com/users");

                    if(s.equals("null")) {
                        reference.child(usernameStr).child ("profile").child("password").setValue(passwordStr);
                        reference.child(usernameStr).child ("profile").child("email").setValue(emailStr);

                        startActivity(new Intent (RegisterActivity.this, ProfileActivity.class));

                    }
                    else {
                        try {
                            JSONObject obj = new JSONObject(s);

                            if (!obj.has(usernameStr)) {
                                reference.child(usernameStr).child ("profile").child("password").setValue(passwordStr);
                                reference.child(usernameStr).child ("profile").child("email").setValue(emailStr);

                                startActivity(new Intent (RegisterActivity.this, ProfileActivity.class));
                            } else {
                                usernameid.setError("username already exists");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

            RequestQueue rQueue = Volley.newRequestQueue(RegisterActivity.this);
            rQueue.add(request);
        }
    }
}
