package com.example.kelasasqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kelasasqlite.database.DBController;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class TemanEdit extends AppCompatActivity {
    private TextInputEditText tNama, tTelpon;
    private Button simpanBtn;
    String id, nm, tlp;
    DBController controller = new DBController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        tNama = (TextInputEditText)findViewById(R.id.tiNama);
        tTelpon = (TextInputEditText)findViewById(R.id.tiTelpon);
        simpanBtn = (Button)findViewById(R.id.bSave);

        id = getIntent().getStringExtra("id");
        nm = getIntent().getStringExtra("nama");
        tlp = getIntent().getStringExtra("telepon");

        setTitle("Edit Data");
        tNama.setText(nm);
        tTelpon.setText(tlp);

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tNama.getText().toString().equals("")||tTelpon.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Data belum komplit !", Toast.LENGTH_SHORT).show();
                }else {
                    nm = tNama.getText().toString();
                    tlp = tTelpon.getText().toString();

                    HashMap<String,String> qvalues =  new HashMap<>();
                    qvalues.put("id", id);
                    qvalues.put("nama",nm);
                    qvalues.put("telpon",tlp);

                    controller.updateData(qvalues);
                    callHome1();
                }
            }
        });
    }

    public void callHome1(){
        Intent intent = new Intent(TemanEdit.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}