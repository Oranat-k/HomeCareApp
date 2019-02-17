package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.PlannerAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.PlannerDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Tabs.MorningTab;

public class HomeCareUitil {
    public static ArrayList<PlannerDetail> arrPlanner = new ArrayList<> ();
    public static  String date;
    public static JSONObject objData;
    public static Context mcontext;
    public static String during;
    public static ListView listView;

    private static void setDafault() {


        PlannerDetail plan = new PlannerDetail ("พลิกตัว","default","false");
        PlannerDetail plan1 = new PlannerDetail ("กายภาพบำบัด","default","false");
        PlannerDetail plan2 = new PlannerDetail ("ตรวจเท้า","default","false");

        arrPlanner.add (plan);
        arrPlanner.add (plan1);
        arrPlanner.add (plan2);
    }

    public static void  checkStatus(JSONObject obj, ArrayList<PlannerDetail> arrPlanner){

        try {
            for(int i = 0 ; i < arrPlanner.size () ; i++) {
                if (obj.has (arrPlanner.get (i).getTitle ())) {
                    arrPlanner.get (i).setStatus (obj.getString (arrPlanner.get (i).getTitle ()));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace ();
        }

    }//Status Morning afternoon evening

    public static void setAdapter(String date){
        PlannerAdapter AP = new PlannerAdapter (arrPlanner,mcontext,date,during);
        listView.setAdapter (AP);

    }

    public static boolean  checkData(JSONObject obj , String dateCheck, String duringCheck){


       try {
           if (obj.has (dateCheck)){
               if (obj.getJSONObject (dateCheck).has (duringCheck)){
                   return true;
               }

           }
       } catch (JSONException e) {
           e.printStackTrace ();
       }


        return false;

    }

    public static void getPlanerData( Context context , Calendar selectToday , String duringInput , ListView listViewInput){

        arrPlanner.clear ();
        //clear data

        DateFormat formater = new SimpleDateFormat ("dd-MM-yyyy");
        date = formater.format (selectToday.getTime ());

        mcontext = context;
        during = duringInput;
        listView = listViewInput;

        setDafault ();
        setAdapter(date);

        String url = "https://homecare-90544.firebaseio.com/users/"+UserDetail.userName+"/patients/"
                +UserDetail.patient.get (UserDetail.selectPatient).getId ()+".json";
        Log.d ("url firebase", "{"+url+"}");

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {

                if (!s.equals ("")){
                    try {
                        objData = new JSONObject(s);

                        if (checkData(objData.getJSONObject ("Planners"),date,during)){
                            getPlanerDefault(objData.getJSONObject ("Planners").getJSONObject (date).getJSONObject (during));
                        }
                        if (checkData(objData.getJSONObject ("Pressures"),date,during)){
                            getPressureData (objData.getJSONObject ("Pressures").getJSONObject (date).getJSONObject (during));
                        }
                        if (checkData(objData.getJSONObject ("Sugars"),date,during)){
                            getSugarData(objData.getJSONObject ("Sugars").getJSONObject (date).getJSONObject (during));
                        }
                        if (checkData(objData.getJSONObject ("Symptoms"),date,during)){
                            getSymptomData(objData.getJSONObject ("Symptoms").getJSONObject (date).getJSONObject (during));
                        }


                        getMedicineData(objData.getJSONObject ("Medicines"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(mcontext);
        rQueue.add(request);
    }

    public static void  getPlanerDefault(JSONObject obj){
        checkStatus(obj,arrPlanner);
        setAdapter(date);
    }

    public static void getPressureData(JSONObject obj){

        try {
            arrPlanner.add (setNewPressure(obj));
        } catch (JSONException e) {
            e.printStackTrace ();
        }
        setAdapter(date);

    }

    public static void getSugarData(JSONObject obj){

        try {
            arrPlanner.add (setNewSugar(obj));
        } catch (JSONException e) {
            e.printStackTrace ();
        }
        setAdapter(date);

    }

    public static void getSymptomData(JSONObject obj){

        try {
            arrPlanner.add (setNewSymptom (obj));
        } catch (JSONException e) {
            e.printStackTrace ();
        }
        setAdapter(date);

    }

    public static void getMedicineData(JSONObject obj){

        String duringtime = "";
        switch (during){
            case "morning": duringtime = "เช้า";break;
            case "afternoon": duringtime = "กลางวัน";break;
            case "evening": duringtime = "เย็น";break;
            case "beforbad": duringtime = "ก่อนนอน";break;
        }


        try {
            Iterator i = obj.keys();
            String key = "";
            while(i.hasNext()){
                key = i.next().toString();


                if(obj.getJSONObject (key).getString ("Status").equals ("true")){

                    if(obj.getJSONObject (key).getJSONObject ("Range").getString (during).equals ("true")){
                        arrPlanner.add(setNewMedicine(obj.getJSONObject (key),key,duringtime));
                    }

                }

            }
        } catch (JSONException e) {
            e.printStackTrace ();
        }
        setAdapter(date);

    }

    public static PlannerDetail setNewPressure(JSONObject obj) throws JSONException {
        String status = "default";
        int top = Integer.parseInt (obj.getString ("Top"));
        int below =  Integer.parseInt (obj.getString ("Below"));

        if (top >= 100 && top < 130 && below >= 60 && below < 80 ){
            status = "very good";
        }else if (top >= 130 && top < 135 && below >= 80 && below < 85){
            status = "good";
        }else if (top >= 135 && below >= 85){
            status = "bad";
        }

        return new PlannerDetail (
                "ค่าความดัน",
                top +" / "+below,
                "pressure",
                status
        );

    }

    public static PlannerDetail setNewSugar(JSONObject obj) throws JSONException {
        String status = "default";
        int Value = Integer.parseInt (obj.getString ("Value"));
//        String time = ((obj.getString ("Time") == "beforefood")? "ก่อนอาหาร" : "หลังอาหาร");

        if (Value >= 60 && Value < 100){
            status = "very good";
        }else if (Value >= 100 && Value < 126){
            status = "good";
        }else if (Value >= 126 && Value >= 200){
            status = "bad";
        }


        return new PlannerDetail (
                "ค่าน้ำตาล",
                Value +"  ",
                "pressure",
                status
        );

    }

    public static PlannerDetail setNewSymptom(JSONObject obj) throws JSONException {
        String status = "symptom";
        String Value  = (obj.getString ("Value"));
//        String time = ((obj.getString ("Time") == "beforefood")? "ก่อนอาหาร" : "หลังอาหาร");



        return new PlannerDetail (
                "อาการ",
                Value +"  ",
                "symptom",
                status
        );

    }

    public static PlannerDetail setNewMedicine(JSONObject obj,String key,String during) throws JSONException {

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
}
