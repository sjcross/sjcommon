package wolfson.common.FileConditions;

import java.io.File;

/**
 * Checks the input file against a list of acceptable extensions
 * Created by sc13967 on 24/10/2016.
 */
public class ParentContainsString implements FileCondition {
    private String[] testStr;
    private int mode;

    public ParentContainsString(String testStr) {
        this.testStr = new String[]{testStr};
        this.mode = FileCondition.INC_PARTIAL;

    }

    public ParentContainsString(String testStr, int mode) {
        this.testStr = new String[]{testStr};
        this.mode = mode;

    }

    public ParentContainsString(String[] testStr) {
        this.testStr = testStr;
        this.mode = FileCondition.INC_PARTIAL;

    }

    public ParentContainsString(String[] testStr, int mode) {
        this.testStr = testStr;
        this.mode = mode;

    }

    public boolean test(File file) {
        boolean cnd = false;

        if (file != null) {
            String name = file.getParent();

            for (int i = 0; i < testStr.length; i++) {
                if (mode == FileCondition.INC_COMPLETE) {
                    if (name.equals(testStr[i])) cnd = true;

                } else if (mode == FileCondition.INC_PARTIAL) {
                    if (name.contains(testStr[i])) cnd = true;

                } else if (mode == FileCondition.EXC_COMPLETE) {
                    if (!name.equals(testStr[i])) cnd = true;

                } else if (mode == FileCondition.EXC_PARTIAL) {
                    if (!name.contains(testStr[i])) cnd = true;

                }
            }
        }

        return cnd;

    }
}