package com.dhimandasgupta.datepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dhimandasgupta.datepicker.DatePicker.OnDateSelectedListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DatePicker datePicker = (DatePicker)
                findViewById(R.id.activity_main_date_picker);
        if (datePicker != null) {
            datePicker.setDateSelectedListener(new OnDateSelectedListener() {
                @Override
                public void onDateSelected(int dayOfMonth, int monthIndex, int year) {
                    Toast.makeText(getApplicationContext(),
                            dayOfMonth + "/" +
                                    monthIndex + "/" +
                                    year,
                            Toast.LENGTH_LONG).
                            show();
                }
            });
        }
    }
}
