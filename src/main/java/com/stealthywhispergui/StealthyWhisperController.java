package com.stealthywhispergui;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StealthyWhisperController {
    @FXML AnchorPane anchorPane;

    @FXML private TextField encodedMessageTextField;
    @FXML private TextField visibleMessageTextField;
    @FXML private TextField hiddenMessageTextField;

    @FXML private Button settingsButton;
    @FXML private Button visibleMsgCopyButton;
    @FXML private Button hiddenMsgCopyButton;
    @FXML private Button encodedMsgCopyButton;

    // Variables for switching scenes
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML public void initialize() {
        anchorPane.setOnMouseClicked(event -> {
            anchorPane.requestFocus();
        });
    }

    final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

    public void encrypt(ActionEvent e) {
        //System.out.println("Encrypt");
        String visibleMessage = visibleMessageTextField.getText();
        String hiddenMessage = hiddenMessageTextField.getText();

        encodedMessageTextField.setText("");
        resetError();

        if (visibleMessage.isEmpty())
            visibleMessageTextField.pseudoClassStateChanged(errorClass, true);
        else
            visibleMessageTextField.pseudoClassStateChanged(errorClass, false);
        if (hiddenMessage.isEmpty())
            hiddenMessageTextField.pseudoClassStateChanged(errorClass, true);
        else
            hiddenMessageTextField.pseudoClassStateChanged(errorClass, false);

        if (!(visibleMessage.isEmpty() || hiddenMessage.isEmpty())) {
            encodedMessageTextField.setText(StealthyWhisperAlgorithm.insertMessage(visibleMessage, hiddenMessage));
        }
    }

    public void decrypt(ActionEvent e) {
        //System.out.println("Decrypt");
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
        root = FXMLLoader.load(getClass().getResource("stealthy-whisper-settings.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("stealthy-whisper-main.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void visibleMsgCopyButton(ActionEvent e) {
        StringSelection stringSelection = new StringSelection(visibleMessageTextField.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        visibleMessageTextField.requestFocus();
    }

    public void hiddenMsgCopyButton(ActionEvent e) {
        StringSelection stringSelection = new StringSelection(hiddenMessageTextField.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        hiddenMessageTextField.requestFocus();
    }

    public void encodedMsgCopyButton(ActionEvent e) {
        StringSelection stringSelection = new StringSelection(encodedMessageTextField.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        encodedMessageTextField.requestFocus();
    }

    public void copyFunction (TextField textField) {

        StringSelection stringSelection = new StringSelection(textField.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
