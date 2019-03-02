package akkaudom.oranat.th.ac.su.reg.homecarese.Tabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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
import java.util.Date;
import java.util.Map;

import akkaudom.oranat.th.ac.su.reg.homecarese.Adapter.PatientAdapter;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.R;

import static android.graphics.Color.rgb;

public class SugarTab extends Activity {

    Context mcontext = SugarTab.this;
    ArrayList<DataPoint> arrValue = new ArrayList<> ();


    GraphView graph;

    Spinner patientNames;

    int value = 0;
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
                        +UserDetail.patient.get (UserDetail.selectPatient).getId ()+"/Sugars");

        reference1.addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                arrValue.clear ();

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

                    }//loop check time

                    SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");

                    try {
                        Date date = format.parse(ds.getKey ());
                        int mount  = date.getMonth ();
                        date.setMonth (mount+1);
                        date.setHours (0);
                        date.setMinutes (0);
                        date.setSeconds (0);
                        arrValue.add (new DataPoint (date,value/count));

                        value = 0;
                        count = 0;
                    } catch (ParseException e) {
                        e.printStackTrace ();
                    }


                }

                LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<>(arrValue.toArray(new DataPoint[arrValue.size ()]));
                PointsGraphSeries<DataPoint> pointsGraphSeries = new PointsGraphSeries<>(arrValue.toArray(new DataPoint[arrValue.size ()]));
                pointsGraphSeries.setShape(PointsGraphSeries.Shape.POINT);
                pointsGraphSeries.setColor(Color.parseColor("#f9b7b7"));
                lineGraphSeries.setColor(Color.parseColor("#f9b7b7"));
                lineGraphSeries.setDrawBackground (true);
                lineGraphSeries.setBackgroundColor (rgb(255, 254, 108));
                graph.addSeries (lineGraphSeries);
                graph.addSeries(pointsGraphSeries);
                // Design chart line Top

                graph.getGridLabelRenderer().setGridColor(Color.BLUE);


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
        value += Integer.parseInt (ds.child ("Value").getValue ().toString ());
        count += 1;
    }


}