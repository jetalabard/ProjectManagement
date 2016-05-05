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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.Dialog;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.application.model.MyDate;
import projectmanagement.ihm.view.MyPopup;
import projectmanagement.ihm.view.cell.NumberSpinner;

/**
 *
 * @author Jérémy
 */
public class TabGeneralUpdateDialog extends Tab {

    private final ManagerLanguage managerLang;
    private DatePicker endDatePicker;
    private DatePicker startDatePicker;
    private NumberSpinner duration;
    private final Task task;
    private final Dialog dialog;
    private ComboBox comboBox;

    public TabGeneralUpdateDialog(Task task, Dialog dialog) {
        super();
        this.task = task;
        this.dialog = dialog;
        managerLang = ManagerLanguage.getInstance();
        createTab();
    }

    private void createTab() {
        this.setText(ManagerLanguage.getInstance().getLocalizedTexte("GeneralInformation"));
        VBox vbox = new VBox();

        initDateBeginAndDateEnd();

        vbox = createTextName(vbox);
        vbox = createComboBox(vbox);
        vbox = createDatePickerBeginListenerAndAdd(vbox);
        vbox = createDatePickerEndListenerAndAdd(vbox);
        vbox = createDurationSpinner(vbox);
        vbox = createPriority(vbox);
        vbox = createNoteArea(vbox);
        setContent(vbox);
    }

    private VBox createNoteArea(VBox vbox) {
        TextArea note = new TextArea(task.getNote());
        note.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                task.setNote(newValue);
            }
        });
        vbox.getChildren().add(dialog.createLignDialog(managerLang.getLocalizedTexte("Note"), note));
        return vbox;
    }

    private VBox createPriority(VBox vbox) {
        NumberSpinner number = new NumberSpinner(0);
        number.setNumber(BigDecimal.valueOf(task.getPriority()));
        vbox.getChildren().add(dialog.createLignDialog(managerLang.getLocalizedTexte("Priority"), number));
        number.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                if (Integer.valueOf(newValue) < 10 && Integer.valueOf(newValue) > 0) {
                    task.setPriority(Integer.valueOf(newValue));
                }
            }
        });
        return vbox;
    }

    private VBox createDurationSpinner(VBox vbox) {
        vbox.getChildren().add(dialog.createLignDialog(managerLang.getLocalizedTexte("Duration"), duration));
        duration.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {

            if (comboBox.getValue().equals(managerLang.getLocalizedTexte("textOptionUpdate1"))) {
                MyDate newDate = new MyDate(task.getDatebegin().getTime() + (Integer.valueOf(newValue) * MyDate.DAY));
                task.setDateend(newDate);
                endDatePicker.setValue(task.getDateend().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } else if (comboBox.getValue().equals(managerLang.getLocalizedTexte("textOptionUpdate2"))) {
                task.setDatebegin(new MyDate(task.getDateend().getTime() - (Integer.valueOf(newValue) * MyDate.DAY)));
                startDatePicker.setValue(task.getDatebegin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } else {
                //duration ne s incrémente plus dans le cas par défaut
            }

        });
        return vbox;
    }

    private VBox createDatePickerBeginListenerAndAdd(VBox vbox) {
        startDatePicker.valueProperty().addListener((ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) -> {
            if (!comboBox.getValue().equals(managerLang.getLocalizedTexte("textOptionUpdate1"))) {
                MyDate date = new MyDate(Date.from(newValue.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                if (date.after(task.getDateend())) {
                    MyDate old = new MyDate(Date.from(oldValue.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    task.setDatebegin(old);
                    startDatePicker.setValue(task.getDatebegin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    MyPopup.showPopupMessage(managerLang.getLocalizedTexte("TextDatebeginGrowThanDateEnd"), dialog.getStage());
                } else {
                    task.setDatebegin(date);
                    startDatePicker.setValue(task.getDatebegin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                }
                duration.setNumber(BigDecimal.valueOf(MyDate.diffDays(task.getDatebegin(), task.getDateend())));
            }
        });
        vbox.getChildren().add(dialog.createLignDialog(managerLang.getLocalizedTexte("DateBegin"), startDatePicker));
        return vbox;
    }

    private VBox createTextName(VBox vbox) {
        TextField name = new TextField(task.getName());
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                task.setName(newValue);
            }
        });
        vbox.getChildren().add(dialog.createLignDialog(managerLang.getLocalizedTexte("Name"), name));
        return vbox;
    }

    private void initDateBeginAndDateEnd() {
        LocalDate date = task.getDatebegin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        startDatePicker = new DatePicker(date);
        LocalDate dateEnd = task.getDateend().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        endDatePicker = new DatePicker(dateEnd);
        duration = new NumberSpinner(1);
        duration.setNumber(BigDecimal.valueOf(MyDate.days(MyDate.diff(task.getDatebegin(), task.getDateend()))));
    }

    private VBox createComboBox(VBox vbox) {
        ObservableList<String> options = FXCollections.observableArrayList(
                managerLang.getLocalizedTexte("textOptionUpdate1"),
                managerLang.getLocalizedTexte("textOptionUpdate2"),
                managerLang.getLocalizedTexte("textOptionUpdate3")
        );
        comboBox = new ComboBox(options);
        comboBox.setValue(managerLang.getLocalizedTexte("textOptionUpdate2"));
        endDatePicker.setDisable(true);
        startDatePicker.setDisable(false);
        duration.setDisable(false);
        comboBox.valueProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            if (newValue.toString().equals(managerLang.getLocalizedTexte("textOptionUpdate2"))) {
                endDatePicker.setDisable(true);
                startDatePicker.setDisable(false);
                duration.setDisable(false);
            } else if (newValue.toString().equals(managerLang.getLocalizedTexte("textOptionUpdate1"))) {
                endDatePicker.setDisable(false);
                startDatePicker.setDisable(true);
                duration.setDisable(false);
            } else {
                endDatePicker.setDisable(false);
                startDatePicker.setDisable(false);
                duration.setDisable(true);
            }
        });
        vbox.getChildren().add(dialog.createLignDialog(managerLang.getLocalizedTexte("Options"), comboBox));
        return vbox;
    }

    private VBox createDatePickerEndListenerAndAdd(VBox vbox) {
        endDatePicker.valueProperty().addListener((ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) -> {
            if (!comboBox.getValue().equals(managerLang.getLocalizedTexte("textOptionUpdate2"))) {
                MyDate date = new MyDate(Date.from(newValue.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                if (date.before(task.getDateend())) {
                    MyDate old = new MyDate(Date.from(oldValue.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    task.setDateend(old);
                    endDatePicker.setValue(task.getDateend().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    MyPopup.showPopupMessage(managerLang.getLocalizedTexte("TextDateEndGrowThanDateBegin"), dialog.getStage());
                } else {
                    task.setDateend(date);
                    endDatePicker.setValue(task.getDateend().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                }
                duration.setNumber(BigDecimal.valueOf(MyDate.diffDays(task.getDatebegin(), task.getDateend())));
            }
        });
        vbox.getChildren().add(dialog.createLignDialog(managerLang.getLocalizedTexte("DateEnd"), endDatePicker));
        return vbox;
    }

}
