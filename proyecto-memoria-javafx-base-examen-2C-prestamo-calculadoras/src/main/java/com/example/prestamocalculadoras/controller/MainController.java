package com.example.prestamocalculadoras.controller;

import com.example.prestamocalculadoras.model.PrestamoCalculadora;
import com.example.prestamocalculadoras.repository.PrestamoCalculadoraRepository;
import com.example.prestamocalculadoras.service.PrestamoCalculadoraService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class MainController {

    @FXML private TextField txtNombreSolicitante;
    @FXML private TextField txtCantidad;
    @FXML private ComboBox<String> cbTipoCalculadora;
    @FXML private ListView<String> lvRegistros;

    private final PrestamoCalculadoraService service = new PrestamoCalculadoraService();
    private String nombreOriginal;

    @FXML
    public void initialize() {
        cargarTurnos();
        actualizarLista();
        lvRegistros.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarSeleccion(newValue);
            }
        });
    }

    private void cargarTurnos() {
        cbTipoCalculadora.getItems().addAll(service.obtenerTipos());
    }

    @FXML
    public void agregar() {

        String nombreSolicitante = txtNombreSolicitante.getText();
        String cantidad = txtCantidad.getText();
        String tipoCalculadora = cbTipoCalculadora.getValue();


        String error = service.agregar(nombreSolicitante, cantidad, tipoCalculadora);

        // 3. Validar respuesta
        if (error != null) {
            mostrarMensaje("Error", error, Alert.AlertType.ERROR);
        } else {

            actualizarLista();
            limpiar();
            mostrarMensaje("Éxito", "Registro agregado correctamente", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void buscar() {
        PrestamoCalculadora registro = service.buscarPorNombreSolicitante(txtNombreSolicitante.getText());
        if (registro == null) {
            mostrarMensaje("Aviso", "Registro no encontrado", Alert.AlertType.WARNING);
            return;
        }
        txtNombreSolicitante.setText(registro.getNombreSolicitante());
        txtCantidad.setText(registro.getCantidad());
        cbTipoCalculadora.setValue(registro.getTipoCalculadora());
        nombreOriginal = registro.getNombreSolicitante();
    }

    @FXML
    public void actualizar() {

        String nuevoNombre = txtNombreSolicitante.getText();
        String nuevaCantidad = txtCantidad.getText();
        String nuevoTipo = cbTipoCalculadora.getValue();


        String error = service.actualizar(nombreOriginal, nuevoNombre, nuevaCantidad, nuevoTipo);

        if (error != null) {
            mostrarMensaje("Error de Actualización", error, Alert.AlertType.ERROR);
        } else {

            actualizarLista();
            limpiar();
            mostrarMensaje("Éxito", "Registro actualizado correctamente", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void eliminar() {

        String nombreSolicitante = txtNombreSolicitante.getText();
        String error = service.eliminar(nombreSolicitante);

        if (error != null) {
            mostrarMensaje("Error", error, Alert.AlertType.ERROR);
        } else {

            actualizarLista();
            limpiar();
            mostrarMensaje("Éxito", "Registro eliminado", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void limpiar() {
        txtNombreSolicitante.clear();
        txtCantidad.clear();
        cbTipoCalculadora.setValue(null);
        lvRegistros.getSelectionModel().clearSelection();
        nombreOriginal = null;
    }

    private void actualizarLista() {
        lvRegistros.getItems().clear();
        List<PrestamoCalculadora> registros = service.obtenerTodos();
        for (PrestamoCalculadora t : registros) {
            lvRegistros.getItems().add(t.toString());
        }
    }

    private void cargarSeleccion(String textoSeleccionado) {
        List<PrestamoCalculadora> registros = service.obtenerTodos();
        for (PrestamoCalculadora actual : registros) {
            if (actual.toString().equals(textoSeleccionado)) {
                txtNombreSolicitante.setText(actual.getNombreSolicitante());
                txtCantidad.setText(actual.getCantidad());
                cbTipoCalculadora.setValue(actual.getTipoCalculadora());
                nombreOriginal = actual.getNombreSolicitante();
                break;
            }
        }
    }

    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}


