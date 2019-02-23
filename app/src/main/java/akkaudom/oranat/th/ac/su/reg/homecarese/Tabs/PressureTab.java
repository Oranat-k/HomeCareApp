package akkaudom.oranat.th.ac.su.reg.homecarese.Tabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.DoctorAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.PatientAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.DoctorDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.NotificationActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.R;

public class PressureTab extends Activity {

    Context mcontext = PressureTab.this;
    ArrayList<DataPoint> arrTop = new ArrayList<> ();
    ArrayList<DataPoint> arrBelow = new ArrayList<> ();

    GraphView graph;

    Spinner patientNames;

    int top = 0;
    int below = 0;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pressure_tab);

        graph = (GraphView) findViewById(R.id.graph);

        patientNames = (Spinner) findViewById (R.id.patientNames);
        PatientAdapter weekAdt = new PatientAdapter(mcontext, UserDetail.patient);
        patientNames.setAdapter(weekAdt);
        patientNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                UserDetail.selectPatient = arg2;
                getTabData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //optionally do something here
            }
        });



    }
    public  void getTabData(){

        DatabaseReference reference1 = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://homecare-90544.firebaseio.com/users/"+UserDetail.userName+"/patients/"
                        +UserDetail.patient.get (UserDetail.selectPatient).getId ()+"/Pressures");

        reference1.addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                arrTop.clear ();
                arrBelow.clear ();
                graph.removeAllSeries ();

                for(DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, String> map = (Map) ds.getValue ();


                    if (ds.hasChild ("morning")){
                        calTopNBelow(ds.child ("morning"));

                    }else if (ds.hasChild ("afternoon")){
                        calTopNBelow(ds.child ("afternoon"));

                    }else if (ds.hasChild ("evening")){
                        calTopNBelow(ds.child ("evening"));

                    }else if (ds.hasChild ("beforbed")){
                        calTopNBelow(ds.child ("beforbed"));

                    }

                    SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");

                    try {
                        Date date = format.parse(ds.getKey ());
                        int mount  = date.getMonth ();
                        date.setMonth (mount+1);
                        date.setHours (0);
                        date.setMinutes (0);
                        date.setSeconds (0);
                        arrTop.add (new DataPoint (date,top/count));
                        arrBelow.add (new DataPoint (date,below/count));
                        top = 0;
                        below = 0;
                        count = 0;
                    } catch (ParseException e) {
                        e.printStackTrace ();
                    }


                }

                LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<>(arrTop.toArray(new DataPoint[arrTop.size ()]));
                PointsGraphSeries<DataPoint> pointsGraphSeries = new PointsGraphSeries<>(arrTop.toArray(new DataPoint[arrTop.size ()]));
                pointsGraphSeries.setShape(PointsGraphSeries.Shape.POINT);
                pointsGraphSeries.setColor(Color.parseColor("#f9b7b7"));
                lineGraphSeries.setColor(Color.parseColor("#f9b7b7"));
                graph.addSeries (lineGraphSeries);
                graph.addSeries(pointsGraphSeries);


                LineGraphSeries<DataPoint> lineGraphSeries2 = new LineGraphSeries<>(arrBelow.toArray(new DataPoint[arrBelow.size ()]));
                PointsGraphSeries<DataPoint> pointsGraphSeries2 = new PointsGraphSeries<>(arrBelow.toArray(new DataPoint[arrBelow.size ()]));
                pointsGraphSeries.setShape(PointsGraphSeries.Shape.POINT);
                pointsGraphSeries.setColor(Color.parseColor("#f9b7c7"));
                lineGraphSeries.setColor(Color.parseColor("#f9b7c7"));
                graph.addSeries (lineGraphSeries2);
                graph.addSeries(pointsGraphSeries2);


                // set date label formatter
                graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter (mcontext));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });


    }
    public  void calTopNBelow(DataSnapshot ds  ){
        top += Integer.parseInt (ds.child ("Top").getValue ().toString ());
        below += Integer.parseInt (ds.child ("Below").getValue ().toString ());
        count += 1;
    }



}