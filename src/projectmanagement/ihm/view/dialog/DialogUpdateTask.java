/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view.dialog;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import projectmanagement.application.business.Predecessor;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.Dialog;
import projectmanagement.application.model.LoaderImage;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.application.model.MyDate;
import projectmanagement.application.model.RessourcesTable;
import projectmanagement.ihm.view.MyTableView;
import projectmanagement.ihm.view.MyTableViewPredecessor;
import projectmanagement.ihm.view.MyTableViewRessource;
import projectmanagement.ihm.view.cell.NumberSpinner;
import projectmanagement.ihm.view.Style;

/**
 *
 * @author Jérémy
 */
public class DialogUpdateTask extends Dialog {

    private Task initialtask;
    private Task task;
    private MyTableView table;
    private List<RessourcesTable> listeRessource;
     private List<Predecessor> listePredecessor;
    private int indexTask;

    public DialogUpdateTask(Stage dialog, Stage stageParent) {
        super(dialog, stageParent, 1);
        
    }

    @Override
    public void createDialog() {
        Style.getStyle("dialog.css", this);
        this.listeRessource = RessourcesTable.transformRessourceToResssourceTable(task.getRessources());
        this.listePredecessor = task.getPredecessor();

        task.setPredecessor(listePredecessor);
        task.setRessources(RessourcesTable.transformRessourceTableToResssource(listeRessource));
        HBox header = createHeaderDialog(LoaderImage.getImage("Calendar-48.png"), getManagerLang().getLocalizedTexte("TextDialogUpdateTask"));
        TabPane tab = new TabPane();

        Tab general = createTabGeneral();
        Tab ressources = createTabRessource();

        Tab predecessors = createTabPredecessor();
        tab.getTabs().add(general);
        tab.getTabs().add(ressources);
        tab.getTabs().add(predecessors);
        HBox hbox = createLignDialogButtonValidationUpdateTask(ManagerLanguage.getInstance().getLocalizedTexte("Aply"),
                ManagerLanguage.getInstance().getLocalizedTexte("Cancel"), getStage(), this);
        this.getChildren().addAll(header, tab, hbox);
    }

    public void setTask(Task task) {
        this.task = new Task(task.getId(), task.getName(), task.getDatebegin(), task.getDateend(), task.getPriority(), task.getNote(), task.getIdProject(),task.getRessources(),task.getPredecessor());
        this.initialtask = new Task(task.getId(), task.getName(), task.getDatebegin(), task.getDateend(), task.getPriority(), task.getNote(), task.getIdProject(),task.getRessources(),task.getPredecessor());
    }

    private Tab createTabGeneral() {
        Tab general = new Tab(ManagerLanguage.getInstance().getLocalizedTexte("GeneralInformation"));
        VBox vbox = new VBox();
        TextField name = new TextField(task.getName());
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                task.setName(newValue);
            }
        });
        vbox.getChildren().add(createLignDialog(getManagerLang().getLocalizedTexte("Name"), name));

        LocalDate date = task.getDatebegin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DatePicker startDatePicker = new DatePicker(date);
        startDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {

            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                task.setDatebegin(new MyDate(Date.from(newValue.atStartOfDay(ZoneId.systemDefault()).toInstant())));
            }

        });
        vbox.getChildren().add(createLignDialog(getManagerLang().getLocalizedTexte("DateBegin"), startDatePicker));

        LocalDate dateEnd = task.getDatebegin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DatePicker endDatePicker = new DatePicker(dateEnd);
        endDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {

            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                task.setDateend(new MyDate(Date.from(newValue.atStartOfDay(ZoneId.systemDefault()).toInstant())));
            }

        });
        vbox.getChildren().add(createLignDialog(getManagerLang().getLocalizedTexte("DateEnd"), endDatePicker));

        NumberSpinner number = new NumberSpinner();
        number.setNumber(BigDecimal.valueOf(task.getPriority()));
        vbox.getChildren().add(createLignDialog(getManagerLang().getLocalizedTexte("Priority"), number));
        number.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                if(Integer.valueOf(newValue) <10 && Integer.valueOf(newValue)>0){
                    task.setPriority(Integer.valueOf(newValue));
                }
            }
        });

        TextArea note = new TextArea(task.getNote());
        note.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                task.setNote(newValue);
            }
        });
        vbox.getChildren().add(createLignDialog(getManagerLang().getLocalizedTexte("Note"), note));

        general.setContent(vbox);
        return general;
    }

    public void setTableView(MyTableView table) {
        this.table = table;
    }

    public Task getInitialtask() {
        return initialtask;
    }

    public void setInitialtask(Task initialtask) {
        this.initialtask = initialtask;
    }

    public MyTableView getTable() {
        return table;
    }

    public void setTable(MyTableView table) {
        this.table = table;
    }

    public List<RessourcesTable> getListeRessource() {
        return listeRessource;
    }

    public void setListeRessource(List<RessourcesTable> listeRessource) {
        this.listeRessource = listeRessource;
    }

    public List<Predecessor> getListePredecessor() {
        return listePredecessor;
    }

    public void setListePredecessor(List<Predecessor> listePredecessor) {
        this.listePredecessor = listePredecessor;
    }

    public Task getTask() {
        return task;
    }

    private Tab createTabRessource() {
        Tab ressources = new Tab(ManagerLanguage.getInstance().getLocalizedTexte("RessourcesInformation"));
        MyTableViewRessource tableRessource = new MyTableViewRessource(task, this);

        ressources.setContent(tableRessource);
        return ressources;
    }

    private Tab createTabPredecessor() {
        Tab predecessors = new Tab(ManagerLanguage.getInstance().getLocalizedTexte("PredecessorsInformation"));
        MyTableViewPredecessor table = new MyTableViewPredecessor(task, this);
        predecessors.setContent(table);
        return predecessors;
    }

    public void setIndexTask(int index) {
        this.indexTask = index;
    }
    
    public int getIndexTaskUpdate(){
        return indexTask;
    }

}
