package com.cwelth.godspeed;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Map;

public class EventHandlers {
    public static final double BASE_PLAYER_SPEED = 0.10000000149011612;
    public static final double BASE_MOUNT_SPEED = 0.22744558647890503;

    @SubscribeEvent
    void onPlayerMoves(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;

        if(player instanceof EntityPlayerMP)
        {
            boolean isFound = false;
            for(Map.Entry<String, Double> entry : Config.blocksAffected.entrySet())
            {
                Block biq = Block.REGISTRY.getObject(new ResourceLocation(entry.getKey()));
                if(player.world.getBlockState(player.getPosition().down()).getBlock() == biq) {
                    player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(entry.getValue().floatValue());
                    isFound = true;
                }
                if((player.isRiding() && player.getLowestRidingEntity() instanceof EntityLiving && player.world.getBlockState(player.getLowestRidingEntity().getPosition().down()).getBlock() == biq )) {
                    CollectedBaseSpeeds.get(player.world).add(player.getLowestRidingEntity().getClass().toString(), ((EntityLiving)player.getLowestRidingEntity()).getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue());
                    ((EntityLiving)player.getLowestRidingEntity()).getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(entry.getValue().floatValue() * 3);
                    isFound = true;
                }
            }
            if(!isFound) {
                if(player.isRiding() && player.getLowestRidingEntity() instanceof EntityLiving)
                {
                    double baseSpeed = CollectedBaseSpeeds.get(player.world).getBaseSpeed((player.getLowestRidingEntity()).getClass().toString());
                    if(baseSpeed != 0)
                        ((EntityLiving)player.getLowestRidingEntity()).getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(baseSpeed);
                } else {
                    player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(BASE_PLAYER_SPEED);
                }
            }

        }
    }
}
