package ua.kpi.comsys.iv7216;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class FilmParse {
    private InputStream inputStream;
    private Film[] films;
    String data = "";

    public FilmParse(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void parse() throws IOException, JSONException {
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        data = new String(buffer, "UTF-8");
        JSONObject jsonObject = new JSONObject(data);
        JSONArray array = jsonObject.getJSONArray("Search");
        films = new Film[array.length()];
        for (int i = 0; i < array.length(); i++) {
            films[i] = new Film(
                    array.getJSONObject(i).getString("Title"),
                    array.getJSONObject(i).getString("Year"),
                    array.getJSONObject(i).getString("imdbID"),
                    array.getJSONObject(i).getString("Type"),
                    array.getJSONObject(i).getString("Poster").toLowerCase().replaceAll(".jpg", "")
                    );
        }
    }

    public Film[] getFilms() {
        return films;
    }
}
