package Controllers;

import Entities.Horse;
import Entities.MedicalVisit;
import Service.HorseService;
import Service.MedicalVisitService;
import Service.VetService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AddMedicalVisitController {

    @FXML
    private ComboBox<Horse> horseComboBox;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private DatePicker visitDatePicker;

    private MedicalVisitService medicalVisitService=new MedicalVisitService();

    @FXML
    private void initialize() {
        // Populate the ComboBox with horses from the database
        populateHorseComboBox();

        // Set the cell factory to display horse names in the ComboBox
        horseComboBox.setCellFactory(new Callback<ListView<Horse>, ListCell<Horse>>() {
            @Override
            public ListCell<Horse> call(ListView<Horse> param) {
                return new ListCell<Horse>() {
                    @Override
                    protected void updateItem(Horse item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        });
    }

    // Method to populate the ComboBox with horses
    private void populateHorseComboBox() {
        HorseService horseService = new HorseService();
        try {
            List<Horse> horses = horseService.ReadAll();
            horseComboBox.getItems().addAll(horses);
        } catch (SQLException e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }
    }

    public void returnTo(Event actionEvent)
    {RouterController.navigate("/fxml/MedicalvisitCrud.fxml");
    }
    @FXML
    void addVisit(Event actionEvent) {
        int horseId = horseComboBox.getValue().getId();
        String description = descriptionTextField.getText();
        Date visitDate = java.sql.Date.valueOf(visitDatePicker.getValue());

        if (horseId == 0 || description.isEmpty() || visitDate == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("alert");
            alert.setHeaderText(null);
            alert.setContentText("stp remplir les informations");
            alert.showAndWait();
            return;
        }

        try {
            // Assuming you have methods to get the horse ID and vet ID
            int vetId = getLoggedInVetId();

            MedicalVisit medicalVisit = new MedicalVisit(horseId, vetId, description, visitDate);
            medicalVisitService.add(medicalVisit);

            // Show a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Medical visit added successfully");
            alert.showAndWait();

            returnTo(actionEvent);
        } catch (SQLException e) {
            e.printStackTrace();
            // Show an error message if adding the medical visit fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("erreur");
            alert.setHeaderText(null);
            alert.setContentText("ajout échoué");
            alert.showAndWait();
        }
    }

    private int getLoggedInVetId() {
        return VetService.user.getId();
    }

    // Other controller methods...
}
