package wbif.sjx.common.HighContent.GUI;

import wbif.sjx.common.HighContent.Object.HCParameter;

import javax.swing.*;

/**
 * Created by Stephen on 20/05/2017.
 */
public class BooleanParameter extends JCheckBox {
    private HCParameter parameter;

    public BooleanParameter(HCParameter parameter) {
        this.parameter = parameter;

    }

    public HCParameter getParameter() {
        return parameter;
    }
}