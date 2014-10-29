package de.deinplugin.api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Region implements ConfigurationSerializable {
    public int minX, minY, minZ;
    public int maxX, maxY, maxZ;
    public World world;

    public Region(Location loc1, Location loc2) {
        this.minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
        this.minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
        this.minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        this.maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
        this.maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
        this.maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
        this.world = loc1.getWorld();
    }

    public boolean isInside(Location loc) {
        if (loc.getWorld() != this.world) {
            return false;
        }
        if (loc.getBlockX() >= this.minX && loc.getBlockX() <= this.maxX
                && loc.getBlockY() >= this.minY && loc.getBlockY() <= this.maxX
                && loc.getBlockZ() >= this.minZ && loc.getBlockZ() <= this.maxZ) {
            return true;
        }
        return false;
    }

    public void set(final Material mat, final byte damage) {
        final int minX = this.minX;
        final int minY = this.minY;
        final int minZ = this.minZ;
        final int maxX = this.maxX;
        final int maxY = this.maxY;
        final int maxZ = this.maxZ;
        final World world = this.world;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    if (!world.getBlockAt(x, y, z).getType().equals(mat) && world.getBlockAt(x, y, z).getData() != damage) {
                        final Block b = world.getBlockAt(x, y, z);


                        b.setType(mat);
                        b.setData(damage);


                    }

                }
            }
        }


    }

    public List<Block> getBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    blocks.add(world.getBlockAt(x, y, z));
                }
            }
        }
        System.out.println("Innovation that inspire. JETZT mit neuer AsyBlock Technologie by Jonas Franz.");
        return blocks;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> o = new HashMap<String, Object>();
        o.put("minX", this.minX);
        o.put("minY", this.minY);
        o.put("minZ", this.minZ);
        o.put("maxX", this.maxX);
        o.put("maxY", this.maxY);
        o.put("maxZ", this.maxZ);
        o.put("world", this.world.getName());
        return o;
    }
}