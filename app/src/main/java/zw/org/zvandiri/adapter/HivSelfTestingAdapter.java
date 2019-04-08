package zw.org.zvandiri.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.HivSelfTesting;

import java.util.ArrayList;

/**
 * @uthor Tasu Muzinda
 */
public class HivSelfTestingAdapter extends ArrayAdapter<HivSelfTesting> {

    private Context context;
    private ArrayList<HivSelfTesting> list;
    TextView name;

    public HivSelfTestingAdapter(Context context, ArrayList<HivSelfTesting> list){
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
        }
        name = (TextView) newView.findViewById(R.id.adapter_name);
        HivSelfTesting item = list.get(pos);
        name.setText(pos + 1 + "." + item.toString());
        return newView;
    }

    public void onDataSetChanged(ArrayList<HivSelfTesting> list){
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public HivSelfTesting getItem(int position) {
        return list.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }
}
