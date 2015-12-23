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
import com.brekcel.csgostate.JSON.Player;
import com.brekcel.csgostate.JSON.Weapon;
import com.brekcel.csgostate.post.PostHandlerAdapter;

/**
 * TODO Description.
 * @author MG031901
 *
 */
public class StatePostHandler extends PostHandlerAdapter {

    Timer bombTimer;
    int bombTime;

    private final Map<String, Sound> soundMap;
	private String playerTeam = "s";
	private String activity = "menu";
	private boolean roundEnded = false;
	private boolean roundStarted = false;
	private int round = 0;

    /**
     *
     */
    public StatePostHandler(Map<String, Sound> soundMap) {
        this.soundMap = soundMap;
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
                        //System.out.println("Explode in: " + bombTime);
                    }
                }
            }, 0, 1000);
        } else {
            roundEnd("bomb_"+bomb);
        }
    }
    
    @Override
    public void roundWinningTeamChange(String win_team) {
    	String winTeam = win_team.toLowerCase();
    	if(!playerTeam.equals("s")) {
	    	String result = playerTeam.equalsIgnoreCase(winTeam) ? "win" : "lose";
	    	if(!roundEnd("round_end_"+result+"_"+playerTeam)) {
	        	if(!roundEnd("round_end_"+result)) {
	    	    	if(!roundEnd("round_end_"+winTeam)) {
	    	    		roundEnd("round_end");
	    	    	}
	        	}
	    	}
    	} else {
	    	if(!roundEnd("round_end_"+winTeam)) {
	    		roundEnd("round_end");
	    	}
    	}
    }
    
    public boolean roundEnd(String event) {
        if(bombTimer != null) {
            bombTimer.cancel();
            bombTimer = null;
        }
    	if(this.roundEnded == false) {
    		if(playSoundFromEvent(event)) {
        		roundEnded = true;
        		roundStarted = false;
    		}
    		return roundEnded;
    	}
    	return false;
    }

    @Override
    public void receivedJsonResponse(JsonResponse jsonResponse) {
    	Player player = jsonResponse.getPlayer();
    	this.activity = player.getActivity();
    	if(activity.equals("playing")) {
        	this.round = jsonResponse.getMap().getRound();
        	if(!jsonResponse.getRound().getPhase().equals("over")) {
        		if(player.getState().getHealth() != 0) {
                	this.playerTeam = player.getTeam().toLowerCase();
        		}
        	}
    	}
    }
    
	@Override
	public void playerTeamChange(String team) {
		if(!team.equalsIgnoreCase(this.playerTeam)) {
			this.playerTeam = team.toLowerCase();
			if(!playSoundFromEvent("player_team_"+this.playerTeam)) {
		    	playSoundFromEvent("player_team");
			}
		}
	}

	@Override
	public void playerHealthChange(int health) {
		if(roundStarted) {
			System.out.println("player_health: "+health);
			playSoundFromEvent("player_health");
		}
	}

    @Override
    public void phaseChange(String phase) {
    	if(phase.equals("gameover")) {
        	playSoundFromEvent("map_end");
    	} else if(phase.equals("intermission")) {
        	playSoundFromEvent("map_intermission");
    	} else if(phase.equals("live")) {
        	playSoundFromEvent("map_start");
    	}
    }
    

    @Override
    public void roundPhaseChange(String phase) {
    	//System.out.println("roundPhase: "+ phase);
    	if(phase.equals("freezetime")) {
        	roundEnded = false;
    		roundStarted = true;
    		if(playSoundFromEvent("round_start_"+round)) {
            	playSoundFromEvent("round_start");
    		}
    	} else if(phase.equals("live")) {
        	playSoundFromEvent("round_freeze_end");
    	} else if(phase.equals("warmup")) {
        	playSoundFromEvent("round_warmup");
    	}
    }
    
    
    @Override
    public void weaponShoot(Weapon weapon) {
        System.out.println("shoot_"+weapon.getName()+" : shoot_"+weapon.getType());
        if(!playSoundFromEvent("weapon_shoot_"+weapon.getName())) {
            if(!playSoundFromEvent("weapon_shoot_"+weapon.getType())) {
            	playSoundFromEvent("weapon_shoot");
            }
        }
    }

    /**
     * @param event
     * @return
     */
    public boolean playSoundFromEvent(String event) {
    	System.out.println("Event: "+event);
        if(soundMap.isEmpty()) {
            return false;
        }
        Sound sound = soundMap.get(event);
        if(sound == null) {
            return false;
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

        return true;
    }
}
