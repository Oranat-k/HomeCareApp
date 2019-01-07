package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Calendar;
import java.util.Iterator;

public class PlannerListActivity extends AppCompatActivity {

    ArrayList<PlannerDetail> morningArrPlanner = new ArrayList<> ();
    ArrayList<PlannerDetail> afternoonArrPlanner = new ArrayList<> ();
    ArrayList<PlannerDetail> eveningArrPlanner = new ArrayList<> ();
    ArrayList<PlannerDetail> befoebedArrPlanner = new ArrayList<> ();


    ListView MorningPlanner,AfternoonPlanner,EveningPlanner,BeforBedPlanner ;

    Calendar dateSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_planner_list);
        getDataToArr();
    }

    public  void  checkStatus(JSONObject obj , String during , ArrayList<PlannerDetail> arrPlanner){

        try {
            JSONObject objDuring =  obj.getJSONObject (during);
            for(int i = 0 ; i < arrPlanner.size () ; i++) {
                if (objDuring.has (arrPlanner.get (i).getTitle ())) {
                    arrPlanner.get (i).setStatus (objDuring.getString (arrPlanner.get (i).getTitle ()));

                }
            }
        } catch (JSONException e) {
            e.printStackTrace ();
        }

    }

    private void getDataToArr() {

        dateSelect = Calendar.getInstance ();
        dateSelect.set (Calendar.HOUR_OF_DAY,0);
        dateSelect.set (Calendar.MINUTE,0);
        dateSelect.set (Calendar.SECOND,0);

        final String date = dateSelect.get (Calendar.DAY_OF_MONTH) + "-" +dateSelect.get (Calendar.MONTH)+1+"-" +dateSelect.get (Calendar.YEAR);


        morningArrPlanner.add (new PlannerDetail ("พลิกตัว","false"));
        morningArrPlanner.add (new PlannerDetail ("กายภาพบำบัด","false"));
        morningArrPlanner.add (new PlannerDetail ("ตรวจเท้า","false"));

        PlannerAdapter morningAP = new PlannerAdapter (morningArrPlanner,PlannerListActivity.this ,
                date,"Morning");
        MorningPlanner = findViewById (R.id.MorningPlanner);
        MorningPlanner.setAdapter (morningAP);

        afternoonArrPlanner.add (new PlannerDetail ("พลิกตัว","false"));
        afternoonArrPlanner.add (new PlannerDetail ("กายภาพบำบัด","false"));
        afternoonArrPlanner.add (new PlannerDetail ("ตรวจเท้า","false"));

        PlannerAdapter afterAP = new PlannerAdapter (afternoonArrPlanner,PlannerListActivity.this,date,"Afternoon");
        AfternoonPlanner = findViewById (R.id.AfternoonPlanner);
        AfternoonPlanner.setAdapter (afterAP);

        eveningArrPlanner.add (new PlannerDetail ("พลิกตัว","false"));
        eveningArrPlanner.add (new PlannerDetail ("กายภาพบำบัด","false"));
        eveningArrPlanner.add (new PlannerDetail ("ตรวจเท้า","false"));

        PlannerAdapter eveningAP = new PlannerAdapter (eveningArrPlanner,PlannerListActivity.this,date,"Evening");
        EveningPlanner = findViewById (R.id.EveningPlanner);
        EveningPlanner.setAdapter (eveningAP);

        befoebedArrPlanner.add (new PlannerDetail ("พลิกตัว","false"));
        befoebedArrPlanner.add (new PlannerDetail ("กายภาพบำบัด","false"));
        befoebedArrPlanner.add (new PlannerDetail ("ตรวจเท้า","false"));

        PlannerAdapter beforbedAP = new PlannerAdapter (befoebedArrPlanner,PlannerListActivity.this,date,"BeforBed");
        BeforBedPlanner = findViewById (R.id.BeforBedPlanner);
        BeforBedPlanner.setAdapter (beforbedAP);

        String url = "https://homecare-90544.firebaseio.com/users/"+UserDetail.userName+"/patients/"
                +UserDetail.patient[UserDetail.selectPatient]+"/Planners/"+date+".json";
        Log.d("dddd",url);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj = new JSONObject(s);

                    Iterator i = obj.keys();
                    String key = "";

                    while(i.hasNext()){
                        key = i.next().toString();

                        switch (key){
                            case "Morning":
                                checkStatus(obj,key,morningArrPlanner);
                                PlannerAdapter morningAP = new PlannerAdapter (morningArrPlanner,PlannerListActivity.this ,
                                        date,"Morning");
                                MorningPlanner = findViewById (R.id.MorningPlanner);
                                MorningPlanner.setAdapter (morningAP);

                                break;
                            case "Afternoon":
                                checkStatus(obj,key,afternoonArrPlanner);
                                PlannerAdapter afterAP = new PlannerAdapter (afternoonArrPlanner,PlannerListActivity.this,date,"Afternoon");
                                AfternoonPlanner = findViewById (R.id.AfternoonPlanner);
                                AfternoonPlanner.setAdapter (afterAP);
                                break;
                            case "Evening":
                                checkStatus(obj,key,eveningArrPlanner);
                                PlannerAdapter eveningAP = new PlannerAdapter (eveningArrPlanner,PlannerListActivity.this,date,"Evening");
                                EveningPlanner = findViewById (R.id.EveningPlanner);
                                EveningPlanner.setAdapter (eveningAP);
                                break;
                            case "BeforBed":
                                checkStatus(obj,key,befoebedArrPlanner);
                                PlannerAdapter beforbedAP = new PlannerAdapter (befoebedArrPlanner,PlannerListActivity.this,date,"BeforBed");
                                BeforBedPlanner = findViewById (R.id.BeforBedPlanner);
                                BeforBedPlanner.setAdapter (beforbedAP);
                                break;
                        }


                    }

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

        RequestQueue rQueue = Volley.newRequestQueue(PlannerListActivity.this);
        rQueue.add(request);




















    }//ดึงข้อมูลมาโชว์

    public void onClickaddPlanner(View view){
        startActivity (new Intent (PlannerListActivity.this,PlannerActivity.class));
    }//back page
}
