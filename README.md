# CSGOEventSounds
Plays sounds when certain events happen.


# Usage

Have Java installed.  
Open a command prompt in the directory which ```event-sounds-<version>.jar``` is installed.  
Run this in the command prompt:  
```event-sounds-<version>.jar <config>```


# Config

The config is in json format:
```json
{
	"event_name": {
		"file": "path_to_file",
		"volume": "decibal adjustment (float, negative = quieter/positive =louder)"
	}
}
````
The sound file can be either MP3, or WAV.

# List of Events (Currently usable):
- Bomb Timer:
  - bomb_planted : bomb was planted
  - bomb_defused : bomb was defused
  - bomb_exploded : bomb exploded
  - bomb_# Every second when the bomb is active.
