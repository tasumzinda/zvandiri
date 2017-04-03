package zw.org.zvandiri.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.ObstercHist;

import java.util.ArrayList;

/**
 * Created by jackie muzinda on 6/1/2017.
 */
public class ObstetricHistAdapter extends ArrayAdapter<ObstercHist> {

    private Context context;
    private ArrayList<ObstercHist> list;
    TextView name;

    public ObstetricHistAdapter(Context context, ArrayList<ObstercHist> list){
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
        ObstercHist obstetricHist = list.get(pos);
        name.setText(pos + 1 + ". " + obstetricHist);
        return newView;
    }

    public void onDataSetChanged(ArrayList<ObstercHist> list){
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }
}
