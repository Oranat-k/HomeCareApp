package akkaudom.oranat.th.ac.su.reg.homecarese.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.HomeDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.Detail.PatientDetail;
import akkaudom.oranat.th.ac.su.reg.homecarese.R;

public class HomeAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<HomeDetail> mData;
    private LayoutInflater mInflater;

    public HomeAdapter(Context context, ArrayList<HomeDetail> data) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.activity_detail, parent, false);
            holder.txtWeek = (TextView) convertView.findViewById(R.id.topic);
            holder.imagePro = (ImageView) convertView.findViewById (R.id.pic);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String img = mData.get (position).getImgUrl ();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child(img.substring (1,img.length ()));

        Glide.with(mContext)
                .using(new FirebaseImageLoader ())
                .load(storageReference)
                .into(holder.imagePro);


        holder.txtWeek.setText(mData.get(position).getTopic ());

        convertView.setTag(holder);

        return convertView;
    }

    public class ViewHolder {
        TextView txtWeek;
        ImageView imagePro;
    }
}