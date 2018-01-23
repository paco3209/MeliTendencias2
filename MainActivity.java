package com.example.francisco.melitendencias;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<TendenciasMeli>> {

    private static final String LOG_TAG = MainActivity.class.getName();

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://api.mercadolibre.com/sites/MLA/trends/search";

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    /** Adapter for the list of earthquakes */
    private MeliAdapter mAdapter;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView TendenciaListView = (ListView)findViewById(R.id.list);

        mAdapter = new MeliAdapter(this, new ArrayList<TendenciasMeli>());

        TendenciaListView.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,BusquedaActivity.class);
                startActivity(i);
            }
        });

        TendenciaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TendenciasMeli current = mAdapter.getItem(i);

                Uri MeliUri = Uri.parse(current.getUrl());

                Intent abirLink = new Intent(Intent.ACTION_VIEW,MeliUri);

                startActivity(abirLink);



            }
        });


                ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible

            // Update empty state with no connection error message

        }



    }

    @Override
    public Loader<List<TendenciasMeli>> onCreateLoader(int id, Bundle args) {

        return new MeliLoader(this,USGS_REQUEST_URL);
    }





    @Override
    public void onLoadFinished(Loader<List<TendenciasMeli>> loader, List<TendenciasMeli> melidatos) {

        if(melidatos !=null && !melidatos.isEmpty()){
            mAdapter.addAll(melidatos);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<TendenciasMeli>> loader) {
        mAdapter.clear();
    }
}
