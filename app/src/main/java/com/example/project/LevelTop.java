package com.example.project;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class LevelTop extends AppCompatActivity {

    Dialog dialog;
    Dialog dialogEnd;

    private TextView example, count;
    Random random = new Random();
    private int num1, num2, otv, otvFalse, N;
    private long startTime = 0, currentTime = 0;
    private float res_time = 0;
    private int kol_true_otv = 0, max_true_otv = 50;
    private String textTime = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal2);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //вызов диалогового окна в начале игры
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialog); // путь к макету диалогового окна
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false); // нельзя закрыть системной кнопкой

        ImageView previewimg = dialog.findViewById(R.id.previewimg);
        previewimg.setImageResource(R.drawable.previewimgone);

        TextView textdescription = dialog.findViewById(R.id.textdescription);
        textdescription.setText(R.string.leveltop);

        //кнопка, закрывающая диалоговое окно
        TextView btnclose = dialog.findViewById(R.id.btnclose);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    //возврат к меню
                    Intent intent = new Intent(LevelTop.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    //
                }
                dialog.dismiss(); // закрываем диалоговое окно
            }
        });

        //кнопка продолжить
        Button btncontinue = dialog.findViewById(R.id.btncontinue);
        btncontinue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dialog.dismiss(); // закрываем диалоговое окно
                init();
            }
        });
        dialog.show(); // диалоговое окно


        //вызов диалогового окна в конце игры
        dialogEnd = new Dialog(this);
        dialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEnd.setContentView(R.layout.dialogend2); // путь к макету диалогового окна
        dialogEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogEnd.setCancelable(false); // нельзя закрыть системной кнопкой

        ImageView previewimgEnd = dialogEnd.findViewById(R.id.previewimg);
        previewimgEnd.setImageResource(R.drawable.previewimgone);

        //кнопка, закрывающая диалоговое окно
        TextView btnclose2 = dialogEnd.findViewById(R.id.btnclose);
        btnclose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    //возврат к меню
                    Intent intent = new Intent(LevelTop.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    //
                }
                dialogEnd.dismiss(); // закрываем диалоговое окно
            }
        });


        //кнопка продолжить
        TextView btnok = dialogEnd.findViewById(R.id.btnok);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    //возврат к меню
                    Intent intent = new Intent(LevelTop.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }catch (Exception e){
                    //
                }
                dialogEnd.dismiss(); // закрываем диалоговое окно
            }
        });
    }

    private void init(){
        startTime = System.currentTimeMillis();
        example = findViewById(R.id.example);
        count = findViewById(R.id.count);

        numbers();
    }

    //генерация случайных чисел
    private void numbers(){
        num1 = random.nextInt(50);
        num2 = random.nextInt(50);
        otv = num1 + num2;
        otvFalse = random.nextInt(100);
        while (otvFalse == num1+num2) {
            otvFalse = random.nextInt(50);
        }

        N = random.nextInt(2);
        String ex;
        if (N==1){
            ex = num1 + "+" + num2 + "=" + otv;
        }else{
            ex = num1 + "+" + num2 + "=" + otvFalse;
        }
        example.setText(ex);

        //Выход из уровня
        if (kol_true_otv >= max_true_otv){
            currentTime = System.currentTimeMillis();
            res_time = (float)(currentTime - startTime) / 1000;

            textTime = String.format("%.2f", res_time);
            TextView tvRes = dialogEnd.findViewById(R.id.res); //Вывод результата
            tvRes.setText("Твой результат: " + textTime + "s");

            // сохранение данных
            SharedPreferences rating = getSharedPreferences("Rating", MODE_PRIVATE);
            final float res = rating.getFloat("Res", 10000);
            if (res>res_time){
                SharedPreferences.Editor editor = rating.edit();
                editor.putFloat("Res", res_time);
                editor.apply();

                TextView tvBestRes = dialogEnd.findViewById(R.id.bestRes);  //Вывод лучшего результата
                tvBestRes.setText("Лучший результат:\n" + String.format("%.2f", res_time) + "s");
            }else {
                TextView tvBestRes = dialogEnd.findViewById(R.id.bestRes);  //Вывод лучшего результата
                tvBestRes.setText("Лучший результат:\n" + String.format("%.2f", res) + "s");
            }

            dialogEnd.show();

        }
    }

    //Нажали кнопку "верно"
    public void onClickTrue(View viev){
        if (N==1){
            kol_true_otv += 1;
            count.setText(String.valueOf(kol_true_otv));

            numbers();
            currentTime = System.currentTimeMillis();
            res_time = (float)(currentTime - startTime) / 1000;
            String textTime = "Time: " + res_time + "s";
            final TextView time = findViewById(R.id.time);
            time.setText(textTime);
        }else {
            count.setText(String.valueOf(kol_true_otv));
            numbers();
            currentTime = System.currentTimeMillis();
            res_time = (float)(currentTime - startTime) / 1000;
            String textTime = "Time: " + res_time + "s";
            final TextView time = findViewById(R.id.time);

            time.setText(textTime);
        }
    }

    //Нажали кнопку "неверно"
    public void onClickFalse(View viev){
        if (N!=1){
            kol_true_otv += 1;
            count.setText(String.valueOf(kol_true_otv));

            numbers();
            currentTime = System.currentTimeMillis();
            res_time = (float)(currentTime - startTime) / 1000;
            String textTime = "Time: " + res_time + "s";
            final TextView time = findViewById(R.id.time);
            time.setText(textTime);
        }else {
            count.setText(String.valueOf(kol_true_otv));
            numbers();
            currentTime = System.currentTimeMillis();
            res_time = (float) (currentTime - startTime) / 1000;
            String textTime = "Time: " + res_time + "s";
            final TextView time = findViewById(R.id.time);
            time.setText(textTime);
        }
    }

    // системная кнопка назад
    @Override
    public void onBackPressed(){
        try{
            // вернуться к меню
            Intent intent = new Intent(LevelTop.this, MainActivity.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            //
        }
    }

}