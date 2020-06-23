package com.nathankruiz3.grocerylist.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.nathankruiz3.grocerylist.R;

public class DetailsActivity extends AppCompatActivity {

    private TextView itemName, quantity, dateAdded;
    private int groceryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        itemName = (TextView) findViewById(R.id.itemNameDet);
        quantity = (TextView) findViewById(R.id.qtyDet);
        dateAdded = (TextView) findViewById(R.id.dateAddedDet);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            itemName.setText(bundle.getString("name"));
            quantity.setText(bundle.getString("quantity"));
            dateAdded.setText(bundle.getString("date"));
            groceryID = bundle.getInt("id");
        }
    }
}