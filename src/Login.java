package com.miquelynhollingsworth.journal;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
//import android.widget.EditText;
//import android.widget.Button;
//import android.content.Intent;

import com.miquelynhollingsworth.journal.models.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    private TextView registerLink;
    private ListView lvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        //final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        //final Button bLogin = (Button) findViewById(R.id.bLogin);
        //registerLink = (TextView) findViewById(R.id.tvRegisterHere);
        lvMovies = (ListView) findViewById(R.id.lvMovies);

       /* bLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new JSONTASK().execute("http://jsonparsing.parseapp.com/jsonData/moviesData.txt");
//                new JSONTASK().execute("http://jsonparsing.parseapp.com/jsonData/moviesDemoList.txt");
//                new JSONTASK().execute("http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt");

            }

        });*/
    }

    public class JSONTASK extends AsyncTask<String, String, List<MovieModel>> {

        @Override
        protected List<MovieModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("movies");

                List<MovieModel> movieModelList = new ArrayList<>();

                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    MovieModel movieModel = new MovieModel();
                    movieModel.setMovie(finalObject.getString("movie"));
                    movieModel.setYear(finalObject.getInt("year"));
                    movieModel.setRating((float) finalObject.getDouble("rating"));
                    movieModel.setDirector(finalObject.getString("director"));
                    movieModel.setDuration(finalObject.getString("duration"));
                    movieModel.setTagline(finalObject.getString("tagline"));
                    movieModel.setImage(finalObject.getString("image"));
                    movieModel.setStory(finalObject.getString("director"));

                    List<MovieModel.Cast> castList = new ArrayList<>();
                    for (int j = 0; j<finalObject.getJSONArray("cast").length(); j++){
                        MovieModel.Cast cast = new MovieModel.Cast();
                        cast.setName(finalObject.getJSONArray("cast").getJSONObject(j).getString("name"));
                        castList.add(cast);
                    }
                    movieModel.setCastList(castList);
                    //adding the final object in the list
                    movieModelList.add(movieModel);
                }
                return movieModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<MovieModel> result) {
            super.onPostExecute(result);

            MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.row, result);
            lvMovies.setAdapter(adapter);
        }
    }

    public class MovieAdapter extends ArrayAdapter{

        private List<MovieModel> movieModelList;
        private int resource;
        private LayoutInflater inflater;
        public MovieAdapter(Context context, int resource, List<MovieModel> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = inflater.inflate(resource, null);
            }

            ImageView ivMovieIcon;
            TextView tvMovie;
            TextView tvTagline;
            TextView tvYear;
            TextView tvDuration;
            TextView tvDirector;
            RatingBar rbMovieRating;
            TextView tvCast;
            TextView tvStory;

            ivMovieIcon = (ImageView)findViewById(R.id.ivIcon);
            tvMovie = (TextView)convertView.findViewById(R.id.tvMovie);
            tvTagline = (TextView)convertView.findViewById(R.id.tvTagline);
            tvYear = (TextView)convertView.findViewById(R.id.tvYear);
            tvDuration = (TextView)convertView.findViewById(R.id.tvDuration);
            tvDirector = (TextView)convertView.findViewById(R.id.tvDirector);
            rbMovieRating = (RatingBar) convertView.findViewById(R.id.rbMovie);
            tvCast = (TextView)convertView.findViewById(R.id.tvCast);
            tvStory = (TextView)convertView.findViewById(R.id.tvStory);

            tvMovie.setText(movieModelList.get(position).getMovie());
            tvTagline.setText(movieModelList.get(position).getTagline());
            tvYear.setText("Year: " + movieModelList.get(position).getMovie());
            tvDuration.setText(movieModelList.get(position).getDuration());
            tvDirector.setText(movieModelList.get(position).getDirector());

            // rating bar
            rbMovieRating.setRating(movieModelList.get(position).getRating()/2);

            StringBuffer stringBuffer = new StringBuffer();
            for(MovieModel.Cast cast : movieModelList.get(position).getCastList()){
                stringBuffer.append(cast.getName() + ", ");
            }

            tvCast.setText(stringBuffer);
            tvStory.setText(movieModelList.get(position).getStory());

            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action bar item clicks here. The action bar will
        //automatically handle clicks on the Home/Up button, so long
        //as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            new JSONTASK().execute("http://jsonparsing.parseapp.com/jsonData/moviesData.txt");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}