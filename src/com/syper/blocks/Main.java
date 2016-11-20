package com.syper.blocks;

import com.mrbbot.json.JSON;
import com.mrbbot.json.JSONArray;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.File;
import java.util.Random;

public class Main extends Application {
    private Random random = new Random();

    private XForm root;
    private XForm blocks;

    private Camera camera;
    private Translate cameraTranslate;
    private Rotate cameraRotateX, cameraRotateY, cameraRotateZ;

    private String[] shapes = new String[] {"strange", "small", "full"};
    private int shapeIndex = 0;

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

        root.getChildren().add(cameraRootZ);
        cameraRootZ.getChildren().add(cameraRootY);
        cameraRootY.getChildren().add(cameraRootX);
        cameraRootX.getChildren().add(cameraRoot);
        cameraRoot.getChildren().add(camera);

        //camera.getTransforms().add(new Rotate(180, Rotate.Z_AXIS));
    }

    private void addBox(double x, double y, double z) {
        double r = random.nextDouble();
        double g = random.nextDouble();
        double b = random.nextDouble();
        addBox(x, y, z, new Color(r, g, b, 1));
    }

    private void addBox(double x, double y, double z, Color color) {
        Box box = new Box(1, 1, 1);
        box.setMaterial(new PhongMaterial(color));
        box.getTransforms().add(new Translate(x, y, z));
        blocks.getChildren().add(box);
    }

    private void addBlocks() {
        blocks.getChildren().clear();

        JSONArray json = JSON.parseArray(new File("blocks" + File.separator + shapes[shapeIndex] + ".json"));
        int zMax = json.size();
        double zStart = 0.0 - ((((double) zMax) - 1.0) / 2.0);

        for(int z = 0; z < zMax; z++) {
            JSONArray face = json.getArray(z);
            int yMax = face.size();
            double yStart = 0.0 - ((((double) yMax) - 1.0) / 2.0);

            for(int y = 0; y < yMax; y++) {
                JSONArray row = face.getArray(y);
                int xMax = row.size();
                double xStart = 0.0 - ((((double) xMax) - 1.0) / 2.0);

                for(int x = 0; x < xMax; x++) {
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

    private void reset() {
        cameraRotateX.setAngle(-25);
        cameraRotateY.setAngle(-45);
        cameraTranslate.setZ(-15);
        addBlocks();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Syper Blocks");

        root = new XForm();
        blocks = new XForm();
        root.getChildren().add(blocks);

        setup();

        SubScene subScene = new SubScene(root, 1024, 768, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.WHITE);
        subScene.setCamera(camera);
        Group group = new Group();
        group.getChildren().add(subScene);
        Scene scene = new Scene(group);

        scene.setOnKeyPressed((e) -> {
            switch(e.getCode()) {
                case LEFT:
                    cameraRotateY.setAngle(cameraRotateY.getAngle() - 5);
                    break;
                case RIGHT:
                    cameraRotateY.setAngle(cameraRotateY.getAngle() + 5);
                    break;
                case UP:
                    cameraRotateX.setAngle(cameraRotateX.getAngle() - 5);
                    break;
                case DOWN:
                    cameraRotateX.setAngle(cameraRotateX.getAngle() + 5);
                    break;
                case A:
                    shapeIndex--;
                    if(shapeIndex < 0)
                        shapeIndex = shapes.length - 1;
                    reset();
                    break;
                case D:
                    shapeIndex++;
                    if(shapeIndex > shapes.length - 1)
                        shapeIndex = 0;
                    reset();
                    break;
                case R:
                    reset();
                    break;
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
