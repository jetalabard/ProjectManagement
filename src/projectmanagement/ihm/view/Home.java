/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
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
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.LoaderImage;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.ihm.controller.HomeController;
import projectmanagement.ihm.controller.Tags;

/**
 *
 * @author Jérémy
 */
public final class Home extends Page {

    private final Stage mainStage;

    public Home(Stage mainStage) {
        super();
        this.mainStage = mainStage;
        createView();
    }

    @Override
    public void createView() {
        this.mainStage.setMinHeight(600);
        this.mainStage.setMinWidth(550);
        this.setTop(new MenuPM(this, mainStage, null, null));
        FlowPane flow1 = getChoiceCreateOpen(mainStage);
        ScrollPane flow2 = null;
        if(!DAO.getInstance().getAllProject().isEmpty()){
            flow2 = getOpenProject(mainStage);
        }
        Separator sep = createSeparator();
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        VBox.setMargin(flow1, new Insets(50, 50, 50, 50));
        if(flow2 == null){
            vbox.getChildren().addAll(flow1, sep);
        }else{
            VBox.setMargin(flow2, new Insets(50, 50, 50, 50));
            vbox.getChildren().addAll(flow1, sep, flow2);
        }
        Style.getStyle("home.css", this);
        this.setCenter(vbox);

    }

    private FlowPane getChoiceCreateOpen(Stage mainStage) {

        FlowPane pane = new FlowPane();
        pane.setAlignment(Pos.CENTER);
        BorderPane border1 = createPanelOpenProject(LoaderImage.getImage("Folder Filled-50.png"), 
                ManagerLanguage.getInstance().getLocalizedTexte("NewProject"));
        BorderPane border2 = createPanelOpenProject(LoaderImage.getImage("Open Folder Filled-50.png"), 
                ManagerLanguage.getInstance().getLocalizedTexte("OpenProject"));
        border1.addEventHandler(MouseEvent.MOUSE_CLICKED,new HomeController(null, mainStage, Tags.CREATE_PROJECT));
        border2.addEventHandler(MouseEvent.MOUSE_CLICKED,new HomeController(null, mainStage, Tags.OPEN));
        FlowPane.setMargin(border1, new Insets(10, 10, 10, 10));
        FlowPane.setMargin(border2, new Insets(10, 10, 10, 10));
        pane.getChildren().addAll(border1, border2);

        return pane;
    }

    private ScrollPane getOpenProject(Stage mainStage) {
        List<Project> projects = DAO.getInstance().getAllProject();
        FlowPane pane = new FlowPane();
        pane.setAlignment(Pos.CENTER);
        for (int i = projects.size() - 1; i >= 0; i--) 
        {
                BorderPane border = createPanelOpenProject(LoaderImage.getImage("Open Folder Filled-50.png"), projects.get(i).getTitle());
                pane.getChildren().add(border);
                border.addEventHandler(MouseEvent.MOUSE_CLICKED, new HomeController( projects.get(i), mainStage,Tags.OPEN_PROJECT));
                FlowPane.setMargin(border, new Insets(10, 10, 10, 10));
        }
        
        final ScrollPane scroll = new ScrollPane();
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Horizontal scroll bar
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Vertical scroll bar
        scroll.setContent(pane);
        scroll.setFitToWidth(true);
        return scroll;
    }

    private Separator createSeparator() {
        Separator separator1 = new Separator();
        separator1.setMaxWidth(400);
        separator1.getStyleClass().add("separator");
        return separator1;
    }

    private BorderPane createPanelOpenProject(String image, String text) {
        BorderPane pane = new BorderPane();
        ImageView open = new ImageView(image);
        Text t2 = new Text();
        t2.setFont(new Font(20));
        t2.setText(text);
        t2.setFill(Color.WHITE);
        SetMarginAndAlignementBorderPane(t2, open);
        pane.setTop(open);
        pane.setCenter(t2);
        pane.getStyleClass().add("borderPane");
        return pane;
    }

    private void SetMarginAndAlignementBorderPane(Text t2, ImageView open) {
        BorderPane.setMargin(t2, new Insets(10, 10, 10, 10));
        BorderPane.setMargin(open, new Insets(10, 10, 10, 10));
        BorderPane.setAlignment(open, Pos.CENTER);
        BorderPane.setAlignment(t2, Pos.CENTER);
    }

}
