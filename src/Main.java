import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("Hello world!");
        System.out.println("This is an example!");

        // Skapa ett JSON Object
       JSONObject jsonOb = new JSONObject();

       // Spara värden i JSON Object
        jsonOb.put("namn", "Marcus");
        jsonOb.put("age", 34);

        // Skriva ut värden
        System.out.println("Mitt namn är: " + jsonOb.get("namn"));
        System.out.println("Jag är " + jsonOb.get("age") + " år gammal");

//        fetchJsonFromFile();
        fetchJsonFromAPI();
    }

    private static void fetchJsonFromFile() throws IOException, ParseException {
        String filepath = "/Users/idaroos/Documents/JSON/data.json";
        //Hämta data från JSON fil
        JSONObject fetchData = (JSONObject) new JSONParser().parse(new FileReader(filepath));
        // Konvertera data till ett JSONObject
        JSONObject p1 = (JSONObject) fetchData.get("p1");
        JSONObject p2 = (JSONObject) fetchData.get("p2");

        // Hämta och skriv ut data
        String nameP1 = (String) p1.get("name"), nameP2=p2.get("name").toString();
        int ageP1 = Integer.parseInt(p1.get("age").toString()) , ageP2 = Integer.parseInt(p2.get("age").toString());

        System.out.println("Mitt namn är " + nameP1 + " och jag är " + ageP1 + " år gammal.");
        System.out.println("Mitt namn är " + nameP2 + " och jag är " + ageP2 + " år gammal.");
    }

    static void fetchJsonFromAPI() throws IOException, ParseException {
     // Spara URL till API
     URL url = new URL ("https://api.wheretheiss.at/v1/satellites/25544");
     HttpURLConnection conn = (HttpURLConnection) url.openConnection();

     // Hämta data och koppla API:t
        conn.setRequestMethod("GET"); // Sätter request method till GET för att hämta datan
        conn.connect(); // utför kopplingen till API:t


        if(conn.getResponseCode() == 200) { // kolla om kopplingen lyckades eller inte
            System.out.println("Koppling lyckades");
        }else{
            System.out.println("Koppling misslyckades");
        }
        StringBuilder strData = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());
        while (scanner.hasNext()) {
            strData.append(scanner.nextLine());
        }
        scanner.close();

        JSONObject dataObject = (JSONObject) new JSONParser().parse(String.valueOf(strData));

        String latitudeObj = (String) dataObject.get("latitude").toString();
        String longitudeObj = (String) dataObject.get("longitude").toString();
        String nameObj = (String) dataObject.get("name");

        System.out.println(dataObject);
        System.out.println("Name: " + nameObj + " Latitude: " + latitudeObj + " Longitude: " + longitudeObj);
    }
};