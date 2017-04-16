package com.rmathur.firebasedatastore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    // Firebase
    DatabaseReference myRef;

    // UI
    Button btnIncrementCounter;
    TextView txtCounterValue;

    // Globals
    final String TAG = "MainActivity";
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIncrementCounter = (Button) findViewById(R.id.btnIncrement);
        txtCounterValue = (TextView) findViewById(R.id.txtCounterValue);

        // Set the initial counter text to the initial value of counter
        txtCounterValue.setText(String.valueOf(counter));

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("counter");
        myRef.setValue(counter);

        // set up an onclicklistener to increment the value of counter each button press,
        // and save it to the "counter" key we got a reference to before
        btnIncrementCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                myRef.setValue(counter);
            }
        });

        // Read the "counter" key/value from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                // get the value of the key from the data snapshot parameter
                int value = dataSnapshot.getValue(Integer.class);
                Log.d(TAG, "Value is: " + value);

                // update the counter text with the new value of the counter
                txtCounterValue.setText(String.valueOf(value));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
