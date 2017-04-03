package zw.org.zvandiri.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.MentalHealthItem;

import java.util.ArrayList;

/**
 * Created by User on 12/19/2016.
 */
public class MentalHealthItemAdapter extends ArrayAdapter<MentalHealthItem> {

    private Context context;
    private ArrayList<MentalHealthItem> list;
    TextView name;

    public MentalHealthItemAdapter(Context context, ArrayList<MentalHealthItem> list){
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
        MentalHealthItem mentalHealthItem = list.get(pos);
        name.setText(pos + 1 + ". " + mentalHealthItem);
        return newView;
    }

    public void onDataSetChanged(ArrayList<MentalHealthItem> list){
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }
}
