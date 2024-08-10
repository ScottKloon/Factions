package br.com.redeend.factions.zone;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ZoneManager {
    private final Map<Chunk, ZoneType> zones = new HashMap<>();
    private final Map<Chunk, String> chunkOwners = new HashMap<>(); // Mapa para armazenar o dono da chunk

    public void setZone(Chunk chunk, ZoneType type, String owner) {
        zones.put(chunk, type);
        chunkOwners.put(chunk, owner);
    }

    public ZoneType getZone(Chunk chunk) {
        return zones.getOrDefault(chunk, ZoneType.LIVRE);
    }

    public String getChunkOwner(Chunk chunk) {
        return chunkOwners.get(chunk);
    }

    public boolean isChunkOwned(Chunk chunk) {
        return chunkOwners.containsKey(chunk);
    }
}
