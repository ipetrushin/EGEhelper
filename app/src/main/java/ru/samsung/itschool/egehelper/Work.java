package ru.samsung.itschool.egehelper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class Work extends AppCompatActivity {
    public final String TAG = "MyLogger";
    Spinner spinner;
    EditText score, hour, minute, sec;
    TextView[] ViewBalls = new TextView[9];
    TextView[] ViewItems = new TextView[9];
    SchoolItems[] items = new SchoolItems[9];
    String[] AllItems = {"Информатика", "Физика", "Химия", "Иностранный язык", "История",
    "Общество", "Биология", "География", "Литература"};
    private final int ID_DIALOG = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        spinner = findViewById(R.id.spinner);
        score = findViewById(R.id.score);
        hour = findViewById(R.id.hour2);
        minute = findViewById(R.id.minute2);
        sec = findViewById(R.id.second2);

        ViewItems[0] = findViewById(R.id.SredItems_1);
        ViewBalls[0] = findViewById(R.id.SredBall_1);
        ViewItems[1] = findViewById(R.id.SredItems_2);
        ViewBalls[1] = findViewById(R.id.SredBall_2);
        ViewItems[2] = findViewById(R.id.SredItems_3);
        ViewBalls[2] = findViewById(R.id.SredBall_3);
        ViewItems[3] = findViewById(R.id.SredItems_4);
        ViewBalls[3] = findViewById(R.id.SredBall_4);
        ViewItems[4] = findViewById(R.id.SredItems_5);
        ViewBalls[4] = findViewById(R.id.SredBall_5);
        ViewItems[5] = findViewById(R.id.SredItems_6);
        ViewBalls[5] = findViewById(R.id.SredBall_6);
        ViewItems[6] = findViewById(R.id.SredItems_7);
        ViewBalls[6] = findViewById(R.id.SredBall_7);
        ViewItems[7] = findViewById(R.id.SredItems_8);
        ViewBalls[7] = findViewById(R.id.SredBall_8);
        ViewItems[8] = findViewById(R.id.SredItems_9);
        ViewBalls[8] = findViewById(R.id.SredBall_9);


        String[] name_item = getIntent().getStringArrayExtra("name_item");
        for (int i = 0; i < name_item.length; i++){
            items[i] = new SchoolItems(name_item[i]);
            ViewItems[i].setText(name_item[i] + ": ");
            ViewBalls[i].setText(Integer.toString(items[i].getSredScore()));
        }
        Log.d(TAG, "items.length = " + items[0].getCount());


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, name_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(1);
    }

    public void onClickAddStatic(View view){
        try{
            if (score.getText().toString().isEmpty() || hour.getText().toString().isEmpty() ||
                    minute.getText().toString().isEmpty() || sec.getText().toString().isEmpty()){
                throw new Exception("Не всё инициализировано");
            }
            int scores = Integer.parseInt(score.getText().toString());
            if (scores > 100 || scores <= 0){
                throw new Exception("Неверные значение scores");
            }
            if (Integer.parseInt(hour.getText().toString()) < 0 || Integer.parseInt(minute.getText().toString()) < 0
                    || Integer.parseInt(minute.getText().toString()) >= 60 || Integer.parseInt(sec.getText().toString()) < 0
                    || Integer.parseInt(sec.getText().toString()) >= 60){
                throw new Exception("Неправильное время");
            }


            int second = Integer.parseInt(sec.getText().toString());
            second += Integer.parseInt(minute.getText().toString()) * 60;
            second += Integer.parseInt(hour.getText().toString())* 3600;

            for (int i = 0; i < items.length; i++){
                if (items[i].name.equals(spinner.getSelectedItem().toString())){
                    items[i].setStatistic(scores, second);
                    break;
                }
            }

            Toast.makeText(this, "✓", Toast.LENGTH_SHORT).show();


            for (int i = 0; i < items[0].getCount(); i++){
                ViewBalls[i].setText(Integer.toString(items[i].getSredScore()));
            }

            score.setText("");
            hour.setText("");
            minute.setText("");
            sec.setText("");

        }catch (Exception e){
            Toast.makeText(this, "Укажите корректные данные!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClckBack(View view){
        Intent i = new Intent(Work.this, MainActivity.class);
        startActivity(i);
    }

    public void onClickAddItems(View view){
        showDialog(ID_DIALOG);
    }

    @Override
    protected Dialog onCreateDialog(int id){
        switch (id) {
            case ID_DIALOG:
                try {
                    if (AllItems.length - items[0].getCount() + 2 == 0) {
                        throw new Exception("Использованы все предметы");
                    }
                    String[] Left = new String[AllItems.length - items[0].getCount() + 2];

                    int m = 0;
                    for (int i = 0; i < AllItems.length; i++){
                        boolean prov = true;
                        for (int j = 0; j < items[0].getCount(); j++){
                            if (AllItems[i].equals(items[j].name)){
                                prov = false;
                            }
                        }
                        if (prov){
                            Left[m] = AllItems[i];
                            m++;
                            prov = true;
                        }
                    }

                    final String[] LeftItems = Left;
                    Log.d(TAG, Arrays.toString(LeftItems));

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Добавить предмет:")
                            .setItems(LeftItems, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int item){
                                    items[items[0].getCount()] = new SchoolItems(LeftItems[item]);
                                    Toast.makeText(getApplicationContext(),  "Предмет" + "\"" + LeftItems[item] + "\"" + " добавлен", Toast.LENGTH_SHORT).show();
                                    ///
                                    String[] debag = new String[items[0].getCount()];
                                    for (int i = 0; i < debag.length; i++){
                                        debag[i] = items[i].name;
                                    }

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Work.this, android.R.layout.simple_spinner_item, debag);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                    spinner.setAdapter(adapter);
                                    ViewItems[items[0].getCount()].setText(LeftItems[item] + ": ");
                                    ViewBalls[items[0].getCount()].setText("0");

                                    Log.d(TAG, "count = " + items[0].getCount());
                                    Log.d(TAG, "id = " + item);
                                    Log.d(TAG, "items[] = " + Arrays.toString(debag));
                                    ///
                                }
                            })
                            .setCancelable(true);
                    return builder.create();

                } catch (Exception e) {
                    Toast.makeText(this, "Вы добавили все предметы!", Toast.LENGTH_SHORT).show();
                }
                default: return null;
        }
    }

}
