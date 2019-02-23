package akkaudom.oranat.th.ac.su.reg.homecarese.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.DoctorDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.ProfileDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorAdapter extends BaseAdapter {

    private ArrayList<DoctorDetail> list;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public DoctorAdapter(ArrayList<DoctorDetail> list, Context c) {
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


        View view = mLayoutInflater.inflate(R.layout.list_item_doctor,parent,false);
        final Holder h = new Holder();

        // set id's
        h.nameDoctor = (TextView)(view.findViewById(R.id.nameDoctor));

        h.nameDoctor.setText(list.get(position).getNameDoctor ());

        h.nameHospital = (TextView)(view.findViewById(R.id.nameHos));

        h.nameHospital.setText(list.get(position).getNameHospital ());

        h.docTime = (TextView)(view.findViewById(R.id.docTime));

        h.docTime.setText(list.get(position).getTimeDoc ());

//        h.statusPatient = (TextView)(view.findViewById(R.id.statusPatient));

//        h.statusPatient.setText("สถานะ : "+list.get(position).getStatusPatient ());


        notifyDataSetChanged();

        return view;
    }

    private class Holder

    {
        TextView nameDoctor;
        TextView nameHospital;
        TextView docTime;

    }
}






