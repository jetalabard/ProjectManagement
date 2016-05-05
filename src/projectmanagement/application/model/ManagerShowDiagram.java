/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import projectmanagement.ihm.view.MyTabPane;

/**
 *
 * @author Jérémy
 */
public class ManagerShowDiagram {

    private static ManagerShowDiagram instance;

    private boolean ganttTabShow;
    private boolean pertTabShow;

    private MyTabPane tabDiagram;

    
    public boolean isTabPertShow(){
        return tabDiagram.getTabPert().isSelected();
    }

    private ManagerShowDiagram() {
        this.pertTabShow = true;
        this.ganttTabShow = true;
    }

    public void closeTabGantt() {
        if (tabDiagram != null) {
            ganttTabShow = false;
            this.tabDiagram.closeGantt();
        }
    }
    
    public boolean canShowTabPane(){
        return pertTabShow || ganttTabShow;
    }

    public void closeTabPert() {
        if (tabDiagram != null) {
            pertTabShow = false;
            this.tabDiagram.closePert();
        }
    }

    public void showTabGantt() {
        ganttTabShow = true;
        this.tabDiagram.createTabGantt();
        this.tabDiagram.init();
    }

    public void showTabPert() {
        pertTabShow = true;
        this.tabDiagram.createTabPert();
        this.tabDiagram.init();
    }

    public void setGanttTabShow(boolean ganttTabShow) {
        this.ganttTabShow = ganttTabShow;
    }

    public void setPertTabShow(boolean pertTabShow) {
       this.pertTabShow = pertTabShow;
    }


    public MyTabPane getTabDiagram() {
        return tabDiagram;
    }

    public void setTabDiagram(MyTabPane tabDiagram) {
        this.tabDiagram = tabDiagram;
    }

    public final static ManagerShowDiagram getInstance() {
        if (instance == null) {
            instance = new ManagerShowDiagram();
        }
        return instance;
    }

    public boolean isGanttTabShow() {
        return ganttTabShow;
    }

    public boolean isPertTabShow() {
        return pertTabShow;
    }
    
    public void export(File file) throws IOException {
        BufferedImage snapShot;
        WritableImage tempImage ;
        if (isTabPertShow()) {
           tempImage = new WritableImage((int)tabDiagram.getAnchorPert().getWidth(),
                (int)tabDiagram.getAnchorPert().getHeight());
           tabDiagram.getAnchorPert().snapshot(null, tempImage);
        }
        else{
            tempImage = new WritableImage((int)tabDiagram.getAnchorGantt().getWidth(),
                (int)tabDiagram.getAnchorGantt().getHeight());
            tabDiagram.getAnchorGantt().snapshot(null, tempImage);
        }
        snapShot = SwingFXUtils.fromFXImage(tempImage, null);
        ImageIO.write(snapShot, "png", file);
    }

}
