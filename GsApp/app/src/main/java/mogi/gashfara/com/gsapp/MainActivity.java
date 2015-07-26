package mogi.gashfara.com.gsapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ImageRecordsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new ImageRecordsAdapter(this);

        ListView listView = (ListView) findViewById(R.id.list1);
        listView.setAdapter(mAdapter);

        fetch();

    }

    private void fetch() {
        JsonObjectRequest request = new JsonObjectRequest(
                "http://gashfara.com/test/json.txt" ,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            List<ImageRecord> imageRecords = parse(jsonObject);

                            mAdapter.swapImageRecords(imageRecords);
                        }
                        catch(JSONException e) {
                            Toast.makeText(getApplicationContext(), "Unable to parse data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        VolleyApplication.getInstance().getRequestQueue().add(request);
    }
    private List<ImageRecord> parse(JSONObject json) throws JSONException {
        ArrayList<ImageRecord> records = new ArrayList<ImageRecord>();

        JSONArray jsonImages = json.getJSONArray("images");

        for(int i =0; i < jsonImages.length(); i++) {
            JSONObject jsonImage = jsonImages.getJSONObject(i);
            String title = jsonImage.getString("title");
            String url = jsonImage.getString("url");

            ImageRecord record = new ImageRecord(url, title);
            records.add(record);
        }

        return records;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
