package zw.org.zvandiri.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.MedicalHist;

import java.util.ArrayList;

/**
 * Created by User on 12/19/2016.
 */
public class MedicalHistAdapter extends ArrayAdapter<MedicalHist> {

    private Context context;
    private ArrayList<MedicalHist> list;
    TextView name;

    public MedicalHistAdapter(Context context, ArrayList<MedicalHist> list){
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
        MedicalHist medicalHist = list.get(pos);
        name.setText(pos + 1 + ". " + medicalHist);
        return newView;
    }

    public void onDataSetChanged(ArrayList<MedicalHist> list){
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }
}
