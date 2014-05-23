package com.examples.youtubeapidemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.youtube.player.YouTubePlayer;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 22/5/14
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class RetriveData extends AsyncTask<String, Context, String>  {
    private SharedPreferences tokenPreferences;
    ArrayList<String> list;
    public YouTubePlayer YPlayer;
        Context context;
    public RetriveData(MyActivity yPlayer)  {
       YPlayer=yPlayer.YPlayer;
                        context=yPlayer.getApplicationContext();
        tokenPreferences =context.getSharedPreferences("pageToken", Context.MODE_MULTI_PROCESS);
    }


    @Override
    protected String doInBackground(String... params) {
        String token=tokenPreferences.getString("telugu".toLowerCase(), "");
        Log.d("test"," token setting  "+token);
        list=Data.getVedioIds(token);
        SharedPreferences.Editor editor = tokenPreferences.edit();
        editor.putString("telugu".toLowerCase(), list.get(0));
        Log.d("test3"," token"+list.get(0));
        list.remove(0);
        editor.commit();
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void onPostExecute(String s) {

        super.onPostExecute(s);    //To change body of overridden methods use File | Settings | File Templates.
        Log.d("test", "onPostExcute");
        YPlayer.cueVideos(list);
        Log.d("test","onPostExcute");
    }
}
