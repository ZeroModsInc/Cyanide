package com.github.zeromodsinc.cyanide.ui.playerview.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Provides a fake world that can be used to render entities in client-side GUIs without a world actually running.
 *
 * @author superbas11
 */
@SideOnly(Side.CLIENT)
public class FakeWorld extends WorldClient {

    public FakeWorld(WorldSettings worldSettings, FakeNetHandlerPlayClient netHandler) {
        //super(new FakeSaveHandler(), new WorldInfo(worldSettings, "FakeWorld"), new FakeWorldProvider(), new Profiler(), true);
        super(netHandler, worldSettings, 0, EnumDifficulty.HARD, new Profiler());
        this.provider.registerWorld(this);
    }

    @Override
    protected boolean isChunkLoaded(int i, int i1, boolean b) {
        return false;
    }


    @Override
    public BlockPos getTopSolidOrLiquidBlock(BlockPos pos) {
        return new BlockPos(pos.getX(), 63, pos.getZ());
    }

    @Override
    public boolean isAirBlock(BlockPos pos) {
        return pos.getY() > 63;
    }

    @Override
    public IBlockState getBlockState(BlockPos pos) {
        return pos.getY() > 63 ? Blocks.air.getDefaultState() : Blocks.grass.getDefaultState();
    }

    @Override
    public boolean setBlockState(BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public boolean setBlockToAir(BlockPos pos) {
        return true;
    }

    @Override public void markChunkDirty(BlockPos pos, TileEntity unusedTileEntity) {}
    @Override public boolean destroyBlock(BlockPos pos, boolean dropBlock) { return this.isAirBlock(pos); }

    @Override
    public void notifyNeighborsOfStateChange(BlockPos pos, Block blockType) {
        super.notifyNeighborsOfStateChange(pos, blockType);
    }

    @Override
    public void notifyNeighborsOfStateExcept(BlockPos pos, Block blockType, EnumFacing skipSide) {
    }

    @Override
    public void markAndNotifyBlock(BlockPos pos, Chunk chunk, IBlockState iblockstate, IBlockState newState, int flags) {
    }

    @Override
    public void markBlocksDirtyVertical(int par1, int par2, int par3, int par4) {
    }

    @Override
    public void markBlockRangeForRenderUpdate(int p_147458_1_, int p_147458_2_, int p_147458_3_, int p_147458_4_, int p_147458_5_, int p_147458_6_) {
    }

    @Override
    public boolean isBlockTickPending(BlockPos pos, Block blockType) {
        return false;
    }

    @Override
    public int getLightFromNeighbors(BlockPos pos) {
        return 14;
    }

    @Override
    public int getLight(BlockPos pos, boolean checkNeighbors) {
        return 14;
    }

    @Override
    public int getLight(BlockPos pos) {
        return 14;
    }

    @Override
    public int getLightFor(EnumSkyBlock type, BlockPos pos) {
        return 14;
    }

    @Override
    public int getLightFromNeighborsFor(EnumSkyBlock type, BlockPos pos) {
        return 14;
    }

    @Override
    public boolean canBlockSeeSky(BlockPos pos) {
        return pos.getY() > 62;
    }

    @Override
    public BlockPos getHeight(BlockPos pos) {
        return new BlockPos(pos.getX(), 63, pos.getZ());
    }

    @Override
    public int getChunksLowestHorizon(int x, int z) {
        return 63;
    }

    @Override
    protected void updateBlocks() {
    }

    @Override
    public void markBlockRangeForRenderUpdate(BlockPos rangeMin, BlockPos rangeMax) {
    }

    @Override
    public void setLightFor(EnumSkyBlock type, BlockPos pos, int lightValue) {
    }

    @Override
    public float getLightBrightness(BlockPos pos) {
        return 1f;
    }

    @Override
    public float getSunBrightnessFactor(float p_72967_1_) {
        return 1f;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float p_72971_1_) {
        return 1f;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightnessBody(float p_72971_1_) {
        return 1f;
    }

    @Override
    public boolean isDaytime() {
        return true;
    }

    @Override
    public void spawnParticle(EnumParticleTypes particleType, boolean p_175682_2_, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... p_175682_15_) {
    }

    @Override
    public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... p_175688_14_) {
    }

    @Override
    public boolean addWeatherEffect(Entity par1Entity) {
        return false;
    }

    @Override
    public void onEntityAdded(Entity par1Entity) {
    }

    @Override
    public void onEntityRemoved(Entity par1Entity) {
    }

    @Override
    public void removeEntity(Entity par1Entity) {
    }

    @Override
    public int calculateSkylightSubtracted(float par1) {
        return 6;
    }

    @Override
    public void scheduleBlockUpdate(BlockPos pos, Block blockIn, int delay, int priority) {
    }

    @Override
    public void updateEntities() {
    }

    @Override
    public void updateEntityWithOptionalForce(Entity entityIn, boolean forceUpdate) {
        if (forceUpdate)
            ++entityIn.ticksExisted;
    }

    @Override
    public TileEntity getTileEntity(BlockPos pos) {
        return null;
    }

    @Override
    public boolean extinguishFire(EntityPlayer player, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getDebugLoadedEntities() {
        return "";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getProviderName() {
        return "";
    }

    @Override
    public void setTileEntity(BlockPos pos, TileEntity tileEntityIn) {
    }

    @Override
    public void removeTileEntity(BlockPos pos) {
    }

    @Override
    public void markTileEntityForRemoval(TileEntity p_147457_1_) {
    }

    @Override
    public boolean isBlockNormalCube(BlockPos pos, boolean _default) {
        return true;
    }

    @Override
    public void tick() {
    }

    @Override
    protected void updateWeather() {
    }

    @Override
    public void updateWeatherBody() {
    }

    @Override
    public boolean canBlockFreezeWater(BlockPos pos) {
        return false;
    }

    @Override
    public boolean canBlockFreezeNoWater(BlockPos pos) {
        return false;
    }

    @Override
    public boolean canBlockFreeze(BlockPos pos, boolean noWaterAdj) {
        return false;
    }

    @Override
    public boolean canBlockFreezeBody(BlockPos pos, boolean noWaterAdj) {
        return false;
    }

    @Override
    public boolean canSnowAt(BlockPos pos, boolean checkLight) {
        return false;
    }

    @Override
    public boolean canSnowAtBody(BlockPos pos, boolean checkLight) {
        return false;
    }

    @Override
    public boolean tickUpdates(boolean par1) {
        return false;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List getPendingBlockUpdates(Chunk par1Chunk, boolean par2) {
        return null;
    }

    @Override
    public void loadEntities(Collection<Entity> entityCollection) {
    }

    @Override
    public void unloadEntities(Collection<Entity> entityCollection) {
    }

    @SuppressWarnings("rawtypes")
    @Override
    public int countEntities(Class par1Class) {
        return 0;
    }

    @Override
    public int getStrongPower(BlockPos pos) {
        return 0;
    }

    @Override
    public int getStrongPower(BlockPos pos, EnumFacing direction) {
        return 0;
    }

    @Override
    public boolean isSidePowered(BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public int getRedstonePower(BlockPos pos, EnumFacing facing) {
        return 0;
    }

    @Override
    public boolean isBlockPowered(BlockPos pos) {
        return false;
    }

    @Override
    public int isBlockIndirectlyGettingPowered(BlockPos pos) {
        return 0;
    }

    @Override
    public void checkSessionLock() throws MinecraftException {
    }

    @Override
    public long getSeed() {
        return 1;
    }

    @Override
    public long getTotalWorldTime() {
        return 1;
    }

    @Override
    public long getWorldTime() {
        return 1;
    }

    @Override
    public void setWorldTime(long par1) {
    }

    @Override
    public BlockPos getSpawnPoint() {
        return new BlockPos(0, 64, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void joinEntityInSurroundings(Entity par1Entity) {
    }

    @Override
    public boolean canSeeSky(BlockPos pos) {
        return pos.getY() > 62;
    }

    @Override
    public boolean canMineBlockBody(EntityPlayer player, BlockPos pos) {
        return false;
    }

    @Override
    public void setEntityState(Entity par1Entity, byte par2) {
    }

    @Override
    public float getThunderStrength(float delta) {
        return 0.0F;
    }

    @Override
    public void addBlockEvent(BlockPos pos, Block blockIn, int eventID, int eventParam) {
    }

    @Override
    public void updateAllPlayersSleepingFlag() {
    }

    @Override
    public boolean isRainingAt(BlockPos strikePosition) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setThunderStrength(float p_147442_1_) {
    }

    @Override
    public float getRainStrength(float par1) {
        return 0.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setRainStrength(float par1) {
    }

    @Override
    public boolean isThundering() {
        return false;
    }

    @Override
    public boolean isRaining() {
        return false;
    }

    @Override
    public boolean isBlockinHighHumidity(BlockPos pos) {
        return false;
    }

    @Override
    public void playBroadcastSound(int p_175669_1_, BlockPos pos, int p_175669_3_) {
    }

    @Override
    public int getHeight() {
        return 256;
    }

    @Override
    public int getActualHeight() {
        return 256;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void makeFireworks(double par1, double par3, double par5, double par7, double par9, double par11, NBTTagCompound par13nbtTagCompound) {
    }

    @Override
    public boolean addTileEntity(TileEntity tile) {
        return true;
    }

    @Override
    public void addTileEntities(Collection<TileEntity> tileEntityCollection) {
    }

    @Override
    public boolean isSideSolid(BlockPos pos, EnumFacing side) {
        return pos.getY() <= 63;
    }

    @Override
    public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {
        return pos.getY() <= 63;
    }

    @Override
    public int countEntities(EnumCreatureType type, boolean forSpawnCount) {
        return 0;
    }

    @Override
    protected IChunkProvider createChunkProvider() {
        return new FakeChunkProvider();
    }

    @Override
    public Chunk getChunkFromChunkCoords(int par1, int par2) {
        return null;
    }

    protected static class FakeWorldProvider extends WorldProvider {
        @Override
        public boolean isSurfaceWorld() {
            return true;
        }

        @Override
        public boolean canRespawnHere() {
            return true;
        }

        @Override
        public int getAverageGroundLevel() {
            return 63;
        }


        @Override
        @SideOnly(Side.CLIENT)
        public boolean doesXZShowFog(int par1, int par2) {
            return false;
        }

        @Override
        public String getDimensionName() {
            return null;
        }

        @Override
        public String getInternalNameSuffix() {
            return null;
        }

        @Override
        public void setDimension(int dim) {
        }

        @Override
        public String getSaveFolder() {
            return null;
        }

        @Override
        public BlockPos getRandomizedSpawnPoint() {
            return new BlockPos(0, 64, 0);
        }

        @Override
        public boolean shouldMapSpin(String entity, double x, double y, double z) {
            return false;
        }

        @Override
        public int getRespawnDimension(EntityPlayerMP player) {
            return 0;
        }

        @Override
        public boolean isDaytime() {
            return true;
        }

        @Override
        public void setAllowedSpawnTypes(boolean allowHostile, boolean allowPeaceful) {
        }

        @Override
        public void calculateInitialWeather() {
        }

        @Override
        public void updateWeather() {
        }

        @Override
        public boolean canBlockFreeze(BlockPos pos, boolean byWater) {
            return false;
        }

        @Override
        public boolean canSnowAt(BlockPos pos, boolean checkLight) {
            return false;
        }

        @Override
        public long getSeed() {
            return 1;
        }

        @Override
        public long getWorldTime() {
            return 1;
        }

        @Override
        public void setWorldTime(long time) {
        }

        @Override
        public boolean canMineBlock(EntityPlayer player, BlockPos pos) {
            return false;
        }

        @Override
        public boolean isBlockHighHumidity(BlockPos pos) {
            return false;
        }

        @Override
        public int getHeight() {
            return 256;
        }

        @Override
        public int getActualHeight() {
            return 256;
        }

        @Override
        public void resetRainAndThunder() {
        }

        @Override
        public boolean canDoLightning(Chunk chunk) {
            return false;
        }

        @Override
        public boolean canDoRainSnowIce(Chunk chunk) {
            return false;
        }

        @Override
        public BlockPos getSpawnPoint() {
            return new BlockPos(0, 64, 0);
        }

        @Override
        public boolean canCoordinateBeSpawn(int par1, int par2) {
            return true;
        }
    }

    protected static class FakeSaveHandler implements ISaveHandler {
        @Override
        public WorldInfo loadWorldInfo() {
            return null;
        }

        @Override
        public void checkSessionLock() {
        }

        @Override
        public IChunkLoader getChunkLoader(WorldProvider var1) {
            return null;
        }

        @Override
        public void saveWorldInfoWithPlayer(WorldInfo var1, NBTTagCompound var2) {
        }

        @Override
        public void saveWorldInfo(WorldInfo var1) {
        }

        @Override
        public IPlayerFileData getPlayerNBTManager() {
            return null;
        }

        @Override
        public void flush() {
        }

        @Override
        public File getWorldDirectory() {
            return null;
        }

        @Override
        public File getMapFileFromName(String var1) {
            return null;
        }

        @Override
        public String getWorldDirectoryName() {
            return null;
        }
    }

    protected static class FakeChunkProvider implements IChunkProvider {
        @Override
        public boolean chunkExists(int x, int z) {
            return false;
        }

        @Override
        public Chunk provideChunk(int var1, int var2) {
            return null;
        }

        @Override
        public Chunk provideChunk(BlockPos blockPosIn) {
            return null;
        }

        @Override
        public void populate(IChunkProvider chunkProvider, int x, int z) {

        }

        @Override
        public boolean populateChunk(IChunkProvider chunkProvider, Chunk chunkIn, int x, int z) {
            return false;
        }

        @Override
        public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressCallback) {
            return false;
        }

        @Override
        public boolean unloadQueuedChunks() {
            return false;
        }

        @Override
        public boolean canSave() {
            return false;
        }

        @Override
        public String makeString() {
            return null;
        }

        @Override
        public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
            return null;
        }

        @Override
        public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
            return null;
        }

        @Override
        public int getLoadedChunkCount() {
            return 0;
        }

        @Override
        public void recreateStructures(Chunk chunkIn, int x, int z) {

        }

        @Override
        public void saveExtraData() {

        }
    }
}