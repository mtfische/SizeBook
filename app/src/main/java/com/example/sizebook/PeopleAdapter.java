package com.example.sizebook;

import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.http.conn.ConnectTimeoutException;

import java.util.ArrayList;

/**
 * Created by Malcolm on 2017-02-03.
 */

public class PeopleAdapter extends ArrayAdapter<Person> {

    private ArrayList<Person> people;
    private Context context;

    public PeopleAdapter(ArrayList<Person> people, Context context) {
        super(context,R.layout.person_layout, people);
        this.people = people;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //LayoutInflater inflater = LayoutInflater.from(context);
            //RelativeLayout inflater = (RelativeLayout) LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.person_layout, parent, false);
        }
        // Now we can fill the layout with the right values
        TextView tv = (TextView) convertView.findViewById(R.id.name);
        TextView bustField = (TextView) convertView.findViewById(R.id.bust);
        TextView chestField = (TextView) convertView.findViewById(R.id.chest);
        TextView waistField = (TextView) convertView.findViewById(R.id.waist);
        TextView inseamField = (TextView) convertView.findViewById(R.id.inseam);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        Person p = people.get(position);

        //Log.d("tag", p.toString());
        //Log.d("tag", toString().valueOf(p.getChest()));


        tv.setText(p.getName());
        if(p.getBust() != 0){bustField.setText("Bust Size: " + p.getBust()+"\u2033");}
        if(p.getChest() != 0){chestField.setText("Chest Size: " + p.getBust()+"\u2033");}
        if(p.getWaist() != 0){waistField.setText("Waist Size: " + p.getWaist()+"\u2033");}
        if(p.getInseam() != 0){inseamField.setText("Inseam Size: " + p.getBust()+"\u2033");}
        date.setText("Last updated: " + p.getDate().toString());

        return convertView;
    }
}
