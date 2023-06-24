package com.traube.stealthywhispergui;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.io.IOException;
import java.util.Objects;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StealthyWhisperController {
    @FXML AnchorPane anchorPane;

    // Main Scene
    @FXML private TextField encodedMessageTextField;
    @FXML private TextField visibleMessageTextField;
    @FXML private TextField hiddenMessageTextField;

    // Settings Scene
    @FXML private TextField cipherKeyTextField;

    // Variables for switching scenes
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML public void initialize() {
        anchorPane.setOnMouseClicked(event -> anchorPane.requestFocus());

        if (cipherKeyTextField != null) {
            cipherKeyTextField.setText(SettingsManager.getSetting("cipherKey", ""));
        }
    }

    final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
    public void encrypt(ActionEvent e) {
        String visibleMessage = visibleMessageTextField.getText();
        String hiddenMessage = hiddenMessageTextField.getText();

        encodedMessageTextField.setText("");
        resetError();

        visibleMessageTextField.pseudoClassStateChanged(errorClass, visibleMessage.isEmpty());
        hiddenMessageTextField.pseudoClassStateChanged(errorClass, hiddenMessage.isEmpty());

        if (!(visibleMessage.isEmpty() || hiddenMessage.isEmpty())) {
            encodedMessageTextField.setText(StealthyWhisperAlgorithm.insertMessage(visibleMessage, hiddenMessage));
        }
    }

    public void decrypt(ActionEvent e) {
        visibleMessageTextField.setText("");
        hiddenMessageTextField.setText("");
        resetError();

        if (encodedMessageTextField.getText().isEmpty())
            encodedMessageTextField.pseudoClassStateChanged(errorClass, true);
        else {
            encodedMessageTextField.pseudoClassStateChanged(errorClass, false);
            String encodedMessage = encodedMessageTextField.getText();
            String[] messages = StealthyWhisperAlgorithm.decodeMessage(encodedMessage);
            hiddenMessageTextField.setText(messages[0]);
            visibleMessageTextField.setText(messages[1]);
        }
    }

    public void clear(ActionEvent e) {
        encodedMessageTextField.setText("");
        visibleMessageTextField.setText("");
        hiddenMessageTextField.setText("");
        resetError();
    }

    public void resetError(){
        visibleMessageTextField.pseudoClassStateChanged(errorClass, false);
        hiddenMessageTextField.pseudoClassStateChanged(errorClass, false);
        encodedMessageTextField.pseudoClassStateChanged(errorClass, false);
    }

    public void switchToSettings(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("stealthy-whisper-settings.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        // cipherKeyTextField.setText(SettingsManager.getSetting("cipherKey", "test"));
    }

    public void switchToMain(ActionEvent event) throws IOException {
        SettingsManager.saveSetting("cipherKey", cipherKeyTextField.getText());

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("stealthy-whisper-main.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void visibleMsgCopyButton(ActionEvent e) { copyFunction(visibleMessageTextField); }

    public void hiddenMsgCopyButton(ActionEvent e) { copyFunction(hiddenMessageTextField); }

    public void encodedMsgCopyButton(ActionEvent e) { copyFunction(encodedMessageTextField); }

    void copyFunction(TextField messageTextField) {
        messageTextField.requestFocus();
        StringSelection stringSelection = new StringSelection(messageTextField.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
