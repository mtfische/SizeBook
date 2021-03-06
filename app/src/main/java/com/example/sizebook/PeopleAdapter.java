package com.example.sizebook;

import android.app.Activity;
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
    private Context mContext;

    public PeopleAdapter(ArrayList<Person> people, Context context) {
        super(context,R.layout.person_layout, people);
        this.people = people;
        this.mContext = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        // check if convertView is null
        final ViewGroup parentView = parent;
        if (convertView == null) {
            // inflate the new layout
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.person_layout, parent, false);
        }
        final int editPosition = position;
        // Fill the layout with the right values
        TextView tv = (TextView) convertView.findViewById(R.id.name);
        TextView bustField = (TextView) convertView.findViewById(R.id.bust);
        TextView chestField = (TextView) convertView.findViewById(R.id.chest);
        TextView waistField = (TextView) convertView.findViewById(R.id.waist);
        TextView inseamField = (TextView) convertView.findViewById(R.id.inseam);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        Person p = people.get(position);

        Button deleteBtn = (Button) convertView.findViewById(R.id.delete);
        Button editBtn = (Button) convertView.findViewById(R.id.edit);

        editBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditUser.class);
                Gson gson = new Gson();
                intent.putExtra("obj",gson.toJson(people));
                intent.putExtra("pos", position);
                mContext.startActivity(intent);
                PeopleAdapter.this.notifyDataSetChanged();

            }
        });
        //listener for the delete button
        deleteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //delete person from arraylist
                people.remove(editPosition);
                IOhandler file = new IOhandler(mContext);
                file.saveInFile(people);

                View main = ((Activity)mContext).getWindow().getDecorView().findViewById(android.R.id.content);
                TextView count =  (TextView) main.findViewById(R.id.count);
                count.setText("Number of records: "+people.size());
                PeopleAdapter.this.notifyDataSetChanged();


            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);

        //set field values if set
        tv.setText(p.getName());
        if(p.getBust() != 0){bustField.setText("Bust Size: " + String.format("%.1f",p.getBust())+"\u2033");}
        if(p.getChest() != 0){chestField.setText("Chest Size: " + String.format("%.1f",p.getChest())+"\u2033");}
        if(p.getWaist() != 0){waistField.setText("Waist Size: " + String.format("%.1f",p.getWaist())+"\u2033");}
        if(p.getInseam() != 0){inseamField.setText("Inseam Size: " + String.format("%.1f",p.getInseam())+"\u2033");}
        date.setText("Last updated: " + sdf.format(p.getDate()));

        return convertView;
    }
}
