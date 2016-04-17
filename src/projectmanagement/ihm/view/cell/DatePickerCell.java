/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view.cell;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import projectmanagement.application.business.StateNotSave;
import projectmanagement.application.business.Task;
import projectmanagement.application.dataloader.ProjectDAO;
import projectmanagement.application.model.ManageUndoRedo;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.application.model.MyDate;
import projectmanagement.ihm.controller.Tags;

/**
 *
 * @author Jérémy
 */
public class DatePickerCell<S, T> extends TableCell<Task, MyDate> {

    private DatePicker datePicker;
    private ObservableList<Task> tasks;
    private final String column; 
    private boolean initialzation;
    private static int init;

    public DatePickerCell(ObservableList<Task> tasks, String column) {
        super();
        this.tasks = tasks;
        this.column = column;

        if (datePicker == null) {
            initialzation = true;
            createDatePicker();
        }
        setGraphic(datePicker);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

    }

    @Override
    public void updateItem(MyDate item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            } else {
                if (item != null) {
                    setDatepikerDate(item.toString());
                    setText(item.toString());
                    
                }
                setGraphic(this.datePicker);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        }
    }

    private void setDatepikerDate(String dateAsStr) {

        LocalDate ld = null;
        int jour, mois, annee;

        jour = mois = annee = 0;
        try {
            jour = Integer.parseInt(dateAsStr.substring(0, 2));
            mois = Integer.parseInt(dateAsStr.substring(3, 5));
            annee = Integer.parseInt(dateAsStr.substring(6, dateAsStr.length()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        ld = LocalDate.of(annee, mois, jour);
        datePicker.setValue(ld);
    }

    private void createDatePicker() {
        Locale.setDefault(ManagerLanguage.getInstance().getLocaleCourante());
        this.datePicker = new DatePicker();
        datePicker.setPromptText(MyDate.DATE_FORMAT);
        datePicker.setEditable(true);
        datePicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                LocalDate date = datePicker.getValue();
                int index = getIndex();
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
                cal.set(Calendar.MONTH, date.getMonthValue() - 1);
                cal.set(Calendar.YEAR, date.getYear());

                MyDate mydate = new MyDate(cal.getTime());
                setText(mydate.toString());
                commitEdit(mydate);

                if (null != getTasks()) {
                    if (column.equals(Tags.DATE_BEGIN)) {
                        Task task = ProjectDAO.getInstance().getCurrentProject().getTasks().get(index);
                        task.setDatebegin(mydate);
                        if(task.equals(getTasks().get(index))){
                            getTasks().get(index).setDatebegin(mydate);
                        }
                        if(initialzation == false){
                            ManageUndoRedo.getInstance().add(ProjectDAO.getInstance().getCurrentProject().getTasks());
                             ProjectDAO.getInstance().getCurrentProject().setState(new StateNotSave());
                        }
                        else{
                            initialzation = false;
                        }
                    } else {
                        Task task = ProjectDAO.getInstance().getCurrentProject().getTasks().get(index);
                        task.setDateend(mydate);
                        if(task.equals(getTasks().get(index))){
                            getTasks().get(index).setDateend(mydate);
                        }
                        if(initialzation == false){
                            ManageUndoRedo.getInstance().add(ProjectDAO.getInstance().getCurrentProject().getTasks());
                             ProjectDAO.getInstance().getCurrentProject().setState(new StateNotSave());
                        }
                        else{
                            initialzation = false;
                        }
                    }
                    

                }
            }
        });
        setAlignment(Pos.CENTER);
    }
   

    @Override
    public void startEdit() {
        super.startEdit();
        
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
       
        
    }

    public ObservableList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ObservableList<Task> tasks) {
        this.tasks = tasks;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
    }
    
    public static void UninitializedInLayoutChildrenFunction(boolean value){
        if(value == true){
            init =0;
        }
        else{
            init =1;
        }
        
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren(); //To change body of generated methods, choose Tools | Templates.
        if(init != 0){
            System.out.println("Date cell layout children");
            initialzation = true;
        }
    }
    
    

}
