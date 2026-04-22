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
        
        String nombreSolicitante=txtNombreSolicitante.getText();
        String cantidadPagar=txtCantidad.getText();
        String cbTipoCalculadora2=cbTipoCalculadora.getValue();
        String agregarPersonas= service.agregar(nombreSolicitante,cantidadPagar,cbTipoCalculadora2);
        if (agregarPersonas!=null){
     mostrarMensaje("Error al agregar", "No se logro agregar a la persona", Alert.AlertType.WARNING);
             return;
            }
            actualizarLista();
       limpiar();
               mostrarMensaje("Persona Agregada con exito", "Se agrego con exito a la persona", Alert.AlertType.INFORMATION);

       
       
       
       
      
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

    // 1. Validar que sí haya un registro seleccionado/buscado
    if (nombreOriginal == null || nombreOriginal.trim().isEmpty()) {
        mostrarMensaje("Error", "Primero debes buscar o seleccionar un registro", Alert.AlertType.ERROR);
        return;
    }

    // 2. Obtener datos de los controles
    String nombreNuevo = txtNombreSolicitante.getText();
    String cantidad = txtCantidad.getText();
    String tipo = cbTipoCalculadora.getValue();

    // 3. Mandar al service
    String resultado = service.actualizar(
            nombreOriginal,
            nombreNuevo,
            cantidad,
            tipo
    );

    // 4. Verificar resultado
    if (resultado != null) {
        mostrarMensaje("Error", resultado, Alert.AlertType.ERROR);
        return;
    }

    actualizarLista(); 

    limpiar(); 

    // 7. Resetear nombreOriginal
    nombreOriginal = null;

    // 8. Mensaje de éxito
    mostrarMensaje("Éxito", "Registro actualizado correctamente", Alert.AlertType.INFORMATION);
}

    @FXML
    public void eliminar() {
    
    String nombreSolicitante=txtNombreSolicitante.getText();
     String eliminarPersona=service.eliminar(nombreSolicitante);
     if (eliminarPersona!=null){
   mostrarMensaje("Error al eliminar a la persona ", "No se pudo eliminar a la persona ", Alert.AlertType.WARNING);
     return;
    }
    actualizarLista();
    limpiar ();
   mostrarMensaje("Persona Eliminada con exito", "Se logro eliminar a la persona con exitoS", Alert.AlertType.INFORMATION);
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



