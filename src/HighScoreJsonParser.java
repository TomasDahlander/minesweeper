import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * created by: darja
 * created at: 2022-08-24
 * using: IntelliJ IDEA
 */
public class HighScoreJsonParser {

    private static HighScore parseMapToHighScore(Map<String,String> map){
        return new HighScore(
                Integer.parseInt(map.get("time")),
                String.valueOf(map.get("date")),
                String.valueOf(map.get("difficulty")),
                String.valueOf(map.get("name"))
        );
    }

    public static List<HighScore> parseJsonListToHighScoreList(String jsonList){
        List<HighScore> list = new LinkedList<>();
        jsonList = jsonList.replace("[","").replace("]","");
        StringBuilder builder = new StringBuilder(jsonList);

        while (builder.length() > 0){
            int indexOfFirstJsonObjectStart = builder.indexOf("{");
            int indexOfFirstJsonObjectEnd = builder.indexOf("}");
            String jsonObject = builder.substring(indexOfFirstJsonObjectStart,indexOfFirstJsonObjectEnd+1);
            Map<String,String> jsonObjectMap = parseJsonToMap(jsonObject);
            HighScore highScore = parseMapToHighScore(jsonObjectMap);
            list.add(highScore);
            builder.delete(0,indexOfFirstJsonObjectEnd+2);
        }
        return list;
    }

    private static Map<String,String> parseJsonToMap(String json){
        json = json.replaceAll("[{\" }]","");
        StringBuilder builder = new StringBuilder(json);
        Map<String,String> map = new HashMap<>();

        for(int i = 0; i < 4; i++){
            int indexOfColon = 0;
            int indexOfSeparator;
            String key = null;
            String value;
            try{
                indexOfColon = builder.indexOf(":");
                key = builder.substring(0,indexOfColon);
                indexOfSeparator = builder.indexOf(",");
                value = builder.substring(indexOfColon+1,indexOfSeparator);
                builder.delete(0,indexOfSeparator+1);
            }catch(StringIndexOutOfBoundsException e){ // The last field in the json object = the last loop.
                value = builder.substring(indexOfColon+1);
            }
            map.put(key,value);
        }
        return map;
    }
}
