package com.examples.youtubeapidemo;

import android.util.Log;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.apache.http.HttpRequest;
import com.google.api.services.youtube.YouTube;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 22/5/14
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class Data {

    public static  void main(String[] args){
        System.out.println(getVedioIds("").toString());
    }

    public static ArrayList<String> getVedioIds(String token){
        ArrayList<String> list=new ArrayList<String>();
        Iterator<SearchResult> iteratorSearchResults=null;
        try {
            YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                @Override
                public void initialize(com.google.api.client.http.HttpRequest httpRequest) throws IOException {
                    //To change body of implemented methods use File | Settings | File Templates.
                }
            }).setApplicationName("youtube-cmdline-search-sample").build();
            YouTube.Search.List search = youtube.search().list("id");
            String apiKey = "AIzaSyBFWGVBMO04Y_BQGfnrXOCSNSHBItbPvUU";
            search.setKey(apiKey);
            search.setQ("telugu songs");
            search.setVideoDuration("medium");
            search.setType("video");
            search.setPageToken(token);
            search.setFields("items(*),nextPageToken");
            search.setMaxResults(Long.parseLong("30"));

            // Call the API and print results.
            SearchListResponse searchResponse = search.execute();
           String nextPageTokenoken= searchResponse.getNextPageToken();
            list.add(nextPageTokenoken);

            Log.d("test1"," token"+nextPageTokenoken);
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
                //   prettyPrint(searchResultList.iterator(), queryTerm);
                iteratorSearchResults = searchResultList.iterator();
            }
            if (!iteratorSearchResults.hasNext()) {
                System.out.println(" There aren't any results for your query.");
            }

            while (iteratorSearchResults.hasNext()) {

                SearchResult singleVideo = iteratorSearchResults.next();
                ResourceId rId = singleVideo.getId();

                list.add(rId.getVideoId());
            }

        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
            Log.d("test1", e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            Log.d("test1", t.toString());
            Log.d("test1", t.getMessage());
            Log.d("test1", t.getCause()+"");
            t.printStackTrace();
        }
        Log.d("test2"," token"+list.get(0));
        Log.d("test1", list.toString());
        return list;
    }
}
