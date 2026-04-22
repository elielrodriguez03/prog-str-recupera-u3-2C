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
        // 1, 2 y 4. Validaciones de presencia de datos
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) return "El nombre del solicitante es obligatorio.";
        if (cantidad == null || cantidad.trim().isEmpty()) return "La cantidad es obligatoria.";
        if (tipoCalculadora == null) return "Debe seleccionar un tipo de calculadora.";

        // 3. Validar que cantidad sea un número entero mayor que 0
        if (!esCantidadValida(cantidad)) return "La cantidad debe ser un número entero mayor a 0.";

        // 5. Validar que no exista otro registro con el mismo nombreSolicitante
        if (repository.buscarPorNombreSolicitante(nombreSolicitante.trim()) != null) {
            return "Ya existe un registro con ese nombre de solicitante.";
        }

        // 6. Crear objeto y guardar en repository
        PrestamoCalculadora nuevo = new PrestamoCalculadora(nombreSolicitante.trim(), cantidad.trim(), tipoCalculadora);
        repository.guardar(nuevo);

        // 7. Regresar null si todo salió bien
        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String cantidad, String tipoCalculadora) {
        // 1, 2, 3, 5. Validaciones básicas
        if (nombreOriginal == null || nombreOriginal.isEmpty()) return "No se ha seleccionado un registro original.";
        if (nombreNuevo == null || nombreNuevo.trim().isEmpty()) return "El nombre no puede estar vacío.";
        if (cantidad == null || cantidad.trim().isEmpty()) return "La cantidad no puede estar vacía.";
        if (tipoCalculadora == null) return "Seleccione un tipo de calculadora.";

        // 4. Validar cantidad numérica
        if (!esCantidadValida(cantidad)) return "La cantidad debe ser un número entero mayor a 0.";

        // 6. Buscar el registro original
        PrestamoCalculadora registro = repository.buscarPorNombreSolicitante(nombreOriginal);

        // 7. Si no existe, regresar error
        if (registro == null) return "El registro original no fue encontrado en la base de datos.";

        // 8. Si el nombre cambió, validar que el nuevo nombre no esté repetido en otro registro
        if (!nombreOriginal.equalsIgnoreCase(nombreNuevo.trim())) {
            if (repository.buscarPorNombreSolicitante(nombreNuevo.trim()) != null) {
                return "El nuevo nombre ya existe en otro registro. Elija uno diferente.";
            }
        }

        // 9. Actualizar los atributos del objeto encontrado
        registro.setNombreSolicitante(nombreNuevo.trim());
        registro.setCantidad(cantidad.trim());
        registro.setTipoCalculadora(tipoCalculadora);

        // 10. Regresar null si todo salio bien
        return null;
    }

    public String eliminar(String nombreSolicitante) {
        // 1. Validar que nombreSolicitante no esté vacío
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) return "Debe proporcionar un nombre para eliminar.";

        // 2. Buscar si existe el registro y 4. eliminarlo si existe
        boolean eliminado = repository.eliminarPorNombreSolicitante(nombreSolicitante.trim());

        // 3. Si no existe (el metodo devolvió false), regresar mensaje de error
        if (!eliminado) {
            return "No se pudo eliminar: El registro no existe.";
        }

        // 5. Regresar null si se eliminó correctamente
        return null;
    }

    // Metodo de ejemplo: les puede servir como apoyo para la validación numérica.
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
