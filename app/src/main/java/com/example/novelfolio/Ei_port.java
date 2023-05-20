package com.example.novelfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Ei_port extends AppCompatActivity {
    private ArrayList<String> stringList = new ArrayList<>();

    TextView tf1;
    TextView tf2;
    TextView tf3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ei_port);

        tf1 = findViewById(R.id.tfExport1);
        tf2 = findViewById(R.id.tfImport1);


        tf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download(view);
            }
        });

        tf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImport(view);
            }
        });

    }

    private void download(View v) {
        String text = "TEST FOR EXPORT LIBRARY";  // kani na part kay kung unsay isulod sa file na ewrite
        FileOutputStream fos = null;

        try {
            fos = openFileOutput("export.txt", MODE_PRIVATE);  //export.txt mao ni file name
            fos.write(text.getBytes());
            Toast.makeText(this, "Saved to " +getFilesDir(), Toast.LENGTH_SHORT).show();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {
            if(fos != null){
                try{
                    fos.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

    }

    private void getImport(View view){
        //tf3 = findViewById(R.id.testing);          uncomment ni para test if mu gana ang gi export ug import   ug dapat sad e unccomment ang naa sa ei_port.xml
        FileInputStream fis = null;

        try {
            fis = openFileInput("export.txt");   //file name na iyang pangitaon
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            StringBuilder sb = new StringBuilder();
            String text;

            while((text = br.readLine()) != null){
                sb.append(text).append("\n");
            }
            //tf3.setText(sb.toString());          uncomment ni para test if mu gana ang gi export ug import
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(fis != null){
                try{
                    fis.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }


}