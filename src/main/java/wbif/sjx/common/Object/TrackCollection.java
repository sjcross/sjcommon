package wbif.sjx.common.Object;

import wbif.sjx.common.Analysis.DirectionalPersistenceCalculator;
import wbif.sjx.common.Analysis.MSDCalculator;
import wbif.sjx.common.MathFunc.CumStat;

import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * Created by sc13967 on 13/06/2017.
 */
public class TrackCollection extends LinkedHashMap<Integer,Track> {

    // PUBLIC METHODS

    /**
     * Rolling Euclidean distance.  Values are stored per frame, relative to the start of that track.
     * @return
     */
    public double[][] getAverageRollingEuclideanDistance(boolean pixelDistances, boolean relativeToTrackStart) {
        // Determining the first and last frames
        int firstFrame = Integer.MAX_VALUE;
        int lastFrame = 0;
        for (Track track:values()) {
            int[] f = track.getF();
            if (f[0] < firstFrame) {
                firstFrame = f[0];
            }
            if (f[f.length-1] > lastFrame) {
                lastFrame = f[f.length-1];
            }
        }
        int longestDuration = lastFrame-firstFrame;

        // Creating the CumStat array
        CumStat[] cs = new CumStat[longestDuration+1];
        for (int i=0;i<cs.length;i++) {
            cs[i] = new CumStat();
        }

        for (Track track:values()) {
            int[] f = track.getF();
            double[] rollingEuclideanDistance = track.getRollingEuclideanDistance(pixelDistances);

            for (int i=0;i<rollingEuclideanDistance.length;i++) {
                int pos = relativeToTrackStart ? f[i]-f[0] : f[i]-firstFrame;
                cs[pos].addMeasure(rollingEuclideanDistance[i]);
            }
        }

        // Getting the frame numbers
        double[] f = new double[longestDuration];
        for (int i=0;i<f.length;i++) {
            f[i] = i;
        }

        // Getting the average and standard deviations
        double[] averageEuclideanDistance = Arrays.stream(cs).mapToDouble(CumStat::getMean).toArray();
        double[] stdevEuclideanDistance = Arrays.stream(cs).mapToDouble(CumStat::getStd).toArray();

        return new double[][]{f,averageEuclideanDistance,stdevEuclideanDistance};

    }

    /**
     * Rolling total path length.  Values are stored per frame, relative to the start of that track.
     * @return
     */
    public double[][] getAverageTotalPathLength(boolean pixelDistances, boolean relativeToTrackStart) {
        // Determining the first and last frames
        int firstFrame = Integer.MAX_VALUE;
        int lastFrame = 0;
        for (Track track:values()) {
            int[] f = track.getF();
            if (f[0] < firstFrame) {
                firstFrame = f[0];
            }
            if (f[f.length-1] > lastFrame) {
                lastFrame = f[f.length-1];
            }
        }
        int longestDuration = lastFrame-firstFrame;

        // Creating the CumStat array
        CumStat[] cs = new CumStat[longestDuration+1];
        for (int i=0;i<cs.length;i++) {
            cs[i] = new CumStat();
        }

        for (Track track:values()) {
            int[] f = track.getF();
            double[] rollingTotalPathLength = track.getRollingTotalPathLength(pixelDistances);

            for (int i=0;i<rollingTotalPathLength.length;i++) {
                int pos = relativeToTrackStart ? f[i]-f[0] : f[i]-firstFrame;
                cs[pos].addMeasure(rollingTotalPathLength[i]);
            }
        }

        // Getting the frame numbers
        double[] f = new double[longestDuration];
        for (int i=0;i<f.length;i++) {
            f[i] = i;
        }

        // Getting the average and standard deviations
        double[] averageTotalPathLength = Arrays.stream(cs).mapToDouble(CumStat::getMean).toArray();
        double[] stdevTotalPathLength = Arrays.stream(cs).mapToDouble(CumStat::getStd).toArray();

        return new double[][]{f,averageTotalPathLength,stdevTotalPathLength};

    }

    /**
     * Rolling directionality ratio.  Values are stored per frame, relative to the start of that track.
     * @return
     */
    public double[][] getAverageDirectionalityRatio(boolean pixelDistances, boolean relativeToTrackStart) {
        // Determining the first and last frames
        int firstFrame = Integer.MAX_VALUE;
        int lastFrame = 0;
        for (Track track:values()) {
            int[] f = track.getF();
            if (f[0] < firstFrame) {
                firstFrame = f[0];
            }
            if (f[f.length-1] > lastFrame) {
                lastFrame = f[f.length-1];
            }
        }
        int longestDuration = lastFrame-firstFrame;

        // Creating the CumStat array
        CumStat[] cs = new CumStat[longestDuration+1];
        for (int i=0;i<cs.length;i++) {
            cs[i] = new CumStat();
        }

        for (Track track:values()) {
            int[] f = track.getF();
            double[] rollingDirectionalityRatio = track.getRollingDirectionalityRatio(pixelDistances);
            for (int i=0;i<rollingDirectionalityRatio.length;i++) {
                int pos = relativeToTrackStart ? f[i]-f[0] : f[i]-firstFrame;
                cs[pos].addMeasure(rollingDirectionalityRatio[i]);
            }
        }

        // Getting the frame numbers
        double[] f = new double[longestDuration];
        for (int i=0;i<f.length;i++) {
            f[i] = i;
        }

        // Getting the average and standard deviations
        double[] averageDirectionalityRatio = Arrays.stream(cs).mapToDouble(CumStat::getMean).toArray();
        double[] stdDirectionalityRatio = Arrays.stream(cs).mapToDouble(CumStat::getStd).toArray();

        return new double[][]{f,averageDirectionalityRatio,stdDirectionalityRatio};

    }

    /**
     * Average directional persistence.  Values are stored as the average for each frame gap.
     * @return
     */
    public double[][] getAverageDirectionalPersistence(boolean pixelDistances) {
        // Determining the longest duration.  This is also the largest possible frame gap.
        int longestDuration = 0;
        for (Track track:values()) {
            if (track.getDuration() > longestDuration) {
                longestDuration = track.getDuration();
            }
        }

        // Creating the CumStat array
        CumStat[] cs = new CumStat[longestDuration+1];
        for (int i=0;i<cs.length;i++) {
            cs[i] = new CumStat();
        }

        for (Track track:values()) {
            DirectionalPersistenceCalculator.calculate(cs,track.getF(),track.getX(pixelDistances),track.getY(pixelDistances),track.getZ(pixelDistances));
        }

        // Getting the frame intervals
        double[] df = new double[longestDuration];
        for (int i=0;i<df.length;i++) {
            df[i] = i;
        }

        // Getting the average and standard deviations
        double[] averageDirectionalPersistence = Arrays.stream(cs).mapToDouble(CumStat::getMean).toArray();
        double[] stdevDirectionalPersistence = Arrays.stream(cs).mapToDouble(CumStat::getStd).toArray();

        return new double[][]{df,averageDirectionalPersistence,stdevDirectionalPersistence};

    }

    /**
     * Average MSD.  Values are stored as the average for each frame gap.
     * @return
     */
    public double[][] getAverageMSD(boolean pixelDistances) {
        // Determining the longest duration.  This is also the largest possible frame gap.
        int longestDuration = 0;
        for (Track track:values()) {
            if (track.getDuration() > longestDuration) {
                longestDuration = track.getDuration();
            }
        }

        // Creating the CumStat array
        CumStat[] cs = new CumStat[longestDuration+1];
        for (int i=0;i<cs.length;i++) {
            cs[i] = new CumStat();
        }

        for (Track track:values()) {
            MSDCalculator.calculate(cs,track.getF(),track.getX(pixelDistances),track.getY(pixelDistances),track.getZ(pixelDistances));
        }

        // Getting the frame intervals
        double[] df = new double[cs.length];
        for (int i=0;i<cs.length;i++) {
            df[i] = i;
        }

        // Getting the average and standard deviations
        double[] averageMSD = Arrays.stream(cs).mapToDouble(CumStat::getMean).toArray();
        double[] stdevMSD = Arrays.stream(cs).mapToDouble(CumStat::getStd).toArray();

        return new double[][]{df,averageMSD,stdevMSD};

    }

    /**
     * Number of objects per frame
     * @return int[][]{frame[],n[]
     */
    public int[][] getNumberOfObjects(boolean relativeToTrackStart) {
        // Determining the first and last frames
        int firstFrame = Integer.MAX_VALUE;
        int lastFrame = 0;
        for (Track track:values()) {
            int[] f = track.getF();
            if (f[0] < firstFrame) {
                firstFrame = f[0];
            }
            if (f[f.length-1] > lastFrame) {
                lastFrame = f[f.length-1];
            }
        }

        // Counting the number of objects present in each frame
        int[] n = new int[lastFrame-firstFrame+1];
        for (Track track:values()) {
            int[] f = track.getF();
            for (int ff:f) {
                int pos = relativeToTrackStart ? ff-f[0] : ff-firstFrame;
                n[pos] = n[pos] + 1;
            }
        }

        // Creating an array of frame numbers
        int[] f = new int[lastFrame-firstFrame+1];
        for (int i=0;i<f.length;i++) {
            f[i] = i+firstFrame;
        }

        return new int[][]{f,n};

    }

    /**
     * Returns the largest frame number of any track
     * @return
     */
    public int getHighestFrame() {
        int maxFr = 0;
        for (Track track:values()) {
            for (int fr:track.keySet()) {
                maxFr = Math.max(maxFr,fr);
            }
        }

        return maxFr;

    }
}
