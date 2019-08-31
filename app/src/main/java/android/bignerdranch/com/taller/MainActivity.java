package android.bignerdranch.com.taller;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class MainActivity extends Activity {
    public List<Color> colors;
    public List<Type> figures;
    public Random random = new Random();
    public int solution;
    private LinearLayout linearLayout;
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private TextView info;
    private TextView title;
    private TextView correctText;
    private TextView incorrectText;
    private TextView score;
    private TextView nice;
    private TextView noce;
    private int questions;
    private int current;
    private int correct;
    private int incorrect;
    private long last;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questions = getIntent().getIntExtra("QUESTIONS", 10);
        fillList();
        linearLayout = findViewById(R.id.content_polygon);
        buttonA = findViewById(R.id.A);
        buttonB = findViewById(R.id.B);
        buttonC = findViewById(R.id.C);
        info = findViewById(R.id.question);
        title = findViewById(R.id.title);
        correctText = findViewById(R.id.text_correct);
        incorrectText = findViewById(R.id.text_incorrect);
        score = findViewById(R.id.score);
        nice = findViewById(R.id.nice);
        noce = findViewById(R.id.noce);

        nice.setVisibility(View.GONE);
        noce.setVisibility(View.GONE);
        current = 1;
        correct = 0;
        incorrect = 0;
        correctText.setText("0");
        incorrectText.setText("0");
        score.setText("0");

        title.setText(String.format("Pregunta No %d/%d", current, questions));
        generateRound();

        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - last< 400)
                    return;
                last = SystemClock.elapsedRealtime();
                makeAnswer(0);
            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - last< 400)
                    return;
                last = SystemClock.elapsedRealtime();
                makeAnswer(1);
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - last< 400)
                    return;
                last = SystemClock.elapsedRealtime();
                makeAnswer(2);
            }
        });


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea salir?\nPerdera todo progreso.");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void makeAnswer(int answer){
        if(answer == solution){
            correct++;
            correctText.setText(correct + "");
            score.setText((correct*5) + "");
            noce.setVisibility(View.GONE);
            nice.setVisibility(View.VISIBLE);
        }
        else{
            incorrect++;
            incorrectText.setText(incorrect + "");
            noce.setVisibility(View.VISIBLE);
            nice.setVisibility(View.GONE);
        }
        current++;

        if(current == questions + 1){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Resultados");
            builder.setMessage(String.format("%d de %d correctas.\n¿Desea intentarlo de nuevo?", correct, questions));
            builder.setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    current = 1;
                    correct = 0;
                    incorrect = 0;
                    correctText.setText("0");
                    incorrectText.setText("0");
                    nice.setVisibility(View.GONE);
                    noce.setVisibility(View.GONE);
                    score.setText("0");
                    title.setText(String.format("Pregunta No %d/%d", current, questions));
                    generateRound();
                }
            });
            builder.setNegativeButton("Regresar al menú.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else
            title.setText(String.format("Pregunta No %d/%d", current, questions));

        generateRound();
    }

    private void generateRound() {
        linearLayout.removeAllViews();
        Collections.shuffle(figures);
        Collections.shuffle(colors);
        random.setSeed(System.currentTimeMillis());
        solution = random.nextInt(3);
        linearLayout.addView(figures.get(solution).inflateView(colors.get(solution).color, this));
        if(random.nextBoolean()){
            buttonA.setText(figures.get(0).name);
            buttonB.setText(figures.get(1).name);
            buttonC.setText(figures.get(2).name);
            info.setText(R.string.figure_question);
        }
        else{
            buttonA.setText(colors.get(0).name);
            buttonB.setText(colors.get(1).name);
            buttonC.setText(colors.get(2).name);
            info.setText(R.string.color_question);
        }
        Collections.shuffle(colors);
        buttonA.setTextColor(colors.get(0).color);
        buttonB.setTextColor(colors.get(1).color);
        buttonC.setTextColor(colors.get(2).color);

    }

    private void fillList() {
        this.colors = new ArrayList<>();
        this.figures = new ArrayList<>();

        colors.add(new Color(android.graphics.Color.RED, "Rojo"));
        colors.add(new Color(android.graphics.Color.BLUE, "Azul"));
        colors.add(new Color(android.graphics.Color.YELLOW, "Amarillo"));
        colors.add(new Color(android.graphics.Color.GREEN, "Verde"));

        figures.add(new Type(0,0, "Circulo"));     // Circle
        figures.add(new Type(1, 0, "Cuadrado"));    // Rectangle
        figures.add(new Type(2, 3, "Triangulo"));
        figures.add(new Type(2, 5, "Pentagono"));
        figures.add(new Type(2, 6, "Hexagono"));
    }
/*
    public Polygon getNewPolygon() {
        return new Polygon(this,200, colors.get(random.nextInt(colors.size())), random.nextInt(3), 5);
    }
    */
}
