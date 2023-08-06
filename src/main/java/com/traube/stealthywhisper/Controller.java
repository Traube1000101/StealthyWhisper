package com.traube.stealthywhisper;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static com.traube.stealthywhisper.Application.globalLoader;
import static com.traube.stealthywhisper.Application.version;

public class Controller {
    @FXML AnchorPane anchorPane;

    @FXML private TextField encodedMessageTextField;
    @FXML private TextField visibleMessageTextField;
    @FXML private TextField hiddenMessageTextField;


    @FXML public void initialize() {
        anchorPane.setOnMouseClicked(event -> anchorPane.requestFocus());
    }

    final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

    public void encrypt() {
        String visibleMessage = visibleMessageTextField.getText();
        String hiddenMessage = hiddenMessageTextField.getText();

        encodedMessageTextField.setText("");
        resetError();

        visibleMessageTextField.pseudoClassStateChanged(errorClass, visibleMessage.isEmpty());
        hiddenMessageTextField.pseudoClassStateChanged(errorClass, hiddenMessage.isEmpty());

        if (!(visibleMessage.isEmpty() || hiddenMessage.isEmpty())) {
            encodedMessageTextField.setText(Algorithm.insertMessage(visibleMessage, Algorithm.encodeMessage(hiddenMessage, (byte)1)));
        }
    }

    public void decrypt() {
        visibleMessageTextField.setText("");
        hiddenMessageTextField.setText("");
        resetError();

        if (encodedMessageTextField.getText().isEmpty())
            encodedMessageTextField.pseudoClassStateChanged(errorClass, true);
        else {
            encodedMessageTextField.pseudoClassStateChanged(errorClass, false);
            String encodedMessage = encodedMessageTextField.getText();
            try {
                String[] messages = Algorithm.decodeMessage(encodedMessage);
                hiddenMessageTextField.setText(messages[0]);
                visibleMessageTextField.setText(messages[1]);
            }
            catch (Exception e) {
                infoBox("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    public void clear() {
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

    public void openSettings() {
            loadScene(getClass().getResource("settings.fxml"));
    }

    public static void loadScene(ActionEvent event, URL sceneUrl) {
        try {
            // Switch to settings scene
            Locale locale = new Locale(SettingsManager.getSetting("locale", Locale.getDefault().getCountry()));

            globalLoader = new FXMLLoader();
            ResourceBundle langBundle = ResourceBundle.getBundle("com.traube.bundles.lang", locale);
            globalLoader.setResources(langBundle);
            globalLoader.setLocation(Objects.requireNonNull(sceneUrl));
            Parent root = globalLoader.load();

            Stage stage = new Stage();
            Image icon = new Image(Objects.requireNonNull(Controller.class.getResourceAsStream("app-icon.png")));

            stage.getIcons().add(icon);
            stage.setTitle("Settings");
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);

            //stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            /* // Use when triggered by menu item instead of button
            if (event != null) {
                MenuItem menuItem = (MenuItem) event.getSource();
                scene = menuItem.getParentPopup().getOwnerWindow().getScene();
                stage = (Stage) scene.getWindow();

            }
             */

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Throwable cause = e.getCause();
            cause.printStackTrace();
        }
    }

    static void loadScene(URL sceneUrl) { loadScene(null,sceneUrl); }

    void loadScene(ActionEvent event) { loadScene(event, globalLoader.getLocation()); }

    public void visibleMsgCopyButton() { copyFunction(visibleMessageTextField); }

    public void hiddenMsgCopyButton() { copyFunction(hiddenMessageTextField); }

    public void encodedMsgCopyButton() { copyFunction(encodedMessageTextField); }

    void copyFunction(TextField messageTextField) {
        messageTextField.requestFocus();
        StringSelection stringSelection = new StringSelection(messageTextField.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public static void infoBox(String titleBar, String infoMessage, String headerMessage, Alert.AlertType infoBoxType)
    {
        Alert alert = new Alert(infoBoxType);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.getButtonTypes().add(new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE));

        // add icon to alert box
        Image icon = new Image(Objects.requireNonNull(Controller.class.getResourceAsStream("app-icon.png")));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);

        alert.showAndWait();
    }
    public static void infoBox(String titleBar, String infoMessage, Alert.AlertType infoBoxType)
    {
        /* By specifying a null headerMessage String, we cause the dialog to
           not have a header */
        infoBox(titleBar, infoMessage, null, infoBoxType);
    }

    public void exitApplication(ActionEvent event) {
        Stage stage;
        MenuItem menuItem = (MenuItem) event.getSource();
        stage = (Stage) menuItem.getParentPopup().getOwnerWindow().getScene().getWindow();
        stage.close();
    }

    public void openAbout() {
        infoBox("About", "StealthyWhisper\nVersion:\t\t" + version + "\nCreated by:\tTraube", Alert.AlertType.NONE);
    }
}
