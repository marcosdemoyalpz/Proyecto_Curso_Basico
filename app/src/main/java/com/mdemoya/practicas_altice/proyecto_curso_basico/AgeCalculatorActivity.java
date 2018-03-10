package com.mdemoya.practicas_altice.proyecto_curso_basico;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AgeCalculatorActivity extends AppCompatActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Button datePickerNacimientoFromButton;

    public Calendar dateFrom = Calendar.getInstance();
    public SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yyyy", new Locale("es", "ES"));
    public SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_calculator);

        datePickerNacimientoFromButton = findViewById(R.id.datePickerNacimientoButton);

        findViewById(R.id.calcular_edad_button).setOnClickListener(this);

        dateFrom = Calendar.getInstance();

        datePickerNacimientoFromButton.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(
                            this,
                            this,
                            dateFrom.get(Calendar.YEAR),
                            dateFrom.get(Calendar.MONTH),
                            dateFrom.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        Calendar nullTime = Calendar.getInstance();
        nullTime.set(Calendar.HOUR_OF_DAY, 0);
        nullTime.set(Calendar.MINUTE, 0);


        datePickerNacimientoFromButton.setText(dateFormat.format(dateFrom.getTime()));
        CalcularNacimiento(dateFrom);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        dateFrom = calendar;
        datePickerNacimientoFromButton.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    private void CalcularNacimiento(Calendar nacimiento) {
        Calendar today = Calendar.getInstance();

        int currentYear = today.get(Calendar.YEAR);
        int nacimientoYear = nacimiento.get(Calendar.YEAR);

        int edad = currentYear - nacimientoYear;

        // if dob is month or day is behind today's month or day
        // reduce age by 1
        int curMonth = today.get(Calendar.MONTH);
        int dobMonth = nacimiento.get(Calendar.MONTH);
        if (dobMonth > curMonth) { // this year can't be counted!
            edad--;
        } else if (dobMonth == curMonth) { // same month? check for day
            int curDay = today.get(Calendar.DAY_OF_MONTH);
            int dobDay = nacimiento.get(Calendar.DAY_OF_MONTH);
            if (dobDay > curDay) { // this year can't be counted!
                edad--;
            }
        }

        TextView resultText = findViewById(R.id.result_text_nacimiento);
        String result = getString(R.string.defaultResultTime);

        result = edad + " " + getString(R.string.yearsSuffix);

        resultText.setText(result);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calcular_edad_button:
                CalcularNacimiento(dateFrom);
                break;

            default:
                Toast.makeText(this, "Unknown button clicked!", Toast.LENGTH_LONG).show();
                break;
        }
    }
}