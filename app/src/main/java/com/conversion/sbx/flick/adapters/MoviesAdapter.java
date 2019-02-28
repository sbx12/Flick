package com.conversion.sbx.flick.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.conversion.sbx.flick.DetailActivity;
import com.conversion.sbx.flick.R;
import com.conversion.sbx.flick.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import butterknife.InjectView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;
    int yesP = 1, noP = 0;
    @InjectView(R.id.tvPoster)
    ImageView tvPoster;
    @InjectView(R.id.tvtitle)
    TextView tvtitle;
    @InjectView(R.id.tvOverView)
    TextView tvOverView;
    @InjectView(R.id.tvRating)
    TextView tvRating;
    @InjectView(R.id.containerimovie)
    RelativeLayout containerimovie;


    public MoviesAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("smile", "onCreateViewHolder");
        View view;
        if (getItemViewType(viewType) == yesP)
            view = LayoutInflater.from(context).inflate(R.layout.item_movie_5star, parent, false);
        else
            view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("smile", "onBindViewHolder" + position);
        Movie movie = movies.get(position);
        if (getItemViewType(position) == yesP)
            holder.bindPop(movie);
        else
            holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (movies.get(position).isPopular())
            return yesP;
        else
            return noP;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        TextView tvRating;
        ImageView tvPoster;
        RelativeLayout container;


        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvtitle);
            tvOverview = itemView.findViewById(R.id.tvOverView);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvPoster = itemView.findViewById(R.id.tvPoster);
            container = itemView.findViewById(R.id.containerimovie);
        }

        //For the under 5 stars movies
        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            tvRating.setText(movie.getVoteAverage() + "/10");

            String imageUrl = movie.getPosterPath();

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
            }
            //NAV to
            Glide.with(context).load(imageUrl).apply(new RequestOptions().placeholder(R.drawable.moon1).transform(new RoundedCornersTransformation(20, 20))).into(tvPoster);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }

            });
        }

        //For the poplar movies
        public void bindPop(final Movie movie) {
            String imageUrl;
            imageUrl = movie.getBackdropPath();

            Glide.with(context).load(imageUrl).apply(new RequestOptions().placeholder(R.drawable.moon1).transform(new RoundedCornersTransformation(20, 20))).into(tvPoster);


        }
    }
}
