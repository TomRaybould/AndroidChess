package com.example.miketomkowich.chess72android;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tomraybould_miketomkowich.chess72android.R;

public class Completed_Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed__game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button yes_record = (Button) findViewById(R.id.yes_record);
        Button no_record = (Button) findViewById(R.id.no_record);

        yes_record.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Dialog dialog = new Dialog(Completed_Game.this);
                //dialog.setTitle("Add a game");
                dialog.setContentView(R.layout.dialog_add_game);
                dialog.show();

                final EditText name1 = (EditText)dialog.findViewById(R.id.name);
                // final EditText date1 = (EditText)dialog.findViewById(R.id.date); for later
                Button submit = (Button)dialog.findViewById(R.id.submit);
                Button cancel = (Button)dialog.findViewById(R.id.cancel);

                submit.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Toast.makeText(getApplicationContext(),"Hit submit", Toast.LENGTH_SHORT).show();
                        game_memory.getGames().add(name1.getText().toString());
                        dialog.cancel();
                        Intent intent = new Intent(Completed_Game.this, Home_Screen.class);
                        startActivity(intent);
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Toast.makeText(getApplicationContext(),"Hit cancel", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

            }
        });
        no_record.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Completed_Game.this, Home_Screen.class);
                startActivity(intent);

            }
        });

    }

}
