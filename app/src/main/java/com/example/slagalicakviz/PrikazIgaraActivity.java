package com.example.slagalicakviz;



import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

       Button buttonKorakPoKorak = findViewById(R.id.buttonKorak);
        buttonKorakPoKorak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrikazIgaraActivity.this,KorakPoKorakActivity.class);
                startActivity(intent);
            }
        });

        Button buttonAsocijacije = findViewById(R.id.buttonAsocijacije);
        buttonAsocijacije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrikazIgaraActivity.this,Asocijacije.class);
                startActivity(intent);
            }
        });

        Button buttonKoZnaZna = findViewById(R.id.buttonKoZnaZna);
        buttonKoZnaZna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrikazIgaraActivity.this,KoZnaZna.class);
                startActivity(intent);
            }
        });

        Button buttonSpojnice = findViewById(R.id.buttonSpojnice);
        buttonSpojnice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrikazIgaraActivity.this,Spojnice.class);
                startActivity(intent);
            }
        });



    }
}