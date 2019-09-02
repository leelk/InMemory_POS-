package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class DashBoardController {
    public AnchorPane dashBoardRoot;

    public void btnManageMember(MouseEvent mouseEvent) throws IOException {
        URL resource1 = this.getClass().getResource("/view/ManageMember.fxml");
        Parent rootDash = FXMLLoader.load(resource1);
        Scene scene = new Scene(rootDash);
        Stage primaryStage = (Stage)this.dashBoardRoot.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void btnManageBook(MouseEvent mouseEvent) throws IOException {
        URL resource1 = this.getClass().getResource("/view/ManageBook.fxml");
        Parent rootDash = FXMLLoader.load(resource1);
        Scene scene = new Scene(rootDash);
        Stage primaryStage = (Stage)this.dashBoardRoot.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void btnIssueBook(MouseEvent mouseEvent) throws IOException {

        URL resource1 = this.getClass().getResource("/view/IssueBook.fxml");
        Parent rootDash = FXMLLoader.load(resource1);
        Scene scene = new Scene(rootDash);
        Stage primaryStage = (Stage)this.dashBoardRoot.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void btnIssued(MouseEvent mouseEvent) throws IOException {
        URL resource1 = this.getClass().getResource("/view/IssueHandle.fxml");
        Parent rootDash = FXMLLoader.load(resource1);
        Scene scene = new Scene(rootDash);
        Stage primaryStage = (Stage)this.dashBoardRoot.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}