package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.json.JSONHelpers;

/**
 * Created by magulo on 8/29/16.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

//import org.foodrev.android_example_recycler_view.MyJsonAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.json.MyJsonAdapter;

/**
 * Created by magulo on 5/30/16.
 */
public class JSONAsyncRequest extends AsyncTask<String, Void, String> {

    private Context context;
    private RecyclerView mRecyclerView;
    private ArrayAdapter<String> adapter;

    private MyJsonAdapter mAdapter;
    public JSONAsyncRequest(Context context, RecyclerView mRecyclerView) {
        this.context = context;
        this.mRecyclerView = mRecyclerView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... urls) {
        String result = null;
        try {
            result = doPut2("http://ec2-52-25-100-113.us-west-2.compute.amazonaws.com:5000/plan");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param in
     * @return
     * @throws IOException
     */
    private String readStream(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    public String doGetRequest(String urlString) throws IOException {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                return readStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String doPut2(String urlString) throws IOException {
        OutputStream os = null;
        HttpURLConnection httpcon;
        String url = null;
        String data = null;
        String result = null;
        try{
//Connect
            JSONObject plan_request = createPlanRequest();
            URL foodrev_url = new URL(urlString);
            httpcon = (HttpURLConnection) foodrev_url.openConnection();
            httpcon.setReadTimeout(10000);
            httpcon.setConnectTimeout(15000);
            httpcon.setRequestMethod("POST");
            httpcon.setDoInput(true);
            httpcon.setDoOutput(true);
            if (plan_request != null) {
                httpcon.setFixedLengthStreamingMode(plan_request.toString().getBytes().length);
            }
            httpcon.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpcon.setRequestProperty("Accept", "application/json");
            httpcon.connect();
//Write
            os = httpcon.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(plan_request.toString());
            writer.close();
            os.flush();

            InputStreamReader isr = new InputStreamReader(httpcon.getInputStream());
            BufferedReader br  = new BufferedReader(isr);
//Read
//            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"UTF-8"));
//            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            result = sb.toString();
            Log.d(result,"is the result");

            return result;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            os.close();
        }
        return null;

    }

    //create JSON
    private JSONObject createPlanRequest() {
        try {
            JSONObject plan_request = new JSONObject();

            JSONArray jsonArray = new JSONArray();

            Log.d("Checkpoint 3","TAG");
            //persons
            // '{"persons":["Alice", "Bob", "Charlie"],
            jsonArray.put("Alice");
            jsonArray.put("Bob");
            jsonArray.put("Charlie");

            plan_request.put("persons",jsonArray);
            jsonArray = new JSONArray();

//            Log.d("plan request is " + plan_request.toString() + "\n\n","TAG");
            //locations
            // "locations":["loc1", "loc2", "loc3"],
            jsonArray.put("loc1");
            jsonArray.put("loc2");
            jsonArray.put("loc3");

            plan_request.put("locations",jsonArray);
            jsonArray = new JSONArray();

//            Log.d("plan request is " + plan_request.toString() + "\n\n","TAG");
            //cars
            // "cars":["car1", "car2"],
            jsonArray.put("car1");
            jsonArray.put("car2");

            plan_request.put("cars",jsonArray);
            jsonArray = new JSONArray();

//            Log.d("plan request is " + plan_request.toString() + "\n\n","TAG");
            //at_persons
            // "at_persons":[["Alice","loc1"], ["Bob", "loc1"], ["Charlie", "loc1"]],

            JSONArray[] jsonArrays = new JSONArray[3];
            jsonArrays[0] = new JSONArray();
            jsonArrays[1] = new JSONArray();
            jsonArrays[2] = new JSONArray();

            jsonArrays[0].put("Alice");
            jsonArrays[0].put("loc1");

            jsonArrays[1].put("Bob");
            jsonArrays[1].put("loc1");

            jsonArrays[2].put("Charlie");
            jsonArrays[2].put("loc1");

            jsonArray.put(jsonArrays[0]);
            jsonArray.put(jsonArrays[1]);
            jsonArray.put(jsonArrays[2]);

            plan_request.put("at_persons",jsonArray);
            jsonArray = new JSONArray();
            jsonArrays[0] = new JSONArray();
            jsonArrays[1] = new JSONArray();
            jsonArrays[2] = new JSONArray();


//            Log.d("plan request is " + plan_request.toString() + "\n\n","TAG");
            //at_cars
            // "at_cars":[["car1", "loc1"], ["car2", "loc2"]],
            jsonArrays[0].put("car1");
            jsonArrays[0].put("loc1");

            jsonArrays[1].put("car2");
            jsonArrays[1].put("loc2");

            jsonArray.put(jsonArrays[0]);
            jsonArray.put(jsonArrays[1]);

            plan_request.put("at_cars",jsonArray);
            jsonArray = new JSONArray();
            jsonArrays[0] = new JSONArray();
            jsonArrays[1] = new JSONArray();
//            Log.d("plan request is " + plan_request.toString() + "\n\n","TAG");
            //car_capacities
            // "car_capacities":[["car1", 100], ["car2", 100]],
            jsonArrays[0].put("car1");
            jsonArrays[0].put(100);

            jsonArrays[1].put("car2");
            jsonArrays[1].put(100);

            jsonArray.put(jsonArrays[0]);
            jsonArray.put(jsonArrays[1]);

            plan_request.put("car_capacities",jsonArray);

            jsonArray = new JSONArray();
            jsonArrays[0] = new JSONArray();
            jsonArrays[1] = new JSONArray();

//            Log.d("plan request is " + plan_request.toString() + "\n\n","TAG");
            //supply_init
            // "supply_init":[["loc2", 200]],
            jsonArrays[0].put("loc2");
            jsonArrays[0].put(200);

            jsonArray.put(jsonArrays[0]);
            plan_request.put("supply_init",jsonArray);
            jsonArray = new JSONArray();
            jsonArrays[0] = new JSONArray();

//            Log.d("plan request is " + plan_request.toString() + "\n\n","TAG");
            //demand_init
            // "demand_init":[["loc3", 200]]'
            jsonArrays[0].put("loc3");
            jsonArrays[0].put(200);

            jsonArray.put(jsonArrays[0]);
            plan_request.put("demand_init",jsonArray);
//            Log.d("plan request is " + plan_request.toString() + "\n\n","TAG");

            Log.d(plan_request.toString(),"Plan Request");
            return plan_request;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //update UI here
    @Override
    protected void onPostExecute(String result){
        String[] myDataset = parse(result);

        //specify an adapter
        mAdapter = new MyJsonAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        Toast.makeText(this.context, result, Toast.LENGTH_LONG).show();

        mAdapter.notifyDataSetChanged();

    }

     public String[] parse(String jsonLine) {
         List<String> planStepsList = new ArrayList<String>();
        JsonElement jElement = new JsonParser().parse(jsonLine);
        JsonObject jObject = jElement.getAsJsonObject();
        JsonArray jArray = jObject.getAsJsonArray("plan_steps");

         for (JsonElement entry : jArray) {
             planStepsList.add(entry.toString());
         }
         String[] myDataset = new String[planStepsList.size()];

         myDataset = planStepsList.toArray(myDataset);

         for(int i =0; i<myDataset.length; i++){
             myDataset[i] = formatString(myDataset[i]);
         }

        return  myDataset;
    }

    public String formatString(String unformattedLine){
        String formattedLine = "";
        String wordArray[] = unformattedLine.replaceAll("\"", "").replaceAll(":","").split("\\s+");
        Integer stepNum = Integer.parseInt(wordArray[0]);
        String firstWord = wordArray[1];


        //categorize types of lines
        if (firstWord.equals("DRIVE")){
            formattedLine += wordArray[2] + " " + wordArray[1] + "s from "
                    + wordArray[4] + " to " + wordArray[5];
        }
        else if (firstWord.equals("LOAD")){
            formattedLine += wordArray[2] + " " + wordArray[1] + "s from "  + wordArray[3];
        }
        else if (firstWord.equals("UNLOAD")){
            formattedLine += wordArray[2] + " " + wordArray[1] + "s from " + wordArray[3];
        }

        return formattedLine;
    }
}