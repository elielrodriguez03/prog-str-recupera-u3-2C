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
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) return "Debe de ingresar un nombre";
        if (cantidad == null || cantidad.trim().isEmpty()) return "Escriba la cantidad";
        if (!esCantidadValida(cantidad)) return "La cantidad debe ser un número entero mayor a 0";
        if (tipoCalculadora == null) return "Selccione una opción";
        if (repository.buscarPorNombreSolicitante(nombreSolicitante.trim()) != null) {
            return "Ya existe un registro con ese nombre";
        }
        PrestamoCalculadora nuevo = new PrestamoCalculadora(nombreSolicitante.trim(), cantidad, tipoCalculadora);
        repository.guardar(nuevo);
        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String cantidad, String tipoCalculadora) {
        if (nombreOriginal == null) return "Debe buscar o seleccionar un registro";
        if (nombreNuevo == null || nombreNuevo.trim().isEmpty()) return "Debe de escribir un nombre nuevo";
        if (!esCantidadValida(cantidad)) return "La cantidad debe ser válida";
        if (tipoCalculadora == null) return "Seleccione un tipo de calculadora";
        PrestamoCalculadora registro = repository.buscarPorNombreSolicitante(nombreOriginal);
        if (registro == null) return "El registro original ya no existe.";
        if (!nombreOriginal.equalsIgnoreCase(nombreNuevo.trim())) {
            if (repository.buscarPorNombreSolicitante(nombreNuevo.trim()) != null) {
                return "El nuevo nombre ya está registrado por otra persona.";
            }
        }
        registro.setNombreSolicitante(nombreNuevo.trim());
        registro.setCantidad(cantidad);
        registro.setTipoCalculadora(tipoCalculadora);

        return null;
    }


    public String eliminar(String nombreSolicitante) {
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "Ingrese el nombre del solicitante a eliminar.";
        }

        boolean eliminado = repository.eliminarPorNombreSolicitante(nombreSolicitante.trim());
        if (!eliminado) return "No se encontró el registro para eliminar.";

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

