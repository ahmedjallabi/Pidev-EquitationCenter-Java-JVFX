package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class AdminDashboardController {
    @FXML
    private Label adminNameLabel;

    public void initialize() {
        // Set the admin name in the label
        GuiLoginController guilogin = new GuiLoginController();
        String name="Bienvenue "+guilogin.user.getName()+"!";
        adminNameLabel.setText(name);
    }
    public void goToLogn(MouseEvent mouseEvent) {
    }

    public void goToNavigate(ActionEvent actionEvent) {
    }

    public void goToUsers(MouseEvent mouseEvent) {
        RouterController router=new RouterController();
        router.navigate("/fxml/HorseCrud.fxml");
    }

    public void goToActivities(MouseEvent mouseEvent) {
        RouterController router=new RouterController();
        router.navigate("/fxml/ActivitiesCRUD.fxml");
    }

    public void goToCommands(MouseEvent mouseEvent) {
    }

    public void goToReclamations(MouseEvent mouseEvent) {
    }

    public void goToEvent(MouseEvent mouseEvent) {
    }

    public void goToLivraisons(MouseEvent mouseEvent) {
    }

    public void gotoHorse(MouseEvent mouseEvent) {
        RouterController r=new RouterController();
        r.navigate("../fxml/HorseCrud.fxml");

    }
}
