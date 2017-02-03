package com.example.sizebook;

import android.content.ComponentName;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
            convertView = inflater.inflate(R.layout.person_layout, parent, false);
        }
// Now we can fill the layout with the right values
        TextView tv = (TextView) convertView.findViewById(R.id.name);
        TextView distView = (TextView) convertView.findViewById(R.id.dist);
        Person p = people.get(position);

        tv.setText(p.getName());
        distView.setText("" + p.getName());

        return convertView;
    }
}
