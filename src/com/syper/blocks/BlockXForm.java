package com.syper.blocks;

import com.mrbbot.json.JSON;
import com.mrbbot.json.JSONArray;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.io.File;
import java.util.Random;

public class BlockXForm extends XForm {
    private static Color[] colors = new Color[]{
            new Color(204.0 / 255.0, 18.0 / 255.0, 54.0 / 255.0, 1),
            new Color(233.0 / 255.0, 78.0 / 255.0, 24.0 / 255.0, 1),
            new Color(233.0 / 255.0, 152.0 / 255.0, 0.0 / 255.0, 1),
            new Color(63.0 / 255.0, 165.0 / 255.0, 53.0 / 255.0, 1),
            new Color(0.0 / 255.0, 117.0 / 255.0, 191.0 / 255.0, 1),
            new Color(128.0 / 255.0, 54.0 / 255.0, 140.0 / 255.0, 1)
    };

    private static Random random = new Random();

    private JSONArray json;
    private final int width;
    private final int height;

    private XForm blocks;

    private Camera camera;
    public Translate cameraTranslate;
    public Rotate cameraRotateX, cameraRotateY, cameraRotateZ;

    public BlockXForm(String shape, int width, int height) {
        json = JSON.parseArray(new File("blocks" + File.separator + shape + ".json"));
        this.width = width;
        this.height = height;

        blocks = new XForm();
        getChildren().add(blocks);

        setup();
    }

    public SubScene getSubScene(Color background) {
        SubScene subScene = new SubScene(this, width, height, true, SceneAntialiasing.BALANCED);
        subScene.setFill(background);
        subScene.setCamera(camera);
        return subScene;
    }

    public void setShape(String shape) {
        json = JSON.parseArray(new File("blocks" + File.separator + shape + ".json"));
        reset();
    }

    private void setupCamera() {
        camera = new PerspectiveCamera(true);

        cameraTranslate = new Translate();
        cameraRotateX = new Rotate(0, Rotate.X_AXIS);
        cameraRotateY = new Rotate(0, Rotate.Y_AXIS);
        cameraRotateZ = new Rotate(0, Rotate.Z_AXIS);

        XForm cameraRootX = new XForm();
        cameraRootX.getTransforms().add(cameraRotateX);
        XForm cameraRootY = new XForm();
        cameraRootY.getTransforms().add(cameraRotateY);
        XForm cameraRootZ = new XForm();
        cameraRootZ.getTransforms().add(cameraRotateZ);
        XForm cameraRoot = new XForm();
        cameraRoot.getTransforms().add(cameraTranslate);

        getChildren().add(cameraRootZ);
        cameraRootZ.getChildren().add(cameraRootY);
        cameraRootY.getChildren().add(cameraRootX);
        cameraRootX.getChildren().add(cameraRoot);
        cameraRoot.getChildren().add(camera);

        //camera.getTransforms().add(new Rotate(180, Rotate.Z_AXIS));
    }

    private void addBox(double x, double y, double z) {
        addBox(x, y, z, colors[random.nextInt(colors.length)]);
    }

    private void addBox(double x, double y, double z, Color color) {
        Box box = new Box(1, 1, 1);
        box.setMaterial(new PhongMaterial(color));
        box.getTransforms().add(new Translate(x, y, z));
        blocks.getChildren().add(box);
    }

    private void addBlocks() {
        blocks.getChildren().clear();

        if(json == null)
            return;

        int zMax = json.size();
        double zStart = 0.0 - ((((double) zMax) - 1.0) / 2.0);

        for (int z = 0; z < zMax; z++) {
            JSONArray face = json.getArray(z);
            int yMax = face.size();
            double yStart = 0.0 - ((((double) yMax) - 1.0) / 2.0);

            for (int y = 0; y < yMax; y++) {
                JSONArray row = face.getArray(y);
                int xMax = row.size();
                double xStart = 0.0 - ((((double) xMax) - 1.0) / 2.0);

                for (int x = 0; x < xMax; x++) {
                    int value = row.getInt(x);

                    if (value == 1) {
                        addBox(xStart + x, yStart + y, zStart + z);
                    }
                }
            }
        }
    }

    private void setup() {
        setupCamera();
        reset();
    }

    public void reset() {
        cameraRotateX.setAngle(-25);
        cameraRotateY.setAngle(-45);
        cameraTranslate.setZ(-14);
        addBlocks();
    }
}
