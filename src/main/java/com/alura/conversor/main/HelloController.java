package com.alura.conversor.main;

import com.alura.conversor.comun.GestorMonedas;
import com.alura.conversor.comun.base.CrearMonedas;
import com.alura.conversor.entidad.Moneda;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.stream.Collectors;

public class HelloController {

    @FXML
    private TextField valorPesos;

    @FXML
    private TextField nombreMoneda;

    @FXML
    private TextField total;

    @FXML
    private ListView<String> suggestionsListView;

    @FXML
    private Button refrescar;

    @FXML
    private Button totalizar;

    @FXML
    private Spinner<Integer> cantidadConvertir;
    private CrearMonedas crearMonedas;
    private GestorMonedas gestorMonedas;
    private Moneda moneda;

    private int intValorPesos;

    @FXML
    public void initialize() {
        this.asignarimagen();
        crearMonedas = new CrearMonedas();
        gestorMonedas = new GestorMonedas(crearMonedas.instanciarMonedas());

        List<String> listMonedas = gestorMonedas.obtenerListaMonedas();

        suggestionsListView.setVisible(false);
        suggestionsListView.setManaged(false);
        suggestionsListView.setCellFactory(TextFieldListCell.forListView());

        nombreMoneda.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                suggestionsListView.setVisible(false);
                suggestionsListView.setManaged(false);
            } else {
                List<String> currentSuggestions = listMonedas.stream()
                        .filter(suggestion -> suggestion.toLowerCase().startsWith(newValue.toLowerCase()))
                        .collect(Collectors.toList());

                if (currentSuggestions.isEmpty()) {
                    suggestionsListView.setVisible(false);
                    suggestionsListView.setManaged(false);
                } else {
                    ObservableList<String> observableList = FXCollections.observableArrayList(currentSuggestions);
                    suggestionsListView.setItems(observableList);
                    suggestionsListView.setVisible(true);
                    suggestionsListView.setManaged(true);
                }
            }
        });

        suggestionsListView.setOnMouseClicked(event -> {
            String monedaSeleccionada = suggestionsListView.getSelectionModel().getSelectedItem();
            nombreMoneda.setText(monedaSeleccionada);
            this.actulizarValor(monedaSeleccionada);
            suggestionsListView.setVisible(false);
            suggestionsListView.setManaged(false);
        });
    }


    public void actulizarValor(String monedaSeleccionada){
        crearMonedas = new CrearMonedas();
        // gestorMonedas = new GestorMonedas(crearMonedas.instanciarMonedas());
        moneda = gestorMonedas.obtenerMonedaNombre(monedaSeleccionada);
        valorPesos.setText("$ " + String.valueOf(moneda.getValorEnPesos()) + " COP");

    }

    public void asignarimagen(){
        refrescar.setGraphic(new ImageView(new Image("actualizar.png")));
    }

    public void calcularTotal(){
       int cantidad = cantidadConvertir.valueProperty().get();
       if(valorPesos.getText().isEmpty()){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setHeaderText(null);
           alert.setTitle("Error");
           alert.setContentText("No ha seleccionado ninguna moneda");
           alert.showAndWait();
       }else {
           double intValorPesos = moneda.getValorEnPesos();
           double totalConversion = (cantidad * intValorPesos);
           total.setText(String.valueOf(totalConversion));
       }


    }



}

