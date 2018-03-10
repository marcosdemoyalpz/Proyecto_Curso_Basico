package com.mdemoya.practicas_altice.proyecto_curso_basico;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateIntervalActivity extends AppCompatActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Button datePickerIntervalFromButton, timePickerIntervalFromButton,
            datePickerIntervalToButton, timePickerIntervalToButton;

    int hoursFrom, hoursTo, minutesFrom, minutesTo = 0;
    boolean isDateFrom = true;
    public Calendar currentDate, dateFrom, dateTo = Calendar.getInstance();
    public SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yyyy", new Locale("es", "ES"));
    public SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_interval);

        datePickerIntervalFromButton = findViewById(R.id.datePickerIntervalFromButton);
        timePickerIntervalFromButton = findViewById(R.id.timePickerIntervalFromButton);
        datePickerIntervalToButton = findViewById(R.id.datePickerIntervalToButton);
        timePickerIntervalToButton = findViewById(R.id.timePickerIntervalToButton);

        findViewById(R.id.calcular_intervalo_button).setOnClickListener(this);

        hoursFrom = hoursTo = 0;
        dateTo = dateFrom = currentDate = Calendar.getInstance();

        datePickerIntervalFromButton.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(
                            this,
                            this,
                            currentDate.get(Calendar.YEAR),
                            currentDate.get(Calendar.MONTH),
                            currentDate.get(Calendar.DAY_OF_MONTH));
            isDateFrom = true;
            datePickerDialog.show();
        });

        datePickerIntervalToButton.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(
                            this,
                            this,
                            currentDate.get(Calendar.YEAR),
                            currentDate.get(Calendar.MONTH),
                            currentDate.get(Calendar.DAY_OF_MONTH));
            isDateFrom = false;
            datePickerDialog.show();
        });

        Calendar nullTime = Calendar.getInstance();
        nullTime.set(Calendar.HOUR_OF_DAY, 0);
        nullTime.set(Calendar.MINUTE, 0);

        timePickerIntervalFromButton.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    this,
                    0,
                    0,
                    false);
            isDateFrom = true;
            timePickerDialog.show();
        });
        timePickerIntervalToButton.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    this,
                    0,
                    0,
                    false);
            isDateFrom = false;
            timePickerDialog.show();
        });

        datePickerIntervalFromButton.setText(dateFormat.format(currentDate.getTime()));
        datePickerIntervalToButton.setText(dateFormat.format(currentDate.getTime()));
        timePickerIntervalFromButton.setText(timeFormat.format(nullTime.getTime()));
        timePickerIntervalToButton.setText(timeFormat.format(nullTime.getTime()));
        CalcularIntervalo(findViewById(R.id.calcular_intervalo_button));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        if (isDateFrom == true) {
            dateFrom = calendar;
            datePickerIntervalFromButton.setText(dateFormat.format(calendar.getTime()));
        } else {
            dateTo = calendar;
            datePickerIntervalToButton.setText(dateFormat.format(calendar.getTime()));
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        if (isDateFrom == true) {
            hoursFrom = hourOfDay;
            minutesFrom = minute;
            timePickerIntervalFromButton.setText(timeFormat.format(calendar.getTime()));
        } else {
            hoursTo = hourOfDay;
            minutesTo = minute;
            timePickerIntervalToButton.setText(timeFormat.format(calendar.getTime()));
        }
    }

    private void CalcularIntervalo(View view) {
        switch (view.getId()) {
            case R.id.calcular_intervalo_button:
                long seconds = ((dateTo.getTimeInMillis()) - (dateFrom.getTimeInMillis())) / 1000;
                long hours = (seconds / 3600);
                hours = hours + (hoursTo - hoursFrom);
                seconds = (hours * 3600) + ((minutesTo - minutesFrom) * 60);
                long millisecs = seconds * 1000;

                seconds = millisecs / 1000 % 60;
                long minutes = millisecs / (60 * 1000) % 60;
                hours = millisecs / (60 * 60 * 1000) % 24;
                long days = millisecs / (24 * 60 * 60 * 1000);

                TextView resultText = findViewById(R.id.result_text_interval);
                String result = getString(R.string.defaultResultTime);

//                result = years + " " + getString(R.string.yearsSuffix)
//                        + " " + months + " " + getString(R.string.monthsSuffix)
//                        + "\n" + days + " " + getString(R.string.daysSuffix)
//                        + " " + hours + " " + getString(R.string.hoursSuffix);

                result = days + " " + getString(R.string.daysSuffix)
                        + ", " + hours + " " + getString(R.string.hoursSuffix)
                        + ",\n" + minutes + " " + getString(R.string.minutesSuffix);
//                        + ", " + seconds + " " + getString(R.string.secondsSuffix);

                resultText.setText(result);
                break;

            default:
                Toast.makeText(this, "Unknown button clicked!", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        CalcularIntervalo(view);
    }
}
