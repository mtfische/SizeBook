package com.example.sizebook;

import android.content.Context;

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

/**
 * Created by Malcolm on 2017-02-05.
 */

public class IOhandler {
    private static final String FILENAME = "file.sav";
    private Context ctx;

    //constructor gets context
    public IOhandler(Context context){
        this.ctx = context;
    };

    //loads arraylist<person> from file
    public ArrayList<Person> loadFromFile() {
        ArrayList<Person> people;
        try {
            FileInputStream fis = ctx.openFileInput(FILENAME);
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
        return people;
    }

    //saves arrayList<person> to file
    public void saveInFile(ArrayList<Person> people) {
        try {
            FileOutputStream fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
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

