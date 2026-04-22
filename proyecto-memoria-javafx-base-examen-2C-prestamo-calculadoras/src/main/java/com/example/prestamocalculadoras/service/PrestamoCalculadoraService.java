package com.example.prestamocalculadoras.service;

import com.example.prestamocalculadoras.model.PrestamoCalculadora;
import com.example.prestamocalculadoras.repository.PrestamoCalculadoraRepository;

import java.util.List;

public class PrestamoCalculadoraService {

    private final PrestamoCalculadoraRepository repository = new PrestamoCalculadoraRepository();

    private final String[] tipos = {"Básica", "Científica", "Gráfica"};

    public String[] obtenerTipos() {
        return tipos;
    }

    public List<PrestamoCalculadora> obtenerTodos() {
        return repository.obtenerTodos();
    }

    public PrestamoCalculadora buscarPorNombreSolicitante(String nombreSolicitante) {
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return null;
        }
        return repository.buscarPorNombreSolicitante(nombreSolicitante.trim());
    }

    public String agregar(String nombreSolicitante, String cantidad, String tipoCalculadora) {
        // TODO:
        // 1. Validar que nombreSolicitante no esté vacío.
        if (nombreSolicitante == null || nombreSolicitante.trim().isBlank()){
            return "El nombre del alumno es obligatorio.";
        }
        // 2. Validar que cantidad no esté vacía.
        if (cantidad == null || cantidad.trim().isBlank()){
            return "El nombre del alumno es obligatorio.";
        }
        // 3. Validar que cantidad sea un número entero mayor que 0.
        // 4. Validar que tipoCalculadora no sea null.
        if (tipoCalculadora == null || tipoCalculadora.trim().isBlank()){
            return "El tipo de calculadora es obligatorio.";
        }
        // 5. Validar que no exista otro registro con el mismo nombreSolicitante.
        if (repository.buscarPorNombreSolicitante(nombreSolicitante.trim()) != null) {
            return "Ya existe un registro para este prestamo.";
        }
        // 6. Si todo está bien, crear un objeto PrestamoCalculadora y guardarlo en repository.
        PrestamoCalculadora nuevo = new PrestamoCalculadora(nombreSolicitante.trim(), cantidad.trim(), tipoCalculadora);
        repository.guardar(nuevo);
        // 7. Regresar null cuando el registro se guarde correctamente.
        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String cantidad, String tipoCalculadora) {
        // TODO:
        // 1. Validar que nombreOriginal no sea null ni vacío.
        if (nombreOriginal == null || nombreOriginal.isEmpty()) return "No puede estar vacio.";
        // 2. Validar que nombreNuevo no esté vacío.
        if (nombreNuevo == null || nombreNuevo.trim().isBlank()){
            return "No puede estar vacio.";
        }
        // 3. Validar que cantidad no esté vacía.
        if (cantidad == null || cantidad.trim().isBlank()){
            return "No puede estar vacio el campo de vacio.";
        }
        // 4. Validar que cantidad sea un número entero mayor que 0.

        // 5. Validar que tipoCalculadora no sea null.
        if (tipoCalculadora == null || tipoCalculadora.trim().isBlank()){
            return "No puede estar vacio el campo de tipo de calculadora.";
        }
        // 6. Buscar el registro original usando nombreOriginal.
        PrestamoCalculadora registro = repository.buscarPorNombreSolicitante(nombreOriginal);
        if (registro == null) return "El registro original ya no existe.";
        // 7. Si no existe, regresar mensaje de error.

        // 8. Si el nombre cambió, validar que el nuevo nombre no esté repetido.
        if (!nombreOriginal.equalsIgnoreCase(nombreNuevo.trim())) {
            if (repository.buscarPorNombreSolicitante(nombreNuevo.trim()) != null) {
                return "El nuevo nombre ya pertenece a otro registro.";
            }
        }
        // 9. Si todo está bien, actualizar los atributos del objeto encontrado.
        registro.setNombreSolicitante(nombreNuevo.trim());
        registro.setCantidad(cantidad.trim());
        registro.setTipoCalculadora(tipoCalculadora);
        // 10. Regresar null si todo salió bien.
        return null;
    }

    public String eliminar(String nombreSolicitante) {
        // TODO:
        // 1. Validar que nombreSolicitante no esté vacío.
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) return "Debe ingresar el nombre del solicitante a eliminar.";
        // 2. Buscar si existe el registro.
        // 3. Si no existe, regresar mensaje de error.

        // 4. Si existe, eliminarlo desde repository.
        boolean eliminado = repository.eliminarPorNombreSolicitante(nombreSolicitante.trim());
        if (!eliminado) {
            return "No se encontró el solicitante para eliminar.";
        }
        // 5. Regresar null si se eliminó correctamente.
        return null;
    }

    // Método de ejemplo: les puede servir como apoyo para la validación numérica.
    public boolean esCantidadValida(String cantidad) {
        if (cantidad == null || cantidad.trim().isEmpty()) {
            return false;
        }

        for (int i = 0; i < cantidad.length(); i++) {
            if (!Character.isDigit(cantidad.charAt(i))) {
                return false;
            }
        }

        return Integer.parseInt(cantidad) > 0;
    }
}
