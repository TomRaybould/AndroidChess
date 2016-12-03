package com.example.miketomkowich.chess72android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class gamestage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamestage);
      //  System.out.println("poop1");
        GridView gridview = (GridView) findViewById(R.id.gridview);
       // System.out.println("poop2");
        gridview.setAdapter(new ImageAdapter(this));
        //System.out.println("poop3");
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(gamestage.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
