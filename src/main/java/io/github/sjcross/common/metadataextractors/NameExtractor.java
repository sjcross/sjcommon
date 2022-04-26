package io.github.sjcross.common.metadataextractors;

/**
 * Created by steph on 30/04/2017.
 */
public interface NameExtractor {
    String getName();
    String getPattern();
    boolean extract(Metadata result, String str);

}
