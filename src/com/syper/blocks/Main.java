package com.syper.blocks;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private static String[] shapes = new String[] {"strange", "small", "full"};
    private int shapeIndex = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Syper Blocks");

        BlockXForm mainDisplay = new BlockXForm(shapes[shapeIndex], 1024, 568);

        BorderPane pane = new BorderPane();

        Group group = new Group();
        group.getChildren().add(mainDisplay.getSubScene(Color.WHITE));
        pane.setCenter(group);

        HBox hBox = new HBox();

        SubScene sub1 = new BlockXForm("small", 256, 200).getSubScene(Color.LIGHTGRAY);
        sub1.setOnMouseClicked((event -> System.out.println("Box 1 Clicked")));
        SubScene sub2 = new BlockXForm("strange", 256, 200).getSubScene(Color.DARKGRAY);
        sub2.setOnMouseClicked((event -> System.out.println("Box 2 Clicked")));
        SubScene sub3 = new BlockXForm("full", 256, 200).getSubScene(Color.LIGHTGRAY);
        sub3.setOnMouseClicked((event -> System.out.println("Box 3 Clicked")));
        SubScene sub4 = new BlockXForm("small", 256, 200).getSubScene(Color.DARKGRAY);
        sub4.setOnMouseClicked((event -> System.out.println("Box 4 Clicked")));

        hBox.getChildren().addAll(sub1, sub2, sub3, sub4);

        pane.setBottom(hBox);


        Scene scene = new Scene(pane, 1024, 768);
        scene.setFill(Color.BLACK);

        scene.setOnKeyPressed((e) -> {
            switch(e.getCode()) {
                case LEFT:
                    mainDisplay.cameraRotateY.setAngle(mainDisplay.cameraRotateY.getAngle() - 5);
                    break;
                case RIGHT:
                    mainDisplay.cameraRotateY.setAngle(mainDisplay.cameraRotateY.getAngle() + 5);
                    break;
                case UP:
                    mainDisplay.cameraRotateX.setAngle(mainDisplay.cameraRotateX.getAngle() - 5);
                    break;
                case DOWN:
                    mainDisplay.cameraRotateX.setAngle(mainDisplay.cameraRotateX.getAngle() + 5);
                    break;
                case A:
                    shapeIndex--;
                    if(shapeIndex < 0)
                        shapeIndex = shapes.length - 1;
                    mainDisplay.setShape(shapes[shapeIndex]);
                    break;
                case D:
                    shapeIndex++;
                    if(shapeIndex > shapes.length - 1)
                        shapeIndex = 0;
                    mainDisplay.setShape(shapes[shapeIndex]);
                    break;
                case R:
                    mainDisplay.reset();
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
