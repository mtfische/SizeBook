package com.example.sizebook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.sizebook.R.id.date;
import static com.example.sizebook.R.id.listView;
import static java.lang.Math.round;

public class AddUser extends AppCompatActivity {
    private Gson gson = new Gson();
    private ArrayList<Person> people;
    private IOhandler saveHandler = new IOhandler(this);

    //parse the form fields
    public Person packageData()
    {
        int[] floatIds = {R.id.neck, R.id.bust, R.id.chest, R.id.waist, R.id.hip, R.id.inseam};
        Double[] floatRes = new Double[6];
        int i = 0;
        boolean errorFlag = false;

        //loop through the double fields
        for (int id : floatIds){
            try{
                final EditText field = (EditText) findViewById(id);
                Double num = parseDouble(field, field.getText().toString());
                floatRes[i] = num;

            }
            catch(FieldException e) {
                Log.d("tag", "caught exception");
                EditText field = (EditText) findViewById(e.getField().getId());
                field.setError(e.getMessage());
                errorFlag = true;
            }
            i++;
        }
        String name = "";
        Date date = new Date();
        String comment = "";
        try {
            final EditText nameField = (EditText) findViewById(R.id.name);
            name = nameField.getText().toString().trim();
            if(name == null || name.isEmpty()){
                throw new FieldException("Name Field is required",nameField);
            }
            else if(name.length() > 26){
                throw new FieldException("Name should be at most 26 characters",nameField);
            }

        }catch (FieldException e){
            EditText field = (EditText) findViewById(e.getField().getId());
            field.setError(e.getMessage());
            errorFlag = true;
        }

        try {
            final EditText dateField = (EditText) findViewById(R.id.date);
            date = parseDate(dateField, dateField.getText().toString());
        }
        catch (FieldException e){
            EditText field = (EditText) findViewById(e.getField().getId());
            field.setError(e.getMessage());
            errorFlag = true;
        }


        try {
            final EditText commentField = (EditText) findViewById(R.id.comment);
            comment = commentField.getText().toString();
            if(comment.length() > 100){
                throw new FieldException("Comment too long (MAX 100 Characters)",commentField);
            }
        }
        catch (FieldException e){
            //Log.d("tag", "caught exception");
            EditText field = (EditText) findViewById(e.getField().getId());
            field.setError(e.getMessage());
            errorFlag = true;
        }
        //if error dont create a person
        if (!errorFlag) {
            int j;
            for(j = 0; j<6; j++) {
                Log.d("tag", "values: " + floatRes[j]);
            }
            Person form = new Person(name);
            form.setDate(date);
            form.setNeck(floatRes[0]);
            form.setBust(floatRes[1]);
            form.setChest(floatRes[2]);
            form.setWaist(floatRes[3]);
            form.setHip(floatRes[4]);
            form.setInseam(floatRes[5]);
            form.setComment(comment);
            Log.d("tag", "person complete");
            return form;
        }
        return null;
    }
    //Validates Double fields
    private Double parseDouble(EditText field, String token) throws FieldException {
        try{
            //Log.d("tag", "parsing  float");
            if(token.isEmpty()){return 0.0;}
            Double input = Double.parseDouble(token);
            if (input < 0){
                throw new FieldException("Field must be greater than zero",field);
            }
            return input;
        }
        catch(NumberFormatException e){
            throw new FieldException("Field is incorrectly assigned, Must be a decimal number greater than 0.",field);
        }
    }
    //validates date field
    //referenced https://www.mkyong.com/java/how-to-check-if-date-is-valid-in-java/
    private Date parseDate(EditText field, String dateStr) throws FieldException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        if(dateStr == null || dateStr.isEmpty()){return new Date();}
        try {
            final EditText dateField = (EditText) findViewById(date);
            Date date = sdf.parse(dateStr);
            return date;

        } catch (ParseException e) {
            throw new FieldException("Invalid format. Must have form yyy-mm-dd",field);
        }
    }

    //onclick of add button
    public void add(View view){

        Person temp = packageData();

        if(temp != null) {
            people.add(temp);
            saveHandler.saveInFile(people);
            finish();
        }
    }
    //onclick of cancel button
    public void cancel(View view)
    {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        //Get the people obj
        Intent entryIntent = getIntent();
        String obj = entryIntent.getStringExtra("obj");
        Type listType = new TypeToken<ArrayList<Person>>(){}.getType();
        people = gson.fromJson(obj, listType);

        ViewGroup entrylayout = (ViewGroup) findViewById(R.id.ScrollView);
    }
}

