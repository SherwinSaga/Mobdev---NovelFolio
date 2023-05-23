package com.example.novelfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Ei_port extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView tf1;
    TextView tf2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ei_port);

        tf1 = findViewById(R.id.tfExport1);
        tf2 = findViewById(R.id.tfImport1);


        tf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download();
            }
        });

        tf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImport();
            }
        });

    }

    private void download() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore.getInstance().collection("users").document(user.getUid()).collection("favorites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String text = "";
                    int counter = 0;
                    int size = task.getResult().size();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String appText = document.getId() + "-" + document.getLong("currChapterNum").toString();
                        text += appText;
                        if (counter < size - 1) {
                            text += ", ";
                        }

                        counter++;
                    }

                    // kani na part kay kung unsay isulod sa file na ewrite
                    FileOutputStream fos = null;

                    try {
                        fos = openFileOutput("export.txt", MODE_PRIVATE);  //export.txt mao ni file name
                        fos.write(text.getBytes());
                        Toast.makeText(Ei_port.this, "Saved to " +getFilesDir(), Toast.LENGTH_SHORT).show();
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
            }
        });

    }

    private void getImport(){
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

            String[] favs = sb.toString().split(", ");

            CollectionReference favorites = db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("favorites");

            for (String fav : favs) {
                String[] docIdAndCurrChapNum = fav.split("-");
                String novelDocId = docIdAndCurrChapNum[0];
                String currChapNum = docIdAndCurrChapNum[1].trim();

                DocumentReference docRef = favorites.document(novelDocId);

                HashMap<String, Object> updates = new HashMap<>();
                updates.put("currChapterNum", Integer.parseInt(currChapNum));
                docRef.set(updates, SetOptions.merge())
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(Ei_port.this, "Successfully imported your favorites.", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(Ei_port.this, "Failed to import your favorites.", Toast.LENGTH_SHORT).show();
                        });
            }


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