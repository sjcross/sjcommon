package wbif.sjx.common.HighContent.Object;

import ij.ImagePlus;
import ij.plugin.ZProjector;

import java.util.HashMap;

/**
 * Created by steph on 30/04/2017.
 */
public class Image {
    private ImagePlus imagePlus;
    private HashMap<String,Measurement> measurements = new HashMap<>();

    // CONSTRUCTORS

    public Image (ImagePlus imagePlus) {
        this.imagePlus = imagePlus;
    }


    // PUBLIC METHODS

    public Image projectImageInZ() {
        ZProjector z_projector = new ZProjector(imagePlus);
        z_projector.setMethod(ZProjector.MAX_METHOD);
        z_projector.doProjection();
        ImagePlus iplOut = z_projector.getProjection();

        return new Image(iplOut);

    }

    public void addMeasurement(String name, Measurement measurement) {
        measurements.put(name,measurement);

    }

    public Measurement getMeasurement(String name) {
        return measurements.get(name);

    }


    // GETTERS AND SETTERS

    public ImagePlus getImagePlus() {
        return imagePlus;
    }

    public void setImagePlus(ImagePlus imagePlus) {
        this.imagePlus = imagePlus;
    }

    public HashMap<String, Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(HashMap<String, Measurement> measurements) {
        this.measurements = measurements;
    }

}