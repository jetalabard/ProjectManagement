/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view.dialog;

import projectmanagement.ihm.view.cell.StringCellRessource;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import projectmanagement.application.business.Equipment;
import projectmanagement.application.business.Human;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.application.model.RessourceConverter;
import projectmanagement.application.model.RessourcesTable;
import projectmanagement.ihm.controller.RowControllerTableRessource;
import projectmanagement.ihm.controller.Tags;
import projectmanagement.ihm.view.cell.IntegerEditingCellRessource;

/**
 *
 * @author Jérémy
 */
public class MyTableViewRessource extends TableView<RessourcesTable> {

    private Task task =null;
    
    private RessourcesTable copyRessource;
    private final DialogUpdateTask dialogParent;
    
    public MyTableViewRessource(Task task, DialogUpdateTask dialogParent) {
        this.task = task;
        this.dialogParent = dialogParent;
        createTableView();
    }

    private void createTableView() {
        
        setItems(FXCollections.observableArrayList( this.dialogParent.getListeRessource()));
        setEditable(true);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn col = createColumnType();
        TableColumn col2 = createColumnName();
        TableColumn col3 = createColumnFirstName();
        TableColumn col4 = createColumnCost();
        TableColumn col5 = createColumnReference();
        TableColumn col6 = createColumnRole();
        setPlaceholder(new Text(dialogParent.getManagerLang().getLocalizedTexte("NoContentClickToAdd")));

        getColumns().addAll(col, col2, col3, col4, col5,col6);

        setContextMenu(new ContextMenu(createContextMenuAddHuman(),
                createContextMenuAddEquipement(),createContextMenuDelete(),
                createContextMenuCopy()));
        setRowFactory(new RowControllerTableRessource(this,dialogParent));
        getSelectionModel().selectFirst();
            
    }
   
    private MenuItem createContextMenuDelete() {
        MenuItem mnuDel = new MenuItem(dialogParent.getManagerLang().getLocalizedTexte("Delete"));
        mnuDel.setOnAction((ActionEvent t) -> {
            if (!getItems().isEmpty()) {
                RessourcesTable item = getSelectionModel().getSelectedItem();
                if (item != null) {
                    if (item.getId() != null) {
                        //en base sinon si == null pas encore eu le insert
                        DAO.getInstance().deleteRessource(item.getId());
                    }
                    dialogParent.getListeRessource().remove(item);
                    getItems().remove(item);  
                }
            }
        });
        return mnuDel;
    }

    private MenuItem createContextMenuAddHuman()
    {
        MenuItem mnuAdd = new MenuItem(dialogParent.getManagerLang().getLocalizedTexte("AddHuman"));
        mnuAdd.setOnAction((ActionEvent t) -> {
            Human human = new Human(0.0f, ManagerLanguage.getInstance().getLocalizedTexte("NameRessource"),
                    dialogParent.getManagerLang().getLocalizedTexte("FirstNameRessource") ,
                    dialogParent.getManagerLang().getLocalizedTexte("RoleRessource"), task.getId());
            RessourcesTable ressource = new RessourcesTable(human);
            dialogParent.getListeRessource().add(ressource);
            getItems().add(ressource);
            if (getItems().size() == 1) {
                getSelectionModel().selectFirst();
            }
        });
        return mnuAdd;
    }
    private MenuItem createContextMenuAddEquipement()
    {
        MenuItem mnuAdd = new MenuItem(dialogParent.getManagerLang().getLocalizedTexte("AddEquipment"));
        mnuAdd.setOnAction((ActionEvent t) -> {
            Equipment equip = new Equipment(0.0f, dialogParent.getManagerLang().getLocalizedTexte("RefRessource"),
                    dialogParent.getManagerLang().getLocalizedTexte("NameRessource"), task.getId());
            RessourcesTable ressource = new RessourcesTable(equip);
            dialogParent.getListeRessource().add(ressource);
            getItems().add(ressource);
            if (getItems().size() == 1) {
                getSelectionModel().selectFirst();
            }
        });
        return mnuAdd;
    }
    
    private MenuItem createContextMenuCopy() {
        
        MenuItem mnuAdd = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("CopyRessource"));
        mnuAdd.setOnAction((ActionEvent t) -> {
            if (!getItems().isEmpty()) {
                RessourcesTable item = getItems().get(getSelectionModel().getSelectedIndex());
                if (item != null) {
                    copyRessource = new RessourcesTable(item.getReference(),item.getName(),item.getFirstname(),item.getRole(),item.getId(),item.getType(),item.getIdTask(),item.getCost());
                    if (getContextMenu().getItems().size() == 4) {
                        getContextMenu().getItems().add(createContextMenuPaste());
                    }
                }
            }
        });
        return mnuAdd;
    }

    public MenuItem createContextMenuPaste() {
        MenuItem mnuAdd = new MenuItem(dialogParent.getManagerLang().getLocalizedTexte("PasteRessource"));
        mnuAdd.setOnAction((ActionEvent t) -> {
            getItems().add(copyRessource);
            dialogParent.getListeRessource().add(copyRessource);
            getContextMenu().getItems().remove(getContextMenu().getItems().size() - 1);
            copyRessource = null;
        });
        return mnuAdd;
    }

    private TableColumn createColumnType() {
        TableColumn col = new TableColumn(dialogParent.getManagerLang().getLocalizedTexte("Type"));
         col.setCellValueFactory(new PropertyValueFactory<>("type"));
         col.setSortable(false);
        col.setCellFactory(column -> {
            return new TableCell<RessourcesTable, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(new RessourceConverter().toString(item));
                        setStyle("-fx-background-color: #d7d7c1;");
                    }
                }
            };
        });
        col.setEditable(false);
        return col;
    }


     private TableColumn createColumnName() {
        TableColumn col = new TableColumn(dialogParent.getManagerLang().getLocalizedTexte("NameRessource"));
        col.setCellValueFactory(new PropertyValueFactory<>("name"));
        col.setSortable(false);
        col.setCellFactory((Object col1) -> new StringCellRessource(Tags.RESSOURCE_NAME));
        return col;
    }


    private TableColumn createColumnFirstName() {
        TableColumn col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("FirstameRessource"));
        col.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        col.setSortable(false);
        col.setCellFactory((Object col1) -> {
            return new StringCellRessource(Tags.RESSOURCE_FIRSTNAME);
        });

        return col;
    }

    private TableColumn createColumnCost() {
        
        TableColumn col2 = new TableColumn(dialogParent.getManagerLang().getLocalizedTexte("Cost"));
        col2.setCellValueFactory(new PropertyValueFactory<>("cost"));
        col2.setSortable(false);
        col2.setCellFactory((Object col1) -> new IntegerEditingCellRessource(Tags.COST,1));
        return col2;
    }

    private TableColumn createColumnReference() {
         TableColumn col = new TableColumn(dialogParent.getManagerLang().getLocalizedTexte("ReferenceRessource"));
         col.setCellValueFactory(new PropertyValueFactory<>("reference"));
         col.setSortable(false);
        col.setCellFactory((Object col1) -> new StringCellRessource(Tags.REFERENCE));
        return col;
    }

    private TableColumn createColumnRole() {
         TableColumn col = new TableColumn(dialogParent.getManagerLang().getLocalizedTexte("RoleRessource"));
         col.setCellValueFactory(new PropertyValueFactory<>("role"));
         col.setSortable(false);
        col.setCellFactory((Object col1) -> new StringCellRessource(Tags.ROLE));

        return col;
    }
}
