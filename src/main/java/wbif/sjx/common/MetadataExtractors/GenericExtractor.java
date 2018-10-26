package wbif.sjx.common.MetadataExtractors;

import wbif.sjx.common.Object.HCMetadata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenericExtractor implements NameExtractor {
    private static final String name = "Generic";
    private final String pattern;
    private final String[] groups;

    public GenericExtractor(String pattern, String[] groups) {
        this.pattern = pattern;
        this.groups = groups;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public boolean extract(HCMetadata result, String str) {
        Pattern fi_pattern = Pattern.compile(pattern);
        Matcher fi_matcher = fi_pattern.matcher(str);

        if (fi_matcher.find()) {
            int nGroups = Math.min(fi_matcher.groupCount(),groups.length);
            for (int i=0;i<nGroups;i++) {
                String group = groups[i];
                String value = fi_matcher.group(i+1);
                result.put(group,value);

            }
            return true;

        } else {
            return false;

        }
    }
}