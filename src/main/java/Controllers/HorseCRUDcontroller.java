package Controllers;

import Entities.Activity;
import Entities.Horse;
import Service.HorseService;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;




public class HorseCRUDcontroller {

    @FXML
    private TableView<Horse> tableView;

    @FXML
    private TextField search_tv;

    private final HorseService horseService = new HorseService();

    @FXML
    public void initialize() {
        initializeTableColumns();
        updateHorseList();
    }

    public void updateHorseList() {
        try {
            List<Horse> horses = horseService.ReadAll();
            tableView.getItems().clear();
            tableView.getItems().addAll(horses);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }
    }

    private void initializeTableColumns() {
        tableView.getColumns().clear();

        TableColumn<Horse, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Horse, String> nameColumn = new TableColumn<>("Nom");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Horse, String> datePensionColumn = new TableColumn<>("Date Pension");
        datePensionColumn.setCellValueFactory(new PropertyValueFactory<>("datePension"));

        TableColumn<Horse, String> breedColumn = new TableColumn<>("Race");
        breedColumn.setCellValueFactory(new PropertyValueFactory<>("breed"));

        TableColumn<Horse, Boolean> isAvailableColumn = new TableColumn<>("Disponibilité");
        isAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));

        // Add column for modify and delete buttons
        TableColumn<Horse, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(getButtonCellFactory());


        tableView.getColumns().addAll(idColumn, nameColumn, datePensionColumn, breedColumn, isAvailableColumn,actionColumn);
    }

    @FXML
    public void searchquery(javafx.scene.input.KeyEvent keyEvent) {
        // Implement search functionality
    }
    private Callback<TableColumn<Horse, Void>, TableCell<Horse, Void>> getButtonCellFactory() {
        return new Callback<TableColumn<Horse, Void>, TableCell<Horse, Void>>() {
            @Override
            public TableCell<Horse, Void> call(final TableColumn<Horse, Void> param) {
                final TableCell<Horse, Void> cell = new TableCell<Horse, Void>() {
                    private final Button modifyButton = new Button();
                    private final Button deleteButton = new Button();

                    {
                        Image modifyImage = new Image(getClass().getResourceAsStream("../assets/modify.png"));
                        ImageView modifyIcon = new ImageView(modifyImage);
                        modifyIcon.setFitWidth(20);
                        modifyIcon.setFitHeight(20);
                        modifyButton.setGraphic(modifyIcon);

                        // Optionally remove focus border:
                        modifyButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");
                        deleteButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");

                        Image deleteImage = new Image(getClass().getResourceAsStream("../assets/delete.png"));
                        ImageView deleteIcon = new ImageView(deleteImage);
                        deleteIcon.setFitWidth(16);
                        deleteIcon.setFitHeight(16);
                        deleteButton.setGraphic(deleteIcon);

                        // Set button actions
                        modifyButton.setOnAction((ActionEvent event) -> {
                            Horse horse = getTableView().getItems().get(getIndex());
                            // Handle modify action here
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            Horse horse = getTableView().getItems().get(getIndex());
                            // Handle delete action here
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(5);
                            buttons.getChildren().addAll(modifyButton, deleteButton); // Add buttons to HBox

                            modifyButton.setFocusTraversable(false);
                            deleteButton.setFocusTraversable(false);

                            modifyButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");
                            deleteButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");

                            // Set button icons
                            Image modifyImage = new Image(getClass().getResourceAsStream("../assets/modify.png"));
                            ImageView modifyIcon = new ImageView(modifyImage);
                            modifyIcon.setFitWidth(20);
                            modifyIcon.setFitHeight(20);
                            modifyButton.setGraphic(modifyIcon);
                            Image deleteImage = new Image(getClass().getResourceAsStream("../assets/delete.png"));
                            ImageView deleteIcon = new ImageView(deleteImage);
                            deleteIcon.setFitWidth(20);
                            deleteIcon.setFitHeight(20);
                            deleteButton.setGraphic(deleteIcon);
                            modifyButton.setOnAction((ActionEvent event) -> {
                                // Load the modifyActivity.fxml file
                                // Access the activity from the table view
                                Horse horse = getTableView().getItems().get(getIndex());
                                System.out.println("activity ID from controller:"+horse.getId());
                                // Set the ID in the controller
                                if (horse != null) {
                                    System.out.println("SETTING ID");
                                    RouterController.navigate("/fxml/ModifierHorse.fxml", horse.getId());

                                    // Close the current window if needed
                                } else {
                                    System.err.println("No activity selected.");
                                    // Handle the case where no activity is selected
                                }

                            });


                            deleteButton.setOnAction((ActionEvent event) -> {
                                Horse horse = getTableView().getItems().get(getIndex());

                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setHeaderText("Delete Activity");
                                alert.setContentText("Vous etes sur tu veux supprimer cette activité?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    try {
                                        HorseService horseService1 = new HorseService();
                                        horseService1.delete(horse);

                                        updateHorseList();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                        errorAlert.setTitle("Error");
                                        errorAlert.setHeaderText("Error Base des données");
                                        errorAlert.setContentText("Un erreur en supprimant l'activité.");
                                        errorAlert.showAndWait();
                                    }
                                }
                            });

                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        };
    }

    @FXML
    public void gotoAjouter(ActionEvent actionEvent) {
    RouterController r= new RouterController();
    r.navigate ("/fxml/AddHOrse.fxml");

    }

    public void gohome(ActionEvent actionEvent) {
        RouterController r= new RouterController();
        r.navigate ("/fxml/VetDashboard.fxml");
    }
}
