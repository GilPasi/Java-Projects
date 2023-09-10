/*Authors : Yulia Moshan 319565610
 * 			Gil Pasi 	 206500936
 * Algorithm planning - assignment 3 
 * */
package utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Importer {
    public static void main(String[] args) {
        try {
            String resourceId = "b9d690de-0a9c-45ef-9ced-3e5957776b26";
            int limit = 5;
            String query = "jones";

            String apiUrl = "https://data.gov.il/he/api/3/action/datastore_search";
            String queryString = String.format("resource_id=%s&limit=%d&q=%s",
                    URLEncoder.encode(resourceId, "UTF-8"), limit, URLEncoder.encode(query, "UTF-8"));

            URL url = new URL(apiUrl + "?" + queryString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            conn.disconnect();

            // Parse the JSON response and extract the desired information
            // Note: You'll need to use a JSON library for parsing the response

            System.out.println("Total results found: " + getTotalResults(response.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getTotalResults(String response) {
        // Implement your JSON parsing logic here to extract the total results
        // This is just a placeholder
        return 0;
    }
}