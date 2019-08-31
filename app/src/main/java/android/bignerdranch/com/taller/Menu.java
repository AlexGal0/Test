package android.bignerdranch.com.taller;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Menu extends Activity {

    public Button start;
    public SeekBar bar;
    public TextView number;
    public int questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        start  = findViewById(R.id.start);
        bar    = findViewById(R.id.number_questions);
        number = findViewById(R.id.questions_text);

        questions = bar.getProgress() + 1;

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                number.setText((progress + 1) + "");
                questions = progress + 1;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("QUESTIONS", questions);
                startActivity(intent);
            }
        });
    }

}
