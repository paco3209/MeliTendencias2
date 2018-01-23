package com.example.francisco.melitendencias;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by francisco on 17/1/18.
 */

public class MeliAdapter extends ArrayAdapter<TendenciasMeli> {
    public MeliAdapter(Context context, List<TendenciasMeli> tendencia) {

        super(context, 0, tendencia);

    }


    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activiy_list_item, parent, false);
        }

        TendenciasMeli tendeciaPoscion = getItem(position);

        // Find the earthquake at the given position in the list of earthquakes
        TextView keyword = (TextView)listItemView.findViewById(R.id.tendenciaTxt);
        String tendencia = tendeciaPoscion.getKeyword();
        keyword.setText(tendencia);

        TextView url = (TextView)listItemView.findViewById(R.id.urlTxt);
        String mUrl = tendeciaPoscion.getUrl();
        url.setText(mUrl);

        return listItemView;
    }


}
