package wtf.dizzle.csgostate.eventsounds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.brekcel.csgostate.Server;
import com.brekcel.csgostate.post.PostHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * TODO Description.
 * @author Mitch Gardner
 *
 */
public class EventSounds {

    private final Map<String, Sound> soundMap = new HashMap<String, Sound>();
    private Gson gson;

    private static int port = 3000;
    private static String authKey = "";

    /**
     * @param args
     */
    public static void main(String[] args) {
    	String filePath = "SoundConfig.cfg";
    	if(args.length == 0) {
            System.out.println("Unable to load config file, using default SoundConfig.cfg in local directory.");
    	}
        if(args.length >= 1) {
        	filePath = args[0];
        }
        if(args.length >= 2) {
            port = Integer.parseInt(args[1]);
        }
        if(args.length >= 3) {
            authKey = args[2];
        }
        File configFile = new File(filePath);
        if(!configFile.exists() || !configFile.isFile()) {
        	System.out.println(" Unable to open config file: " + configFile.getAbsolutePath());
        	System.exit(0);
        }
        
        EventSounds eventSounds = new EventSounds(filePath);
        eventSounds.process();
    }

    /**
     *
     */
    public EventSounds(String filepath) {
        super();
        loadSounds(filepath);
    }

    private void loadSounds(String filepath) {
        try {
            gson = new GsonBuilder().create();
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String jsonstring = IOUtils.toString(br);

            JSONObject jObject = new JSONObject(jsonstring.trim());
            JsonParser parser = new JsonParser();
            JsonObject obj = parser.parse(jsonstring).getAsJsonObject();
            Iterator<?> keys = jObject.keys();

            while( keys.hasNext() ){
                String key = (String) keys.next();
                JsonObject objVal = obj.get(key).getAsJsonObject();
                Sound sound = new Sound();
                sound.setFile(objVal.get("file").getAsString());
                JsonElement volumeObj = objVal.get("volume");
                if(volumeObj != null)  {
                    sound.setVolume(volumeObj.getAsFloat());
                }
                File soundFile = sound.getFile();
                if(soundFile==null || !soundFile.exists() || !soundFile.isFile()) {
                    System.err.println("Cannot find file: " + soundFile.toString());
                    continue;
                }
                System.out.println("Sound added: " + key + " - " + soundFile);
                soundMap.put(key, sound);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    @SuppressWarnings("unused")
    private void process() {
        try {
            PostHandler postHandler = new StatePostHandler(soundMap);
            new Server(port, postHandler, true, authKey);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
