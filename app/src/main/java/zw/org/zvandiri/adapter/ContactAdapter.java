package zw.org.zvandiri.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Contact;

import java.util.ArrayList;

/**
 * Created by jackie muzinda on 10/1/2017.
 */
public class ContactAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private ArrayList<Contact> list;
    TextView name;

    public ContactAdapter(Context context, ArrayList<Contact> list){
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
        Contact contact = list.get(position);
        if(contact == null){
            name.setText(" ");
        }else{
            name.setText(position + 1 + ". " + contact);
        }

        return newView;
    }

    public void onDataSetChanged(ArrayList<Contact> list){
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }
}
