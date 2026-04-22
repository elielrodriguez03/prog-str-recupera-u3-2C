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
        // TODO:
        // 1. Leer txtNombreSolicitante, txtCantidad y cbTipoCalculadora.
        String nombre = txtNombreSolicitante.getText();
        String cantidad = txtCantidad.getText();
        String tipo = cbTipoCalculadora.getValue();

        // 2. Mandar esos datos al service.
        String error = service.agregar(nombre, cantidad, tipo);
        // 3. Si el service regresa un mensaje, mostrar error.
        if (error != null){
            mostrarMensaje("error", error, Alert.AlertType.ERROR);
        }
        // 4. Si regresa null, refrescar la lista y limpiar.
        actualizarLista();
        limpiar();
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

        // Este valor es clave para UPDATE.
        nombreOriginal = registro.getNombreSolicitante();
    }

    @FXML
    public void actualizar() {
        // TODO:
        // UPDATE reutiliza los mismos controles.
        //
        // Flujo esperado:
        // 1. Primero buscar por nombre o seleccionar desde el ListView.
        // 2. Eso debe cargar los datos en pantalla y guardar nombreOriginal.
        // 3. Luego el usuario modifica txtNombreSolicitante, txtCantidad y cbTipoCalculadora.
        String nuevonombre = txtNombreSolicitante.getText();
        String nuevocantidad = txtCantidad.getText();
        String nuevotipo = cbTipoCalculadora.getValue();
        // 4. Al presionar Actualizar, mandar al service:
        String error = service.actualizar(nombreOriginal, txtNombreSolicitante.getText(), txtCantidad.getText(), cbTipoCalculadora.getValue());
        //      - nombreOriginal
        //      - txtNombreSolicitante.getText()
        //      - txtCantidad.getText()
        //      - cbTipoCalculadora.getValue()
        // 5. El service debe buscar el registro original usando nombreOriginal.

        // 6. Si lo encuentra, debe cambiar sus datos.
        if (error != null) {
            mostrarMensaje("Error de Actualización", error, Alert.AlertType.ERROR);
        } else {
            // 7. Refrescar y limpiar
            actualizarLista();
            limpiar();
            mostrarMensaje("Éxito", "Registro actualizado correctamente", Alert.AlertType.INFORMATION);
        }
        //
        // Importante:
        // Si nombreOriginal es null, entonces no se ha buscado ni seleccionado nada.
    }

    @FXML
    public void eliminar() {
        // TODO:
        // DELETE sí borra el objeto de la lista.
        //
        // Flujo esperado:
        // 1. Tomar el nombre desde txtNombreSolicitante.
        // 2. Mandarlo al service.
        String nombre = txtNombreSolicitante.getText();
        // 3. El service debe buscarlo y eliminarlo de la lista.
        String error = service.eliminar(nombre);
        // 4. Refrescar el ListView.
        actualizarLista();
        // 5. Limpiar controles.
        limpiar();
        //
        // También se puede seleccionar un elemento del ListView
        // y luego presionar Eliminar.
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
