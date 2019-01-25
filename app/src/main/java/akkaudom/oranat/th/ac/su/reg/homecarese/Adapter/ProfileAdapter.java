package akkaudom.oranat.th.ac.su.reg.homecarese.Adapter;

import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.MedicineDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.ProfileDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.UserDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileAdapter extends BaseAdapter {

    private ArrayList<ProfileDetail> list;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public ProfileAdapter(ArrayList<ProfileDetail> list, Context c) {
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


        View view = mLayoutInflater.inflate(R.layout.list_item_profile_patient,parent,false);
        final Holder h = new Holder();

        // set id's
        h.namePatient = (TextView)(view.findViewById(R.id.namePatient));

        h.namePatient.setText(list.get(position).getNamePatient ());

        h.statusPatient = (TextView)(view.findViewById(R.id.statusPatient));

        h.statusPatient.setText("สถานะ : "+list.get(position).getStatusPatient ());


        h.imageMed = (CircleImageView) (view.findViewById (R.id.imagePatient));

        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child(list.get (position).getImagePatient ());

        Glide.with(mContext)
                .using(new FirebaseImageLoader ())
                .load(storageReference)
                .into(h.imageMed);



        notifyDataSetChanged();


        return view;
    }


    private class Holder
    {
        TextView namePatient;
        TextView statusPatient;
        CircleImageView imageMed;

    }
}





