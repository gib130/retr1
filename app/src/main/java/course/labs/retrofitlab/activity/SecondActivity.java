package course.labs.retrofitlab.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import course.labs.retrofitlab.R;
import course.labs.retrofitlab.adapter.MoviesAdapter;
import course.labs.retrofitlab.model.Movie;
import course.labs.retrofitlab.model.MoviesResponse;
import course.labs.retrofitlab.rest.ApiClient;
import course.labs.retrofitlab.rest.ServiceGenerator;
import course.labs.retrofitlab.rest.TMDBInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static course.labs.retrofitlab.rest.ApiClient.BASE_URL;

/**
 * Created by Александр on 06.11.2017.
 */

public class SecondActivity extends AppCompatActivity {
    private final static String API_KEY = "7e8f60e325cd06e164799af1e317d7a7";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    TextView text1,text2,text3,text4,text5,text6,text7,text8,text9,text10,text11,text12,text13 = null;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_second);
            String id = getIntent().getExtras().getString("id");
            text1 = (TextView)findViewById(R.id.textView);
            text2 = (TextView)findViewById(R.id.textView2);
            text3 = (TextView)findViewById(R.id.textView3);
            text4 = (TextView)findViewById(R.id.textView4);
            text5 = (TextView)findViewById(R.id.textView5);
            text6 = (TextView)findViewById(R.id.textView6);
            text7 = (TextView)findViewById(R.id.textView7);
            text8 = (TextView)findViewById(R.id.textView8);
            text9 = (TextView)findViewById(R.id.textView9);
            text10 = (TextView)findViewById(R.id.textView10);
            text11 = (TextView)findViewById(R.id.textView11);
            text12 = (TextView)findViewById(R.id.textView12);
            text13 = (TextView)findViewById(R.id.textView13);
        this.mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setRefreshing(true);
        new DownloadTask().execute("http://api.themoviedb.org/3/movie/"+id+"?language=ru&api_key="+API_KEY);
        }
    public String convertInputStreamToString(InputStream stream, int length) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[length];
        reader.read(buffer);
        return new String(buffer);
    }


    private class DownloadTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            //do your request in here so that you don't interrupt the UI thread
            try {
                return downloadContent(params[0]);
            } catch (IOException e) {
                return "Unable to retrieve data. URL may be invalid.";
            }
        }
        @Override
        protected void onPostExecute(String result) {
            //Here you are done with the task
            try {
                JSONObject reader = new JSONObject(result);
                //JSONObject sys  = reader.getJSONObject("adult");
                String adult = reader.getString("adult");
                String id= reader.getString("id");
                String imdb_id= reader.getString("imdb_id");
                String original_language = reader.getString("original_language");
                String original_title= reader.getString("original_title");
                String overview= reader.getString("overview");
                String popularity= reader.getString("popularity");
                String release_date= reader.getString("release_date");
                String status = reader.getString("status");
                String tagline= reader.getString("tagline");
                String title= reader.getString("title");
                String vote_average= reader.getString("vote_average");
                String vote_count= reader.getString("vote_count");
                mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
                mSwipeRefreshLayout.setRefreshing(false);
                text1.setText("id: "+id);
                text2.setText("adult: "+adult);
                text3.setText("imdb_id: "+imdb_id);
                text4.setText("original_language: "+original_language);
                text5.setText("original_title: "+original_title);
                text6.setText("overview: "+overview);
                text7.setText("popularity: "+popularity);
                text8.setText("release_date: "+release_date);
                text9.setText("status: "+status);
                text10.setText("tagline: "+tagline);
                text11.setText("title: "+title);
                text12.setText("vote_average: "+vote_average);
                text13.setText("vote_count: "+vote_count);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        private String downloadContent(String myurl) throws IOException {
            InputStream is = null;
            int length = 9000;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                int response = conn.getResponseCode();
                Log.d("test", "The response is: " + response);
                is = conn.getInputStream();
                // Convert the InputStream into a string
                String contentAsString = convertInputStreamToString(is, length);
                //Toast.makeText(SecondActivity.this, contentAsString, Toast.LENGTH_LONG).show();
                return contentAsString;
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }
    }
}

