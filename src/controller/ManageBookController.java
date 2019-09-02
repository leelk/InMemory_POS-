package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.BookTM;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class ManageBookController {


    public AnchorPane bookRoot;
    public JFXButton btnNewBookID;
    public JFXTextField txtBookID;
    public JFXTextField txtTitle;
    public JFXTextField txtAuthor;
    public TableView<BookTM> tblBook;
    public JFXButton btnAddID;
    public JFXButton btnDeleteID;

    public void initialize() {

        txtBookID.setDisable(true);
        txtTitle.setDisable(true);
        txtAuthor.setDisable(true);
        btnDeleteID.setDisable(true);
        btnAddID.setDisable(true);

        tblBook.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("bookID"));
        tblBook.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
        tblBook.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("author"));
        tblBook.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("status"));

        ObservableList<BookTM> books = FXCollections.observableList(DB.booksDB);
        tblBook.setItems(books);


        tblBook.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BookTM>() {
            @Override
            public void changed(ObservableValue<? extends BookTM> observable, BookTM oldValue, BookTM newValue) {

                BookTM selectedItem = tblBook.getSelectionModel().getSelectedItem();

                if (selectedItem == null) {
                    btnAddID.setText("Add");
                    btnDeleteID.setDisable(true);
                    return;
                }
                txtAuthor.setDisable(false);
                txtTitle.setDisable(false);
                txtBookID.setDisable(false);
                btnAddID.setText("Update");
                btnAddID.setDisable(false);
                btnDeleteID.setDisable(false);
                txtTitle.setText(selectedItem.getTitle());
                txtAuthor.setText(selectedItem.getAuthor());
                txtBookID.setText(selectedItem.getBookID());
            }
        });
    }


    public void btnNewBook(ActionEvent actionEvent) {
        tblBook.getSelectionModel().clearSelection();
        btnDeleteID.setDisable(true);
        txtTitle.setDisable(false);
        txtAuthor.setDisable(false);
        btnAddID.setDisable(false);
        txtTitle.clear();
        txtAuthor.clear();
        txtTitle.requestFocus();

        int maxid = 0;

        for (BookTM book : DB.booksDB) {
            int id = Integer.parseInt(book.getBookID().replace("B", ""));
            if (id > maxid) {
                maxid = id;
            }
        }
        maxid += 1;
        String nextID = "";

        if (maxid < 10) {
            nextID = "B00" + maxid;
        } else if (maxid < 100) {
            nextID = "B0" + maxid;
        } else {
            nextID = "B" + maxid;
        }
        txtBookID.setText(nextID);
    }

    public void btnAdd(ActionEvent actionEvent) {
        if (txtTitle.getText().equalsIgnoreCase("") || txtAuthor.getText().equalsIgnoreCase("")) {
            new Alert(Alert.AlertType.ERROR, "Book Title or Autor Name can not be empty ! ", ButtonType.OK).show();
        } else {
            if (btnAddID.getText().equalsIgnoreCase("Add")) {
                ObservableList<BookTM> book = tblBook.getItems();
                book.add(new BookTM(txtBookID.getText(), txtTitle.getText(), txtAuthor.getText(), true));
                btnNewBook(actionEvent);
            } else {
                BookTM selectedItem = tblBook.getSelectionModel().getSelectedItem();
                selectedItem.setTitle(txtTitle.getText());
                selectedItem.setAuthor(txtAuthor.getText());
                tblBook.refresh();
            }
        }
    }

    public void btnDelete(ActionEvent actionEvent) {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this customer?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            BookTM selectedItem = tblBook.getSelectionModel().getSelectedItem();
            tblBook.getItems().remove(selectedItem);
        }
    }

    public void btnHome(MouseEvent mouseEvent) throws IOException {

        URL resource = this.getClass().getResource("/view/DashBoard.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) this.bookRoot.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
