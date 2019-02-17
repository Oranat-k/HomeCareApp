package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.MedicineDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;

public class HistoryMedicineActivity extends AppCompatActivity {

    ArrayList <MedicineDetail> medArrl = new ArrayList<MedicineDetail> ();
    ListView lstHisMed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        getSupportActionBar().setTitle("ประวัติยา");


        setContentView (R.layout.activity_history_medicine);

        lstHisMed = (ListView) findViewById (R.id.lstHisMed) ;

        String url = "https://homecare-90544.firebaseio.com/users/"+UserDetail.userName+"/patients/"
                +UserDetail.patient.get (UserDetail.selectPatient).getId ()+"/Medicines.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj = new JSONObject(s);

                    Iterator i = obj.keys();
                    String key = "";
                    while(i.hasNext()){
                        key = i.next().toString();
                        MedicineDetail newMed = new MedicineDetail ();
                        newMed.setNameMed (key);
                        newMed.setTimeMed (obj.getJSONObject (key).getString ("Time"));
                        newMed.setRange (obj.getJSONObject (key).getJSONObject ("Range"));
                        newMed.setAmount (obj.getJSONObject (key).getString ("Amount"));
                        newMed.setImageUrl (obj.getJSONObject (key).getString ("ImageUrl"));
                        newMed.setStatus (obj.getJSONObject (key).getString ("Status"));

                        medArrl.add (newMed);

                    }

                    MedicineAdapter medicineAdapter = new MedicineAdapter (medArrl,HistoryMedicineActivity.this);
                    lstHisMed.setAdapter(medicineAdapter);

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

        RequestQueue rQueue = Volley.newRequestQueue(HistoryMedicineActivity.this);
        rQueue.add(request);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    } //กดกลับ ตั้งชื่อหน้านั้น


    public void onClickaddMedicine(View view){
        startActivity (new Intent (HistoryMedicineActivity.this,AddPlannerActivity.class));

    }


}
