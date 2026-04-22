package com.example.prestamocalculadoras.controller;

import com.example.prestamocalculadoras.model.PrestamoCalculadora;
import com.example.prestamocalculadoras.service.PrestamoCalculadoraService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class MainController {
    @FXML
    private TextField txtNombreSolicitante;

    @FXML
    private TextField txtCantidad;

    @FXML
    private ComboBox<String> cbTipoCalculadora;

    @FXML
    private ListView<String> lvRegistros;

    private final PrestamoCalculadoraService service = new PrestamoCalculadoraService();

    private String nombreOriginal;

    @FXML
    public void initialize() {
        cargarTipos();
        actualizarLista();

        lvRegistros.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarSeleccion(newValue);
            }
        });
    }

    private void cargarTipos() {
        String[] tipos = service.obtenerTipos();
        for (int i = 0; i < tipos.length; i++) {
            cbTipoCalculadora.getItems().add(tipos[i]);
        }
    }

    @FXML
    public void agregar() {
        String nombre = txtNombreSolicitante.getText();
        String cantidad = txtCantidad.getText();
        String tipo = cbTipoCalculadora.getValue();

        String error = service.agregar(nombre, cantidad, tipo);

        if (error != null) {
            mostrarMensaje("Error de validación", error, Alert.AlertType.ERROR);
        } else {
            actualizarLista();
            limpiar();
        }
    }

    @FXML
    public void buscar() {
        // Método de ejemplo resuelto.
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
        String nombreNuevo = txtNombreSolicitante.getText();
        String cantidad = txtCantidad.getText();
        String tipo = cbTipoCalculadora.getValue();

        // nombreOriginal se llenó en buscar() o cargarSeleccion()
        String error = service.actualizar(nombreOriginal, nombreNuevo, cantidad, tipo);

        if (error != null) {
            mostrarMensaje("Error al actualizar", error, Alert.AlertType.ERROR);
        } else {
            actualizarLista();
            limpiar();
            mostrarMensaje("Éxito", "Registro actualizado correctamente", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void eliminar() {
        String nombre = txtNombreSolicitante.getText();

        String error = service.eliminar(nombre);

        if (error != null) {
            mostrarMensaje("Error al eliminar", error, Alert.AlertType.ERROR);
        } else {
            actualizarLista();
            limpiar();
            mostrarMensaje("Éxito", "Registro eliminado exitosamente", Alert.AlertType.INFORMATION);
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

        for (int i = 0; i < registros.size(); i++) {
            lvRegistros.getItems().add(registros.get(i).toString());
        }
    }

    private void cargarSeleccion(String textoSeleccionado) {
        List<PrestamoCalculadora> registros = service.obtenerTodos();

        for (int i = 0; i < registros.size(); i++) {
            PrestamoCalculadora actual = registros.get(i);

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
