package akkaudom.oranat.th.ac.su.reg.homecarese.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.MedicineDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class MedicineAdapter extends BaseAdapter {

    private ArrayList<MedicineDetail> list;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public MedicineAdapter(ArrayList<MedicineDetail> list, Context c) {
        this.list = list;
        this.mContext = c;
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


        View view = mLayoutInflater.inflate(R.layout.list_item_medicine,parent,false);
        final Holder h = new Holder();

        // set id's
        h.titleMed = (TextView)(view.findViewById(R.id.titleMed));

        h.titleMed.setText(list.get(position).getNameMed ());

        h.subTitleMed = (TextView)(view.findViewById(R.id.subTitleMed));

        h.subTitleMed.setText(list.get(position).getRange ()+","+list.get(position).getTimeMed ()+","+list.get(position).getAmount ()+"เม็ด");

        h.imageMed = (CircleImageView) (view.findViewById (R.id.imageMed));

        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child(list.get (position).getImageUrl ());

        Glide.with(mContext)
                .using(new FirebaseImageLoader ())
                .load(storageReference)
                .into(h.imageMed);

        h.switchMed = (Switch) (view.findViewById (R.id.switchMed));

        if (list.get (position).getStatus ().equals ("true")) {
            h.switchMed.setChecked (true);

        }//checklist

        h.switchMed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseReference referenMedicine = FirebaseDatabase.getInstance()
                        .getReferenceFromUrl("https://homecare-90544.firebaseio.com");
                if (isChecked){
                    referenMedicine.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                            .child("Medicines").child(list.get (position).getNameMed ()).child ("Status").setValue("true");
                }else {

                    referenMedicine.child ("users").child(UserDetail.userName).child("patients").child(UserDetail.patient[UserDetail.selectPatient])
                            .child("Medicines").child(list.get (position).getNameMed ()).child ("Status").setValue("false");
                }
            }
        }); //ระงับการใช้ยา



        notifyDataSetChanged();


        return view;
    }


    private class Holder
    {
        TextView titleMed;
        TextView subTitleMed;
        CircleImageView imageMed;
        Switch switchMed;

    }
}





