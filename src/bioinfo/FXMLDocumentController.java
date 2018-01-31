/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bioinfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import bioinfo.Matriz;
import java.util.Arrays;

import javafx.beans.property.SimpleStringProperty;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


import javafx.util.Callback;

/**
 *
 * @author Fabian
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button button;
    @FXML
    private Label lblsec1, m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16, m17, m18, m19, m20, m22, m23, m24, m25;
    @FXML
    private Label lblsec2, lbl1, lbl2, lbl3, lbl4, lbl6, lbl7, puntos;
    @FXML
    private Label labelsecuencia01;
    @FXML
    private TextArea txtSecuencia1;
    @FXML
    private TextArea txtSecuencia2;
    @FXML
    private TextArea txtSecuencia01;
    @FXML
    private Accordion acordeon;
    @FXML
    private Pane paneMatriz;
    @FXML
    private ScrollPane scroll1;

    @FXML
    private AnchorPane paneAlin;
    
    int pos = 0;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        int fila, columna;
        String sequencia1 = txtSecuencia1.getText();
        String sequencia2 = txtSecuencia2.getText();
        fila = sequencia1.length() + 1;
        columna = sequencia2.length() + 1;
        Matriz mt = new Matriz(fila, columna);
        mt.MatrizScoring();
        mt.MatrizFinal(sequencia1, sequencia2);
        //mt.mostrarMatrizFinal();
        mt.valoresMatrizScoring();
        mt.mostrarMatriz();
        mt.alineamiento(sequencia1, sequencia2);
        lbl6.setVisible(false);
        lbl7.setVisible(false);
        crearGrid(fila, columna, mt.getMatrix());
        alinearField(mt.getSecuenciaA(), mt.getSecuenciaB());
        acordeon.setDisable(false);
        button.setVisible(false);
    }

    private void alinearField(String secuencia_a, String secuencia_b) {
        String[] auxS1 = secuencia_a.split("");
        String[] auxS2 = secuencia_b.split("");
        System.out.println(secuencia_a);
        System.out.println(secuencia_b);
        HBox prueba1 = new HBox();
        HBox prueba4 = new HBox();
        HBox prueba3 = new HBox();

        prueba1.setSpacing(20);
        prueba3.setSpacing(20);
        prueba4.setSpacing(20);

        for (int i = 0; i < secuencia_a.length(); i++) {
            Label label = new Label();
            Label labe2 = new Label();
            Label labe3 = new Label();
            if (Arrays.asList(auxS1[i]).contains(auxS2[i])) {
                label.setText("" + auxS1[i] + "");
                labe2.setText("" + auxS2[i] + "");
                labe3.setText("|");
                label.setTextFill(Color.web("#002bff"));
                labe2.setTextFill(Color.web("#002bff"));
                labe3.setTextFill(Color.web("#002bff"));
            } else {
                label.setText("" + auxS1[i] + "");
                labe2.setText("" + auxS2[i] + "");
                labe3.setText(" ");
                label.setTextFill(Color.web("RED"));
                labe2.setTextFill(Color.web("RED"));
            }
            label.setFont(new Font("Courier New", 35));
            labe2.setFont(new Font("Courier New", 35));
            labe3.setFont(new Font("Courier New", 35));

            prueba1.getChildren().add(label);
            prueba3.getChildren().add(labe2);
            prueba4.getChildren().add(labe3);
        }

        paneAlin.getChildren().add(prueba1);
        paneAlin.getChildren().add(prueba3);
        paneAlin.getChildren().add(prueba4);
        prueba3.setLayoutY(80);
        prueba4.setLayoutY(40);
        paneAlin.setStyle("-fx-background-color: #5EB299;");
        scroll1.setContent(paneAlin);
    }

    private void crearGrid(int fil, int col, int[][] matriz) {
        String m = "";
        String[][] matriz1 = new String[fil][col];
        int m1;
        TableView<String[]> matrizScoring = new TableView<>();
        ObservableList<String[]> data = FXCollections.observableArrayList();

        matrizScoring.setPrefHeight(215);
        matrizScoring.setPrefWidth(603);
        matrizScoring.maxWidth(603);
        matrizScoring.maxHeight(215);
        for (int i = 0; i < fil; i++) {
            for (int j = 0; j < col; j++) {
                m1 = matriz[i][j];
                m = String.valueOf(m1);
                matriz1[i][j] = m;
            }
        }

        data.addAll(Arrays.asList(matriz1));
        data.remove(0);

        for (int i = 0; i < matriz[0].length; i++) {

            TableColumn tc = new TableColumn(matriz1[0][i]);
            tc.setSortable(false);
            tc.setStyle("-fx-alignment: CENTER-RIGHT;");
            final int colNo = i;
            tc.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });

            tc.setPrefWidth(90);
            matrizScoring.getColumns().add(tc);
            tc.setEditable(false);

        }
        matrizScoring.setItems(data);
        paneMatriz.getChildren().add(matrizScoring);
        puntos.setText(m);
    }

    @FXML
    void draggingOver(DragEvent event) {
        Dragboard board = event.getDragboard();
        if (board.hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    void dropping(DragEvent event) {
        try {
            Dragboard board = event.getDragboard();
            List<File> phil = board.getFiles();
            FileInputStream fis;
            fis = new FileInputStream(phil.get(0));
            StringBuilder builder = new StringBuilder();
            int ch;
            String secuencia1 = "", secuencia2 = "";
            while ((ch = fis.read()) != -1) {
                builder.append((char) ch);
            }
            fis.close();
            txtSecuencia01.setText(builder.toString());
            verificarTxt();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void verificarTxt() {
        String sec1 = txtSecuencia01.getText();
        sec1 = sec1.replaceAll(" ", "");
        sec1 = sec1.replaceAll("\n", "");
        char[] secuenciaTotal = sec1.toCharArray();
        int aux = 0;
        String secuencia1 = "", secuencia2 = "";
        for (int i = 0; i < secuenciaTotal.length; i++) {
            if (secuenciaTotal[i] == '>') {
                aux++;
            }
            if (secuenciaTotal[i] == 'A' || secuenciaTotal[i] == 'T' || secuenciaTotal[i] == 'C' || secuenciaTotal[i] == 'G') {
                if (aux == 1) {
                    secuencia1 += secuenciaTotal[i];
                }
                if (aux == 2) {
                    secuencia2 += secuenciaTotal[i];
                }
            }
        }
        txtSecuencia1.setText(secuencia1);
        txtSecuencia2.setText(secuencia2);
        activarLabel();
    }

    private void activarLabel() {
        txtSecuencia01.setVisible(false);
        labelsecuencia01.setVisible(false);
        txtSecuencia1.setVisible(true);
        txtSecuencia2.setVisible(true);
        lblsec1.setVisible(true);
        lblsec2.setVisible(true);
        button.setVisible(true);
        lbl1.setVisible(false);
        lbl2.setVisible(false);
        lbl3.setVisible(false);
        lbl4.setVisible(false);
        acordeon.setVisible(true);
        lbl6.setVisible(true);
        lbl7.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtSecuencia1.setWrapText(true);
        txtSecuencia01.setWrapText(true);
        txtSecuencia2.setWrapText(true);
        GridPane.setHalignment(m1, HPos.CENTER);
        GridPane.setHalignment(m14, HPos.CENTER);
        GridPane.setHalignment(m2, HPos.CENTER);
        GridPane.setHalignment(m15, HPos.CENTER);
        GridPane.setHalignment(m3, HPos.CENTER);
        GridPane.setHalignment(m16, HPos.CENTER);
        GridPane.setHalignment(m4, HPos.CENTER);
        GridPane.setHalignment(m17, HPos.CENTER);
        GridPane.setHalignment(m5, HPos.CENTER);
        GridPane.setHalignment(m18, HPos.CENTER);
        GridPane.setHalignment(m6, HPos.CENTER);
        GridPane.setHalignment(m19, HPos.CENTER);
        GridPane.setHalignment(m7, HPos.CENTER);
        GridPane.setHalignment(m20, HPos.CENTER);
        GridPane.setHalignment(m8, HPos.CENTER);
        GridPane.setHalignment(m22, HPos.CENTER);
        GridPane.setHalignment(m9, HPos.CENTER);
        GridPane.setHalignment(m10, HPos.CENTER);
        GridPane.setHalignment(m23, HPos.CENTER);
        GridPane.setHalignment(m11, HPos.CENTER);
        GridPane.setHalignment(m24, HPos.CENTER);
        GridPane.setHalignment(m12, HPos.CENTER);
        GridPane.setHalignment(m25, HPos.CENTER);
        GridPane.setHalignment(m13, HPos.CENTER);
        // System.out.println(matrizScore.getColumns()); 
    }
}
