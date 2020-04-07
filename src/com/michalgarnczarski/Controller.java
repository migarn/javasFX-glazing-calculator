package com.michalgarnczarski;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private Label headerLabel;
    @FXML
    private TextField glassWidthTextField;
    @FXML
    private TextField glassHeightTextField;
    @FXML
    private TextField spacerWidthTextField;
    @FXML
    private Label upperOutput;
    @FXML
    private Label middleOutputBlack;
    @FXML
    private Label middleOutputRed;
    @FXML
    private Label lowerOutput;

    public void initialize() {

        headerLabel.setText("Kalkulator służy do obliczania minimalnych grubości szyb." +
                "\nObliczenia oparte są na normie zakładowej firmy PRESS GLASS SA.");

        glassWidthTextField.setText("0");
        glassHeightTextField.setText("0");
        spacerWidthTextField.setText("16");

        onlyNumbers(glassWidthTextField);
        onlyNumbers(glassHeightTextField);
        onlyNumbers(spacerWidthTextField);

        limitTextFieldLength(glassWidthTextField, 4);
        limitTextFieldLength(glassHeightTextField, 4);
        limitTextFieldLength(spacerWidthTextField, 2);
    }

    private void onlyNumbers(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            textField.setText(newValue.replaceAll("[^\\d]",""));
        });
    }

    private void limitTextFieldLength(TextField textField, int maxLength) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (textField.getText().length() > maxLength) {
                String limitedString = textField.getText().substring(0, maxLength);
                textField.setText(limitedString);
            }
        });
    }

    @FXML
    private void calculate() {
        Glass glass = new Glass(Integer.parseInt(glassWidthTextField.getText()),
                Integer.parseInt(glassHeightTextField.getText()), Integer.parseInt(spacerWidthTextField.getText()));

        GlassDescriptionCreator descriptionCreator = new GlassDescriptionCreator(glass);

        if (descriptionCreator.getThicknessDefiner().getThickness() != 0) {
            upperOutput.setText("\n" + descriptionCreator.defineGeneralDescription() +
                    "\n\n" + descriptionCreator.defineThicknessDescription());

        }

        middleOutputBlack.setText("");
        middleOutputRed.setText("");
        lowerOutput.setText(descriptionCreator.defineLatterDescription());
    }
}
