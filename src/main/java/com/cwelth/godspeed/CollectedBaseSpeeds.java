package com.cwelth.godspeed;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashMap;
import java.util.Map;

public class CollectedBaseSpeeds extends WorldSavedData {
    public static final String MOUNTSPEEDS = ModMain.MODID + "_MountSpeeds";
    private Map<String, Double> SPEEDS = new HashMap<>();

    public CollectedBaseSpeeds(){
        super(MOUNTSPEEDS);
    }

    public CollectedBaseSpeeds(String name) {
        super(name);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        for(String tag : nbt.getKeySet())
        {
            SPEEDS.put(tag, nbt.getDouble(tag));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        for(Map.Entry<String, Double> item : SPEEDS.entrySet())
        {
            compound.setDouble(item.getKey(), item.getValue());
        }
        return compound;
    }

    public static CollectedBaseSpeeds get(World world){
        MapStorage storage = world.getMapStorage();
        CollectedBaseSpeeds instance = (CollectedBaseSpeeds) storage.getOrLoadData(CollectedBaseSpeeds.class, MOUNTSPEEDS);

        if (instance == null) {
            instance = new CollectedBaseSpeeds();
            storage.setData(MOUNTSPEEDS, instance);
        }
        return instance;
    }

    public void add(String entityName, double baseSpeed)
    {
        if(SPEEDS.containsKey(entityName)) return;
        SPEEDS.put(entityName, baseSpeed);
        ModMain.logger.info("Added baseSpeed ("+baseSpeed+") for Entity: "+entityName);
        markDirty();
    }

    public double getBaseSpeed(String entityName)
    {
        if(!SPEEDS.containsKey(entityName))return 0;
        return SPEEDS.get(entityName);
    }
}
