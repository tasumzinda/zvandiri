package zw.org.zvandiri.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Cd4Count;
import zw.org.zvandiri.business.domain.Cd4Count;

import java.util.ArrayList;

/**
 * Created by User on 12/17/2016.
 */
public class Cd4CountAdapter extends ArrayAdapter<Cd4Count> {

    private Context context;
    private ArrayList<Cd4Count> list;
    TextView name;

    public Cd4CountAdapter(Context context, ArrayList<Cd4Count> list){
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
        }
        name = (TextView) newView.findViewById(R.id.adapter_name);
        Cd4Count cd4Count = list.get(position);
        name.setText(position + 1 + ". " + cd4Count);
        return newView;
    }

    public void onDataSetChanged(ArrayList<Cd4Count> list){
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }
}
