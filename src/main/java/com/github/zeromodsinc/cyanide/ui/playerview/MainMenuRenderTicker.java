package com.github.zeromodsinc.cyanide.ui.playerview;

import com.mojang.util.UUIDTypeAdapter;
import com.github.zeromodsinc.cyanide.ui.playerview.client.util.EntityUtils;
import com.github.zeromodsinc.cyanide.ui.playerview.util.FakeNetHandlerPlayClient;
import com.github.zeromodsinc.cyanide.ui.playerview.util.FakeWorld;
import com.github.zeromodsinc.cyanide.ui.playerview.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@SuppressWarnings({ "rawtypes", "unchecked" })
@SideOnly(Side.CLIENT)
public class MainMenuRenderTicker {
    private static Minecraft mcClient;
    private static boolean isRegistered = false;
    private static List<SimpleEntry<UUID, String>> fallbackPlayerNames;
    private static Random random = new Random();
    private static int id;
    private static boolean erroredOut = false;

    static {
        fallbackPlayerNames = new ArrayList<SimpleEntry<UUID, String>>();
        // UUIDs gotten using mctools.connorlinfoot.com
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("41834a728902449586b8731b1c253afe"), "superbas11"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("92d459067a50474285b6b079db9dc189"), "bspkrs"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("2efa46fa29484d98b822fa182d254870"), "lorddusk"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("b9a89002b3924545ab4d5b1ff60c88a6"), "Arkember"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("48a16fc8bc1f4e7284e97ec73b7d8ea1"), "TTFTCUTS"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("95fe0728e1bd4989a9803d8976aedda9"), "WayofFlowingTime"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("e6b5c088068044df9e1b9bf11792291b"), "Grumm"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("af1d579e8787433099b2e7b3777c3e7a"), "Sacheverell"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("4c13e7d20e854bdebb121a43be506302"), "Quetzz"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("0192723fb3dc495a959f52c53fa63bff"), "Pahimar"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("f46302d20b7c4cc6aee9cd714ae6b9d1"), "ZeldoKavira"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("0eff7eb1d1b74612a9c9791b7ad6277a"), "sfPlayer1"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("c7d5d58a51a84d2698f55550a37bd8d1"), "jadedcat"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("72ddaa057bbe4ae298922c8d90ea0ad8"), "RWTema"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("754e416456cc4139bb7911cfaafdebcc"), "Scottwears"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("7ef08b4e5f3d40a793d426a19fe0efe2"), "neptunepink"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("90201c8957124f99a1c95d751565560a"), "Aureylian"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("bbb87dbe690f4205bdc572ffb8ebc29d"), "direwolf20"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("d0719201af1d4aab93d0f514ab59ed8e"), "Krystal_Raven"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("069a79f444e94726a5befca90e38aaf5"), "Notch"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("61699b2ed3274a019f1e0ea8c3f06bc6"), "Dinnerbone"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("e358f2774a4c42f69ad83addf4869ea6"), "Adubbz"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("892017ede9a04f259a37b8811614707d"), "AlgorithmX2"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("6d074736b1e94378a99bbd8777821c9c"), "Cloudhunter"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("3af4f9617eb64cb0b26443fd593cf42a"), "Lunatrius"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("75831c039a0a496ba7776a78ef8833a6"), "_Sunstrike"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("b72d87cefa984a5ab5a05db51a018d09"), "sdkillen"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("af1483804ba54a3da47d710f710f9265"), "Minalien"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("6bdd4acd5637448898583de07cc820d5"), "futureamnet"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("8e32d7a9c8124fa78daf465ff9ffa262"), "AbrarSyed"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("6ac7c57d8c154ffeae5d5e04bd606786"), "TDWP_FTW"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("d3cf097a438f4523b770ec11e13ecc32"), "LexManos"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("98beecaf555e40649401b531fb927641"), "Vaht"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("e3ade06278f344ca90fe4fc5781ffc80"), "EddieRuckus"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("83898b2861184900913741ffc46b6e10"), "progwml6"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("0b7509f0245841609ce12772b9a45ac2"), "ohaiiChun"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("aa29ede708174c65b19277a32e772b9c"), "fuj1n"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("d4d119aad410488a87340053577d4a1a"), "Mikeemoo"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("898ed21379b249f98602d1bb03d19ba2"), "boq42"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("9671e3e159184ad0bd473f7f27f57074"), "Toby"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("f3c8d69b077645128434d1b2165909eb"), "dan200"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("cf3e2c7ed70348e0808ef139bf26ff9d"), "ecutruin"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("b1890bac9f4044fa870ba71ce4df05c2"), "nikstick22"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("a9b6837ea916496790c191aef968c96b"), "Mr_okushama"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("0ea8eca3dbf647cc9d1ac64551ca975c"), "sk89q"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("957397819f754c6c9a93621c72f5bf9c"), "ShadwDrgn"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("c501d5507e3c463e8a95256f86d9a47d"), "chicken_bones"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("0f95811ab3b64dbaba034adfec7cf5ab"), "azanor"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("4f3a8d1e33c144e7bce8e683027c7dac"), "Soaryn"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("03020c4cf2f640b1a37c4e2b159b15b7"), "pillbox"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("b476c98e175048bfad5ea21becd0aaeb"), "mDiyo"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("839a930d5b874583b8da0760f9fa0254"), "IceBladeRage"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("4b9a5c51e9324e1ead30637101fb6fae"), "Thunderdark"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("7e6d65ed6fd840a786f8df09791572fc"), "Myrathi"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("b97e12cedbb14c0cafc8132b708a9b88"), "XCompWiz"));
        fallbackPlayerNames.add(new SimpleEntry<>(UUIDTypeAdapter.fromString("e43e9766f90348e1818fd41bb48d80d5"), "FireBall1725"));

        id = -1;
    }

    private WorldClient world;
    private EntityPlayerSP player;

    public MainMenuRenderTicker() {
        mcClient = Minecraft.getMinecraft();
    }

    public int the_what = -1;

    public void onTick() {
        if (!erroredOut) {
            try {
                if ((player == null) || (player.worldObj == null))
                    init();

                //In cause of someone resetting the renderer. cough cough... schematica...
                if (mcClient.getRenderManager().worldObj == null || mcClient.getRenderManager().livingPlayer == null)
                    mcClient.getRenderManager().cacheActiveRenderInfo(world, mcClient.fontRendererObj, player, player, mcClient.gameSettings, 0.0F);

                if ((world != null) && (player != null)) {
                    mcClient.thePlayer = player;
                    mcClient.theWorld = world;
                    ScaledResolution sr = new ScaledResolution(mcClient);
                    final int mouseX = (Mouse.getX() * sr.getScaledWidth()) / mcClient.displayWidth;
                    final int mouseY = sr.getScaledHeight() - ((Mouse.getY() * sr.getScaledHeight()) / mcClient.displayHeight) - 1;

                    //Draw entities
                    int distanceToSide = ((mcClient.currentScreen.width / 2) - 98) / 2;
                    float targetHeight = (float) (sr.getScaledHeight_double() / 5.0F) / 1.3F;

                    int posX = sr.getScaledWidth() - distanceToSide;
                    the_what = (sr.getScaledWidth() - (sr.getScaledWidth() - posX)) * 2;
                    int posY = (int) ((sr.getScaledHeight() / 2) + (targetHeight * 0.9f));

                    EntityUtils.drawEntityOnScreen(
                            posX, posY, targetHeight,
                            posX - mouseX,
                            (posY) - (player.height * targetHeight * (player.getEyeHeight() / player.height)) - mouseY,
                            player);
                }
            } catch (Throwable e) {
                LogHelper.severe("Main menu mob rendering encountered a serious error and has been disabled for the remainder of this session.");
                e.printStackTrace();
                erroredOut = true;
                player = null;
                world = null;
            }
            mcClient.thePlayer = null;
            mcClient.theWorld = null;
        }
    }

    public void onGameTick() {
        if (world != null && player != null) {
            mcClient.thePlayer = player;
            world.updateEntity(player);
            mcClient.thePlayer = null;
        }
    }

    private void init() {
        try {
            boolean createNewWorld = world == null;
            WorldSettings worldSettings = new WorldSettings(0, WorldSettings.GameType.NOT_SET, true, false, WorldType.DEFAULT);
            FakeNetHandlerPlayClient netHandler = new FakeNetHandlerPlayClient(mcClient);

            if (createNewWorld) {
                world = new FakeWorld(worldSettings, netHandler);
            }

            if (createNewWorld || (player == null)) {
                player = new EntityPlayerSP(mcClient, world, netHandler, null);
                int ModelParts = 0;
                for (EnumPlayerModelParts enumplayermodelparts : mcClient.gameSettings.getModelParts()) {
                    ModelParts |= enumplayermodelparts.getPartMask();
                }
                player.getDataWatcher().updateObject(10, Byte.valueOf((byte) ModelParts));
                player.dimension = 0;
                player.movementInput = new MovementInputFromOptions(mcClient.gameSettings);
                player.eyeHeight = 1.82F;
            }

            mcClient.getRenderManager().cacheActiveRenderInfo(world, mcClient.fontRendererObj, player, player, mcClient.gameSettings, 0.0F);
        } catch (Throwable e) {
            LogHelper.severe("Main menu mob rendering encountered a serious error and has been disabled for the remainder of this session.");
            e.printStackTrace();
            erroredOut = true;
            player = null;
            world = null;
        }
    }
}
