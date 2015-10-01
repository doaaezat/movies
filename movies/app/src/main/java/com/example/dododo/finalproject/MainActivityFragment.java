package com.example.dododo.finalproject;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.dododo.finalproject.data.Contract;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A placeholder fragment containing a simple view.
 * public class MainActivityFragment extends Fragment {
 * <p/>
 * public MainActivityFragment() {
 * }
 *
 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
 * Bundle savedInstanceState) {
 * return inflater.inflate(R.layout.fragment_main, container, false);
 * }
 * }
 */
public class MainActivityFragment extends Fragment {

    private customeAdapter myAdapter;
    GridView gridview;

       /**
        * A callback interface that all activities containing this fragment must
         * implement. This mechanism allows activities to be notified of item
        * selections.
        */
              public interface Callback {
             /**
                * DetailFragmentCallback for when an item has been selected.
                */
                     public void onItemSelected(Uri dateUri);
           }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridview = (GridView) rootView.findViewById(R.id.idofgridview);
        updateView();

        // myAdapter.notifyDataSetChanged(); b3d lta8eer bt3ml save ll4akl lgded
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                gson.ResultsEntity gsonobj = new gson.ResultsEntity();
                //gson.ResultsEntity opject = (gson.ResultsEntity) getItem(position);
                gsonobj = (gson.ResultsEntity) myAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailFragment.class);
                intent.putExtra("movieId", gsonobj.getId());


                startActivity(intent);

            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    private void updateView() {

        Task task = new Task();

        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = sharedPrefs.getString(getString(R.string.pref_units_key), getString(R.string.pref_most_popular));

        if (sortBy.equals(getString(R.string.pref_highest_rated))) {

            try {
                String Str1 = task.execute("http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=04db6a4e0e321dd1bec24ff22c995709").get();
                gson gsonobject1 = new gson();
                Gson parser1 = new Gson();
                gsonobject1 = parser1.fromJson(Str1, gson.class);

                myAdapter = new customeAdapter(getActivity(), gsonobject1.getResults());
                gridview.setAdapter(myAdapter);
                for (gson.ResultsEntity object : gsonobject1.getResults())
                    addhighrate(object.getId(), object.getOriginal_title(), object.getOverview(), String.valueOf(object.getVote_average()),
                            object.getRelease_date(), object.getPoster_path());

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        if (sortBy.equals(getString(R.string.pref_most_popular))) {
              if (task==null){

                   gson.ResultsEntity gsonob = new gson.ResultsEntity();
//                    gsonob = retrivemostpopobj();
//                    gsonob.getPoster_path();

           }
             else {
            try {
                String Str2 = task.execute("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=04db6a4e0e321dd1bec24ff22c995709").get();
                gson gsonobject = new gson();
                Gson parser = new Gson();
                gsonobject = parser.fromJson(Str2, gson.class);
                myAdapter = new customeAdapter(getActivity(), gsonobject.getResults());
                gridview.setAdapter(myAdapter);
                for (gson.ResultsEntity object : gsonobject.getResults())
                    addmostpop(object.getId(), object.getOriginal_title(), object.getOverview(), String.valueOf(object.getVote_average()),
                            object.getRelease_date(), object.getPoster_path());

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
    }


    long addmostpop(int id, String title, String overview, String rate, String relase_date, String image) {
        long mostpopId = 0;
        Uri uriFindByID = Contract.mostpopEntry.CONTENT_URI.buildUpon()
                .appendPath(String.valueOf(id)).build();
        Cursor mostpopCursor = getActivity().getContentResolver().query(
                uriFindByID,
                new String[]{Contract.mostpopEntry.ColumnMovieID, Contract.mostpopEntry.COLUMN_titel},
                Contract.mostpopEntry.ColumnMovieID + " = ?",
                new String[]{String.valueOf(id)},
                null);


        if (mostpopCursor.moveToFirst()) {
            int mostpopIdIndex = mostpopCursor.getColumnIndex(Contract.mostpopEntry.ColumnMovieID);
            mostpopId = mostpopCursor.getLong(mostpopIdIndex);

        } else {
            // Now that the content provider is set up, inserting rows of data is pretty simple.
            // First create a ContentValues object to hold the data you want to insert.


            ContentValues mostpopValues = new ContentValues();


            mostpopValues.put(Contract.mostpopEntry.COLUMN_titel, title);
            mostpopValues.put(Contract.mostpopEntry.COLUMN_overview, overview);
            mostpopValues.put(Contract.mostpopEntry.COLUMN_rated, rate);
            mostpopValues.put(Contract.mostpopEntry.COLUMN_releasdate, relase_date);
            mostpopValues.put(Contract.mostpopEntry.COLUMN_image, image);


            Uri insertedUri = getActivity().getContentResolver().insert(
                    Contract.mostpopEntry.CONTENT_URI,
                    mostpopValues
            );
            mostpopId = ContentUris.parseId(insertedUri);

        }
        mostpopCursor.close();
        return mostpopId;

    }

    long addhighrate(int id, String title, String overview, String rate, String relase_date, String image) {
        long highrateId = 0;
        Uri uriFindByID = Contract.highrateEntry.CONTENT_URI.buildUpon()
                .appendPath(String.valueOf(id)).build();
        Cursor highrateCursor = getActivity().getContentResolver().query(
                uriFindByID,
                new String[]{Contract.highrateEntry.ColumnMovieID, Contract.highrateEntry.COLUMN_titel},
                Contract.highrateEntry.ColumnMovieID + " = ?",
                new String[]{String.valueOf(id)},
                null);


        if (highrateCursor.moveToFirst()) {
            int highrateIdIndex =highrateCursor.getColumnIndex(Contract.mostpopEntry.ColumnMovieID);
            highrateId = highrateCursor.getLong(highrateIdIndex);

        } else {
            // Now that the content provider is set up, inserting rows of data is pretty simple.
            // First create a ContentValues object to hold the data you want to insert.


            ContentValues highrateValues = new ContentValues();


            highrateValues.put(Contract.highrateEntry.COLUMN_titel, title);
            highrateValues.put(Contract.highrateEntry.COLUMN_overview, overview);
            highrateValues.put(Contract.highrateEntry.COLUMN_rated, rate);
            highrateValues.put(Contract.highrateEntry.COLUMN_releasdate, relase_date);
            highrateValues.put(Contract.highrateEntry.COLUMN_image, image);


            Uri insertedUri = getActivity().getContentResolver().insert(
                    Contract.mostpopEntry.CONTENT_URI,
                    highrateValues
            );
            highrateId = ContentUris.parseId(insertedUri);

        }
        highrateCursor.close();
        return highrateId;

    }


    gson.ResultsEntity retrivemostpopobj  (int id ){
        Uri uriFindByID = Contract.mostpopEntry.CONTENT_URI.buildUpon()
                .appendPath(String.valueOf(id)).build();
        Cursor mostpopCursor = getActivity().getContentResolver().query(
                uriFindByID,
                new String[]{Contract.highrateEntry.ColumnMovieID, Contract.highrateEntry.COLUMN_titel},
                Contract.highrateEntry.ColumnMovieID + " = ?",
                new String[]{String.valueOf(id)},
                null);

         gson.ResultsEntity gsonObject = new gson.ResultsEntity();
         gsonObject.setOriginal_title(mostpopCursor.getString(mostpopCursor.getColumnIndex(Contract.mostpopEntry.COLUMN_titel)));
             gsonObject.setOverview(mostpopCursor.getString(mostpopCursor.getColumnIndex(Contract.mostpopEntry.COLUMN_overview)));
            gsonObject.setPoster_path(mostpopCursor.getString(mostpopCursor.getColumnIndex(Contract.mostpopEntry.COLUMN_image)));
            gsonObject.setRelease_date(mostpopCursor.getString(mostpopCursor.getColumnIndex(Contract.mostpopEntry.COLUMN_releasdate)));
             gsonObject.setVote_average(mostpopCursor.getDouble(mostpopCursor.getColumnIndex(Contract.mostpopEntry.COLUMN_rated)));
         return  gsonObject ;

     }

    gson.ResultsEntity retrivehighrateobj(int id) {
        Uri uriFindByID = Contract.highrateEntry.CONTENT_URI.buildUpon()
                .appendPath(String.valueOf(id)).build();
        Cursor highrateCursor = getActivity().getContentResolver().query(
                uriFindByID,
                new String[]{Contract.highrateEntry.ColumnMovieID, Contract.highrateEntry.COLUMN_titel},
                Contract.highrateEntry.ColumnMovieID + " = ?",
                new String[]{String.valueOf(id)},
                null);


        gson.ResultsEntity gsonObject = new gson.ResultsEntity();
        gsonObject.setOriginal_title(highrateCursor.getString(highrateCursor.getColumnIndex(Contract.highrateEntry.COLUMN_titel)));
        gsonObject.setOverview(highrateCursor.getString(highrateCursor.getColumnIndex(Contract.highrateEntry.COLUMN_overview)));
        gsonObject.setPoster_path(highrateCursor.getString(highrateCursor.getColumnIndex(Contract.highrateEntry.COLUMN_image)));
        gsonObject.setRelease_date(highrateCursor.getString(highrateCursor.getColumnIndex(Contract.highrateEntry.COLUMN_releasdate)));
        gsonObject.setVote_average(highrateCursor.getDouble(highrateCursor.getColumnIndex(Contract.highrateEntry.COLUMN_rated)));
        return gsonObject;

    }
}
