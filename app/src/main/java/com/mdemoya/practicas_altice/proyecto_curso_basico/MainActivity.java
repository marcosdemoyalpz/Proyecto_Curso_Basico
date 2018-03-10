package com.mdemoya.practicas_altice.proyecto_curso_basico;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent = null;

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_history:
                intent = new Intent(this, HistoryActivity.class);
                break;

            default:
                Toast.makeText(this, "Unknown item clicked!", Toast.LENGTH_LONG).show();
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.interval_activity_button).setOnClickListener(this);
        findViewById(R.id.future_activity_button).setOnClickListener(this);
        findViewById(R.id.past_activity_button).setOnClickListener(this);
        findViewById(R.id.age_activity_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.interval_activity_button:
                intent = new Intent(this, DateIntervalActivity.class);
                break;

            case R.id.future_activity_button:
                intent = new Intent(this, FutureDateActivity.class);
                break;

            case R.id.past_activity_button:
                intent = new Intent(this, PastDateActivity.class);
                break;

            case R.id.age_activity_button:
                intent = new Intent(this, AgeCalculatorActivity.class);
                break;

            default:
                Toast.makeText(this, "Unknown button clicked!", Toast.LENGTH_LONG).show();
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
