package zw.org.zvandiri.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Dependent;
import zw.org.zvandiri.business.domain.Dependent;

import java.util.ArrayList;

/**
 * Created by jackie muzinda on 6/1/2017.
 */
public class DependantAdapter extends ArrayAdapter<Dependent> {

    private Context context;
    private ArrayList<Dependent> list;
    TextView name;

    public DependantAdapter(Context context, ArrayList<Dependent> list){
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
        Dependent Dependent = list.get(position);
        name.setText(position + 1 + ". " + Dependent);
        return newView;
    }

    public void onDataSetChanged(ArrayList<Dependent> list){
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }
}
