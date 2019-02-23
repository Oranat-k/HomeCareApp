package akkaudom.oranat.th.ac.su.reg.homecarese.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddPressureActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.AddPlanerActivity.AddSugarActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.PlannerDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.PlannerActivity;
import akkaudom.oranat.th.ac.su.reg.homecarese.R;
import de.hdodenhof.circleimageview.CircleImageView;


public class PlannerAdapter extends BaseAdapter {

    private ArrayList<PlannerDetail> list;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private String dateNow;
    private String during;

    public  View view;

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

        switch (list.get (position).getMode ()){
            case "default" :
                setDefault(position,parent);
                break;

            case "medicine":
                setMedicine(position,parent);
                break;

            case "pressure":
                setPressure(position,parent);
                break;

            case "sugar":
                setSugar(position,parent);
                break;

            case "symptom":
                setSymptom(position,parent);
                break;

            case "therapy":
                setTherapy(position,parent);
                break;

            case "doctor":
                setDoctor(position,parent);
                break;

        }

        notifyDataSetChanged();


        return view;
    }
    @SuppressLint("RestrictedApi")
    private void showEditDelPopup(View v , final String status , final int position) {

         MenuBuilder menuBuilder =new MenuBuilder(mContext);
        MenuInflater inflater = new MenuInflater(mContext);
        inflater.inflate(R.menu.plan_menu, menuBuilder);
         MenuPopupHelper optionsMenu = new MenuPopupHelper(mContext, menuBuilder, v);
        optionsMenu.setForceShowIcon(true);

        // Set Item Click Listener
        menuBuilder.setCallback(new MenuBuilder.Callback() {
            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_edit:

                        Log.d ("status",status);

                        if(status.equals ("pressure")){
                            Intent mIntent = new Intent(mContext, AddPressureActivity.class);
                            Bundle extras = new Bundle();
                            extras.putString("Date", list.get (position).getDate ());
                            extras.putString("During", list.get (position).getDuring ());
                            extras.putInt ("Top", Integer.parseInt (list.get (position).getSubtitle ()[1]));
                            extras.putInt("Below", Integer.parseInt (list.get (position).getSubtitle ()[2]));
                            mIntent.putExtras(extras);
                            mContext.startActivity (mIntent);
                        }else if (status.equals ("sugar")){
                            Intent mIntent = new Intent(mContext, AddSugarActivity.class);
                            Bundle extras = new Bundle();
                            extras.putString("Date", list.get (position).getDate ());
                            extras.putString("During", list.get (position).getDuring ());
                            extras.putInt ("Value", Integer.parseInt (list.get (position).getSubtitle ()[1]));
                            extras.putString("Range", list.get (position).getSubtitle ()[2]);
                            mIntent.putExtras(extras);
                            mContext.startActivity (mIntent);
                        }



                        return true;
                    case R.id.menu_del: // Handle option2 Click

                        DatabaseReference referenPressure = FirebaseDatabase.getInstance()
                                .getReferenceFromUrl("https://homecare-90544.firebaseio.com");

                        if(status.equals ("pressure")) {
                            referenPressure.child ("users").child (UserDetail.userName).child ("patients")
                                    .child (UserDetail.patient.get (UserDetail.selectPatient).getId ())
                                    .child ("Pressures").child (list.get (position).getDate ())
                                    .child (list.get (position).getDuring ()).setValue (null);
                        }else if (status.equals ("sugar")){
                            referenPressure.child ("users").child (UserDetail.userName).child ("patients")
                                    .child (UserDetail.patient.get (UserDetail.selectPatient).getId ())
                                    .child ("Sugars").child (list.get (position).getDate ())
                                    .child (list.get (position).getDuring ()).setValue (null);
                        }
                        return true;
                    default:
                        return false;
                } //edit delete in sugar and press
            }

            @Override
            public void onMenuModeChange(MenuBuilder menu) {}
        });


        // Display the menu
        optionsMenu.show();

    }


    private void setTherapy(final int position, ViewGroup parent) {

        view = mLayoutInflater.inflate(R.layout.list_item_planner_therapy,parent,false);
        final therapyHolder h = new therapyHolder ();



        // set id's
        h.titleThe = (TextView)(view.findViewById(R.id.titleSy));

        h.titleThe.setText(list.get (position).getTitle ());

        h.subtitleThe = (TextView)(view.findViewById(R.id.subtitleSy));

        h.subtitleThe.setText(list.get (position).getSubtitle ()[0]);

    }//setSymptom

    private void setSymptom(final int position, ViewGroup parent) {

        view = mLayoutInflater.inflate(R.layout.list_item_planner_symtom,parent,false);
        final symptomHolder h = new symptomHolder ();



        // set id's
        h.titleSy = (TextView)(view.findViewById(R.id.titleSy));

        h.titleSy.setText(list.get (position).getTitle ());

        h.subtitleSy = (TextView)(view.findViewById(R.id.subtitleSy));

        h.subtitleSy.setText(list.get (position).getSubtitle ()[0]);

    }//setSymptom



    private void setSugar(final int position, ViewGroup parent) {

        view = mLayoutInflater.inflate(R.layout.list_item_planner_note,parent,false);
        final pessureHolder h = new pessureHolder ();

        h.imagePessure = (ImageView) (view.findViewById (R.id.imagePessure));
        h.edit = (ImageButton) (view.findViewById (R.id.edit));
        h.edit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                showEditDelPopup (view,"sugar",position);
            }
        });

        // set id's
        h.title = (TextView)(view.findViewById(R.id.titlePr));

        h.title.setText(list.get (position).getTitle ());

        h.subtitle = (TextView)(view.findViewById(R.id.subtitlePr));

        h.subtitle.setText(list.get (position).getSubtitle ()[0]);

        switch(list.get (position).getStatus ()){
            case "bad":
                h.imagePessure.setImageResource (R.drawable.bad);
                break;
            case "good":
                h.imagePessure.setImageResource (R.drawable.good);
                break;
            case "very good":
                h.imagePessure.setImageResource (R.drawable.verygood);
                break;
        }


    }//Pressure layout box

    private void setPressure(final int position, ViewGroup parent) {

        view = mLayoutInflater.inflate(R.layout.list_item_planner_note,parent,false);
        final pessureHolder h = new pessureHolder ();

        h.imagePessure = (ImageView) (view.findViewById (R.id.imagePessure));
        h.edit = (ImageButton) (view.findViewById (R.id.edit));

        h.edit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                showEditDelPopup (view,"pressure",position);
            }
        });



        // set id's
        h.title = (TextView)(view.findViewById(R.id.titlePr));

        h.title.setText(list.get (position).getTitle ());

        h.subtitle = (TextView)(view.findViewById(R.id.subtitlePr));

        h.subtitle.setText(list.get (position).getSubtitle ()[0]);
        Log.d ("show status", list.get (position).getStatus ());

        switch(list.get (position).getStatus ()){
            case "bad":
                h.imagePessure.setImageResource (R.drawable.bad);
                break;
            case "good":
                h.imagePessure.setImageResource (R.drawable.good);
                break;
            case "very good":
                h.imagePessure.setImageResource (R.drawable.verygood);
                break;
        }


    }//Pressure layout box




    private void setMedicine(final int position, ViewGroup parent) {

        view = mLayoutInflater.inflate(R.layout.list_item_planner_medicine,parent,false);
        final medicineHolder h = new medicineHolder ();

        h.imageMed = (CircleImageView) (view.findViewById (R.id.imageMed));

        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child(list.get (position).getImagePath ());

        Glide.with(mContext)
                .using(new FirebaseImageLoader ())
                .load(storageReference)
                .into(h.imageMed);


        // set id's
        h.title = (TextView)(view.findViewById(R.id.titleMed));

        h.title.setText(list.get(position).getTitle ());

        h.subtitle = (TextView)(view.findViewById(R.id.subTitleMed));

        h.subtitle.setText(list.get (position).getSubtitle ()[0]);

        h.border = (RelativeLayout) (view.findViewById (R.id.border)) ;

        h.checklist = (CheckBox)(view.findViewById(R.id.checklist));

        if (list.get (position).getStatus () == "true") {
            h.border.setBackground(ContextCompat.getDrawable(mContext,R.drawable.border_active));
            h.checklist.setButtonTintList(mContext.getResources().getColorStateList(R.color.holo_green_dark));
            h.checklist.setChecked (true);

        }//CheckBox



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
        });//เปลี่ยนสีเมื่อกด checkbox




    }//medicine layout box


    private void setDoctor(final int position, ViewGroup parent) {

        view = mLayoutInflater.inflate(R.layout.list_item_planner_symtom,parent,false);
        final doctorHolder h = new doctorHolder ();

        // set id's
        h.titleDoc = (TextView)(view.findViewById(R.id.titleSy));

        h.titleDoc.setText(list.get (position).getTitle ());

        h.subtitleDoc = (TextView)(view.findViewById(R.id.subtitleSy));

        h.subtitleDoc.setText(list.get (position).getSubtitle ()[0]);

    }//setDoctor



    public void setPlannerDB(String title , boolean status){

        DatabaseReference referenPressure = FirebaseDatabase.getInstance ()
                .getReferenceFromUrl ("https://homecare-90544.firebaseio.com");
        referenPressure.child ("users").child (UserDetail.userName).child ("patients")
                .child (UserDetail.patient.get (UserDetail.selectPatient).getId ())
                .child ("Planners").child(dateNow).child(during)
                .child (title).setValue (status);

    }

    public void setDefault(final int position , ViewGroup parent){
         view = mLayoutInflater.inflate(R.layout.list_item_planner,parent,false);
        final defaultHolder h = new defaultHolder();


        // set id's
        h.title = (TextView)(view.findViewById(R.id.title));

        h.title.setText(list.get(position).getTitle ());

        h.suggest = (TextView)(view.findViewById(R.id.title));

        h.suggest.setText(list.get(position).getTitle ());

        h.border = (LinearLayout)(view.findViewById (R.id.border)) ;

        h.checklist = (CheckBox)(view.findViewById(R.id.checklist));

        h.edit = (ImageButton)(view.findViewById (R.id.edit));


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

    }//default layout box


    private class defaultHolder
    {
        TextView title;
        TextView suggest;
        CheckBox checklist;
        ImageButton  edit;
        LinearLayout border;

    }

    private class medicineHolder
    {
        CircleImageView  imageMed;
        TextView title;
        TextView subtitle;
        CheckBox checklist;
        RelativeLayout border;

    }

    private class pessureHolder
    {
        ImageView  imagePessure;
        TextView title;
        TextView subtitle;
        ImageButton  edit;

    }
    private class symptomHolder
    {
        TextView titleSy;
        TextView subtitleSy;
    }

    private class therapyHolder
    {
        TextView titleThe;
        TextView subtitleThe;
    }

    private class doctorHolder
    {
        TextView titleDoc;
        TextView subtitleDoc;
    }


}





