package com.laskdjlaskdj12.Player;

import org.bukkit.Location;

import java.util.HashMap;

public class PlayerTouchLocation {

    public PlayerTouchLocation(){
        LocationHashMap = new HashMap<String, Location>();
    }

    public void setLocation(Location LastPlayerTouchLocation, String PlayerName){
        LocationHashMap.put(PlayerName, LastPlayerTouchLocation);
    }

    public Location getLocation(String PlayerName){
        return LocationHashMap.get(PlayerName);
    }

    private HashMap<String, Location> LocationHashMap;

}
