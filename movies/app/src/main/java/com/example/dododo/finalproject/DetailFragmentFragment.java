package com.example.dododo.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragmentFragment extends Fragment {

    public DetailFragmentFragment() {
    }
    static final String DETAIL_URI = "URI";
    private Uri mUri;
    ImageView imageview ;
    TextView title ;
    TextView overview ;
    TextView  rating ;
    TextView release ;
    Gson gson = new Gson();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

              Bundle arguments = getArguments();
               if (arguments != null) {
                      mUri = arguments.getParcelable(DetailFragmentFragment.DETAIL_URI);
                   }

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();


        int movieId = intent.getExtras().getInt("movieId");
        gson object = (gson) intent.getSerializableExtra("myobj");


        Task task = new Task();
        try {
            String resultStr = task.execute("https://api.themoviedb.org/3/movie/"+ movieId + "?api_key=f5a335aed31b96b3b82ccc2101bebc07").get();
            gson.ResultsEntity gsonobject = new gson.ResultsEntity();
            Gson parser = new Gson();
            gsonobject = parser.fromJson(resultStr, gson.ResultsEntity.class);

            title = (TextView)rootView. findViewById(R.id.title);
            title.setText(gsonobject.getTitle());
            overview = (TextView) rootView.findViewById(R.id.overview);
            overview.setText(gsonobject.getOverview());

            release = (TextView)rootView. findViewById(R.id.release_date);
            release.setText(gsonobject.getRelease_date());

            rating = (TextView) rootView.findViewById(R.id.rating);
            rating.setText(gsonobject.getVote_average()+"");

            imageview= (ImageView) rootView.findViewById(R.id.imageviewditals);
            String imageurl = gsonobject.getPoster_path();

//
            Picasso.with(getActivity()).load("https://image.tmdb.org/t/p/w185" + imageurl).into(imageview);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return rootView ;
    }


}
