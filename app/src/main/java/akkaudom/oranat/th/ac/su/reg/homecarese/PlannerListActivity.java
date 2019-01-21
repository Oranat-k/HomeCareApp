package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
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

import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.MedicineAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.PlannerAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.MedicineDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.PlannerDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;

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

        BeforBedPlanner = findViewById (R.id.BeforBedPlanner);
        MorningPlanner = findViewById (R.id.MorningPlanner);
        AfternoonPlanner = findViewById (R.id.AfternoonPlanner);
        EveningPlanner = findViewById (R.id.EveningPlanner);
        getDataToArr();

        setScrollList(MorningPlanner);
        setScrollList(AfternoonPlanner);
        setScrollList(EveningPlanner);
        setScrollList(BeforBedPlanner);


        getSupportActionBar().setTitle("Planner");
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setScrollList(ListView list){
        list.setOnTouchListener(new View.OnTouchListener () {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }//ScrollList

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

    }//Status Morning afternoon evening


    private void setDafault(String date) {

        PlannerDetail plan = new PlannerDetail ("พลิกตัว","default","false");

        morningArrPlanner.add (plan);
        morningArrPlanner.add (new PlannerDetail ("กายภาพบำบัด","default","false"));
        morningArrPlanner.add (new PlannerDetail ("ตรวจเท้า","default","false"));


        afternoonArrPlanner.add (plan);
        afternoonArrPlanner.add (new PlannerDetail ("กายภาพบำบัด","default","false"));
        afternoonArrPlanner.add (new PlannerDetail ("ตรวจเท้า","default","false"));


        eveningArrPlanner.add (plan);
        eveningArrPlanner.add (new PlannerDetail ("กายภาพบำบัด","default","false"));
        eveningArrPlanner.add (new PlannerDetail ("ตรวจเท้า","default","false"));

        befoebedArrPlanner.add (plan);
        befoebedArrPlanner.add (new PlannerDetail ("กายภาพบำบัด","default","false"));
        befoebedArrPlanner.add (new PlannerDetail ("ตรวจเท้า","default","false"));

    }

    public void setAdapter(ArrayList<PlannerDetail> arrAdapter,String date,String dauring ,ListView listPlaner){
        PlannerAdapter AP = new PlannerAdapter (arrAdapter,PlannerListActivity.this,date,dauring);
        listPlaner.setAdapter (AP);

    }

    public PlannerDetail setNewPlaner(JSONObject obj,String key,String during) throws JSONException {

        String time = ((obj.getString ("Time") == "beforefood")? "ก่อนอาหาร" : "หลังอาหาร");


        return new PlannerDetail (
                key,
                during+" , "+ time +" , "
                        +obj.getString ("Amount"),
                obj.getString ("ImageUrl"),
                "medicine",
                obj.getString ("Status")

        );

    }

    private void getMedicine() {

        String url = "https://homecare-90544.firebaseio.com/users/"+UserDetail.userName+"/patients/"
                +UserDetail.patient[UserDetail.selectPatient]+"/Medicines.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj = new JSONObject(s);

                    Iterator i = obj.keys();
                    String key = "";
                    while(i.hasNext()){
                        key = i.next().toString();


                        if(obj.getJSONObject (key).getString ("Status").equals ("true")){

                            if(obj.getJSONObject (key).getJSONObject ("Range").getString ("Morning").equals ("true")){
                                morningArrPlanner.add(setNewPlaner(obj.getJSONObject (key),key,"เช้า"));
                            }
                            if(obj.getJSONObject (key).getJSONObject ("Range").getString ("Afternoon").equals ("true")){
                                afternoonArrPlanner.add(setNewPlaner(obj.getJSONObject (key),key,"Afternoon"));
                            }

                            if(obj.getJSONObject (key).getJSONObject ("Range").getString ("Evening").equals ("true")){
                                eveningArrPlanner.add(setNewPlaner(obj.getJSONObject (key),key,"Evening"));
                            }
                            if(obj.getJSONObject (key).getJSONObject ("Range").getString ("BeforBed").equals ("true")){
                                befoebedArrPlanner.add(setNewPlaner(obj.getJSONObject (key),key,"BeforBed"));
                            }

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

    }//getMedicine


    private void getDataToArr() {

        dateSelect = Calendar.getInstance ();
        dateSelect.set (Calendar.HOUR_OF_DAY,0);
        dateSelect.set (Calendar.MINUTE,0);
        dateSelect.set (Calendar.SECOND,0);

        final String date = dateSelect.get (Calendar.DAY_OF_MONTH) + "-" +dateSelect.get (Calendar.MONTH)+1+"-" +dateSelect.get (Calendar.YEAR);


        setDafault(date);
        getMedicine();

        setAdapter (morningArrPlanner,date,"Morning",MorningPlanner);
        setAdapter (afternoonArrPlanner,date,"Afternoon",AfternoonPlanner);
        setAdapter (eveningArrPlanner,date,"Evening",EveningPlanner);
        setAdapter (befoebedArrPlanner,date,"BeforBed",BeforBedPlanner);



        String url = "https://homecare-90544.firebaseio.com/users/"+UserDetail.userName+"/patients/"
                +UserDetail.patient[UserDetail.selectPatient]+"/Planners/"+date+".json";

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
                                setAdapter (morningArrPlanner,date,"Morning",MorningPlanner);

                                break;
                            case "Afternoon":
                                checkStatus(obj,key,afternoonArrPlanner);
                                setAdapter (afternoonArrPlanner,date,"Afternoon",AfternoonPlanner);
                                break;
                            case "Evening":
                                checkStatus(obj,key,eveningArrPlanner);
                                setAdapter (eveningArrPlanner,date,"Evening",EveningPlanner);
                                break;
                            case "BeforBed":
                                checkStatus(obj,key,befoebedArrPlanner);
                                setAdapter (befoebedArrPlanner,date,"BeforBed",BeforBedPlanner);
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
