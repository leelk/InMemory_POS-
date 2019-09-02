package controller;

import db.DB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.*;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IssueHanddleController {
    public TextField txtSeach;
    public TableView<SearchTM> tblIssuedDetails;
    public ComboBox<String> cmbIssuedID;
    public TextField txtMemberName;
    public TextField txtIssuedDate;
    public TextField txtCurrentDate;
    public TextField txtFee;
    public AnchorPane Issudroot;

    ObservableList<SearchTM> list = FXCollections.observableArrayList();


    public void initialize() {

        ObservableList<SearchTM> tempSearch = tblIssuedDetails.getItems();

        System.out.println(DB.isseBookDB);

        tblIssuedDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("issuedId"));
        tblIssuedDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("memberId"));
        tblIssuedDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("memberName"));
        tblIssuedDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("issuedDate"));

        for (IssueBookTM issueBook : DB.isseBookDB) {

            String memberName = null;
            for (MemberTM member : DB.membersDB) {
                if (issueBook.getMemberID().equals(member.getMemberID())) {
                    memberName = member.getMemberName();
                }

            }

            list.add(new SearchTM(
                    issueBook.getIssueID(),
                    issueBook.getMemberID(),
                    memberName,
                    issueBook.getIssueDate()
            ));
        }
        tblIssuedDetails.setItems(list);


        ObservableList items = cmbIssuedID.getItems();
        for (IssueBookTM issueBook : DB.isseBookDB) {
            items.add(issueBook.getIssueID());
        }

        cmbIssuedID.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                String selectedItem = cmbIssuedID.getSelectionModel().getSelectedItem();
                for (IssueBookTM issueBook : DB.isseBookDB) {

                    String memeberName = null;
                    for (MemberTM member : DB.membersDB) {
                        if (issueBook.getMemberID().equals(member.getMemberID())) {
                            memeberName = member.getMemberName();
                            break;
                        }
                    }

                    if (issueBook.getIssueID().equals(selectedItem)) {
                        txtIssuedDate.setText(issueBook.getIssueDate());
                        txtMemberName.setText(memeberName);
                    }

                }
            }
        });


//        txtSeach.getSelectedText().

//        txtSeach.textProperty().addListener((observable, oldValue, newValue) -> {
//            ObservableList tempIssuedDB = FXCollections.observableArrayList(DB.SearchTM);
//            for (SearchTM  x: tempIssuedDB) {
//
//            }
//
//        });
    }


    public void btnCalculateFee(ActionEvent actionEvent) {
        //set buttonDone disable false

        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yy");

        String issueDate = txtIssuedDate.getText();
        String currentDate = txtCurrentDate.getText();

        int validDate = 14;
        double fee = 0;
        try {
            Date dateBefore = myFormat.parse(issueDate);
            Date dateAfter = myFormat.parse(currentDate);
            long difference = dateAfter.getTime() - dateBefore.getTime();
            float daysBetween = (difference / (1000 * 60 * 60 * 24));
            System.out.println("Dates between" + daysBetween);

            if (daysBetween > validDate) {
                int extentndceDays = (int) daysBetween - validDate;
                fee = extentndceDays * 15;
            }
            txtFee.setText(fee + "");

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void btnDone(ActionEvent actionEvent) {
        for (IssueBookTM issueBook : DB.isseBookDB) {
            if (issueBook.getIssueID().equals(cmbIssuedID.getSelectionModel().getSelectedItem())) {
                for (AddBookTM bookDetail : issueBook.getBookDetails()) {
                    for (BookTM bookTM : DB.booksDB) {
                        if (bookDetail.getBookID().equals(bookTM.getBookID())) {
                            bookTM.setStatus(true);
                        }
                    }
                }

            }
        }
    }

    public void btnHome(MouseEvent mouseEvent) throws IOException {

        URL resource = this.getClass().getResource("/view/DashBoard.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) this.Issudroot.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void txtSearchKeyRelease(KeyEvent keyEvent) {

        list.clear();
        String name = txtSeach.getText();

        for (IssueBookTM searchItem : DB.isseBookDB) {
            for (MemberTM member : DB.membersDB) {
                if (searchItem.getMemberID().equals(member.getMemberID())) {
                    if (searchItem.getMemberID().contains(name) || searchItem.getIssueDate().contains(name) || searchItem.getIssueID().contains(name) || member.getMemberName().contains(name)) {
                        list.add(new SearchTM(searchItem.getIssueID(),
                                searchItem.getMemberID(),
                                member.getMemberName(),
                                searchItem.getIssueDate()
                        ));
                    }
                }
            }
        }
        tblIssuedDetails.setItems(list);
    }
}
