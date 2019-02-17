package akkaudom.oranat.th.ac.su.reg.homecarese;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.PlannerAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.PlannerDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Tabs.MorningTab;

public class HomeCareUitil {
    public static ArrayList<PlannerDetail> morningArrPlanner = new ArrayList<> ();

    private static void setDafault(ArrayList<PlannerDetail> list) {


        PlannerDetail plan = new PlannerDetail ("พลิกตัว","default","false");
        PlannerDetail plan1 = new PlannerDetail ("กายภาพบำบัด","default","false");
        PlannerDetail plan2 = new PlannerDetail ("ตรวจเท้า","default","false");

        list.add (plan);
        list.add (plan1);
        list.add (plan2);
    }

    public static void  checkStatus(JSONObject obj, String during, ArrayList<PlannerDetail> arrPlanner){

        try {
            JSONObject objDuring =  obj.getJSONObject (during);
            for(int i = 0 ; i < arrPlanner.size () ; i++) {
                if (objDuring.has (arrPlanner.get (i).getTitle ())) {
                    arrPlanner.get (i).setStatus (objDuring.getString (arrPlanner.get (i).getTitle ()));
                    Log.d ("ffffff", arrPlanner.get (i).getStatus ());

                }
            }
        } catch (JSONException e) {
            e.printStackTrace ();
        }

    }//Status Morning afternoon evening

    public static void setAdapter(ArrayList<PlannerDetail> arrAdapter,String date,String dauring ,ListView listPlaner,Context c){
        PlannerAdapter AP = new PlannerAdapter (arrAdapter,c,date,dauring);
        listPlaner.setAdapter (AP);

    }

    public static void getMorning(final Context mcontext , Calendar selectToday , final ListView listView){

        morningArrPlanner.clear ();
        //clear data

        final String date = selectToday.get (Calendar.DAY_OF_MONTH) + "-" +selectToday.get (Calendar.MONTH)+1+"-" +selectToday.get (Calendar.YEAR);
        setDafault (morningArrPlanner);
        setAdapter(morningArrPlanner,date,"morning",listView ,mcontext);

        String url = "https://homecare-90544.firebaseio.com/users/"+UserDetail.userName+"/patients/"
                +UserDetail.patient.get (UserDetail.selectPatient).getId ()+"/Planners/"+date+".json";
        Log.d ("url ",url);

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
                            case "morning":
                                checkStatus(obj,key,morningArrPlanner);
                                setAdapter(morningArrPlanner,date,"morning",listView ,mcontext);
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

        RequestQueue rQueue = Volley.newRequestQueue(mcontext);
        rQueue.add(request);
    }
}
