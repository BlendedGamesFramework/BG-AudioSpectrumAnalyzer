package github.bewantbe.audio_analyzer_for_android;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.regex.Pattern;


public class RetrieveFeedTask extends AsyncTask<String, Void, Integer> {
    private static final String USER_AGENT = "Mozilla/5.0";



    protected String reverseString(String input){
        // getBytes() method to convert string
        // into bytes[].
        byte[] strAsByteArray = input.getBytes();

        byte[] result = new byte[strAsByteArray.length];

        // Store result in reverse order into the
        // result byte[]
        for (int i = 0; i < strAsByteArray.length; i++) {
            result[i] = strAsByteArray[strAsByteArray.length - i - 1];
        }

        return new String(result);

    }

    public Integer doInBackground(String... urls) {
        int result = 0;
        URL obj;
        obj = null;
        try {
            obj = new URL(urls[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) obj.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert con != null;
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode;
        responseCode = 0;
        try {
            responseCode = con.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("GET Response Code :: " + responseCode);
        Log.i("RESPONSE CODE", String.valueOf(responseCode));
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(
                        con.getInputStream(), "UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String inputLine;
            inputLine = null;
            StringBuffer response = new StringBuffer();

            while (true) {
                try {
                    if (!((inputLine = in.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                response.append(inputLine);
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // print result
            Log.i("RESPONSE CODE", response.toString());
            Log.i("RESPONSE TYPE", response.getClass().toString());
            //Response tiene comillas "" extras por lo que hay que quitarselas para que sea un json valido
            String responseString = response.toString();
            String newResponseString = responseString.replaceFirst("\"", "");
            String reverseString = reverseString(newResponseString);
            String newReverseString = reverseString.replaceFirst("\"", "");
            String newFinalString = reverseString(newReverseString);
            String newFinalString3 = newFinalString.replaceAll(Pattern.quote("\\"), "");

            GlobalClass globalVariable = GlobalClass.getInstance();
            try {
                JSONObject jsonObj = new JSONObject(newFinalString3);
                Log.i("Actual id_player", jsonObj.get("id_players").toString());

                globalVariable.setId_player((int) jsonObj.get("id_players"));
                int id_player = globalVariable.getId_player();
                Log.i("Actual id_player in global variables", String.valueOf(id_player));
                result = id_player;
                return result;


            } catch (JSONException e) {
                e.printStackTrace();
                return result;

            }
        } else {
            Log.i("RESPONSE CODE", "GET request not worked");
            System.out.println("GET request not worked");
            return result;


        }
    }

    protected void onPostExecute(Void result) {

    }

}