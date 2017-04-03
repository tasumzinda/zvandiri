package zw.org.zvandiri.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;

import java.util.ArrayList;

/**
 * Created by User on 12/25/2016.
 */
public class PatientChangeEventAdapter extends ArrayAdapter<PatientChangeEvent> {

    private Context context;
    private ArrayList<PatientChangeEvent> list;
    TextView name;

    public PatientChangeEventAdapter(Context context, ArrayList<PatientChangeEvent> list){
        super(context, R.layout.list_item, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        View newView = convertView;
        if(newView == null){
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            newView = layoutInflater.inflate(R.layout.list_item, null);
            name = (TextView) newView.findViewById(R.id.adapter_name);
        }
        PatientChangeEvent patientChangeEvent = list.get(pos);
        name.setText(pos + 1 + ". " + patientChangeEvent.getName());
        return newView;
    }

    public void onDataSetChanged(ArrayList<PatientChangeEvent> list){
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }
}
