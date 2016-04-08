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
import projectmanagement.application.dataloader.ProjectDAO;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.ihm.controller.ClickController;
import projectmanagement.ihm.controller.MouseController;

/**
 *
 * @author Jérémy
 */
public class Home extends Page{
    private final Stage mainStage;

    public Home(Stage mainStage) {
        super();
        this.mainStage = mainStage;
        createView();
    }

    @Override
    public void createView() {
        this.setTop(new MenuPM(this,mainStage));
        FlowPane flow1 = getChoiceCreateOpen(mainStage);
        FlowPane flow2 = getOpenProject(mainStage);
        Separator sep = createSeparator();

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        VBox.setMargin(flow1, new Insets(102, 102, 102, 102));
        VBox.setMargin(flow2, new Insets(102, 102, 102, 102));
        vbox.getChildren().addAll(flow1, sep, flow2);
        
        Style.getStyle("/ressources/home.css", this);
        
        this.setCenter(vbox);
    }

    private FlowPane getChoiceCreateOpen(Stage mainStage) {

        FlowPane pane = new FlowPane();
        pane.setAlignment(Pos.CENTER);

        BorderPane border1 = createPanelOpenProject("/ressources/Folder Filled-20.png", ManagerLanguage.getInstance().getLocalizedTexte("NewProject"), null, false,mainStage);
        BorderPane border2 = createPanelOpenProject("/ressources/Open Folder Filled-20.png", ManagerLanguage.getInstance().getLocalizedTexte("OpenProject"), null, true,mainStage);

        pane.getChildren().addAll(border1, border2);

        return pane;
    }
    
    private FlowPane getOpenProject(Stage mainStage) {

        List<Project> projects = new ProjectDAO().getAllProject();
        FlowPane pane = new FlowPane();
        pane.setAlignment(Pos.CENTER);
        for (Project p : projects) {
            BorderPane border = createPanelOpenProject("/ressources/Folder Filled-20.png", p.getTitle(), p, true,mainStage);
            pane.getChildren().add(border);
        }

        return pane;
    }

   

    private BorderPane addFocus(BorderPane vbox) {
        vbox.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseController(vbox,String.valueOf(MouseEvent.MOUSE_ENTERED)));
        vbox.addEventHandler(MouseEvent.MOUSE_EXITED, new MouseController(vbox,String.valueOf(MouseEvent.MOUSE_EXITED)));
        return vbox;
    }

    private Separator createSeparator() {
        Separator separator1 = new Separator();
        separator1.setMaxWidth(400);
        separator1.getStyleClass().add("separator");
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
        pane.setCenter(t2);
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED,new MouseController(pane,String.valueOf(MouseEvent.MOUSE_CLICKED),isopen,p,mainStage));
        pane = addFocus(pane);
        pane.getStyleClass().add("borderPane");
        return pane;
    }

}
