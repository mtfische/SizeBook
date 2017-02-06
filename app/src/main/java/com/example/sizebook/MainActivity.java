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

    private static final String FILENAME = "file.sav";
    ArrayList<Person> people = new ArrayList<Person>();
    private PeopleAdapter adapter;


/*
    public void deleteUser(View v){
        button field = v.findViewById(R.id.delete);
        int position = getIndex(field.getText().toString());
        people.remove(position);
        v.
        //Gson gson = new Gson();
        //Intent intent = new Intent(this,editUserData.class);
        //String array = gson.toJson(people);
        //intent.putExtra("obj", array);
        //startActivity(intent);
        saveInFile();
        adapter.notifyDataSetChanged();
    }
*/
    public void editUser(View v){
        final EditText namefield = (EditText) v.findViewById(R.id.name);
        int position = getIndex(namefield.getText().toString());
        Gson gson = new Gson();
        Intent intent = new Intent(this, AddUser.class);
        String array = gson.toJson(people);
        intent.putExtra("obj", array);
        startActivity(intent);
        //adapter.notifyDataSetChanged();
    }

    public void addUser(View v)
    {
        Gson gson = new Gson();
        Intent intent = new Intent(this, AddUser.class);
        String array = gson.toJson(people);
        intent.putExtra("obj", array);
        startActivity(intent);
        //adapter.notifyDataSetChanged();
    }

    public int getIndex(String name) {
        for(Person person: people){
            if(person.getName().equals(name)){
                return people.indexOf(person);
            }
        }
        return -1;
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFromFile();
        //adapter = new PeopleAdapter(people, this);
        Gson gson = new Gson();

        Intent entryIntent = getIntent();
        //String page = "";
        String page = entryIntent.getStringExtra("Page");
        if(page == null){page = "none";}
        Log.d("tag", "Page: "+page.toString());
        if(page.equals("add")){
            //String obj = entryIntent.getStringExtra("obj");
            //Log.d("tag", "Person: "+obj.toString());
            //Person person = gson.fromJson(obj,Person.class);
            //Log.d("tag","person from JSON:"+person.getName());
            //people.add(person);
            Log.d("tag","people:"+people.size());
        }


    }

    @Override
    public void onStart(){
        super.onStart();
        loadFromFile();
        final ListView listView;
        adapter = new PeopleAdapter(people, this);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        TextView countField = (TextView) findViewById(R.id.count);
        countField.setText("Number of records: "+Integer.toString(people.size()));

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
            public void toggleVisibility(TextView field){
                if(!field.getText().toString().isEmpty()) {
                    if (field.isShown()) {
                        field.setVisibility(View.GONE);
                    } else {
                        field.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        //adapter.notifyDataSetChanged();

    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-01-24 18:19
            Type listType = new TypeToken<ArrayList<Person>>(){}.getType();
            people = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            people = new ArrayList<Person>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(people, out);
            out.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}

/*
* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete entry");
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        people.remove(positionToRemove);
                        adapter.notifyDataSetChanged();
                    }});
                adb.show();
            }
        });
* */