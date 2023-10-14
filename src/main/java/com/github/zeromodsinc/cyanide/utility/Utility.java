package com.github.zeromodsinc.cyanide.utility;

import com.github.zeromodsinc.cyanide.ui.core.drawables.Text;
import com.github.zeromodsinc.cyanide.ui.render.CyanideGL;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Utility {
    private static long refreshRateLastRefreshed = 0;
    private static int lastRefreshRate = 30;

    @Getter
    private static final Text debugHeader = Text.create(Text.FontType.DEFAULT)
            .setAnchor(Text.TextAnchor.CENTER)
            .setShadow(true);

    public static String fmt(String format, Object... replacements) {
        String finalString = format;

        for (int x = 0; x < replacements.length; x++) {
            finalString = finalString.replaceFirst("\\{}", replacements[x].toString());
            finalString = finalString.replaceAll("\\{" + x + "}", replacements[x].toString());
        }

        return finalString;
    }

    public static ScaledResolution res() {
        return new ScaledResolution(Minecraft.getMinecraft());
    }

    public static int getMonitorRefreshRate() {
        if (refreshRateLastRefreshed < System.currentTimeMillis() - 5 * 60 * 1000) {
            int rate = 30;

            GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] devices = environment.getScreenDevices();

            for (GraphicsDevice device : devices) {
                DisplayMode mode = device.getDisplayMode();

                int refreshRate = mode.getRefreshRate();
                if (refreshRate != DisplayMode.REFRESH_RATE_UNKNOWN && refreshRate > rate) {
                    rate = refreshRate;
                }
            }

            lastRefreshRate = rate;
            refreshRateLastRefreshed = System.currentTimeMillis();
        }

        return lastRefreshRate;
    }

    public static <A, B> List<B> convertList(List<A> original, ParameterizedVisitor<A, B> conversion) {
        List<B> list = new ArrayList<>();
        for (A item : original) {
            list.add(conversion.run(item));
        }

        return list;
    }

    public static int maxInList(List<Integer> list) {
        AtomicInteger cur = new AtomicInteger(Integer.MIN_VALUE);
        list.forEach(i -> {
            if (i > cur.get()) {
                cur.set(i);
            }
        });

        return cur.get();
    }

    public static String getOsVersion() {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            Kernel32 kernel = Kernel32.INSTANCE;
            WinNT.OSVERSIONINFOEX vex = new WinNT.OSVERSIONINFOEX();
            if (kernel.GetVersionEx(vex)) {
                return MessageFormat.format("{0}.{1}.{2}",
                        vex.dwMajorVersion.toString(), vex.dwMinorVersion.toString(), vex.dwBuildNumber.toString());
            }
        }

        return System.getProperty("os.version");
    }

    public static String getDebugHeaderText() {
        String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");
        String osVersion = getOsVersion();

        String formatFirst = "{} [{}] {} {}, {}, {} {}";
        String formatSecond = "FPS: {}, ServerTime: {}, Mem: {}, Highest Mem: {}, Free Mem: {}, GUI Scale: {}," +
                " Resolution: {}x{}";

        String first = fmt(formatFirst, "1.8.9", "OpenGL " + CyanideGL.getVersion(),
                CyanideGL.getVendor(), osArch,
                CyanideGL.getRenderer(), osName, osVersion);
        String second = fmt(formatSecond, Minecraft.getDebugFPS(),
                0,
                Memory.getMem().getOptimalString(),
                Memory.getHighestMem().getOptimalString(),
                Memory.getFreeMem().getOptimalString(),
                res().getScaleFactor(),
                Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);

        return first + "\n" + second;
    }
}
