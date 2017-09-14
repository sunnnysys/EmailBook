package sfj_emailbook;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;

public class ViewController implements Initializable {

//<editor-fold defaultstate="collapsed" desc="@FXML-s">
    @FXML
    private Label label;
    @FXML
    TableView table;
    @FXML
    TextField inputFirstname;
    @FXML
    TextField inputLastname;
    @FXML
    TextField inputEmail;
    @FXML
    TextField inputD2;
    @FXML
    Button addNewContactButton;
    @FXML
    StackPane menuPane;
    @FXML
    Pane contactPane;
    @FXML
    Pane exportPane;
    @FXML
    TextField inputExport;
    @FXML
    Button exportButton;
    @FXML
    AnchorPane anchor;
    @FXML
    SplitPane mainSplit;
//</editor-fold>

    private final String MENU_CONTACTS = "Contacts";
    private final String MENU_LIST = "List";
    private final String MENU_EXPORT = "Export";
    private final String MENU_EXIT = "Exit";

    private final ObservableList<Person> data = FXCollections.observableArrayList();

    DBase dBase = new DBase();

    @FXML
    private void addContact(ActionEvent event) {
        String email = inputEmail.getText();
        /*
        if (email.length() > 3 && email.contains("@") && email.contains(".")) {
            Person newPerson = new Person(inputFirstname.getText(), inputLastname.getText(), email, 0);
            data.add(newPerson);
            dBase.addContact(newPerson);
            inputFirstname.clear();
            inputLastname.clear();
            inputEmail.clear();
        } else {
            alert("wrong email address!");
        }*/
        Person newPerson = new Person(inputFirstname.getText(), inputLastname.getText(), email, "1", Integer.parseInt(inputD2.getText()));
        //System.out.println(inputFirstname.getText() + " - " + inputLastname.getText() + " - " + email + " - " + "1" + " - " + Integer.parseInt(inputD2.getText()));
        data.add(newPerson);
        dBase.addContact(newPerson);
        inputFirstname.clear();
        inputLastname.clear();
        inputEmail.clear();
        inputD2.clear();
        

    }

    @FXML
    private void generatePdf(ActionEvent event) {
        PdfGeneration pdfG = new PdfGeneration();
        Date dateNow = new Date();
        SimpleDateFormat formatedDate = new SimpleDateFormat("yyyy.MM.dd'_'k.mm.ss");
        String documentName;
        if (inputExport.getText().isEmpty()) {
            documentName = "emailBookExport_" + formatedDate.format(dateNow);
        } else {
            documentName = inputExport.getText() + "_" + formatedDate.format(dateNow);
        }
        pdfG.pdfGeneration(documentName, data);
        inputExport.setText("");

        try {
            System.out.println(documentName);
            Desktop.getDesktop().open(new File(documentName + ".pdf"));

        } catch (Exception ex) {

        }
    }

    private void setTableData() {

        TableColumn firstNameColumn = new TableColumn("First name");
        firstNameColumn.setMinWidth(100);
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));

        firstNameColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setFirstName(t.getNewValue());
                dBase.updateContact(actualPerson);
            }
        }
        );

        TableColumn lastNameColumn = new TableColumn("Last name");
        lastNameColumn.setMinWidth(100);
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));

        lastNameColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setLasName(t.getNewValue());
                dBase.updateContact(actualPerson);
            }
        }
        );

        TableColumn emailColumn = new TableColumn("Email address");
        emailColumn.setMinWidth(150);
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));

        emailColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setEmail(t.getNewValue());
                dBase.updateContact(actualPerson);
            }
        }
        );

        TableColumn balanceColumn = new TableColumn("Balance");
        balanceColumn.setMinWidth(100);
        balanceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        balanceColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("deposit"));

        balanceColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setDeposit(t.getNewValue());
                dBase.updateContact(actualPerson);
            }
        }
        );

        TableColumn d2Column = new TableColumn("D2");
        d2Column.setMinWidth(100);
        d2Column.setCellFactory(TextFieldTableCell.<Person, Integer>forTableColumn(new IntegerStringConverter()));
        d2Column.setCellValueFactory(new PropertyValueFactory<Person, Integer>("d2"));

        d2Column.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, Integer> t) {
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                //actualPerson.setD2(t.getNewValue().intValue());
                actualPerson.setD2(t.getNewValue().intValue());
                System.out.println(actualPerson.getD2()*2);
                dBase.updateContact(actualPerson);
            }
        }
        );

        table.getColumns().addAll(firstNameColumn, lastNameColumn, emailColumn, balanceColumn, d2Column);
        data.addAll(dBase.getAllContacts());
        table.setItems(data);
    }

    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menu");
        TreeView<String> treeView1 = new TreeView(treeItemRoot1);
        treeView1.setShowRoot(false);

        TreeItem<String> treeItemA = new TreeItem<>(MENU_CONTACTS);
        TreeItem<String> treeItemB = new TreeItem<>(MENU_EXIT);
        treeItemRoot1.getChildren().addAll(treeItemA, treeItemB);
        treeItemA.setExpanded(true);

        Image contactsImage = new Image("contacts.png");
        Node listNode = new ImageView(contactsImage);
        Image exportImage = new Image("export.png");
        Node exportNode = new ImageView(exportImage);

        TreeItem<String> treeItemA1 = new TreeItem<>(MENU_LIST, listNode);
        TreeItem<String> treeItemA2 = new TreeItem<>(MENU_EXPORT, exportNode);

        treeItemA.getChildren().addAll(treeItemA1, treeItemA2);

        menuPane.getChildren().add(treeView1);

        treeView1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();
                System.out.println(selectedMenu);

                if (null != selectedMenu) {
                    switch (selectedMenu) {
                        case MENU_CONTACTS:
                            //selectedItem.setExpanded(true);
                            exportPane.visibleProperty().set(false);
                            contactPane.visibleProperty().set(true);
                            break;
                        case MENU_LIST:
                            exportPane.visibleProperty().set(false);
                            contactPane.visibleProperty().set(true);
                            break;
                        case MENU_EXPORT:
                            exportPane.visibleProperty().set(true);
                            contactPane.visibleProperty().set(false);
                            break;
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                    }
                }

            }

        });

    }

    private void alert(String text) {
        mainSplit.setDisable(true);
        mainSplit.setOpacity(0.4);
        Label label = new Label(text);
        Button alertButton = new Button("OK");
        VBox vbox = new VBox(label, alertButton);
        vbox.setAlignment(Pos.CENTER);
        anchor.getChildren().add(vbox);
        vbox.setLayoutX(250);
        vbox.setLayoutY(300);
        /*anchor.setTopAnchor(vbox, 300.00);
        anchor.setLeftAnchor(vbox, 250.00);*/

        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainSplit.setDisable(false);
                mainSplit.setOpacity(1);
                vbox.setVisible(false);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableData();
        setMenuData();

    }

}
