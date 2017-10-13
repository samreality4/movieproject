package com.example.sam.movieproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.test.espresso.core.deps.guava.base.Throwables;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.movieproject.model.Movie;
import com.example.sam.movieproject.model.OtherData;
import com.example.sam.movieproject.model.OtherDataResult;
import com.example.sam.movieproject.remote.APIService;
import com.example.sam.movieproject.remote.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.id.list;

/**
 * Created by sam on 8/23/17.
 */

public class MovieDetailActivity extends AppCompatActivity implements OtherDataAdapter.trailerClickListener{

    private static final String TAG = MovieDetailActivity.class.getSimpleName();


    public final String API_KEY = BuildConfig.API_KEY;

   private RecyclerView recyclerView;

    private OtherDataAdapter otherDataAdapter;
    List<OtherData> trailerList;


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detailed, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);


        TextView tvOriginalTitle = (TextView) findViewById(R.id.original_title);
        ImageView ivPoster = (ImageView) findViewById(R.id.poster);
        TextView tvOverView = (TextView) findViewById(R.id.overview);
        TextView tvVoteAverage = (TextView) findViewById(R.id.vote_average);
        TextView tvReleaseDate = (TextView) findViewById(R.id.release_date);


        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("Movie");

        String title = movie.getmTitle();
        if (title == null) {
            tvOriginalTitle.setTypeface(null, Typeface.BOLD_ITALIC);
        }

        tvOriginalTitle.setText(movie.getmTitle());


        Picasso.with(this)
                .load(movie.getPosterPath())
                .resize(185,
                        275)
                .error(R.drawable.failure)
                .placeholder(R.drawable.loading)
                .into(ivPoster);


        String overView = movie.getmOverView();
        if (overView == null) {
            tvOverView.setTypeface(null, Typeface.BOLD_ITALIC);
            overView = getResources().getString(R.string.no_summary_bro);
        }
        tvOverView.setText(overView);
        tvVoteAverage.setText(movie.getDetailedVoteAverage());


        String releaseDate = movie.getmReleaseDate();
        if (releaseDate == null) {
            tvReleaseDate.setTypeface(null, Typeface.BOLD_ITALIC);
            releaseDate = getResources().getString(R.string.no_release_date_found);
        }
        tvReleaseDate.setText(releaseDate);

        String ID = movie.getmID();


        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<OtherDataResult> call = apiService.getMovieTrailer(ID,API_KEY);
        call.enqueue(new Callback<OtherDataResult>() {
            @Override
            public void onResponse(Call<OtherDataResult> call, Response <OtherDataResult> response) {
                trailerList = response.body().getResults();
                Log.i("list",response.body().toString());




                otherDataAdapter = new OtherDataAdapter(MovieDetailActivity.this,trailerList);
                recyclerView = (RecyclerView) findViewById(R.id.thumb_recylerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(otherDataAdapter);

                }




            @Override
            public void onFailure(Call<OtherDataResult> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }


    public void onTrailerItemClick(View v, int position) {
        OtherData otherData = trailerList.get(position);
        String YOUTUBE_TRAILER_LINK="https://www.youtube.com/watch?v=";
        String url = YOUTUBE_TRAILER_LINK+otherData.getKey();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }


}


