import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * created by: darja
 * created at: 2022-08-02
 * using: IntelliJ IDEA
 */
public class HighScoreClient {

    private final String baseUrl;
    private final String path;
    private URL url;

    public HighScoreClient(String baseUrl,String path){
        this.baseUrl = baseUrl;
        this.path = path;
        DisableSSLVerification.disableSSL();
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
        int tries = 0;
        while(tries < 3){
            try(BufferedReader stream = new BufferedReader(new InputStreamReader(url.openStream()))) {
                System.out.println("Current try: " + (tries+1) + " to opening socket against:" + baseUrl + path);
                return stream.readLine();
            } catch (IOException e) {
                tries++;
            }
        }
        String error = "Could not send highscores to online service after 3 tries for some reason.";
        JOptionPane.showMessageDialog(null,error);
        return error;
    }
}
