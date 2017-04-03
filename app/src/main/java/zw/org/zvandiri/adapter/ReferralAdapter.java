package zw.org.zvandiri.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Referral;
import zw.org.zvandiri.business.domain.Referral;

import java.util.ArrayList;

/**
 * Created by jackie muzinda on 10/1/2017.
 */
public class ReferralAdapter extends ArrayAdapter<Referral> {

    private Context context;
    private ArrayList<Referral> list;
    TextView name;

    public ReferralAdapter(Context context, ArrayList<Referral> list){
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
        Referral referral = list.get(pos);
        name.setText(pos + 1 + ". " + referral);
        return newView;
    }

    public void onDataSetChanged(ArrayList<Referral> list){
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }
}
