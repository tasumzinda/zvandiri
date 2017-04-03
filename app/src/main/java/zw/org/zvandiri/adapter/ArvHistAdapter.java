package zw.org.zvandiri.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.ArvHist;
import zw.org.zvandiri.business.domain.ArvHist;

import java.util.ArrayList;

/**
 * Created by User on 12/17/2016.
 */
public class ArvHistAdapter extends ArrayAdapter<ArvHist> {

    private Context context;
    private ArrayList<ArvHist> list;
    TextView name;

    public ArvHistAdapter(Context context, ArrayList<ArvHist> list){
        super(context, R.layout.list_view_item, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newView = convertView;
        if(newView == null){
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            newView = layoutInflater.inflate(R.layout.list_view_item, null);
            name = (TextView) newView.findViewById(R.id.adapter_name);
        }
        ArvHist arvHist = list.get(position);
        name.setText(position + 1 + ". " + arvHist);
        return newView;
    }

    public void onDataSetChanged(ArrayList<ArvHist> list){
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }
}
