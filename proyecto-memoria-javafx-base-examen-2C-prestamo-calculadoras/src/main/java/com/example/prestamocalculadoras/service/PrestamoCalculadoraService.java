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
        // 2. Validar que cantidad no esté vacía.
        // 3. Validar que cantidad sea un número entero mayor que 0.
        // 4. Validar que tipoCalculadora no sea null.
        // 5. Validar que no exista otro registro con el mismo nombreSolicitante.
        // 6. Si todo está bien, crear un objeto PrestamoCalculadora y guardarlo en repository.
        // 7. Regresar null cuando el registro se guarde correctamente.
        return "Completa la lógica de agregar en el service";
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String cantidad, String tipoCalculadora) {
        // TODO:
        // 1. Validar que nombreOriginal no sea null ni vacío.
        // 2. Validar que nombreNuevo no esté vacío.
        // 3. Validar que cantidad no esté vacía.
        // 4. Validar que cantidad sea un número entero mayor que 0.
        // 5. Validar que tipoCalculadora no sea null.
        // 6. Buscar el registro original usando nombreOriginal.
        // 7. Si no existe, regresar mensaje de error.
        // 8. Si el nombre cambió, validar que el nuevo nombre no esté repetido.
        // 9. Si todo está bien, actualizar los atributos del objeto encontrado.
        // 10. Regresar null si todo salió bien.
        return "Completa la lógica de actualizar en el service";
    }

    public String eliminar(String nombreSolicitante) {
        // TODO:
        // 1. Validar que nombreSolicitante no esté vacío.
        // 2. Buscar si existe el registro.
        // 3. Si no existe, regresar mensaje de error.
        // 4. Si existe, eliminarlo desde repository.
        // 5. Regresar null si se eliminó correctamente.
        return "Completa la lógica de eliminar en el service";
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
