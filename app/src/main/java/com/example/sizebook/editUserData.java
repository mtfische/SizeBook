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
import java.util.ArrayList;

import static com.example.sizebook.R.id.listView;

public class editUserData extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    Gson gson = new Gson();
    ArrayList<Person> people;

    public Person packageData()
    {
        int[] floatIds = {R.id.neck, R.id.bust, R.id.chest, R.id.waist, R.id.hip, R.id.inseam};
        float[] floatRes = new float[6];
        int[] stringIdS = {R.id.name, R.id.comment};
        int i = 0;
        boolean errorFlag = false;

        for (int id : floatIds){
            try{
                final EditText field = (EditText) findViewById(id);
                float num = parseFloat(field, field.getText().toString());
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
        String comment = "";
        try {
            final EditText nameField = (EditText) findViewById(R.id.name);
            name = nameField.getText().toString();
            if(name == null || name.isEmpty()){
                throw new FieldException("Name Field is required",nameField);
            }
            else if(name.length() > 26){
                throw new FieldException("Name should be at most 26 characters",nameField);
            }

        }catch (FieldException e){
            Log.d("tag", "caught exception");
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

            if (!errorFlag) {
                int j;
                for(j = 0; j<6; j++) {
                    Log.d("tag", "values: " + floatRes[j]);
                }
                Person form = new Person(name);
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
            //Log.d("tag", "name blank");
            return null;
    }

    public float parseFloat(EditText field, String token) throws FieldException {
        try{
            //Log.d("tag", "parsing  float");
            if(token.isEmpty()){return 0;}
            float input = Float.parseFloat(token);
            if (input < 0){
                throw new FieldException("Field must be greater than zero",field);
            }
            return input;
        }
        catch(NumberFormatException e){
            throw new FieldException("Field is incorrectly assigned, Must be a decimal number greater than 0.",field);
        }
    }
    public void add(View view){

        Intent intent = new Intent(this, MainActivity.class);
        Log.d("tag", "packaging");
        Person temp = packageData();
        //Log.d("tag", "temp:"+temp.getName());

        if(temp != null) {
            Log.d("tag", "Person: "+temp.toString());
            people.add(temp);
            saveInFile();
            //intent.putExtra("obj", this.packageData());
            intent.putExtra("Page", "add");
            startActivity(intent);
        }
        //Log.d("tag", "temp was null");
    }

    public void cancel(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Page", "Cancel");
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_data);
        Intent entryIntent = getIntent();
        String obj = entryIntent.getStringExtra("obj");
        Log.d("tag", "Json people: "+obj);
        Type listType = new TypeToken<ArrayList<Person>>(){}.getType();
        people = gson.fromJson(obj, listType);
        Log.d("tag", "Person: "+people.toString());
        ViewGroup entrylayout = (ViewGroup) findViewById(R.id.ScrollView);
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
