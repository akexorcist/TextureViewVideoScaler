package com.akexorcist.textureviewvideoscaler.example;

import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import com.akexorcist.textureviewvideoscaler.VideoScaler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextureView.SurfaceTextureListener {
    private Button btnFill;
    private Button btnFit;
    private Button btnCrop;
    private TextureView textureView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFill = (Button) findViewById(R.id.btn_fill);
        btnFit = (Button) findViewById(R.id.btn_fit);
        btnCrop = (Button) findViewById(R.id.btn_crop);
        textureView = (TextureView) findViewById(R.id.texture_view);

        btnFill.setOnClickListener(this);
        btnFit.setOnClickListener(this);
        btnCrop.setOnClickListener(this);
        textureView.setSurfaceTextureListener(this);

        disableAllButton();
    }

    @Override
    public void onClick(View view) {
        if (view == btnFill) {
            onFillVideoClick();
        } else if (view == btnFit) {
            onFitVideoClick();
        } else if (view == btnCrop) {
            onCropVideoClick();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        initMediaPlayer(surface);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    private void disableAllButton() {
        btnFill.setEnabled(false);
        btnFit.setEnabled(false);
        btnCrop.setEnabled(false);
    }

    private void enableAllButton() {
        btnFill.setEnabled(true);
        btnFit.setEnabled(true);
        btnCrop.setEnabled(true);
    }

    private void onFillVideoClick() {
        setVideoScale(VideoScaler.FILL);
    }

    private void onFitVideoClick() {
        setVideoScale(VideoScaler.FIT_CENTER);
    }

    private void onCropVideoClick() {
        setVideoScale(VideoScaler.CROP_CENTER);
    }

    private void setVideoScale(int scaleType) {
        if (isMediaPlayerAvailable()) {
            float viewWidth = textureView.getWidth();
            float viewHeight = textureView.getHeight();
            float videoWidth = mediaPlayer.getVideoWidth();
            float videoHeight = mediaPlayer.getVideoHeight();
            Matrix matrix = VideoScaler.getVideoScaleMatrix(viewWidth, viewHeight, videoWidth, videoHeight, scaleType);
            textureView.setTransform(matrix);
        }
    }

    private void initMediaPlayer(SurfaceTexture surfaceTexture) {
        Surface surface = new Surface(surfaceTexture);
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setSurface(surface);
            mediaPlayer.setDataSource("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    enableAllButton();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isMediaPlayerAvailable() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }
}
