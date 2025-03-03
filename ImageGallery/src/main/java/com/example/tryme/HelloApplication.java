package com.example.tryme;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {

        String css = getClass().getResource("/com/example/tryme/style.css").toExternalForm();

        var scene = new Scene(createUI(stage), 780, 460);
        stage.setTitle("Gallery");
        stage.setScene(scene);
        stage.show();

        scene.getStylesheets().add(css);
    }

    private Parent createUI(Stage mainStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #F0F0F0;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 0.2");

        HBox hbox = new HBox();
        hbox.setPrefWidth(900);
        hbox.setPrefHeight(40);
        hbox.setStyle("-fx-background-color: #2C3E50;" +
                "-fx-padding: 10px 20px;" +
                "-fx-alignment: center;" +
                "-fx-border-radius: 10px;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 3);");

        Label label = new Label("Your Gallery");
        label.setStyle("-fx-font-size: 18px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #ECF0F1;" +
                "-fx-font-family: 'Arial', sans-serif;" +
                "-fx-padding: 0 15px;");

        hbox.getChildren().add(label);


        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(20);
        gridPane.getStyleClass().add("gridpane");

        String[] imagePaths = {
                "C:/Users/DELL/Downloads/images/image1.jpg",
                "C:/Users/DELL/Downloads/images/image2.jpg",
                "C:/Users/DELL/Downloads/images/image3.jpg",
                "C:/Users/DELL/Downloads/images/image14.jpg",
                "C:/Users/DELL/Downloads/images/image15.jpg",
                "C:/Users/DELL/Downloads/images/image16.jpg",
                "C:/Users/DELL/Downloads/images/image17.jpg",
                "C:/Users/DELL/Downloads/images/image8.jpg",
                "C:/Users/DELL/Downloads/images/image9.png",
                "C:/Users/DELL/Downloads/images/image10.jpg",
                "C:/Users/DELL/Downloads/images/image11.png",
                "C:/Users/DELL/Downloads/images/image13.jpg"
        };

        String[] captions = {
                "12-1-2021", "12-1-2021", "12-1-2021", "12-1-2021", "12-1-2021", "17-01-2019",
                "12-1-2021", "12-1-2021", "12-1-2021", "12-1-2021", "12-1-2021", "12-1-2021"
        };

        int index = 0;
        double imageWidth = 150;
        double imageHeight = 150;

        // Loop to create image thumbnails
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 6; col++) {
                if (index < imagePaths.length) {
                    Image image = new Image("file:" + imagePaths[index]);
                    ImageView imageView = new ImageView(image);

                    imageView.setFitWidth(imageWidth);
                    imageView.setFitHeight(imageHeight);
                    imageView.setPreserveRatio(true);

                    imageView.setStyle("-fx-border-color: black;" +
                            "-fx-border-width: 2px;" +
                            "-fx-border-style: solid;");

                    imageView.setOnMouseEntered(event -> {
                        imageView.setScaleX(1.1);
                        imageView.setScaleY(1.1);
                        imageView.setStyle("-fx-border-color: #2980b9; -fx-border-width: 3px;");
                    });

                    imageView.setOnMouseExited(event -> {
                        imageView.setScaleX(1.0);
                        imageView.setScaleY(1.0);
                        imageView.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
                    });

                    VBox imageBox = new VBox(5);
                    imageBox.setStyle("-fx-border-color: #ffffff;" +
                            "-fx-border-width: 5px;" +
                            "-fx-border-style: solid;" +
                            "-fx-padding: 5px;" +
                            "-fx-border-radius: 5px;");
                    imageBox.getChildren().add(imageView);

                    Label caption = new Label(captions[index]);
                    caption.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");
                    imageBox.getChildren().add(caption);


                    final String[] paths = imagePaths;
                    final int imageIndex = index;

                    imageView.setOnMouseClicked(event -> {
                        showImagePopup(mainStage, List.of(paths), imageIndex);  // Pass the stage here
                    });

                    gridPane.add(imageBox, col, row);
                    index++;
                }
            }
        }


        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        borderPane.setCenter(scrollPane);

        borderPane.setTop(hbox);
        return borderPane;
    }

    private void showImagePopup(Stage mainStage, List<String> imagePaths, int initialIndex) {

        Stage popupStage = new Stage();
        popupStage.setTitle("Image Viewer");

        final int[] currentIndex = {initialIndex};


        ImageView imageView = new ImageView(new Image("file:" + imagePaths.get(currentIndex[0])));
        imageView.setFitWidth(600);
        imageView.setFitHeight(400);
        imageView.setSmooth(true);
        imageView.setPreserveRatio(true);

        // CSS for styling the popup
        String css = getClass().getResource("/com/example/tryme/style.css").toExternalForm();


        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button-back");
        backButton.setOnAction(event -> {
            popupStage.close();
            mainStage.show();
        });

        // Previous and Next buttons for navigation
        Button prevButton = new Button("Previous");
        prevButton.setOnAction(event -> {
            if (currentIndex[0] > 0) {
                currentIndex[0]--;
                updateLargeImage(imageView, imagePaths, currentIndex[0]);


            }
        });

        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> {
            if (currentIndex[0] < imagePaths.size() - 1) {
                currentIndex[0]++;

                updateLargeImage(imageView, imagePaths, currentIndex[0]);


                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.3), imageView);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);


                TranslateTransition translateUp = new TranslateTransition(Duration.seconds(0.7), imageView);
                translateUp.setFromY(30);
                translateUp.setToY(0);


                translateUp.setOnFinished(translateEvent -> fadeIn.play());
                translateUp.play();
            }
        });




        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(backButton, prevButton, nextButton);


        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(imageView, buttonBox);
        vbox.setStyle("-fx-background-color: darkgrey; -fx-padding: 20px;");
        vbox.setAlignment(Pos.CENTER);


        GridPane imageGrid = new GridPane();
        imageGrid.setVgap(10);
        imageGrid.setHgap(20);
        imageGrid.setAlignment(Pos.CENTER);


        int columns = 12;
        for (int i = 0; i < imagePaths.size(); i++) {
            ImageView gridImageView = new ImageView(new Image("file:" + imagePaths.get(i)));
            gridImageView.setFitWidth(60);
            gridImageView.setFitHeight(60);
            gridImageView.setSmooth(true);
            gridImageView.setPreserveRatio(true);


            int row = i / columns;
            int col = i % columns;


            imageGrid.add(gridImageView, col, row);


            gridImageView.setOnMouseEntered(event -> {
                gridImageView.setScaleX(1.1);
                gridImageView.setScaleY(1.1);
                gridImageView.setStyle("-fx-border-color: #2980b9; -fx-border-width: 3px;");
            });

            gridImageView.setOnMouseExited(event -> {
                gridImageView.setScaleX(1.0);
                gridImageView.setScaleY(1.0);
                gridImageView.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
            });


            final int thumbnailIndex = i;
            gridImageView.setOnMouseClicked(event -> {
                currentIndex[0] = thumbnailIndex;
                updateLargeImage(imageView, imagePaths, currentIndex[0]);
            });
        }


        vbox.getChildren().add(imageGrid);


        Scene popupScene = new Scene(vbox, 800, 550);
        popupScene.getStylesheets().add(css);
        popupStage.setScene(popupScene);
        popupStage.show();
    }


    private void updateLargeImage(ImageView imageView, List<String> imagePaths, int index) {
        imageView.setImage(new Image("file:" + imagePaths.get(index)));
    }



    public static void main(String[] args) {
        launch();
    }
}
