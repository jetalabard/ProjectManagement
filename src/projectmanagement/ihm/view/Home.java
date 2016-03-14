/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import projectmanagement.application.business.Project;
import projectmanagement.application.model.DAOProject;
import projectmanagement.ihm.controller.ClickController;

/**
 *
 * @author Jérémy
 */
public class Home extends BorderPane {

    public Home(Stage mainStage) {
        super();
        createView(mainStage);
    }

    private void createView(Stage mainStage) {
        this.setTop(new MenuPM());
        FlowPane flow1 = getChoiceCreateOpen(mainStage);
        FlowPane flow2 = getOpenProject(mainStage);
        Separator sep = createSeparator();

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        VBox.setMargin(flow1, new Insets(102, 102, 102, 102));
        VBox.setMargin(flow2, new Insets(102, 102, 102, 102));
        vbox.getChildren().addAll(flow1, sep, flow2);

        getStylesheets().add(
                getClass().getResource(
                        "/ressources/home.css"
                ).toExternalForm()
        );
        this.setCenter(vbox);
    }

    private FlowPane getChoiceCreateOpen(Stage mainStage) {

        FlowPane pane = new FlowPane();
        pane.setAlignment(Pos.CENTER);

        BorderPane border1 = createPanelOpenProject("/ressources/Folder Filled-20.png", "Create Project", null, false,mainStage);
        BorderPane border2 = createPanelOpenProject("/ressources/Open Folder Filled-20.png", "Open Project", null, true,mainStage);

        pane.getChildren().addAll(border1, border2);

        return pane;
    }

    private FlowPane getOpenProject(Stage mainStage) {

        List<Project> projects = DAOProject.getInstance().getAllProjects();
        FlowPane pane = new FlowPane();
        pane.setAlignment(Pos.CENTER);
        for (Project p : projects) {
            BorderPane border = createPanelOpenProject("/ressources/Folder Filled-20.png", p.getTitle(), p, true,mainStage);
            pane.getChildren().add(border);
        }

        return pane;
    }

    private void focusGained(BorderPane pane) {
        pane.setStyle("-fx-background-color: gray;"
                + "-fx-background-radius: 5.0; "
                + "-fx-padding: 8;"
                + "-fx-background-insets: 0.0 5.0 0.0 5.0");
    }

    private void focusLost(BorderPane pane) {
        pane.setStyle("-fx-background-color: darkgray;"
                + "-fx-background-radius: 5.0; "
                + "-fx-padding: 8;"
                + "-fx-background-insets: 0.0 5.0 0.0 5.0");
    }

    private BorderPane addFocus(BorderPane vbox) {
        vbox.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                focusGained(vbox);
            }
        });

        vbox.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                focusLost(vbox);
            }
        });
        return vbox;
    }

    private Separator createSeparator() {
        Separator separator1 = new Separator();
        separator1.setMaxWidth(400);
        separator1.getStylesheets().add("separator");
        return separator1;
    }

    private BorderPane createPanelOpenProject(String image, String text, Project p, boolean isopen,Stage mainStage) {
        BorderPane pane = new BorderPane();

        ImageView open = new ImageView(image);
        Text t2 = new Text();
        t2.setFont(new Font(20));
        t2.setText(text);
        t2.setFill(Color.WHITE);
        BorderPane.setMargin(t2, new Insets(20, 20, 20, 20));
        BorderPane.setMargin(open, new Insets(20, 20, 20, 20));
        BorderPane.setAlignment(open, Pos.CENTER);
        BorderPane.setAlignment(t2, Pos.CENTER);
        pane.setTop(open);
        pane.setBottom(t2);
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, new ClickController(isopen,p,mainStage));
        pane = addFocus(pane);
        pane.getStyleClass().add("borderPane");
        return pane;
    }

}
