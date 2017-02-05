package com.example.sizebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    ArrayList<Person> people = new ArrayList<Person>();






    public void addUser(View view)
    {
        Gson gson = new Gson();
        Intent intent = new Intent(this,editUserData.class);
        String array = gson.toJson(people);
        intent.putExtra("obj", array);
        startActivity(intent);
    }



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFromFile();
        Gson gson = new Gson();
        ListView listView;
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

        listView = (ListView)findViewById(R.id.listView);
        final PeopleAdapter adapter = new PeopleAdapter(people, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this);
                final int positionToRemove = position;
                //v.findViewById(id);
            }
        });
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