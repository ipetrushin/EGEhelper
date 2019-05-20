package ru.samsung.itschool.egehelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "MyLogger";
    TextView  textView, dethCome;
    String DataBegin, DataEnd;
    Button button, statistics, work, help;
    LinearLayout buttonsLayout, dateLayout;
    TextView Day, Mounth, Year, Hour, Minute, Second;
    TextView textDays, textMounths, textYears, textHours, textMinutes, textSeconds;
    public SchoolItems[] items = new SchoolItems[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dethCome = findViewById(R.id.death);
        button = findViewById(R.id.button);
        statistics = findViewById(R.id.Statistics);
        work = findViewById(R.id.Work);
        help = findViewById(R.id.Help);
        textView = findViewById(R.id.textView);
        buttonsLayout = findViewById(R.id.Buttons);
        dateLayout = findViewById(R.id.DateLayout);
        Day = findViewById(R.id.day);
        Mounth = findViewById(R.id.mouth);
        Year = findViewById(R.id.year);
        Hour = findViewById(R.id.hour);
        Minute = findViewById(R.id.minute);
        Second = findViewById(R.id.second);
        textDays= findViewById(R.id.textDays);
        textMounths = findViewById(R.id.textMounths);
        textYears = findViewById(R.id.textYears);
        textHours = findViewById(R.id.textHours);
        textMinutes = findViewById(R.id.textMinutes);
        textSeconds = findViewById(R.id.textSeconds);


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DataBegin= df.format(Calendar.getInstance().getTime());

        DataEnd = getIntent().getStringExtra("DataEnd");

        items[0] = new SchoolItems("Математика");
        items[1] = new SchoolItems("Русский язык");

        TimerTask timerTask = new TimerTask();
        timerTask.execute();
    }


    public void onClickWork(View view){
        String[] name_item = new String[items[0].getCount()];
        for (int i = 0; i < name_item.length; i++){
            name_item[i] = items[i].name;
        }
        items[0].setCountNull();
        Log.d(TAG, "name_item = " + Arrays.toString(name_item) + '\n' +  "legth = " + name_item.length);
        Intent i = new Intent(MainActivity.this, Work.class);
        i.putExtra("name_item", name_item);
        startActivity(i);
    }

    /*____________________________________________________________________________________________________________
    __________________________________________________ПОТОК_______________________________________________________
    ______________________________________________________________________________________________________________*/


    public class TimerTask extends AsyncTask<Void, Void, Void> {
        int day, mounth, year = 0, hour, minute, second;

        @Override
        protected void onPreExecute(){
            try {
                int[] begin = Parsing(DataBegin);
                int[] end = Parsing(DataEnd);
                if (begin[0] == end[0] && begin[1] == end[1]){
                    year = 0;
                    mounth = 0;
                    day = end[2] - begin[2] - 1;
                }else {
                    day = DayCounter(begin, end) - 1;
                    mounth = MounthCounter(begin, end);
                    year = 0;
                    while (mounth >= 12) {
                        year++;
                        mounth -= 12;
                    }
                }
                if (day > 9) {
                    Day.setText(Integer.toString(day));
                } else Day.setText("0" + Integer.toString(day));
                if (mounth > 9) {
                    Mounth.setText(Integer.toString(mounth));
                }else  Mounth.setText("0" + Integer.toString(mounth));
                if (year > 9) {
                    Year.setText(Integer.toString(year));
                }else Year.setText("0" + Integer.toString(year));
                DateFormat df2 = new SimpleDateFormat("HH:mm:ss");
                String timenow = df2.format(Calendar.getInstance().getTime());
                int[] time = TimeParsing(timenow);
                hour = 23 - time[0];
                minute = 60 - time[1];
                if (minute == 60){
                    minute = 0;
                    hour++;
                }
                second = 60 - time[2];
                if (second == 60){
                    second = 0;
                    minute++;
                }
                if (hour > 9) {
                    Hour.setText(Integer.toString(hour));
                }else Hour.setText("0" + Integer.toString(hour));
                if (minute > 9) {
                    Minute.setText(Integer.toString(minute));
                }else  Minute.setText("0" + Integer.toString(minute));
                if (second > 9) {
                    Second.setText(Integer.toString(second));
                }else Second.setText("0" + Integer.toString(second));


                if (year % 10 >= 5 && year % 10 <= 9 || year % 10 == 0 || year % 100 >= 11 && year % 100 <= 14){
                    textYears.setText("лет");
                } else if (year % 10 >= 2 && year % 10 <= 4){
                    textYears.setText("года");
                }else if (year % 10 == 1){
                    textYears.setText("год");
                }
                /*if (mounth % 10 >= 5 && mounth % 10 <= 9 || mounth % 10 == 0 || mounth % 100 >= 11 && mounth % 100 <= 14){
                    textMounths.setText("месяцев");
                } else if (mounth % 10 >= 2 && mounth % 10 <= 4){
                    textMounths.setText("месяца");
                }else if (mounth % 10 == 1){
                    textMounths.setText("месяц");
                }*/
                if (day % 10 >= 5 && day % 10 <= 9 || day % 10 == 0 || day % 100 >= 11 && day % 100 <= 14){
                    textDays.setText("дней");
                } else if (day % 10 >= 2 && day % 10 <= 4){
                    textDays.setText("дня");
                }else if (day % 10 == 1){
                    textDays.setText("день");
                }
                if (hour % 10 >= 5 && hour % 10 <= 9 || hour % 10 == 0 || hour % 100 >= 11 && hour % 100 <= 14){
                    textHours.setText("часов");
                } else if (hour % 10 >= 2 && hour % 10 <= 4){
                    textHours.setText("часа");
                }else if (hour % 10 == 1){
                    textHours.setText("час");
                }
                /*if (minute % 10 >= 5 && minute % 10 <= 9 || minute % 10 == 0 || minute % 100 >= 11 && minute % 100 <= 14){
                    textMinutes.setText("минут");
                } else if (minute % 10 >= 2 && minute % 10 <= 4){
                    textMinutes.setText("минуты");
                }else if (minute % 10 == 1){
                    textMinutes.setText("минута");
                }
                if (second % 10 >= 5 && second % 10 <= 9 || second % 10 == 0 || second % 100 >= 11 && second % 100 <= 14){
                    textSeconds.setText("секунд");
                } else if (second % 10 >= 2 && second % 10 <= 4){
                    textSeconds.setText("секунды");
                }else if (second % 10 == 1){
                    textSeconds.setText("секунда");
                }*/
            }catch (Exception e){
                Toast.makeText(MainActivity.this, "Блэт, произошла ошибка", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (DataTransf(DataEnd) - DataTransf(DataBegin) > 0){
                try {
                    int[] begin = Parsing(DataBegin);
                    int[] end = Parsing(DataEnd);
                    if (begin[0] == end[0] && begin[1] == end[1]){
                        year = 0;
                        mounth = 0;
                        day = end[2] - begin[2] - 1;
                    }else {
                        day = DayCounter(begin, end) - 1;
                        mounth = MounthCounter(begin, end);
                        year = 0;
                        while (mounth >= 12) {
                            year++;
                            mounth -= 12;
                        }
                    }
                    DateFormat df2 = new SimpleDateFormat("HH:mm:ss");
                    String timenow = df2.format(Calendar.getInstance().getTime());
                    int[] time = TimeParsing(timenow);
                    hour = 23 - time[0];
                    minute = 60 - time[1];
                    if (minute == 60){
                        minute = 0;
                        hour++;
                    }
                    second = 60 - time[2];
                    if (second == 60){
                        second = 0;
                        minute++;
                    }

                    Thread.sleep(1000);
                    publishProgress();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
            if (day > 9) {
                Day.setText(Integer.toString(day));
            }else  Day.setText("0" + Integer.toString(day));
            if (mounth > 9) {
                Mounth.setText(Integer.toString(mounth));
            }else  Mounth.setText("0" + Integer.toString(mounth));
            if (year > 9) {
                Year.setText(Integer.toString(year));
            }else Year.setText("0" + Integer.toString(year));
            if (hour > 9) {
                Hour.setText(Integer.toString(hour));
            }else Hour.setText("0" + Integer.toString(hour));
            if (minute > 9) {
                Minute.setText(Integer.toString(minute));
            }else  Minute.setText("0" + Integer.toString(minute));
            if (second > 9) {
                Second.setText(Integer.toString(second));
            }else Second.setText("0" + Integer.toString(second));


            if (year % 10 >= 5 && year % 10 <= 9 || year % 10 == 0 || year % 100 >= 11 && year % 100 <= 14){
                textYears.setText("лет");
            } else if (year % 10 >= 2 && year % 10 <= 4){
                textYears.setText("года");
            }else if (year % 10 == 1){
                textYears.setText("год");
            }
            /*if (mounth % 10 >= 5 && mounth % 10 <= 9 || mounth % 10 == 0 || mounth % 100 >= 11 && mounth % 100 <= 14){
                textMounths.setText("месяцев");
            } else if (mounth % 10 >= 2 && mounth % 10 <= 4){
                textMounths.setText("месяца");
            }else if (mounth % 10 == 1){
                textMounths.setText("месяц");
            }*/
            if (day % 10 >= 5 && day % 10 <= 9 || day % 10 == 0 || day % 100 >= 11 && day % 100 <= 14){
                textDays.setText("дней");
            } else if (day % 10 >= 2 && day % 10 <= 4){
                textDays.setText("дня");
            }else if (day % 10 == 1){
                textDays.setText("день");
            }
            if (hour % 10 >= 5 && hour % 10 <= 9 || hour % 10 == 0 || hour % 100 >= 11 && hour % 100 <= 14){
                textHours.setText("часов");
            } else if (hour % 10 >= 2 && hour % 10 <= 4){
                textHours.setText("часа");
            }else if (hour % 10 == 1){
                textHours.setText("час");
            }
           /* if (minute % 10 >= 5 && minute % 10 <= 9 || minute % 10 == 0 || minute % 100 >= 11 && minute % 100 <= 14){
                textMinutes.setText("минут");
            } else if (minute % 10 >= 2 && minute % 10 <= 4){
                textMinutes.setText("минуты");
            }else if (minute % 10 == 1){
                textMinutes.setText("минута");
            }
            if (second % 10 >= 5 && second % 10 <= 9 || second % 10 == 0 || second % 100 >= 11 && second % 100 <= 14){
                textSeconds.setText("секунд");
            } else if (second % 10 >= 2 && second % 10 <= 4){
                textSeconds.setText("секунды");
            }else if (second % 10 == 1){
                textSeconds.setText("секунда");
            }*/
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            dethCome.setVisibility(View.VISIBLE);
        }

    }

    /*____________________________________________________________________________________________________________
    ____________________________________________КОНЕЦ_ПОТОКА______________________________________________________
    ______________________________________________________________________________________________________________*/


    //___________________________________________ВСЯКИЕ_ФУНКЦИИ_____________________________________________________


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


    public int[] Parsing(String s){
        String[] st = s.split("-");
        int[] date = new int[st.length];
        for (int i = 0; i < st.length; i++){
            date[i] = Integer.parseInt(st[i]);
        }
        return date;
    }


    public int DayCounter(int[] databegin, int[] dataend){
        int day = 0;

        if (databegin[1] % 2 == 0) {
            if (databegin[1] != 2) {
                day += 30;
            } else if (databegin[1] % 4 == 0) {
                day += 29;
            } else
                day += 28;
        } else if (databegin[1] != 9) {
            day += 31;
        } else
            day += 30;

        return day - databegin[2] + dataend[2];
    }

    public int MounthCounter(int[] databegin, int[] dataend){
        int mounth = (dataend[0] - databegin[0]) * 12;
        mounth -= databegin[1] + 1;
        mounth += dataend[1];
        return mounth;
    }

    public static int[] TimeParsing(String s){
        String[] st = s.split(":");
        int[] time = new int[st.length];
        for (int i = 0; i < st.length; i++){
            time[i] = Integer.parseInt(st[i]);
        }
        return time;
    }
}
