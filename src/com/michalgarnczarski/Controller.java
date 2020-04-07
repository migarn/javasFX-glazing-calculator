package com.michalgarnczarski;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Controller {

    @FXML
    private GridPane gridPane;
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

        String upperOutputMessage = "\n" + descriptionCreator.defineGeneralDescription();
        String redMiddleOutputMessage = "";
        String blackMiddleOutputMessage = "";
        String lowerOutputMessage = "";

        if (descriptionCreator.getThicknessDefiner().getThickness() != -1) {
            upperOutputMessage += "\n\n" + descriptionCreator.defineThicknessDescription();

            if (descriptionCreator.getSurchargeDefiner().getSurcharge() == 0) {
                GridPane.setRowIndex(middleOutputBlack,GridPane.getRowIndex(upperOutput) + 1);
                blackMiddleOutputMessage = descriptionCreator.defineSurchargeDescription();
            } else {
                GridPane.setRowIndex(middleOutputRed,GridPane.getRowIndex(upperOutput) + 1);
                redMiddleOutputMessage = descriptionCreator.defineSurchargeDescription();
            }

            if (glass.getDimensionsRatio() < 0.1) {
                if (descriptionCreator.getSurchargeDefiner().getSurcharge() > 0) {
                    GridPane.setRowIndex(lowerOutput,GridPane.getRowIndex(upperOutput) + 2);
                    redMiddleOutputMessage += "\n\n";
                } else {
                    GridPane.setRowIndex(middleOutputRed,GridPane.getRowIndex(upperOutput) + 2);
                    GridPane.setRowIndex(lowerOutput,GridPane.getRowIndex(upperOutput) + 3);
                }
                redMiddleOutputMessage += descriptionCreator.defineRatioDescription();
            }
            lowerOutputMessage = descriptionCreator.defineLatterDescription();
        } else {
            GridPane.setRowIndex(middleOutputRed,GridPane.getRowIndex(upperOutput) + 1);
            redMiddleOutputMessage = descriptionCreator.defineThicknessDescription();
        }

        upperOutput.setText(upperOutputMessage);
        middleOutputBlack.setText(blackMiddleOutputMessage);
        middleOutputRed.setText(redMiddleOutputMessage);
        lowerOutput.setText(lowerOutputMessage);
    }
}
