package com.examples.youtubeapidemo;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.truiton.youtubeapi.R;
public class MyActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    private YouTubePlayer YPlayer;
    private static final String YoutubeDeveloperKey = "AIzaSyDwttW24EyaqUS8BQjhNxHq7I110WlCaLY";
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        Log.d("test","oncreate");
        youTubeView.initialize(YoutubeDeveloperKey, this);
        Log.d("test","oncreate afterInitialze");
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.you_tube_api, menu);
        return true;
    }*/

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer",
                    errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("test","onActivityResult requestcode:"+requestCode);
        Log.d("test","onActivityResult resultcode:"+resultCode);
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            Log.d("test","onInitializationSuccessif :"+requestCode);
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YoutubeDeveloperKey, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

    @Override
    public void onInitializationSuccess(Provider provider,

                                        YouTubePlayer player, boolean wasRestored) {

        Log.d("test","onInitializationSuccess");

        if (!wasRestored) {
            Log.d("test","onInitializationSuccess :"+wasRestored);
            YPlayer = player;
            /*
             * Now that this variable YPlayer is global you can access it
             * throughout the activity, and perform all the player actions like
             * play, pause and seeking to a position by code.
             */
            YPlayer.cueVideo("2zNSgSzhBfM");
        }
    }

}

