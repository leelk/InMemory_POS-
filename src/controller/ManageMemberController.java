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
import util.MemberTM;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class ManageMemberController {
    public AnchorPane memberRoot;
    public TableView<MemberTM> tblMember;
    public JFXTextField txtMemeberID;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtPhoeNumber;
    public JFXButton btnAddID;
    public JFXButton btnDeleteID;

    public void initialize() {

        txtAddress.setDisable(true);
        txtMemeberID.setDisable(true);
        txtName.setDisable(true);
        txtPhoeNumber.setDisable(true);
        btnAddID.setDisable(true);
        btnDeleteID.setDisable(true);

        tblMember.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("memberID"));
        tblMember.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("memberName"));
        tblMember.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("memberAddress"));
        tblMember.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("memberPhoneNumber"));

        ObservableList<MemberTM> members = FXCollections.observableList(DB.membersDB);
        tblMember.setItems(members);


        tblMember.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MemberTM>() {
            @Override
            public void changed(ObservableValue<? extends MemberTM> observable, MemberTM oldValue, MemberTM newValue) {

                MemberTM selectedItem = tblMember.getSelectionModel().getSelectedItem();


                txtAddress.setDisable(false);
                txtName.setDisable(false);
                txtPhoeNumber.setDisable(false);

                if (selectedItem == null) {
                    btnAddID.setText("Save");
                    btnDeleteID.setDisable(true);
                    return;
                }
                btnAddID.setText("Update");
                btnAddID.setDisable(false);
                btnDeleteID.setDisable(false);
                txtName.setText(selectedItem.getMemberName());
                txtAddress.setText(selectedItem.getMemberAddress());
                txtMemeberID.setText(selectedItem.getMemberID());
                txtPhoeNumber.setText(selectedItem.getMemberPhoneNumber());
            }
        });
    }

    public void btnHome(MouseEvent mouseEvent) throws IOException {

        URL resource = this.getClass().getResource("/view/DashBoard.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) this.memberRoot.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void btnNewMember(ActionEvent actionEvent) {

        tblMember.getSelectionModel().clearSelection();
        btnDeleteID.setDisable(true);
        txtAddress.setDisable(false);
        txtMemeberID.setDisable(false);
        txtName.setDisable(false);
        txtPhoeNumber.setDisable(false);
        btnAddID.setDisable(false);
        txtAddress.clear();
        txtName.clear();
        txtPhoeNumber.clear();
        txtName.requestFocus();

        int maxid = 0;

        for (MemberTM member : DB.membersDB) {
            int id = Integer.parseInt(member.getMemberID().replace("M", ""));
            if (id > maxid) {
                maxid = id;
            }
        }

        maxid += 1;
        String nextID = "";

        if (maxid < 10) {
            nextID = "M00" + maxid;
        } else if (maxid < 100) {
            nextID = "M0" + maxid;
        } else {
            nextID = "M" + maxid;
        }
        txtMemeberID.setText(nextID);
    }

    public void btnAdd(ActionEvent actionEvent) {

        if (txtName.getText().equalsIgnoreCase("") || txtAddress.getText().equalsIgnoreCase("")) {
            new Alert(Alert.AlertType.ERROR, "Name or Address can not be empty ! ", ButtonType.OK).show();
        } else {
            String memberid = txtMemeberID.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String phone = txtPhoeNumber.getText();

            if (name.matches("[A-Z a-z]{3,}")) {
                if (address.matches("\\d{3}[A-Za-z][,]\\s[A-Za-z]{3,5}[,]\\s[A-Za-z]{3,10}")) {
                    if (phone.matches("\\d{10}")) {
                        if (btnAddID.getText().equalsIgnoreCase("add")) {

                            ObservableList<MemberTM> customer = tblMember.getItems();
                            customer.add(new MemberTM(txtMemeberID.getText(), txtName.getText(), txtAddress.getText(), txtPhoeNumber.getText()));
                            btnNewMember(actionEvent);
                        } else {
                            MemberTM selectedItem = tblMember.getSelectionModel().getSelectedItem();
                            selectedItem.setMemberAddress(txtAddress.getText());
                            selectedItem.setMemberName(txtName.getText());
                            selectedItem.setMemberPhoneNumber(txtPhoeNumber.getText());
                            tblMember.refresh();
                        }
                    } else {
                        System.out.println("Phone Number is not in correct format");
                        txtPhoeNumber.requestFocus();
                    }
                } else {
                    System.out.println("Address is not in correct Format");
                    txtAddress.requestFocus();
                }
            } else {
                System.out.println("Name is not in correct format.");
                txtName.requestFocus();
            }
        }
    }

    public void btnDelete(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this customer?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            MemberTM selectedItem = tblMember.getSelectionModel().getSelectedItem();
            tblMember.getItems().remove(selectedItem);
        }

    }
}
