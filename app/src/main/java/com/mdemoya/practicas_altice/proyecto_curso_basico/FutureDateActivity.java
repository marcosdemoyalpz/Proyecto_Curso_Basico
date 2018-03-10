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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class FutureDateActivity extends AppCompatActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Button datePickerFuturoFromButton;

    public Calendar dateFrom = Calendar.getInstance();
    public SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yyyy", Locale.US);
    public SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_date);

        datePickerFuturoFromButton = findViewById(R.id.datePickerFuturoButton);

        findViewById(R.id.calcular_futuro_button).setOnClickListener(this);

        dateFrom = Calendar.getInstance();

        datePickerFuturoFromButton.setOnClickListener(v -> {
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

        datePickerFuturoFromButton.setText(dateFormat.format(dateFrom.getTime()));
        CalcularFuturo(dateFrom, 0);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        dateFrom = calendar;
        datePickerFuturoFromButton.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    private void CalcularFuturo(Calendar dateFrom, int days) {
        TextView resultText = findViewById(R.id.result_text_futuro);
        String result = getString(R.string.defaultResultTime);
        Date futureDate = addDays(dateFrom.getTime(), days);
        result = dateFormat.format(futureDate.getTime());
        resultText.setText(result);
    }

    public Date addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
    }

    public Date subtractDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, -days);

        return cal.getTime();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calcular_futuro_button:
                CalcularFuturo(dateFrom, 10);
                break;

            default:
                Toast.makeText(this, "Unknown button clicked!", Toast.LENGTH_LONG).show();
                break;
        }
    }
}