package me.tehbeard.BeardStat.listeners;

import java.util.List;

import me.tehbeard.BeardStat.BeardStat;
import me.tehbeard.BeardStat.containers.PlayerStatManager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;


public class StatVehicleListener implements Listener {

    
    private PlayerStatManager playerStatManager;
    private List<String> worlds;
    
    public StatVehicleListener(List<String> worlds,PlayerStatManager playerStatManager){
        this.worlds = worlds;
        this.playerStatManager = playerStatManager;
    }
    
    @EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
    public void onVehicleMove(VehicleMoveEvent event){
        if(
                (event.getTo().getBlockX() != event.getFrom().getBlockX() || 
                event.getTo().getBlockY() != event.getFrom().getBlockY() || 
                event.getTo().getBlockZ() != event.getFrom().getBlockZ() )&& 
                !worlds.contains(event.getVehicle().getWorld().getName())){

            
            Location from,to;
            Player player = (event.getVehicle().getPassenger() instanceof Player ? (Player)event.getVehicle().getPassenger() : null);
            if(player==null){return;}
            from = event.getFrom();
            to = event.getTo();
            BeardStat.printDebugCon("Vehicle move fired!");
            if(from.getWorld().equals(to.getWorld())){
                if(from.distance(to) < 10){
                    BeardStat.printDebugCon("Vehicle move writing!");
                    playerStatManager.getPlayerBlob(player.getName()).getStat("vehicle",
                            event.getVehicle().getType().toString().toLowerCase()
                            ).incrementStat((int)Math.ceil(from.distance(to)));
                    
                    
                }
            }
        }

    }
}
