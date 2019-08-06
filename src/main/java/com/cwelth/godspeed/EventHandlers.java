package com.cwelth.godspeed;

import com.cwelth.godspeed.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Map;

public class EventHandlers {

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
                    player.capabilities.setPlayerWalkSpeed(entry.getValue().floatValue());
                    isFound = true;
                }
            }
            if(!isFound)
                player.capabilities.setPlayerWalkSpeed(0.1F);

        }
    }
}
