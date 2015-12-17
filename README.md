# CSGOEventSounds
Plays sounds when certain events happen.

yay documentation.

# Usage

Have Java (1.7) installed.  
Open a command prompt in the directory which ```event-sounds-<version>.jar``` is installed.  
Run this in the command prompt:  
```event-sounds-<version>.jar <config>```

Example:
```batch
@echo off
echo Launching EventSounds
event-sounds.jar SoundConfig.jar
```


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
- [X] Bomb Timer:
  - [X] bomb_planted : bomb was planted
  - [X] bomb_defused : bomb was defused
  - [X] bomb_exploded : bomb exploded
  - [X] bomb_# Every second when the bomb is active.
- [ ] Weapon Events:
  - [ ] weapon_shoot : when ever the player shoots a weapon.
  - [ ] weapon_shoot_<weapon> : this will fire instead of the "weapon_shoot" if setup.
  - [ ] weapon_active : player changes weapon
  - [ ] weapon_active_<weapon> : fires instead of weapon_active but for a certain weapon.
  - [ ] weapon_reload : player reloads weapon
  - [ ] weapon_reload_<weapon> : fires instead of weapon_reload but for a certain weapon.
- [ ] Round Events:
  - [ ] round_warmup : not sure if this will be implemented..
  - [ ] round_start : round start, players are still in buying period.
    - [ ] round_start_# : round start for a certain round number.
  - [ ] round_freeze_end : when the freeze time ends and the players can move.
  - [ ] round_end : when the round ends, usually when a team wins.
    - [ ] round_end_t : T win
    - [ ] round_end_ct : CT win
    - [ ] round_end_win : Local Player wins round.
    - [ ] round_end_lose : Local Player loses round.
    - [ ] round_end_win_t : Local Player wins round on t side.
    - [ ] round_end_win_ct : Local Player wins round on ct side.
    - [ ] round_end_lose_t : Local Player loses round on t side.
    - [ ] round_end_lose_ct : Local Player loses round on ct side.
- [ ] Player:
  - [ ] player_team_<t/ct/spec> : when the player changes teams
  - [ ] player_namechange : when the player changes their name.
  - [ ] player_health : when the player's health changes.
    - [ ] player_health- : when the player's health decreases.
    - [ ] player_health+ : when the player's health increases.
    - [ ] player_health_# : player's health equals amount.
  - [ ] player_armor : when the player's armor changes.
    - [ ] player_armor- : when the player's armor decreases.
    - [ ] player_armor+ : when the player's armor increases.
    - [ ] player_armor_# : player's armor equals amount.
  - [ ] player_helmet : when the player's helmet changes.
    - [ ] player_helmet- : when the player's helmet is disabled.
    - [ ] player_helmet+ : when the player's helmet is enabled.
  - [ ] player_flashed : when the player flashed state changes
    - [ ] player_flashed- : when the player is no longer flashed
    - [ ] player_flashed+ : when the player is flashed
  - [ ] player_burn : when the player's burning state changes.
    - [ ] player_burn- : when the player is not longer burning.
    - [ ] player_burn+ : when the player starts to burn.
  - [ ] player_kill : when the player's kills change
    - [ ] player_kill- : kills decreased
    - [ ] player_kill+ : kills increased
    - [ ] player_kill_# : kills equal
  - [ ] player_kill_hs : when the player's headshot kills change
    - [ ] player_kill_hs- : headshot kills decreased
    - [ ] player_kill_hs+ : headshot kills increased
    - [ ] player_kill_hs_# : headshot kills equal
- [ ] Map Events:
  - [ ] map_start : start of a new map
  - [ ] map_end : end of a map


# Compiling Source

The code is based on **JDK 1.7**, **Maven 3.3.9**; However it will most likely compile on older versions.

This is based off of the project [csgostate](https://github.com/sakki54/CSGOState), created by the user [sakki54](https://github.com/sakki54)  
If compiling with maven (recommended) then be sure to install the CSGOState server via  (with maven install)
``` mvn install:install-file -Dfile=C:\CSGOState-v0.1.jar -DgroupId=com.brekcel.csgostate -DartifactId=csgostate -Dversion=0.1 -Dpackaging=jar ```  
And replacing the version with the current version, also replacing the directory of which the jar file is located.  
