package com.syper.blocks;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {
    private Random random = new Random();

    private XForm root;

    private Camera camera;
    private Translate cameraTranslate;
    private Rotate cameraRotateX, cameraRotateY, cameraRotateZ;

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

        camera.getTransforms().add(new Rotate(180, Rotate.Z_AXIS));
    }

    private void addBox(int x, int y, int z) {
        double r = random.nextDouble();
        double g = random.nextDouble();
        double b = random.nextDouble();
        addBox(x, y, z, new Color(r, g, b, 1));
    }

    private void addBox(int x, int y, int z, Color color) {
        Box box = new Box(1, 1, 1);
        box.setMaterial(new PhongMaterial(color));
        box.getTransforms().add(new Translate(x, y, z));
        root.getChildren().add(box);
    }

    private void addContent() {
        /*addBox(0, 0, 0, Color.GRAY);

        addBox(1, 0, 0, Color.RED);
        addBox(0, 1, 0, Color.GREEN);
        addBox(0, 0, 1, Color.BLUE);*/
    }

    private void setup() {
        setupCamera();
        cameraRotateX.setAngle(30);
        cameraRotateY.setAngle(-135);
        cameraTranslate.setZ(-20);

        addContent();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new XForm();

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
                    cameraRotateY.setAngle(cameraRotateY.getAngle() - 2);
                    break;
                case RIGHT:
                    cameraRotateY.setAngle(cameraRotateY.getAngle() + 2);
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
