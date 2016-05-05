/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view.dialog;

import java.util.ArrayList;
import java.util.List;
import projectmanagement.application.model.PredecessorConverter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import projectmanagement.application.business.Predecessor;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.ihm.controller.RowControllerTablePredecessor;
import projectmanagement.ihm.controller.Tags;
import projectmanagement.ihm.view.MyPopup;
import projectmanagement.ihm.view.cell.ComboBoxTableCellPredecessor;
import projectmanagement.ihm.view.cell.IntegerEditingCellPredecessor;
import projectmanagement.ihm.view.cell.StringCellPredecessor;

/**
 *
 * @author Jérémy
 */
public class MyTableViewPredecessor extends TableView<Predecessor> {

    private Task task = null;

    private final DialogUpdateTask dialogParent;

    public MyTableViewPredecessor(Task task, DialogUpdateTask dialogParent) {
        this.task = task;
        this.dialogParent = dialogParent;
        createTableView();
    }

    private void createTableView() {

        setItems(FXCollections.observableArrayList(this.dialogParent.getListePredecessor()));
        setEditable(true);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setPlaceholder(new Text(ManagerLanguage.getInstance().getLocalizedTexte("NoContentClickToAdd")));
        TableColumn col2 = createColumnType();
        TableColumn col = createColumnTaskParent();
        TableColumn col3 = createColumnGap();
        TableColumn col4 = createColumnConstraint();

        getColumns().addAll(col, col2, col3, col4);

        setContextMenu(new ContextMenu(createContextMenuAddTask(), createContextMenuDelete()));
        setRowFactory(new RowControllerTablePredecessor(this, dialogParent));
        getSelectionModel().selectFirst();
    }

    private MenuItem createContextMenuDelete() {
        MenuItem mnuDel = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("Delete"));
        mnuDel.setOnAction((ActionEvent t) -> {
            if (!getItems().isEmpty()) {
                Predecessor item = getSelectionModel().getSelectedItem();
                if (item != null) {
                    if (item.getId() != null) {
                        //en base sinon si == null pas encore eu le insert
                        DAO.getInstance().deletePredecessor(item.getId());
                    }
                    this.dialogParent.getListePredecessor().remove(getSelectionModel().getSelectedIndex());
                    getItems().remove(getSelectionModel().getSelectedIndex());
                }
            }
        });
        return mnuDel;
    }

    private MenuItem createContextMenuAddTask() {
        MenuItem mnuAdd = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("AddPredecessor"));
        mnuAdd.setOnAction((ActionEvent event) -> {
            calculTaskToAddLikePredecessor();
        });
        return mnuAdd;
    }

    private void calculTaskToAddLikePredecessor() {
        boolean pass = false;
        
        if (dialogParent.getListePredecessor().size()
                == DAO.getInstance().getCurrentProject().getTasks().size() - 1) {
            //on ne peut pas avoir plus de n-1 tâche prédecesseurs
            MyPopup.showPopupMessage(dialogParent.getManagerLang().getLocalizedTexte("TextPredecessor"),
                    dialogParent.getStage());
        } else {
            List<Task> ListRefCurrentTask = new ArrayList<>();
            for (Task t2 : DAO.getInstance().getCurrentProject().getTasks()) {
                if (!t2.equals(task)) {
                    for (Predecessor p : t2.getPredecessor()) {
                        if (p.getIdTaskParent().equals(task.getId()))
                        {
                            //eviter cycle entre deux taches qui se suivent
                            if(!ListRefCurrentTask.contains(t2)){
                                ListRefCurrentTask.add(t2);
                            }
                        }
                    }
                }
            }
            Task end = DAO.getInstance().getCurrentProject().getEnd();
            parcours(task, end, ListRefCurrentTask);
            
            if (ListRefCurrentTask.isEmpty()) {
                //on ajoute la tache que l'on veut qui n'y est pas féjà
                for (Task t : DAO.getInstance().getCurrentProject().getTasks()) {
                    Predecessor pred = new Predecessor("", 0, "", task.getId(), t.getId());
                    if (!t.equals(task)) {
                        if (!dialogParent.getListePredecessor().isEmpty()) {
                            for (Predecessor p2 : dialogParent.getListePredecessor()) {
                                if (!p2.getIdTaskParent().equals(pred.getIdTaskParent())) {
                                    dialogParent.getListePredecessor().add(pred);
                                    getItems().add(pred);
                                    pass = true;
                                    break;
                                }
                            }
                        } else {
                            dialogParent.getListePredecessor().add(pred);
                            getItems().add(pred);
                            pass = true;
                            break;
                        }
                    }
                }
            } else {
                List<Task> taskPossible = new ArrayList<>(DAO.getInstance().getCurrentProject().getTasks());
                taskPossible.removeAll(ListRefCurrentTask);
                taskPossible.remove(task);
                for (Task t : taskPossible) {
                    Predecessor pred = new Predecessor("", 0, "", task.getId(), t.getId());
                    if (!dialogParent.getListePredecessor().isEmpty()) {
                        for (Predecessor p2 : dialogParent.getListePredecessor()) {
                            if (!p2.getIdTaskParent().equals(pred.getIdTaskParent())) {
                                dialogParent.getListePredecessor().add(pred);
                                getItems().add(pred);
                                pass = true;
                                break;
                            }
                        }
                    }else{
                        dialogParent.getListePredecessor().add(pred);
                        getItems().add(pred);
                        pass = true;
                        break;
                    }
                    
                }
            }
            if (pass == false) {
                MyPopup.showPopupMessage(dialogParent.getManagerLang().getLocalizedTexte("TextPredecessor"),
                        dialogParent.getStage());
            }
        }
    }


    private TableColumn createColumnType() {
        TableColumn col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Type"));
        col.setCellValueFactory(new PropertyValueFactory<>("type"));
        col.setCellFactory((Object col1) -> new StringCellPredecessor(Tags.TYPE, 1));
        return col;
    }

    private TableColumn createColumnTaskParent() {
        TableColumn<Predecessor, Integer> col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Predecessor"));
        col.setCellValueFactory(new PropertyValueFactory<>("idTaskParent"));
        col.setOnEditCommit((TableColumn.CellEditEvent<Predecessor, Integer> event) -> {
            Predecessor pred = getSelectionModel().getSelectedItem();
            pred.setIdTaskParent(event.getNewValue());
        });

        col.setCellFactory((TableColumn<Predecessor, Integer> roomPropertyBooleanTableColumn) -> {
            return new ComboBoxTableCellPredecessor(new PredecessorConverter(), dialogParent);
        });
        return col;
    }

    private TableColumn createColumnGap() {
        TableColumn col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Gap"));
        col.setCellValueFactory(new PropertyValueFactory<>("gap"));
        col.setCellFactory((Object col1) -> new IntegerEditingCellPredecessor(Tags.GAP, 1));
        return col;
    }

    private TableColumn createColumnConstraint() {
        TableColumn col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Constraint"));
        col.setCellValueFactory(new PropertyValueFactory<>("constraint"));
        col.setCellFactory((Object col1) -> new StringCellPredecessor(Tags.CONSTRAINT, 1));
        return col;
    }

    private void parcours(Task start, Task end, List<Task> critical) {
        Task temp = null;
        if (end != null && end.getPredecessor() != null) {
            for (Predecessor link : end.getPredecessor()) {
                for (Task t6 : DAO.getInstance().getCurrentProject().getTasks()) {
                    if (t6.getId().equals(link.getIdTaskParent())) {
                        temp = t6;
                    }
                }
                parcours(start, temp, critical);
            }
            if (!critical.contains(end)) {
                critical.add(end);
            }
        }
    }

}
