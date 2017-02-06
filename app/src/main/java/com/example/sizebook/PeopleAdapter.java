package com.example.sizebook;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.conn.ConnectTimeoutException;

import java.text.SimpleDateFormat;
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

    public View getView(final int position, View convertView, ViewGroup parent) {

        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //LayoutInflater inflater = LayoutInflater.from(context);
            //RelativeLayout inflater = (RelativeLayout) LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.person_layout, parent, false);
        }
        final int editPosition = position;
        // Now we can fill the layout with the right values
        TextView tv = (TextView) convertView.findViewById(R.id.name);
        TextView bustField = (TextView) convertView.findViewById(R.id.bust);
        TextView chestField = (TextView) convertView.findViewById(R.id.chest);
        TextView waistField = (TextView) convertView.findViewById(R.id.waist);
        TextView inseamField = (TextView) convertView.findViewById(R.id.inseam);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        Person p = people.get(position);
        Button deleteBtn = (Button) convertView.findViewById(R.id.delete);
        //deleteBtn.setTag(position);
        Button editBtn = (Button) convertView.findViewById(R.id.edit);

        editBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditUser.class);
                Gson gson = new Gson();
                intent.putExtra("obj",gson.toJson(people));
                intent.putExtra("pos", position);
                context.startActivity(intent);
                PeopleAdapter.this.notifyDataSetChanged();

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                people.remove(editPosition);
                IOhandler file = new IOhandler(context);
                file.saveInFile(people);
                PeopleAdapter.this.notifyDataSetChanged();

            }
        });
        //Log.d("tag", p.toString());
        //Log.d("tag", toString().valueOf(p.getChest()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);

        tv.setText(p.getName());
        if(p.getBust() != 0){bustField.setText("Bust Size: " + p.getBust()+"\u2033");}
        if(p.getChest() != 0){chestField.setText("Chest Size: " + p.getBust()+"\u2033");}
        if(p.getWaist() != 0){waistField.setText("Waist Size: " + p.getWaist()+"\u2033");}
        if(p.getInseam() != 0){inseamField.setText("Inseam Size: " + p.getBust()+"\u2033");}
        date.setText("Last updated: " + sdf.format(p.getDate()));

        return convertView;
    }
}
