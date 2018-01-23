package com.example.francisco.melitendencias;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by francisco on 17/1/18.
 */

public class MeliLoader extends AsyncTaskLoader<List<TendenciasMeli>> {

    private static final String LOG_TAG = MeliLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public MeliLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<TendenciasMeli> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<TendenciasMeli> tendencias = ParsearJSon.fetchEarthquakeData(mUrl);
        return tendencias;
    }



}
