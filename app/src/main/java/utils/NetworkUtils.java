package utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Prem on 28-10-2017.
 * This is a Utils class that handles operation related to networking and to fetch information
 */

public class NetworkUtils {

    //Build the URL to query the data
    ///http://go.udacity.com/android-baking-app-json

    public static URL buildURL() {

        Uri buildUrl = Uri.parse(Constants.BAKING_BASE_URL).buildUpon()
                .appendEncodedPath(Constants.BAKING_SUPPORT_URL)
                .build();
        return convertAndroidUrltoJavaUrl(buildUrl);
    }

    private static URL convertAndroidUrltoJavaUrl(Uri uriToConvert) {
        URL url = null;
        try {
            url = new URL(uriToConvert.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * This method returns the entire result from the Http response
     * throws IOException related to network and stream reading
     * params url The URL to fetch the HTTP response from.
     * returns The contents of Http response
     */

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        //Create a HttpURLConnection object
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
