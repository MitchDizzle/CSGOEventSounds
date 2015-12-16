package wtf.dizzle.csgostate.eventsounds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.brekcel.csgostate.Server;
import com.brekcel.csgostate.post.PostHandler;


/**
 * TODO Description.
 * @author Mitch Gardner
 *
 */
public class EventSounds {

    private final Map<String, File> soundMap = new HashMap<String, File>();


    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new EventSounds().process();

        return;
    }

    /**
     *
     */
    public EventSounds() {
        loadSounds();
    }

    private void loadSounds() {

        String fileName = "SoundConfig.cfg";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                addSoundFromString(line);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addSoundFromString(String fullstring) {
        String[] soundStrings = fullstring.split(":", 2);
        File soundFile = new File(soundStrings[1]);
        soundMap.put(soundStrings[0], soundFile);
    }

    /**
     *
     */
    private void process() {
        try {
            PostHandler postHandler = new StatePostHandler(soundMap);
            Server server = new Server(8000, postHandler, true, "asdf1234");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
