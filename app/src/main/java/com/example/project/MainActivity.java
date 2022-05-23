package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Кнопка Начать
        Button buttonStart = (Button)findViewById(R.id.buttonStart);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(MainActivity.this, GameLevels.class);
                    startActivity(intent);finish();
                }catch (Exception e){

                }
            }
        });

        //Кнопка сброс
        Button buttonReset = (Button)findViewById(R.id.buttonReset);

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    // сбросить прогресс
                    SharedPreferences save = getSharedPreferences("Save", MODE_PRIVATE);
                    final int level = save.getInt("Level", 1);

                    SharedPreferences.Editor editor = save.edit();
                    editor.putInt("Level", 1);
                    editor.commit();

                    // сбросить прогресс
                    SharedPreferences rating = getSharedPreferences("Rating", MODE_PRIVATE);
                    final float res = rating.getFloat("Res", 10000);

                    SharedPreferences.Editor editor2 = rating.edit();
                    editor2.putFloat("Res", 10000);
                    editor2.commit();
                }catch (Exception e){

                }
            }
        });

        //Кнопка уровня на рейтинг
        Button buttonSpeedM = findViewById(R.id.buttonSpeedM);

        buttonSpeedM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(MainActivity.this, LevelTop.class);
                    startActivity(intent);finish();
                }catch (Exception e){

                }
            }
        });

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    //Системная кнопка "назад"
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }else{
            backToast = Toast.makeText(this, "Нажмите ещё раз, чтобы выйти", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();

    }
}