import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * created by: darja
 * created at: 2022-08-02
 * using: IntelliJ IDEA
 */
public class HighScoreClient {

    private static final String[] keys = {"time","date","difficulty","name"};

    private String baseUrl;
    private String path;
    private URL url;

    public HighScoreClient(String path){
        this.baseUrl = "http://localhost:8080/highscore";
        this.path = path;
        computeUrl();
    }

    private void computeUrl() {
        try {
            this.url = new URL(baseUrl+path);
        } catch (MalformedURLException e) {
            throw new RuntimeException(
                    "Could not create and url from the parameters below" +
                            "\nBaseUrl: " + baseUrl + "\nPath: " + path + "\nError: " + e);
        }
    }

    public String fetchDataString() {
        try(BufferedReader stream = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return stream.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Problem opening a stream against url: " + url.toString() + "\nError: " + e);
        }
    }

    public Map<String,String> parseJsonToMap(String json){
//        json = json.replaceAll("[{\"}]",""); // If we were to send an object
        StringBuilder builder = new StringBuilder(json);
        Map<String,String> map = new HashMap<>();

        for(String key : keys){
            String value;
            try{
                int indexOfSeparator = builder.indexOf(",");
                value = builder.substring(key.length()+1,indexOfSeparator);
                builder.delete(0,indexOfSeparator+1);
            }catch(StringIndexOutOfBoundsException e){
                value = builder.substring(key.length()+1);
            }
            map.put(key,value);
        }
        return map;
    }

    public HighScore parseMapToHighScore(Map<String,String> map){
        HighScore h = new HighScore(
                Integer.parseInt(map.get("time")),
                String.valueOf(map.get("date")),
                String.valueOf(map.get("difficulty")),
                String.valueOf(map.get("name"))
        );
        return h;
    }

    public static void main(String[] args) {
        HighScoreClient h1 = new HighScoreClient("/test");
        String json = h1.fetchDataString();
        System.out.println(json);
        Map<String, String> map = h1.parseJsonToMap(json);
        HighScore highScore = h1.parseMapToHighScore(map);
        System.out.println(highScore);

        HighScoreClient h2 = new HighScoreClient("/list");
        String json2 = h2.fetchDataString();
        System.out.println(json2);
    }
}
