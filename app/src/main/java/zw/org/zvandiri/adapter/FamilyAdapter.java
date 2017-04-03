package zw.org.zvandiri.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Family;

import java.util.ArrayList;

/**
 * Created by jackie muzinda on 5/1/2017.
 */
public class FamilyAdapter extends ArrayAdapter<Family>{

    private Context context;
    private ArrayList<Family> list;
    TextView name;

    public FamilyAdapter(Context context, ArrayList<Family> list){
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
        Family family = list.get(position);
        name.setText(position + 1 + ". " + family);
        return newView;
    }

    public void onDataSetChanged(ArrayList<Family> list){
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }
}
