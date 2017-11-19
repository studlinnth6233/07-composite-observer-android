package de.fhro.inf.prg3.a07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.danlew.android.joda.JodaTimeAndroid;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JodaTimeAndroid.init(getApplicationContext());

        setContentView(R.layout.activity_main);
    }
}
