package com.example.reproductor;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import java.util.ArrayList;
import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    ImageButton btnPlay;
    ImageButton btnStop;
    ImageButton btnSig;
    ImageButton btnAtr;
    TextView txtName;
    AudioManager audioManager;
    MediaPlayer mp;
    Handler myHAndler= new Handler();
    int canciones[]={R.raw.oneandonly,R.raw.rollinginthedeep,R.raw.rumourhasit,R.raw.setfiretotherain,R.raw.someonelikeyou};
    String can[]={"One and Only-Adele", "Rolling in the deep-Adele","Rumour has it-Adele","Set fire to the rain-Adele","Some one like you-Adele"};
    int index=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAtr=(ImageButton)findViewById(R.id.btnAtr);
        btnSig=(ImageButton)findViewById(R.id.btnSig);

        btnPlay = (ImageButton)findViewById(R.id.btnPlay);
        txtName= (TextView) findViewById(R.id.txtName);
        txtName.setText(can[index]);


        //mp = MediaPlayer.create(this,R.raw.rollinginthedeep);
        mp=MediaPlayer.create(this,canciones[index]);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp.isPlaying()){

                }else{
                    mp.start();
                    Toast.makeText(MainActivity.this, "Play", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnStop= (ImageButton)findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp.isPlaying()){
                    mp.pause();
                    Toast.makeText(MainActivity.this, "Pausa", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnSig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if (index>4)index=0;
                mp.stop();
                mp=MediaPlayer.create(getApplication(),canciones[index]);
                txtName.setText(can[index]);
                mp.start();
            }
        });
        btnAtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index--;
                if (index<0)index=4;
                mp.stop();
                mp=MediaPlayer.create(getApplication(),canciones[index]);
                txtName.setText(can[index]);
                mp.start();
            }
        });

        //Volumen

        audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);

        int maxVolume=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar seekBarVol = findViewById(R.id.seekBarVol);

        seekBarVol.setMax(maxVolume);
        seekBarVol.setProgress(currentVolume);

        seekBarVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Log.d("volume:", Integer.toString(progress));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // Manejo de avance

        final SeekBar seekBarRep = findViewById(R.id.seekBarRep);
        int duration = mp.getDuration();
        int progress = mp.getCurrentPosition();
        seekBarRep.setMax(duration);
        seekBarRep.setProgress(progress);

        seekBarRep.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mp.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekBarRep.setProgress(mp.getCurrentPosition());
            }
            }, 0, 1000
        );

        //ListView
        ListView lvCan = findViewById(R.id.lvCan);

        // inicializar coleccion (bajarla de internet, etc.)
        final ArrayList <String> elementos = new ArrayList<String>(asList(can));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, elementos);

        lvCan.setAdapter(arrayAdapter);

        lvCan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), elementos.get(i), Toast.LENGTH_SHORT).show();

                if (elementos.get(i)=="One and Only-Adele"){
                    mp.stop();
                    index=0;
                    mp=MediaPlayer.create(getApplication(),canciones[index]);
                    txtName.setText(can[index]);
                    mp.start();
                }else if(elementos.get(i)=="Rolling in the deep-Adele"){
                    mp.stop();
                    index=1;
                    mp=MediaPlayer.create(getApplication(),canciones[index]);
                    txtName.setText(can[index]);
                    mp.start();
                }else if(elementos.get(i)=="Rumour has it-Adele"){
                    mp.stop();
                    index=2;
                    mp=MediaPlayer.create(getApplication(),canciones[index]);
                    txtName.setText(can[index]);
                    mp.start();
                }else if(elementos.get(i)=="Set fire to the rain-Adele"){
                    mp.stop();
                    index=3;
                    mp=MediaPlayer.create(getApplication(),canciones[index]);
                    txtName.setText(can[index]);
                    mp.start();
                }else {
                    mp.stop();
                    index = 4;
                    mp = MediaPlayer.create(getApplication(), canciones[index]);
                    txtName.setText(can[index]);
                    mp.start();
                }

            }
        });
    }

}
