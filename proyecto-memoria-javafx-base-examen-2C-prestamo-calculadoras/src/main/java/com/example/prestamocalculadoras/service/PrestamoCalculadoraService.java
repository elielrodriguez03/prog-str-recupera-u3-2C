package com.example.prestamocalculadoras.service;

import com.example.prestamocalculadoras.model.PrestamoCalculadora;
import com.example.prestamocalculadoras.repository.PrestamoCalculadoraRepository;

import java.util.List;

public class PrestamoCalculadoraService {
    private final PrestamoCalculadoraRepository repository = new PrestamoCalculadoraRepository();
    private final String[] tipos = {"Cientifica", "Grafica"};

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

        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) return "DEbes ingresar el nombre del solicitante.";
        if (cantidad == null ||cantidad.isEmpty() || cantidad.equals("0")) return "La cantidad no puede sr vacia y debe ser mayor a cero";
        if (tipoCalculadora == null) return "Debes seleccionar un tipo de calculadora.";

        if (repository.buscarPorNombreSolicitante(nombreSolicitante.trim()) != null) {
            return "ESte registro ya existe.";
        }

        PrestamoCalculadora nuevo = new PrestamoCalculadora(nombreSolicitante.trim(), cantidad.trim(), tipoCalculadora.trim());
        repository.guardar(nuevo);


        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String cantidadNuevo, String tipoNuevo) {

        if (nombreOriginal == null || nombreOriginal.isEmpty()) return "No hay un registro seleccionado para actualizar.";


        PrestamoCalculadora registro = repository.buscarPorNombreSolicitante(nombreOriginal);
        if (registro == null) return "El registro original ya no existe.";


        if (nombreNuevo == null || nombreNuevo.trim().isEmpty()) return "El nuevo nombre no puede estar vacío.";
        if (cantidadNuevo == null || cantidadNuevo.trim().isEmpty()) return "LA cantidad no puede ser vacia.";


        if (!nombreOriginal.equalsIgnoreCase(nombreNuevo.trim())) {
            if (repository.buscarPorNombreSolicitante(nombreNuevo.trim()) != null) {
                return "El Nuevo nombre ya existe.";
            }
        }


        registro.setNombreSolicitante(nombreNuevo.trim());
        registro.setCantidad(cantidadNuevo.trim());
        registro.setTipoCalculadora(tipoNuevo);

        return null;
    }

    public String eliminar(String nombreSolicitante) {

        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) return "Debes ingresar el nombre del solicitante que quieres eliminar.";


        boolean eliminado = repository.eliminarPorNombreSolicitante(nombreSolicitante.trim());
        if (!eliminado) {
            return "No se encontró el solicitante para eliminar.";
        }
        return null;
    }
}