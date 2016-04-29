package com.dnsoftindia.speechtotextdemo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final int SPEECH_CODE = 1;
    private FloatingActionButton fabSpeak;
    private TextView tvConvertedText;

    private void findViews() {
        fabSpeak = (FloatingActionButton) findViewById( R.id.fabSpeak );
        tvConvertedText = (TextView)findViewById( R.id.tvConvertedText );

        fabSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startConvertingSpeechToText();
            }
        });
    }

    private void startConvertingSpeechToText() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Please start speaking...");
        try {
            startActivityForResult(i, SPEECH_CODE);
        } catch (ActivityNotFoundException a) {
            Snackbar.make(fabSpeak, "Speech recognition is not supported on your device. Sorry!",
                    Snackbar.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPEECH_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    tvConvertedText.setText(text);
                }
                break;
            }
        }
    }
}
