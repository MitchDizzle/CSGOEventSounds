package wtf.dizzle.csgostate.eventsounds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.brekcel.csgostate.Server;
import com.brekcel.csgostate.post.PostHandler;


/**
 * TODO Description.
 * @author Mitch Gardner
 *
 */
public class EventSounds {

    private Map<String, Sound> soundMap = new HashMap<String, Sound>();
    private Gson gson;

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new EventSounds("C:\\Users\\Mitch\\Documents\\CSGOEventSounds\\SoundConfig.cfg").process();
        return;
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
				soundMap.put(key, sound);
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*            
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		String line;
		while ((line = br.readLine()) != null) {
		    addSoundFromString(line);
		}
		br.close();
		*/
    }
    
    /*
    private void addSoundFromString(String fullstring) {
        String[] soundStrings = fullstring.split(":", 2);
        Sound sound = new Sound(new File(soundStrings[1]));
        if(!sound.getFile().exists() || !sound.getFile().isFile()) {
        	System.err.println("Cannot find file: " + sound.getFile().toString());
        	return;
        }
        soundMap.put(soundStrings[0], sound);
    	return;
    }
    */

    /**
     *
     */
    private void process() {
        try {
            PostHandler postHandler = new StatePostHandler(soundMap);
            new Server(3000, postHandler, true, "CCWJu64ZV3JHDT8hZc");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
