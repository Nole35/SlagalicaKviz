package com.example.slagalicakviz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrikazIgaraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prikaz_igara);

        Button button =findViewById(R.id.buttonSkocko);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrikazIgaraActivity.this,SkockoActivity.class);
                startActivity(intent);
            }
        });


        Button buttonMojBroj = findViewById(R.id.buttonMojBroj);
        buttonMojBroj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrikazIgaraActivity.this,MojBrojActivity.class);
                startActivity(intent);
            }
        });
    }
}