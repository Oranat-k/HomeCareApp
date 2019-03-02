package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.HomeDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;

public class DetailActivity extends AppCompatActivity {

    ImageView pic;
    TextView topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        getSupportActionBar().hide();
        setContentView (R.layout.activity_detail);

        TextView txtEx = (TextView) findViewById(R.id.idEx);
        txtEx.setText("หัวข้อ " + UserDetail.selectContent);

        pic = (ImageView)  findViewById (R.id.pic);
        topic = (TextView) findViewById (R.id.topic);

        String url = "https://homecare-90544.firebaseio.com/.json"; //หัวใหญ่
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response).getJSONObject("manual");

                    Picasso.get().load(obj.getJSONObject(UserDetail.selectContent).getString("img")).into(pic);

                    topic.setText(obj.getJSONObject(UserDetail.selectContent).getString("topic"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(""+error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(DetailActivity.this);
        requestQueue.add(request);


//        DatabaseReference reference1 = FirebaseDatabase.getInstance()
//                .getReferenceFromUrl("https://homecare-90544.firebaseio.com/");
//
//        Log.d ("mess text", String.valueOf (reference1));
//
//        reference1.child ("manual").addValueEventListener(new ValueEventListener () {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//
//                String img = snapshot.child ("img").getValue ().toString ();
//                Log.d ("mess img", String.valueOf (img));
//
//                Log.d ("snapshot text", String.valueOf (snapshot));
//
//
//                Picasso.get().load(obj.getJSONObject(UserDetail.selectContent).getString("img")).into(ImageBook);
//
//
//
//            }// img profile
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getMessage());
//            }
//        });// img profile on firebase

        }

    public void onClickBack(View v){
        onBackPressed();
    }
}
