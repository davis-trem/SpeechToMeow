package com.leptosstudios.speechtomeow;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    TextToSpeech ttsobject;
    int ttsResult;
    String sentence;
    String sentence2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = (TextView)findViewById(R.id.tvResult);

        //TEXT TO SPEECH STUFF
        ttsobject = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    ttsResult = ttsobject.setLanguage(Locale.UK);
                }else{
                    Toast.makeText(getApplicationContext(), "Feature not supported", Toast.LENGTH_LONG).show();
                }
            }
        });
        sentence = "Hello";
        sentence2 = "";
    }

    public void onButnntonClick(View view){
        if(view.getId() == R.id.button){
            promptSpeechInput();
        }
    }

    public void promptSpeechInput(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something!");

        try {
            startActivityForResult(i, 100);
        }catch (ActivityNotFoundException a){
            Toast.makeText(MainActivity.this, "Device Does Not Support Speech", Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int request_code, int result_code, Intent i){
        super.onActivityResult(request_code, result_code, i);
        switch(request_code){
            case 100: if(result_code == RESULT_OK && i != null){
                ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                tvResult.setText(result.get(0));
                sentence = result.get(0);
            }
                break;
        }
    }

    public void onSpeakClick(View view){
        switch (view.getId()){

            case R.id.bSpeak:

                if(ttsResult == TextToSpeech.LANG_NOT_SUPPORTED || ttsResult == TextToSpeech.LANG_MISSING_DATA){
                    Toast.makeText(getApplicationContext(), "Feature not supported", Toast.LENGTH_LONG).show();
                }else{
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.cat_me);
                    final MediaPlayer mp1 = MediaPlayer.create(this, R.raw.cat_ow);
                    ttsobject.setPitch((float) 2);
                    sentence2 = "";
                    Thread thread;

                    if(sentence.contains(" ") == false){
                        if (sentence.startsWith("a") || sentence.startsWith("e") || sentence.startsWith("i") || sentence.startsWith("o") || sentence.startsWith("u") || sentence.startsWith("A") || sentence.startsWith("E") || sentence.startsWith("I") || sentence.startsWith("O") || sentence.startsWith("U")) {

                            mp.start();
                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    ttsobject.speak(sentence, TextToSpeech.QUEUE_FLUSH, null);
                                    while (true){
                                        if (!ttsobject.isSpeaking()){
                                            mp1.start();
                                            break;
                                        }

                                    }
                                }
                            });

                        } else {
                            sentence2 += sentence.substring(1, sentence.length()) + sentence.charAt(0);
                            mp.start();
                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    ttsobject.speak(sentence2, TextToSpeech.QUEUE_FLUSH, null);
                                    while (true){
                                        if (!ttsobject.isSpeaking()){
                                            mp1.start();
                                            break;
                                        }

                                    }
                                }
                            });


                        }
                    }else {
                        String[] group = sentence.split(" ");
                        for (int i = 0; i < group.length; i++) {
                            if (group[i].startsWith("a") || group[i].startsWith("e") || group[i].startsWith("i") || group[i].startsWith("o") || group[i].startsWith("u") || group[i].startsWith("A") || group[i].startsWith("E") || group[i].startsWith("I") || group[i].startsWith("O") || group[i].startsWith("U")) {
                                mp.start();
                                sentence2 = group[i];
                                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        ttsobject.speak(sentence2, TextToSpeech.QUEUE_FLUSH, null);
                                        while (true){
                                            if (!ttsobject.isSpeaking()){
                                                mp1.start();
                                                break;
                                            }

                                        }
                                    }
                                });
                            } else {
                                sentence2 += group[i].substring(1, group[i].length()) + group[i].charAt(0);
                                mp.start();
                                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        ttsobject.speak(sentence2, TextToSpeech.QUEUE_FLUSH, null);
                                        while (true){
                                            if (!ttsobject.isSpeaking()){
                                                mp1.start();
                                                break;
                                            }

                                        }
                                    }
                                });
                            }

                        }
                    }


                }
                break;
            case R.id.bStop:
                if(ttsobject != null){
                    ttsobject.stop();
                }
                break;
            case R.id.bPigLatin:
                if(ttsResult == TextToSpeech.LANG_NOT_SUPPORTED || ttsResult == TextToSpeech.LANG_MISSING_DATA){
                    Toast.makeText(getApplicationContext(), "Feature not supported", Toast.LENGTH_LONG).show();
                }else{
                    String sentence2 = "";
                    if(sentence.contains(" ") == false){
                        if (sentence.startsWith("a") || sentence.startsWith("e") || sentence.startsWith("i") || sentence.startsWith("o") || sentence.startsWith("u")) {
                            sentence2 += sentence + "ay ";
                        } else {
                            sentence2 += sentence.substring(1, sentence.length()) + sentence.charAt(0) + "ay ";
                        }
                    }else {
                        String[] group = sentence.split(" ");
                        for (int i = 0; i < group.length; i++) {
                            if (group[i].startsWith("a") || group[i].startsWith("e") || group[i].startsWith("i") || group[i].startsWith("o") || group[i].startsWith("u")) {
                                sentence2 += group[i] + "ay ";
                            } else {
                                sentence2 += group[i].substring(1, group[i].length()) + group[i].charAt(0) + "ay ";
                            }

                        }
                    }
                    Toast.makeText(MainActivity.this, sentence2, Toast.LENGTH_LONG).show();
                    ttsobject.setPitch((float) 2);
                    ttsobject.speak(sentence2, TextToSpeech.QUEUE_FLUSH, null);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(ttsobject != null){
            ttsobject.stop();
            ttsobject.shutdown();
        }
    }
}
