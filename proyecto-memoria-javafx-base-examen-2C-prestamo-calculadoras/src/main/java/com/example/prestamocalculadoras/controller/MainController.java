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

    // Aquí se guarda el nombre original del registro encontrado o seleccionado.
    private String nombreOriginal;

    @FXML
    public void initialize() {
        cargarTipos();
        actualizarLista();

        // También se puede cargar un registro seleccionándolo en el ListView.
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
        // 1. Leer txtNombreSolicitante, txtCantidad y cbTipoCalculadora.
        String nombre = txtNombreSolicitante.getText();
        String cantidad = txtCantidad.getText();
        String tipo = cbTipoCalculadora.getValue();

        // 2. Mandar esos datos al service.
        String mensajeError = service.agregar(nombre, cantidad, tipo);

        // 3. Si el service regresa un mensaje, mostrar error.
        if (mensajeError != null) {
            mostrarMensaje("Error", mensajeError, Alert.AlertType.ERROR);
        } else {
            // 4. Si regresa null, refrescar la lista y limpiar.
            actualizarLista();
            limpiar();
            mostrarMensaje("Éxito", "Registro agregado correctamente", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void buscar() {
        // Metodo de ejemplo resuelto.
        PrestamoCalculadora registro = service.buscarPorNombreSolicitante(txtNombreSolicitante.getText());

        if (registro == null) {
            mostrarMensaje("Aviso", "Registro no encontrado", Alert.AlertType.WARNING);
            return;
        }

        txtNombreSolicitante.setText(registro.getNombreSolicitante());
        txtCantidad.setText(registro.getCantidad());
        cbTipoCalculadora.setValue(registro.getTipoCalculadora());

        // Este valor es clave para UPDATE.
        nombreOriginal = registro.getNombreSolicitante();
    }

    @FXML
    public void actualizar() {
        // Validacion de seguridad: Si nombreOriginal es null, no se ha seleccionado nada.
        if (nombreOriginal == null) {
            mostrarMensaje("Aviso", "Primero busca o selecciona un registro para modificar", Alert.AlertType.WARNING);
            return;
        }

        // 4. Al presionar Actualizar, mandar al service los datos actuales de los controles
        String nuevoNombre = txtNombreSolicitante.getText();
        String nuevaCantidad = txtCantidad.getText();
        String nuevoTipo = cbTipoCalculadora.getValue();

        String mensajeError = service.actualizar(nombreOriginal, nuevoNombre, nuevaCantidad, nuevoTipo);

        if (mensajeError != null) {
            mostrarMensaje("Error", mensajeError, Alert.AlertType.ERROR);
        } else {
            // 7. Luego refrescar el ListView y limpiar los controles.
            actualizarLista();
            limpiar();
            mostrarMensaje("Éxito", "Registro actualizado correctamente", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void eliminar() {
        // 1. Tomar el nombre desde txtNombreSolicitante.
        String nombre = txtNombreSolicitante.getText();

        if (nombre == null || nombre.trim().isEmpty()) {
            mostrarMensaje("Aviso", "Debe haber un nombre para eliminar", Alert.AlertType.WARNING);
            return;
        }

        // 2. Mandarlo al service. (El service usa eliminarPorNombreSolicitante del repository internamente)
        String mensajeError = service.eliminar(nombre);

        if (mensajeError != null) {
            mostrarMensaje("Error", mensajeError, Alert.AlertType.ERROR);
        } else {
            // 4. Refrescar el ListView y 5. Limpiar controles.
            actualizarLista();
            limpiar();
            mostrarMensaje("Éxito", "Registro eliminado correctamente", Alert.AlertType.INFORMATION);
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
