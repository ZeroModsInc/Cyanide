package com.github.zeromodsinc.cyanide.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.DecimalFormat;

public class Memory {
    @Getter private static final MemorySize mem = new MemorySize(0L);
    @Getter private static final MemorySize highestMem = new MemorySize(0L);
    @Getter private static final MemorySize freeMem = new MemorySize(0L);

    @Getter
    @AllArgsConstructor
    public static enum SizeType {
        BIT(1L, "bits"),
        NIBBLE(4L, "nybls"),
        BYTE(8L, "B"),
        KILOBYTE(8_192L, "KB"),
        MEGABYTE(8_388_608L, "MB"),
        GIGABYTE(8_589_934_592L, "GB"),
        TERABYTE(8_796_093_022_208L, "TB"),
        PETABYTE(9_007_199_254_740_992L, "PB");

        private final long bitMultiplier;
        private final String abbreviation;
    }

    @Getter
    public static class MemorySize {
        private long bits;

        public MemorySize(long bits) {
            this.bits = bits;
        }

        public MemorySize(SizeType type, double value) {
            this.bits = (long) (value * type.bitMultiplier);
        }

        public long get() {
            return this.bits;
        }

        public double get(SizeType type) {
            double unformatted = (double) this.bits / (double) type.bitMultiplier;
            String to2dp = new DecimalFormat("0.00").format(unformatted);

            return Double.parseDouble(to2dp);
        }

        public double getOptimal() {
            return this.get(this.getOptimalSizeType());
        }

        public void set(long bits) {
            this.bits = bits;
        }

        public void set(SizeType type, double value) {
            this.bits = (long) (value * type.bitMultiplier);
        }

        public SizeType getOptimalSizeType() {
            SizeType[] values = SizeType.values();
            for (int x = values.length - 1; x > 0; x--) {
                SizeType type = values[x];
                if ((double) this.bits / type.bitMultiplier > 1d) {
                    return type;
                }
            }

            return SizeType.BIT;
        }

        public String getOptimalString() {
            return this.getOptimal() + this.getOptimalSizeType().getAbbreviation();
        }
    }
}
