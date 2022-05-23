package com.example.project;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Level4 extends AppCompatActivity {

    Dialog dialog;
    Dialog dialogEnd;

    public int numLeft, numRight, X, Y;
    Array array = new Array();
    Random random = new Random();
    public int count = 0;  // счетчик

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        TextView text_levels = findViewById(R.id.text_levels);
        text_levels.setText(R.string.level4);

        final ImageView img_left = findViewById(R.id.img_left);
        img_left.setClipToOutline(true); // скругляем углы у картинки

        final ImageView img_right = findViewById(R.id.img_right);
        img_right.setClipToOutline(true); // скругляем углы у картинки

        //путь к TV слева
        final TextView text_left = findViewById(R.id.text_left);
        //путь к TV справа
        final TextView text_right = findViewById(R.id.text_right);


        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //вызов диалогового окна в начале игры
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialog); // путь к макету диалогового окна
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false); // нельзя закрыть системной кнопкой

        ImageView previewimg = dialog.findViewById(R.id.previewimg);
        previewimg.setImageResource(R.drawable.previewimgfour);

        TextView textdescription = dialog.findViewById(R.id.textdescription);
        textdescription.setText(R.string.levelfour);

        //кнопка, закрывающая диалоговое окно
        TextView btnclose = dialog.findViewById(R.id.btnclose);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    //возврат к меню
                    Intent intent = new Intent(Level4.this, GameLevels.class);
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

            }
        });
        dialog.show(); // диалоговое окно

        // Назад
        Button btn_back = findViewById(R.id.button_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    // вернуться к меню
                    Intent intent = new Intent(Level4.this, GameLevels.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    //
                }
            }
        });

        //вызов диалогового окна в конце игры
        dialogEnd = new Dialog(this);
        dialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEnd.setContentView(R.layout.dialogend); // путь к макету диалогового окна
        dialogEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogEnd.setCancelable(false); // нельзя закрыть системной кнопкой

        ImageView previewimgEnd = dialogEnd.findViewById(R.id.previewimg);
        previewimgEnd.setImageResource(R.drawable.previewimgfour);

        TextView textdescriptionEnd = dialogEnd.findViewById(R.id.textdescriptionEnd);
        textdescriptionEnd.setText(R.string.levelfourend);

        //кнопка, закрывающая диалоговое окно
        TextView btnclose2 = dialogEnd.findViewById(R.id.btnclose);
        btnclose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    //возврат к меню
                    Intent intent = new Intent(Level4.this, GameLevels.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    //
                }
                dialogEnd.dismiss(); // закрываем диалоговое окно
            }
        });

        //кнопка продолжить
        Button btncontinue2 = dialogEnd.findViewById(R.id.btncontinue);
        btncontinue2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try{
                    Intent intent = new Intent(Level4.this, Level4.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    //
                }
                dialogEnd.dismiss(); // закрываем диалоговое окно

            }
        });


        //прогресс игры
        final int[] progress = {
                R.id.point1, R.id.point2, R.id.point3, R.id.point4, R.id.point5, R.id.point6,
                R.id.point7, R.id.point8, R.id.point9, R.id.point10, };

        //анимация
        final Animation a = AnimationUtils.loadAnimation(Level4.this, R.anim.alpha);

        numLeft = random.nextInt(12);  // число слева
        numRight = random.nextInt(12);  // число справа
        while(numLeft==numRight){
            numRight = random.nextInt(12);
        }
        X = numLeft;
        Y = random.nextInt(12);
        while (true) {
            if (Y!=numLeft && Y!=numRight){
                break;
            }else{
                Y = random.nextInt(12);
            }
        }
        img_left.setImageResource(array.images4[numLeft]);
        text_left.setText(array.texts4[X]);
        img_right.setImageResource(array.images4[numRight]);
        text_right.setText(array.texts4[Y]);


        // нажатие на картинки
        img_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    img_right.setEnabled(false); // блокируем нажатие правой картинки
                    if (numLeft==X){
                        img_left.setImageResource(R.drawable.img_true);
                    }else{
                        img_left.setImageResource(R.drawable.img_false);
                    }
                }else if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if (numLeft==X){
                        if(count<10){
                            count=count+1;
                        }

                        // закрашиваем серым
                        for (int i=0; i<10; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }

                        // закрашиваем зеленым
                        for (int i=0; i<count; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    }else{
                        if(count>0){
                            if(count==1) {
                                count = 0;
                            }else{
                                count -= 2;
                            }
                        }
                        // закрашиваем серым
                        for (int i=0; i<9; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }

                        // закрашиваем зеленым
                        for (int i=0; i<count; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    }
                    if (count==10){
                        // выход из уровня
                        dialogEnd.show();
                    }else{
                        numLeft = random.nextInt(12);  // число слева
                        numRight = random.nextInt(12);  // число справа
                        while(numLeft==numRight){
                            numRight = random.nextInt(12);
                        }
                        Y = numRight;
                        X = random.nextInt(12);
                        while (true) {
                            if (X!=numLeft && X!=numRight){
                                break;
                            }else{
                                X = random.nextInt(12);
                            }
                        }
                        img_left.setImageResource(array.images4[numLeft]);
                        text_left.setText(array.texts4[X]);
                        img_right.setImageResource(array.images4[numRight]);
                        text_right.setText(array.texts4[Y]);

                        img_right.setEnabled(true); // можно нажать правую картинку
                    }
                }
                return true;
            }
        });


        img_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    img_left.setEnabled(false); // блокируем нажатие левой картинки
                    if (numRight==Y){
                        img_right.setImageResource(R.drawable.img_true);
                    }else{
                        img_right.setImageResource(R.drawable.img_false);
                    }
                }else if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if (numRight==Y){
                        if(count<10){
                            count=count+1;
                        }

                        // закрашиваем серым
                        for (int i=0; i<10; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }

                        // закрашиваем зеленым
                        for (int i=0; i<count; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    }else{
                        if(count>0){
                            if(count==1) {
                                count = 0;
                            }else{
                                count -= 2;
                            }
                        }
                        // закрашиваем серым
                        for (int i=0; i<9; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }

                        // закрашиваем зеленым
                        for (int i=0; i<count; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    }
                    if (count==10){
                        // выход из уровня
                        dialogEnd.show();
                    }else{
                        numLeft = random.nextInt(12);  // число слева
                        numRight = random.nextInt(12);  // число справа
                        while(numLeft==numRight){
                            numRight = random.nextInt(12);
                        }
                        X = numLeft;
                        Y = random.nextInt(12);
                        while (true) {
                            if (Y!=numLeft && Y!=numRight){
                                break;
                            }else{
                                Y = random.nextInt(12);
                            }
                        }
                        img_left.setImageResource(array.images4[numLeft]);
                        text_left.setText(array.texts4[X]);
                        img_right.setImageResource(array.images4[numRight]);
                        text_right.setText(array.texts4[Y]);

                        img_left.setEnabled(true); // можно нажать левую картинку
                    }
                }
                return true;
            }
        });
    }

    // системная кнопка назад
    @Override
    public void onBackPressed(){
        try{
            // вернуться к меню
            Intent intent = new Intent(Level4.this, GameLevels.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            //
        }
    }

}