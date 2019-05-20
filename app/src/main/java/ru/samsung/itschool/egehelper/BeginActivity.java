package ru.samsung.itschool.egehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BeginActivity extends AppCompatActivity {
    CalendarView celendar;
    String DataBegin, DataEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);

        celendar = findViewById(R.id.calendarView);
        celendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                DataEnd = new StringBuilder().append(mYear)
                        .append("-").append(mMonth + 1).append("-").append(mDay)
                        .toString();
            }
        });

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DataBegin= df.format(Calendar.getInstance().getTime());
    }

    public void OnClick(View view){
        try {
            if (DataTransf(DataEnd) <= DataTransf(DataBegin)) {
                throw new Exception();
            }

            Intent i = new Intent(BeginActivity.this, MainActivity.class);
            i.putExtra("DataEnd", DataEnd);
            startActivity(i);

        }catch(Exception e){
            Toast.makeText(BeginActivity.this, "Укажите корректную дату", Toast.LENGTH_SHORT).show();
        }
    }


    public int DataTransf(String s){
        String[] st = s.split("-");
        int[] dt = new int[st.length];
        for (int i = 0; i < st.length; i++){
            dt[i] = Integer.parseInt(st[i]);
        }

        int day = 0;
        int i = 1;
        while (i < dt[1]) {
            if (i % 2 == 0) {
                if (i != 2) {
                    day += 30;
                } else if (i % 4 == 0) {
                    day += 29;
                } else
                    day += 28;
            } else if (i != 9) {
                day += 31;
            } else
                day += 30;
            i+=1;
        }
        day += dt[2];
        int k = String.valueOf(1000).length() - 1;

        return dt[0]*(int)Math.pow(10, k) + day;
    }
}
