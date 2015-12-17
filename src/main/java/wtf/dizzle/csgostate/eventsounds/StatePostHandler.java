package wtf.dizzle.csgostate.eventsounds;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.brekcel.csgostate.JSON.JsonResponse;
import com.brekcel.csgostate.post.PostHandlerAdapter;

/**
 * TODO Description.
 * @author MG031901
 *
 */
public class StatePostHandler extends PostHandlerAdapter {

	private Timer bombTimer;
    private int bombTime;
    
    private final Map<String, Sound> soundMap;

    /**
     *
     */
    public StatePostHandler(Map<String, Sound> soundMap) {
        System.out.println(soundMap);
        this.soundMap = soundMap;
    }
    @Override
    public void receivedJsonResponse(JsonResponse jsonResponse) {
    	jsonResponse.toString();
    }

    @Override
    public void roundBombChange(String bomb) {
        if(bomb.equals("planted")) {
        	playSoundFromEvent("bomb_planted");
        	bombTime = 41;
            bombTimer = new Timer();
            bombTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                	bombTime--;
                	if(bombTime <= -1) {
                		bombTimer.cancel();
            			bombTimer = null;
                	} else {
                    	playSoundFromEvent("bomb_"+bombTime);
                        System.out.println(bombTime);
                	}
                }
            }, 0, 1000);
        } else {
            System.out.println("Bomb " + bomb);
    		if(bombTimer != null) {
    			bombTimer.cancel();
    			bombTimer = null;
    		}
        }
    }
    
    public void playSoundFromEvent(String event) {
    	if(soundMap.isEmpty()) {
    		return;
    	}
    	Sound sound = soundMap.get(event);
    	if(sound == null) {
    		return;
    	}
    	File soundFile = sound.getFile();
    	Float soundVolume = sound.getVolume();
    	//Play Sound code.
		try {
	        Clip clip = AudioSystem.getClip();
	        AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
	        AudioInputStream din = null;
	        AudioFormat baseFormat = ais.getFormat();
	        AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
	                                                    baseFormat.getSampleRate(),
	                                                    16,
	                                                    baseFormat.getChannels(),
	                                                    baseFormat.getChannels() * 2,
	                                                    baseFormat.getSampleRate(),
	                                                    false);
	        din = AudioSystem.getAudioInputStream(decodedFormat, ais);
	        clip.open(din);
	        if(soundVolume != null) {
		        FloatControl gainControl =  (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		        gainControl.setValue(soundVolume);
	        }
	        clip.start();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return;
    }
}
