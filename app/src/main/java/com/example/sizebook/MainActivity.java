package com.example.sizebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    ArrayList<Person> people = new ArrayList<Person>();






    public void addUser(View view)
    {
        Intent intent = new Intent(this,editUserData.class);
        startActivity(intent);
    }



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Gson gson = new Gson();
        Intent entryIntent = getIntent();

        String page = entryIntent.getStringExtra("Page");
        Log.d("tag", "Page: "+page.toString());
        if(page.equals("add")){
            String obj = entryIntent.getStringExtra("obj");
            Log.d("tag", "Person: "+obj.toString());
            Person person = gson.fromJson(obj,Person.class);
            people.add(person);
            Log.d("tag","people:"+people.toString());

        }

        //Button addButton = (Button) findViewById(R.id.add);
        //oldPersonList = (ListView) findViewById(R.id.oldPersonList);

        /*
        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                String text = bodyText.getText().toString();

                Tweet tweet = new NormalTweet(text);
                tweetList.add(tweet);

                adapter.notifyDataSetChanged();

                saveInFile();

            }
        });
        */


    }


}
