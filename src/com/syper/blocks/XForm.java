package com.syper.blocks;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class XForm extends Group {
    public Translate translate = new Translate();
    public Translate pivot = new Translate();
    public Translate inversePivot = new Translate();
    public Rotate rotateX = new Rotate();
    { rotateX.setAxis(Rotate.X_AXIS); }
    public Rotate rotateY = new Rotate();
    { rotateY.setAxis(Rotate.Y_AXIS); }
    public Rotate rotateZ = new Rotate();
    { rotateZ.setAxis(Rotate.Z_AXIS); }
    public Scale scale = new Scale();

    public XForm() {
        super();
        getTransforms().addAll(translate, pivot, rotateZ, rotateY, rotateX, scale, inversePivot);
    }
}