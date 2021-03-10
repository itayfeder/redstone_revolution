package com.itayfeder.redstone_revolution.utils;

import net.minecraft.util.math.MathHelper;

public class DyableBlockHelper {
    public static int GetColor(int[] rgb) {
        return MathHelper.rgb(rgb[0], rgb[1], rgb[2]);
    }

    public static int[] ColorToRGB(int color) {
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        int[] rgb = new int[3];
        rgb[0] = (int)(f * 255.0F);
        rgb[1] = (int)(f1 * 255.0F);
        rgb[2] = (int)(f2 * 255.0F);
        return rgb;
    }

    public static int AddColor(int color, int dye) {
        int[] prevrgb = ColorToRGB(color);
        int[] addrgb = ColorToRGB(dye);
        int[] aint = new int[3];
        int i = Math.max(addrgb[0], Math.max(addrgb[1], addrgb[2])) + Math.max(prevrgb[0], Math.max(prevrgb[1], prevrgb[2]));
        int j = 2;
        aint[0] += prevrgb[0] + addrgb[0];
        aint[1] += prevrgb[1] + addrgb[1];
        aint[2] += prevrgb[2] + addrgb[2];

        int j1 = aint[0] / j;
        int k1 = aint[1] / j;
        int l1 = aint[2] / j;
        float f3 = (float)i / (float)j;
        float f4 = (float)Math.max(j1, Math.max(k1, l1));
        j1 = (int)((float)j1 * f3 / f4);
        k1 = (int)((float)k1 * f3 / f4);
        l1 = (int)((float)l1 * f3 / f4);
        int j2 = (j1 << 8) + k1;
        j2 = (j2 << 8) + l1;
        return j2;
    }
}
