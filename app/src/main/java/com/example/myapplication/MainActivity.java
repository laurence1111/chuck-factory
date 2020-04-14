package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.View;
import com.example.myapplication.Entities.Joke;
import com.example.myapplication.Entities.JokeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView mJoke;
    private ImageView mNewJoke;
    private Joke joke;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJoke = findViewById(R.id.mJoke);
        mNewJoke = findViewById(R.id.imageView2);
        mNewJoke.setImageResource(R.drawable.download);
        image = findViewById(R.id.imageView);
        image.setImageResource(R.drawable.title);

        mNewJoke.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {getAPI();}
        });
        getAPI();
    }

    protected void getAPI() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.chucknorris.io/").addConverterFactory(GsonConverterFactory.create()).build();
            JokeService service = retrofit.create(JokeService.class);
            Call<Joke> jokeCall = service.getJoke();
            jokeCall.enqueue(new Callback<Joke>() {
                @Override
                public void onResponse(Call<Joke> call, Response<Joke> response) {
                    if (response.isSuccessful()) {
                        joke = response.body();
                        mJoke.setText(joke.getValue());
                    } else {
                    }
                }
                @Override
                public void onFailure(Call<Joke> call, Throwable t) {
                    }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
