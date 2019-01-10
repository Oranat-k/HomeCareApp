package akkaudom.oranat.th.ac.su.reg.homecarese;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PlannerAdapter extends BaseAdapter {

    private ArrayList<PlannerDetail> list;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private String dateNow;
    private String during;

    public PlannerAdapter(ArrayList<PlannerDetail> list, Context c,String dateNow ,String during) {
        this.list = list;
        this.mContext = c;
        this.dateNow = dateNow;
        this.during = during;
        mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View view = mLayoutInflater.inflate(R.layout.list_item_planner,parent,false);
        final Holder h = new Holder();


        // set id's
        h.title = (TextView)(view.findViewById(R.id.title));

        h.title.setText(list.get(position).getTitle ());

        h.suggest = (TextView)(view.findViewById(R.id.title));

        h.suggest.setText(list.get(position).getTitle ());

        h.border = (LinearLayout)(view.findViewById (R.id.border)) ;

        h.checklist = (CheckBox)(view.findViewById(R.id.checklist));

        if (list.get (position).getStatus () == "true") {
            h.border.setBackground(ContextCompat.getDrawable(mContext,R.drawable.border_active));
            h.checklist.setButtonTintList(mContext.getResources().getColorStateList(R.color.holo_green_dark));
            h.checklist.setChecked (true);

        }//checklist



        h.checklist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    h.border.setBackground(ContextCompat.getDrawable(mContext,R.drawable.border_active));
                    h.checklist.setButtonTintList(mContext.getResources().getColorStateList(R.color.holo_green_dark));

                    setPlannerDB(list.get(position).getTitle () , true);
                }
                else{
                    h.border.setBackground(ContextCompat.getDrawable(mContext,R.drawable.border_none));
                    h.checklist.setButtonTintList(mContext.getResources().getColorStateList(R.color.colorAccent));

                    setPlannerDB(list.get(position).getTitle (), false);

                }
            }
        });
        notifyDataSetChanged();


        return view;
    }

    public void setPlannerDB(String title , boolean status){

        DatabaseReference referenPressure = FirebaseDatabase.getInstance ()
                .getReferenceFromUrl ("https://homecare-90544.firebaseio.com");
        referenPressure.child ("users").child (UserDetail.userName).child ("patients").child (UserDetail.patient[UserDetail.selectPatient])
                .child ("Planners").child(dateNow).child(during)
                .child (title).setValue (status);

    }


    private class Holder
    {
        TextView title;
        TextView suggest;
        CheckBox checklist;
        LinearLayout border;

    }
}





