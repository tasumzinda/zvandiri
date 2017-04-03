package zw.org.zvandiri.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Patient;

import java.util.ArrayList;

/**
 * Created by jackie muzinda on 7/1/2017.
 */
public class TransferPatientToSupportGroupAdapter extends ArrayAdapter<Patient> {

    private Context context;
    private ArrayList<Patient> list;
    TextView name;

    public TransferPatientToSupportGroupAdapter(Context context, ArrayList<Patient> list){
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
        Patient patient = list.get(pos);
        /*if(patient.supportGroup == null){
            name.setText("");
        }else{
            name.setText(patient.supportGroup.name);
        }*/
        return newView;
    }

    public void onDataSetChanged(ArrayList<Patient> list){
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }
}
