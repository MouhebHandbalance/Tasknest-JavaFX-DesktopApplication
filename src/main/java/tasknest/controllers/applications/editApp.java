package tasknest.controllers.applications;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tasknest.models.Application;
import tasknest.models.users;
import tasknest.services.ApplicationService;
import tasknest.services.UserService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class editApp {
    @FXML
    private TextField firstNameLabel;

    @FXML
    private TextField lastNameLabel;

    @FXML
    private TextField emailLabel;

    @FXML
    private TextField phoneNumberLabel;

    @FXML
    private TextField cvField;

    private ApplicationService applicationService ;
    private UserService userService;
   private int user_Id=35;
    private int offer_Id;
    Application Appl;
    public void setAppService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    public void setEditedAPP(Application App) {
        userService = new UserService();
        this.Appl = App;
        if (Appl != null) {
            System.out.println("userAPPqa= " + Appl);
            System.out.println("user ya hajjjj1= " + userService.getUserById(Appl.getUser_id()));

            System.out.println("user ya hajjjj2= " + userService.getUserById(Appl.getUser_id()));
            //initialize();
            System.out.println("user ya hajjjj3= " + userService.getUserById(Appl.getUser_id()));
            users user = userService.getUserById(Appl.getUser_id());
            if (user != null) {
                firstNameLabel.setText(user.getFname());
                lastNameLabel.setText(user.getLname());
                emailLabel.setText(user.getEmail());
                phoneNumberLabel.setText(String.valueOf(user.getPhonenumber()));
            }
        } else {
            System.out.println("Appl object is null!");
        }

    }


    public void initialize() {

       // System.out.println("user ya hajjjj= "+ userService.getUserById(Appl.getUser_id()));
        // Fetch user information and populate labels

    /*    users user = userService.getUserById(A);
        if (user != null) {
            firstNameLabel.setText(user.getFname());
            lastNameLabel.setText(user.getLname());
            emailLabel.setText(user.getEmail());
            phoneNumberLabel.setText(String.valueOf(user.getPhonenumber()));
        }*/

    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    private void uploadCV(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();


        fileChooser.setTitle("Choose PDF File");

        File selectedFile = fileChooser.showOpenDialog(null);


        if (selectedFile != null) {

            String fileName = selectedFile.getName();


            if (fileName.toLowerCase().endsWith(".pdf")) {

                String filePath = selectedFile.getAbsolutePath();

                cvField.setText(filePath);
            } else {

                showAlert(Alert.AlertType.ERROR, "Error", "Invalid File Type", "Please select a PDF file.");
            }
        }
    }



    @FXML
    private void editapp() {

        String cvContent = cvField.getText();
        String cvImagePath = cvField.getText();



        Application application = new Application(Appl.getOffers_id(), Appl.getUser_id(), cvImagePath);


        applicationService.modifier(application);

        showAlert(Alert.AlertType.INFORMATION, "Success", "Application Updated", "Your application has been updated successfully.");

        // Clear input fields
        cvField.clear();
    }

    @FXML
    private void navigateAlloffers(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/offer/DisplayAllOffers.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root);


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   /* @FXML
    private void navigateMyapplications(MouseEvent event) {
        try {
            // Load the FXML file of the DisplayAllOffers view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Applications/seeMyApps.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene on the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/





    public void navigateUserApps(MouseEvent mouseEvent){



        navigateUserAppss(mouseEvent,user_Id,offer_Id )  ;

    }

    public void navigateUserAppss(MouseEvent mouseEvent,int user_Id,int offer_Id ) {
        try {
            System.out.println("user_Idaz= "+user_Id);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Applications/seeMyApps.fxml"));
            Parent root = loader.load();
            System.out.println("user_Idaz= "+user_Id);

            Scene scene = new Scene(root);
            seeMyApps seeMyAppsController = loader.getController();
            seeMyAppsController.setUserApps(user_Id,offer_Id);

            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();


            stage.setScene(scene);


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
