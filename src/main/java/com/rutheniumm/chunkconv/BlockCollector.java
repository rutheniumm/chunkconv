package com.rutheniumm.chunkconv;

import net.minecraft.core.BlockPos;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class BlockCollector {

    public static Map<String, Map<String, String>> getBlocksInChunkRadius(Level level, BlockPos playerPos) {
        Map<String, Map<String, String>> blockData = new HashMap<>();

        // Minecraft 1.17+ has dynamic heights. For 1.16 and below, use fixed values (e.g., 0 to 255)
        int worldHeight = level.getHeight();
        int worldBottom = level.getMinBuildHeight();

        // Chunk coordinates to world coordinates
        int chunkStartX = (playerPos.getX() >> 4) << 4;
        int chunkStartZ = (playerPos.getZ() >> 4) << 4;



        for (int x = chunkStartX; x < chunkStartX + 16; x++) {
            for (int z = chunkStartZ; z < chunkStartZ + 16; z++) {
                for (int y = worldHeight; y >= worldBottom; y--) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState blockState = level.getBlockState(pos);

                    if (!blockState.isAir()) {
                        Map<String, String> mapData = new HashMap<>();
                        mapData.put("x", String.valueOf(x));
                        mapData.put("y", String.valueOf(y));
                        mapData.put("z", String.valueOf(z));
                        mapData.put("blockName", blockState.getBlock().toString());

                        String key = String.format("%s,%s,%s", x, y, z);
                        blockData.put(key, mapData);
                    }
                }
            }
        }

        return blockData;
    }
}