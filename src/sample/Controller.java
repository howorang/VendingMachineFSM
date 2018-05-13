package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class Controller implements VendingStateMachine.OnDialogCallback {

    @FXML
    private Button oneButton;

    @FXML
    private Button twoButton;

    @FXML
    private Button fiveButton;
    @FXML
    private Button teaButton;
    @FXML
    private Button cofeeButton;

    @FXML
    private TextArea history;

    @FXML
    private TextField coinText;
    private VendingStateMachine stateMachine;

    @FXML
    public void initialize() {
        stateMachine = new VendingStateMachine(this);
        oneButton.setOnAction((event) -> changeState(INPUT.ONE));
        twoButton.setOnAction(event -> changeState(INPUT.TWO));
        fiveButton.setOnAction(event -> changeState(INPUT.FIVE));
        teaButton.setOnAction(event -> changeState(INPUT.TEA));
        cofeeButton.setOnAction(event -> changeState(INPUT.COFFE));
    }

    @Override
    public void accept(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void changeState(INPUT input) {
        stateMachine.changeState(input);
        history.setText(stateMachine.getHistory());
        coinText.setText(stateMachine.getCurrentCoins());
    }
}
