package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private ImageView bam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting and declaring XML elements
        mJoke = findViewById(R.id.mJoke);
        mNewJoke = findViewById(R.id.imageView2);
        mNewJoke.setImageResource(R.drawable.download);
        image = findViewById(R.id.imageView);
        image.setImageResource(R.drawable.title);
        bam = findViewById(R.id.imageView3);
        bam.setImageResource(R.drawable.bam1);
        bam.setVisibility(View.GONE);

        // Reducing scale of imageView3 and setting image of imageView3
        //BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inScaled = false;
        //Bitmap source = BitmapFactory.decodeResource(bam.getResources(), R.drawable.bam1, options);
        //bam.setImageBitmap(source);

        // Setting onClickListener to run getAPI method when clicked
        mNewJoke.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                getAPI();
            }
        });
        getAPI();
    }

    // API method
    protected void getAPI() {
        mJoke.setText("");
        bam.clearAnimation();
        bam.setVisibility(View.VISIBLE);
        // Try catch is used to call the API
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
                        mJoke.setText("Error 69");
                    }
                }
                @Override
                public void onFailure(Call<Joke> call, Throwable t) {
                    mJoke.setText("Error 69");
                    }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
            mJoke.setText("Error 69");
        }
        bam.clearAnimation();
        bam.setVisibility(View.GONE);
    }
}
