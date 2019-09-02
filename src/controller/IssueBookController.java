package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.AddBookTM;
import util.BookTM;
import util.IssueBookTM;
import util.MemberTM;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class IssueBookController {
    public AnchorPane IssueRoot;
    public ComboBox cmbMemberID;
    public ComboBox cmbBookID;
    public Label lblIssueID;
    public JFXTextField txtMemberName;
    public JFXTextField txtBookTitle;
    public JFXTextField txtAuthor;
    public TableView<AddBookTM> tbl;

    public JFXTextField txtNumberOfBook;
    public Label lblIsseID;
    public Label lblDate;
    public Button IssueID;
    public Button btnAddID;
    public TableColumn tblIssue;
    public JFXButton CreateNewwIssueBookID;


    //
    // ObservableList<AddBookTM> addIsse = FXCollections.observableArrayList();
    int count = 0;

    public void initialize() {


        tbl.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("bookID"));
        tbl.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
        tbl.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("author"));
        tbl.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("btnDelete"));


        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yy");
        Date date = new Date();
        lblDate.setText(sf.format(date));

        txtAuthor.setDisable(true);
        txtBookTitle.setDisable(true);
        txtMemberName.setDisable(true);
        txtNumberOfBook.setDisable(true);
        cmbBookID.setDisable(true);
        cmbMemberID.setDisable(true);
        IssueID.setDisable(true);
        btnAddID.setDisable(true);

//load all members

        ObservableList<String> ids = cmbMemberID.getItems();
        for (MemberTM member : DB.membersDB) {
            ids.add(member.getMemberID());
        }
//load all books


        ObservableList<String> bookIDs = cmbBookID.getItems();
        for (BookTM book : DB.booksDB) {
            if (book.isStatus()) {
                bookIDs.add(book.getBookID());
            }
        }


        cmbBookID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String selectedItem = (String) cmbBookID.getSelectionModel().getSelectedItem();

                if (selectedItem == null) {
                    return;
                }

                for (BookTM book : DB.booksDB) {

                    if (selectedItem.equals(book.getBookID())) {
                        txtBookTitle.setText(book.getTitle());
                        txtAuthor.setText(book.getAuthor());
                    }


                }
            }
        });


        cmbMemberID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String selectedItem = (String) cmbMemberID.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    return;
                }
                for (MemberTM member : DB.membersDB) {
                    if (selectedItem.equals(member.getMemberID())) {
                        txtMemberName.setText(member.getMemberName());
                    }
                }

            }
        });

        generateIssueId();


    }

    public void btnHome(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/DashBoard.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) this.IssueRoot.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void cmbMemberID(ActionEvent actionEvent) {
    }

    public void cmbBookID(ActionEvent actionEvent) {
    }


    public void btnAdd(ActionEvent actionEvent) {

        ObservableList<AddBookTM> items = tbl.getItems();
        String selectedMember = (String) cmbMemberID.getSelectionModel().getSelectedItem();

        if (selectedMember != "null") {

            String seleItem = (String) cmbBookID.getSelectionModel().getSelectedItem();

            boolean isExsits = false;
            for (AddBookTM item : tbl.getItems()) {
                if (item.getBookID().equals(seleItem)) {
                    new Alert(Alert.AlertType.ERROR, "Can not add same Book!", ButtonType.OK).show();

                    isExsits = true;
                }

            }

            if (!isExsits) {
                JFXButton button = new JFXButton("Delete");
                AddBookTM addBookTM = new AddBookTM((String) cmbBookID.getSelectionModel().getSelectedItem(), txtBookTitle.getText(), txtAuthor.getText(), button);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        count--;
                        tbl.getItems().remove(addBookTM);
                        tbl.refresh();
                    }
                });


                count++;
                items.add(addBookTM);
                tbl.refresh();
                cmbBookID.getSelectionModel().clearSelection();
                txtBookTitle.clear();
                txtAuthor.clear();
//                count set
                txtNumberOfBook.setText(Integer.toString(count));
                IssueID.setDisable(false);

            }

        }


    }

    public void btnIssueBookComplete(ActionEvent actionEvent) {

        int ordersCount = 0;
        ArrayList<AddBookTM> details = new ArrayList<>();
//----------------------------------------------------------------------------------------------------

        for (AddBookTM item : tbl.getItems()) {
            details.add(new AddBookTM(
                    item.getBookID(),
                    item.getTitle(),
                    item.getAuthor()
            ));

            for (BookTM book : DB.booksDB) {
                if (item.getBookID().equals(book.getBookID())) {
                    book.setStatus(false);
                }
            }
        }

        IssueBookTM issueBookTM = new IssueBookTM(
                lblIsseID.getText(),
                (String) cmbMemberID.getSelectionModel().getSelectedItem(),
                lblDate.getText(),
                details
        );
        DB.isseBookDB.add(issueBookTM);

        tbl.getItems().clear();
        txtNumberOfBook.clear();
        cmbMemberID.getSelectionModel().clearSelection();
        txtMemberName.clear();

        ObservableList<String> items = cmbBookID.getItems();
        items.clear();

        for (BookTM book : DB.booksDB) {
            if (book.isStatus()) {
                items.add(book.getBookID());
            }
        }
        generateIssueId();
    }


    public void btnCreateNewIssueBook(ActionEvent actionEvent) {

        txtAuthor.setDisable(false);
        txtBookTitle.setDisable(false);
        txtMemberName.setDisable(false);
        txtNumberOfBook.setDisable(false);
        cmbBookID.setDisable(false);
        cmbMemberID.setDisable(false);
        IssueID.setDisable(false);
        btnAddID.setDisable(false);

        generateIssueId();

    }

    public void generateIssueId() {
        int maxID = 0;

        for (IssueBookTM issueBook : DB.isseBookDB) {
            int current = Integer.parseInt(issueBook.getIssueID().replace("IS", ""));
            if (current > maxID) {
                maxID = current;
            }
        }
        String nextNewID = "";
        maxID = maxID + 1;

        if (maxID < 10) {
            lblIsseID.setText("IS00" + maxID);
        } else if (maxID < 100) {
            lblIsseID.setText("IS0" + maxID);
        } else {
            lblIsseID.setText("IS" + maxID);
        }
    }
}
