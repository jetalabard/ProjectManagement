package application;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class Main extends Application{

	private ProgressBar pb;
	private AnchorPane splashLayout;
	private Stage mainStage;

	public static void main(String[] args) throws Exception {
		launch(args);
	}
	@Override
	public void init() {
		String path=new String("/layout/SplashLayout.fxml");
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource(path));
		splashLayout=null;;
		try {
			splashLayout=(AnchorPane)loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pb = new ProgressBar();
		pb.setPrefHeight(19.0);
		pb.setPrefWidth(445.0);
		pb.setLayoutX(3.0);
		pb.setLayoutY(265.0);
		pb.setProgress(10.0);
		splashLayout.getChildren().addAll(pb);
		splashLayout.setEffect(new DropShadow());
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		
		final Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws InterruptedException {

                for (int i = 0; i < 10; i++) {
                    Thread.sleep(400);
                    updateProgress(i + 1, 10);
                }
                Thread.sleep(400);
                return 0;
            }
        };

        showSplash(
                primaryStage,
                task,
                () -> showMainStage(task.valueProperty())
        );
        new Thread(task).start();

	}
	private void showMainStage(ReadOnlyObjectProperty<Integer> friends ) {
		
		mainStage = new Stage();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/layout/FenetreLayout.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        mainStage.setTitle("Project Management");
        mainStage.getIcons().add(new Image("./ressources/logo_PM_2.png"));

        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);
        mainStage.setMaximized(true);
        mainStage.show();
    }

    private void showSplash( final Stage initStage, Task<?> task,InitCompletionHandler initCompletionHandler ) {
    	
		
		pb.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
            	pb.progressProperty().unbind();
            	pb.setProgress(1);
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();
                initCompletionHandler.complete();
            }
        });

        Scene splashScene = new Scene(splashLayout);
        initStage.setScene(splashScene);
        initStage.initStyle(StageStyle.UNDECORATED);
        initStage.show();
    }

    public interface InitCompletionHandler {
        public void complete();
    }

}