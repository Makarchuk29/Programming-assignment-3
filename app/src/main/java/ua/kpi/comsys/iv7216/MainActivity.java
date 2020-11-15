package ua.kpi.comsys.iv7216;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.*;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends ListActivity {
    private Film[] films;
    private int[] posters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            InputStream inputStream = getApplicationContext().getAssets().open("MoviesList.json");
            FilmParse filmParse = new FilmParse(inputStream);
            filmParse.parse();
            films = filmParse.getFilms();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        posters = new int[films.length];
        for (int i = 0; i < posters.length; i++) {
            if (films[i].getPoster().equals("") || getResources().getIdentifier(films[i].getPoster(), "drawable", getPackageName()) == 0) {
                posters[i] = getResources().getIdentifier("poster_error", "drawable", getPackageName());
                ;
            } else {
                posters[i] = getResources().getIdentifier(films[i].getPoster(), "drawable", getPackageName());
            }
        }
        FilmAdapter mAdapter = new FilmAdapter(this);
        setListAdapter(mAdapter);
    }

    private class FilmAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;

        FilmAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return films.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = mLayoutInflater.inflate(R.layout.activity_main, null);

            ImageView image = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            image.setImageResource(posters[position]);

            TextView titleTextView = (TextView) convertView.findViewById(R.id.textViewTitle);
            titleTextView.setText(films[position].getTitle());

            TextView dateTextView = (TextView) convertView.findViewById(R.id.textViewDate);
            dateTextView.setText(films[position].getYear());

            TextView typeTextView = (TextView) convertView.findViewById(R.id.textViewType);
            typeTextView.setText(films[position].getType());

            return convertView;
        }
    }
}