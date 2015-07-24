package com.ramos.julian.memoria;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;


public class MainActivity extends ActionBarActivity {

    Button record;

    String q1Answer="",q2Answer="",q3Answer="";
    String DIRECTORY="/sparks";
    String filename = Environment.getExternalStorageDirectory()+DIRECTORY+"/responses.csv";
    File file;
    int q4Answer=0;
    boolean recordState=false;

    PrintWriter pFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        file = new File(Environment.getExternalStorageDirectory()+DIRECTORY);
        if (!file.exists()){
            file.mkdirs();
        }

        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.editText);

        String[] countries = getResources().getStringArray(R.array.tookMedicine);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        textView.setAdapter(adapter);

        record =(Button)findViewById(R.id.button);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                q1Answer=((EditText)findViewById(R.id.editText)).getText().toString();
                if (((RadioButton)findViewById(R.id.radioButton)).isChecked()  ){
                    q2Answer="yes";
                }
                else{
                    q2Answer="no";
                }
                q3Answer=((EditText)findViewById(R.id.editText2)).getText().toString();
                q4Answer=((SeekBar)findViewById(R.id.seekBar)).getProgress();

                if (!(q1Answer.equalsIgnoreCase("")&&
                    q2Answer.equalsIgnoreCase("")    )){
                    if (q2Answer.equalsIgnoreCase("yes")){
                        if (!q3Answer.equalsIgnoreCase("")){
                            recordState=true;
                        }
                        else{
                            (new Toast(getApplicationContext())).makeText(getApplicationContext(),
                                    "The third question is empty",Toast.LENGTH_SHORT).show();
                            recordState=false;
                        }
                    }
                    else if (q2Answer.equalsIgnoreCase("no")){
                        recordState=true;
                    }
                    else{
                        (new Toast(getApplicationContext())).makeText(getApplicationContext(),
                                "The second question is empty",Toast.LENGTH_SHORT).show();
                        recordState=false;

                    }

                }
                else{
                    (new Toast(getApplicationContext())).makeText(getApplicationContext(),
                            "The first question is empty",Toast.LENGTH_SHORT).show();
                    recordState=false;
                }


                if (recordState){
                    try {
                        pFile= new PrintWriter(new FileOutputStream(filename,true));
                        pFile.println(String.format("%s,%s,%s,%s,%d",Long.toString(System.currentTimeMillis()),q1Answer,q2Answer,q3Answer,q4Answer));
                        pFile.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    (new Toast(getApplicationContext())).makeText(getApplicationContext(),
                            "Thanks for your answer",Toast.LENGTH_SHORT).show();
                    recordState=false;
                    finish();

                }


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
