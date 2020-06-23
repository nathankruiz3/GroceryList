package com.nathankruiz3.grocerylist.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nathankruiz3.grocerylist.Data.DatabaseHandler;
import com.nathankruiz3.grocerylist.Model.Grocery;
import com.nathankruiz3.grocerylist.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private  AlertDialog.Builder dialogBuilder;
    private  AlertDialog dialog;
    private  EditText groceryItem, groceryQty;
    private  Button saveButton;
    private  DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        byPassActivity();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
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

    private void createPopupDialog() {

        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        groceryItem = (EditText) view.findViewById(R.id.groceryItem);
        groceryQty = (EditText) view.findViewById(R.id.groceryQty);
        saveButton = (Button) view.findViewById(R.id.saveButton);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Todo: Save to db

                // Todo: Go to next screen
                if (!groceryItem.getText().toString().isEmpty()
                        && !groceryQty.getText().toString().isEmpty()) {
                    saveGroceryToDB(view);
                }

            }
        });

    }

    private void saveGroceryToDB(View v) {
        Grocery grocery = new Grocery();
        String newGrocery = groceryItem.getText().toString();
        String newGroceryQuantity = groceryQty.getText().toString();

        grocery.setName(newGrocery);
        grocery.setQuantity(newGroceryQuantity);
        grocery.setId(db.getGroceriesCount());

        // Save grocery to DB
        db.addGroceryItem(grocery);

        Snackbar.make(v, "Item Saved!", Snackbar.LENGTH_LONG).show();

//        Log.d("Item Added ID: ", String.valueOf(db.getGroceriesCount()));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                // start new activity
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        }, 1200);
    }

    public void byPassActivity() {
        if(db.getGroceriesCount() > 0) {

            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }
    }
}