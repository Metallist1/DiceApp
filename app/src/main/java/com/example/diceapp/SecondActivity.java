package com.example.diceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private ListView listView;
    ArrayList<String> listOfItems;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_activity);
        setUpHistory();

        findViewById(R.id.btn_clearHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearHistory();
            }
        });

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }
    private void setUpHistory(){
        listOfItems = new ArrayList<String>();

        listView = findViewById(R.id.history_view);

        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                listOfItems);

        listView.setAdapter(arrayAdapter);

        ArrayList<String> historyOfRolls = getIntent().getStringArrayListExtra("EXTRA_HISTORY_DATA");
        listOfItems.addAll(historyOfRolls);

        arrayAdapter.notifyDataSetChanged();

    }

    private void clearHistory(){
        listOfItems.clear();
        arrayAdapter.notifyDataSetChanged();
    }

    private void back(){
        //Intent is for updating data back to main app
        Intent intent = new Intent();
        intent.putExtra("HISTORY_BACK", listOfItems);
        setResult(RESULT_OK, intent);
        finish();
    }
}
