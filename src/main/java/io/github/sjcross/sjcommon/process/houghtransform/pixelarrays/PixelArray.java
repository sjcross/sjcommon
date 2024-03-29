package io.github.sjcross.sjcommon.process.houghtransform.pixelarrays;

import io.github.sjcross.sjcommon.mathfunc.Indexer;

/**
 * Created by sc13967 on 12/01/2018.
 */
public abstract class PixelArray extends Indexer {
    private Object pixels;

    public PixelArray(int[] dim) {
        super(dim);
        this.pixels = pixels;

    }

    public double getPixelValue(int[] coord) {
        int idx = getIndex(coord);
        return getPixelValue(idx);

    }

    public abstract int length();
    public abstract double getPixelValue(int i);

}
