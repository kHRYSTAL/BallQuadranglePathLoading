package me.khrystal.loadingdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import me.khrystal.widget.BallQuadranglePathLoadingView;

public class MainActivity extends AppCompatActivity {

    private BallQuadranglePathLoadingView mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingView = (BallQuadranglePathLoadingView) findViewById(R.id.loading_view);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seek_bar);
        seekBar.setMax(0xFF);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLoadingView.setPaintAlpha(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setProgress(0xFF);

    }

    public void showView(View v) {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    public void start(View v) {
        mLoadingView.start();
    }

    public void stop(View v) {
        mLoadingView.reset();
    }
}
