package com.mdemoya.practicas_altice.proyecto_curso_basico;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class FutureDateActivity extends AppCompatActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Button datePickerFuturoFromButton;
    EditText daysToAdd;
    int days = 0;

    public Calendar dateFrom = Calendar.getInstance();
    public SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yyyy", new Locale("es", "ES"));
    public SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_date);

        datePickerFuturoFromButton = findViewById(R.id.datePickerFuturoButton);
        daysToAdd = findViewById(R.id.daysToAdd);

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

        daysToAdd.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        CalcularFuturo(dateFrom, GetDays());
                        return true;
                    default:
                        break;
                }
            }
            return false;
        });

        Calendar nullTime = Calendar.getInstance();
        nullTime.set(Calendar.HOUR_OF_DAY, 0);
        nullTime.set(Calendar.MINUTE, 0);

        datePickerFuturoFromButton.setText(dateFormat.format(dateFrom.getTime()));
        CalcularFuturo(dateFrom, GetDays());
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

    private int GetDays() {
        try {
            String strDays = daysToAdd.getText().toString();
            if (strDays != null) {
                if (parseInt(strDays) == 0) {
                    daysToAdd.setText("");
                }
                return Math.abs(parseInt(strDays));
            }
            daysToAdd.setText("");
            return 0;
        } catch (NumberFormatException e) {
            daysToAdd.setText("");
            return 0;
        }
    }

    private void CalcularFuturo(Calendar dateFrom, int days) {
        TextView resultText = findViewById(R.id.result_text_futuro);
        String result = getString(R.string.defaultResultTime);
        Date futureDate = addDays(dateFrom.getTime(), days);
        result = dateFormat.format(futureDate.getTime());
        resultText.setText(result);
    }

    private Date addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calcular_futuro_button:
                CalcularFuturo(dateFrom, GetDays());
                break;

            default:
                Toast.makeText(this, "Unknown button clicked!", Toast.LENGTH_LONG).show();
                break;
        }
    }
}