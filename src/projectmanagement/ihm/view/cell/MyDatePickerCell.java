/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view.cell;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.application.model.MyDate;
import projectmanagement.ihm.controller.Tags;
import projectmanagement.ihm.controller.TaskController;
import projectmanagement.ihm.view.MyPopup;
import projectmanagement.ihm.view.MyTableView;

/**
 *
 * @author Jérémy
 */
public class MyDatePickerCell<S, T> extends TableCell<Task, MyDate> {

    private DatePicker datePicker;
    private final ObservableList<Task> tasks;
    private final String column;
    private final Stage mainStage;
    private final MyTableView table;

    public MyDatePickerCell(ObservableList<Task> tasks, String column, Stage mainStage,MyTableView table) {
        super();
        this.tasks = tasks;
         this.table = table;
        this.column = column;
        this.mainStage = mainStage;
        createDatePicker();
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
            setDatepikerDate(item.toString());
            setText(item.toString());
            setGraphic(datePicker);
        }
    }

    public void setDatepikerDate(String dateAsStr) {
        LocalDate ld;
        int jour, mois, annee;
        if (dateAsStr != null) {
            jour = Integer.parseInt(dateAsStr.substring(0, 2));
            mois = Integer.parseInt(dateAsStr.substring(3, 5));
            annee = Integer.parseInt(dateAsStr.substring(6, dateAsStr.length()));
            ld = LocalDate.of(annee, mois, jour);
            datePicker.setValue(ld);
        }

    }

    public MyDate getValue() {
        LocalDate date = datePicker.getValue();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
        cal.set(Calendar.MONTH, date.getMonthValue() - 1);
        cal.set(Calendar.YEAR, date.getYear());
        return new MyDate(cal.getTime());
    }

    @Override
    public void commitEdit(MyDate newValue) {
        super.commitEdit(newValue); //To change body of generated methods, choose Tools | Templates.
        int index1 = getIndex();
        MyDate newDate = getValue();
        if (null != tasks) {
            setText(newDate.toString());
            if (column.equals(Tags.DATE_BEGIN)) {
                tasks.get(index1).setDatebegin(newDate);
            } else {
                tasks.get(index1).setDateend(newDate);
            }
        }
    }

    private void createDatePicker() {
        Locale.setDefault(ManagerLanguage.getInstance().getLocaleCourante());
        this.datePicker = new DatePicker();
        datePicker.setPromptText(MyDate.DATE_FORMAT);
        datePicker.setEditable(true);
        changeListener();
        datePicker.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
                setDatepikerDate(getValue().toString());
                commitEdit(getValue());
                new TaskController(table).updateListTask(tasks);
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
        datePicker.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                commitEdit(getValue());
                new TaskController(table).updateListTask(tasks);
            }
        });
        setAlignment(Pos.CENTER);
    }

    private void changeListener() {
        textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                MyDate dateNew = MyDate.valueOf(newValue);
                Task task = tasks.get(getIndex());
                if (Tags.DATE_BEGIN.equals(column)) {
                    if (task != null) {
                        if (task.getDateend() != null && dateNew.after(task.getDateend())) {
                            MyDate date = DAO.getInstance().getTask(task.getId()).getDatebegin();
                            if(oldValue == null || oldValue.isEmpty()){
                                setDatepikerDate(DAO.getInstance().getTask(task.getId()).getDatebegin().toString());
                                task.setDatebegin(date);
                                commitEdit(date);
                            }else{
                                setDatepikerDate(oldValue);
                            }
                            MyPopup.showPopupMessage(ManagerLanguage.getInstance()
                                    .getLocalizedTexte("TextDatebeginGrowThanDateEnd")+ " (" +task.getName()+")" , mainStage);
                        }
                        else if (task.getDateend() != null && task.getDatebegin()!= null &&
                                new TaskController(table).dateIsValidateByThisPredecessor(task,MyDate.valueOf(newValue)) == false) {
                            setDatepikerDate(oldValue);
                           // MyPopup.showPopupMessage(ManagerLanguage.getInstance().
                                 //   getLocalizedTexte("TextDateEndPredecessorAfterDateBeginTask"), mainStage);
                        }
                        else{
                        }
                    }
             
                }
                if (Tags.DATE_END.equals(column) && task != null) {
                    if (task.getDatebegin() != null && dateNew.before(task.getDatebegin())) {
                        setDatepikerDate(oldValue);
                        MyPopup.showPopupMessage(ManagerLanguage.getInstance().
                                getLocalizedTexte("TextDateEndGrowThanDateBegin") + " (" + task.getName() + ")", mainStage);
                    }else{
                        
                        new TaskController(table).dateIsValidateIfItsAnPredecessorOfOtherTask(task,MyDate.valueOf(newValue));
                    }
                }

            }
        });
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (!isEmpty()) {
            setDatepikerDate(getValue().toString());
        }
    }

}
