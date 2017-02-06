package com.example.sizebook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {

    //private static final String FILENAME = "file.sav";
    private ArrayList<Person> people = new ArrayList<Person>();
    private IOhandler loadHandler = new IOhandler(this);
    private PeopleAdapter adapter;

    public void addUser(View v) {
        Gson gson = new Gson();
        Intent intent = new Intent(this, AddUser.class);
        String array = gson.toJson(people);
        intent.putExtra("obj", array);
        startActivity(intent);
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        people = loadHandler.loadFromFile();
        //loadFromFile();
    }

    @Override
    public void onStart() {
        super.onStart();
        //IOhandler loadHandler = new IOhandler(this);
        people = loadHandler.loadFromFile();
        //loadFromFile();

        //initialize adapter
        final ListView listView;
        adapter = new PeopleAdapter(people, this);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        TextView countField = (TextView) findViewById(R.id.count);
        countField.setText("Number of records: " + Integer.toString(people.size()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                TextView bustField = (TextView) v.findViewById(R.id.bust);
                toggleVisibility(bustField);
                TextView chestField = (TextView) v.findViewById(R.id.chest);
                toggleVisibility(chestField);
                TextView waistField = (TextView) v.findViewById(R.id.waist);
                toggleVisibility(waistField);
                TextView inseamField = (TextView) v.findViewById(R.id.inseam);
                toggleVisibility(inseamField);


            }

            public void toggleVisibility(TextView field) {
                if (!field.getText().toString().isEmpty()) {
                    if (field.isShown()) {
                        field.setVisibility(View.GONE);
                    } else {
                        field.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
}