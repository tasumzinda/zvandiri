package zw.org.zvandiri.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.SocialHist;

import java.util.ArrayList;

/**
 * Created by User on 12/19/2016.
 */
public class SocialHistAdapter extends ArrayAdapter<SocialHist> {

    private Context context;
    private ArrayList<SocialHist> list;
    TextView name;
    public SocialHistAdapter(Context context, ArrayList<SocialHist> list){
        super(context, R.layout.list_view_item, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        View newView = convertView;
        if(newView == null){
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            newView = layoutInflater.inflate(R.layout.list_view_item, null);
            name = (TextView) newView.findViewById(R.id.adapter_name);
        }
        SocialHist socialHist = list.get(pos);
        name.setText(pos + 1 + ". " + socialHist);
        return newView;
    }

    public void onDataSetChanged(ArrayList<SocialHist> list){
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }
}
