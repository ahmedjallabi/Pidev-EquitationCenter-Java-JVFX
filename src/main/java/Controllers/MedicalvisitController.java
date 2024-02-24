package Controllers;

import Entities.MedicalVisit;
import Service.MedicalVisitService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MedicalvisitController implements Initializable {

    @FXML
    private TableView<MedicalVisit> tableView;

    @FXML
    private TableColumn<MedicalVisit, Integer> id;

    @FXML
    private TableColumn<MedicalVisit, Integer> idHorseColumn;

    @FXML
    private TableColumn<MedicalVisit, Integer> idVetColumn;

    @FXML
    private TableColumn<MedicalVisit, String> descriptionColumn;

    @FXML
    private TableColumn<MedicalVisit, Date> visitDateColumn;

    @FXML
    private TextField search_tv;

    private MedicalVisitService medicalVisitService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        medicalVisitService = new MedicalVisitService();
        initializeColumns();
        loadMedicalVisits();
    }

    private void initializeColumns() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        idHorseColumn.setCellValueFactory(new PropertyValueFactory<>("idHorse"));
        idVetColumn.setCellValueFactory(new PropertyValueFactory<>("idVet"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        visitDateColumn.setCellValueFactory(new PropertyValueFactory<>("visitDate"));
    }

    private void loadMedicalVisits() {
        try {
            List<MedicalVisit> medicalVisits = medicalVisitService.ReadAll();
            tableView.getItems().addAll(medicalVisits);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception properly in your application
        }
    }
    @FXML
    private void gotoAjouter(Event event)
    {
        RouterController.navigate("/fxml/AddMedicalVisit.fxml");
    }
    // You can add more methods for handling user interactions or additional functionalities
}
