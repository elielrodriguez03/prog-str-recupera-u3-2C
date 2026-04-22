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

    if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
        return "El nombre de la persona no puede estar vacío";
    }

    if (cantidad == null || cantidad.trim().isEmpty()) {
        return "La cantidad no puede estar vacía";
    }

    int cantidadInt;

    try {
        cantidadInt = Integer.parseInt(cantidad);
    } catch (NumberFormatException e) {
        return "La cantidad debe ser un número entero";
    }

    if (cantidadInt <= 0) {
        return "La cantidad debe ser mayor que 0";
    }

    if (tipoCalculadora == null || tipoCalculadora.trim().isEmpty()) {
        return "El tipo de calculadora no puede estar vacío";
    }

    if (repository.buscarPorNombreSolicitante(nombreSolicitante) != null) {
        return "Ya existe un registro con ese nombre";
    }

   PrestamoCalculadora prestamo = new PrestamoCalculadora(
    nombreSolicitante.trim(), String.valueOf(cantidadInt), tipoCalculadora
);
    repository.guardar(prestamo);

    // 8. Todo correcto
    return null;
}
    
    

    public String actualizar(String nombreOriginal, String nombreNuevo, String cantidad, String tipoCalculadora) {
       
       if (nombreOriginal == null || nombreOriginal.trim().isEmpty()) {
        return "El nombre original de la persona no puede estar vacío";
    } 
      if (nombreNuevo == null || nombreNuevo.trim().isEmpty()) {
        return "El nombre nuevo de la persona no puede estar vacío";
    }

    if (cantidad == null || cantidad.trim().isEmpty()) {
        return "La cantidad no puede estar vacía";
    }

    int cantidadInt;

    try {
        cantidadInt = Integer.parseInt(cantidad);
    } catch (NumberFormatException e) {
        return "La cantidad debe ser un número entero";
    }

    if (cantidadInt <= 0) {
        return "La cantidad debe ser mayor que 0";
    }

    if (tipoCalculadora == null || tipoCalculadora.trim().isEmpty()) {
        return "El tipo de calculadora no puede estar vacío";
    }
    PrestamoCalculadora buscarPorNombre=repository.buscarPorNombreSolicitante(nombreOriginal);
    if (buscarPorNombre==null){
      return "El nombre que esta buscando no existe";

        }
      if (!nombreNuevo.equalsIgnoreCase(nombreOriginal)){
         if (repository.buscarPorNombreSolicitante(nombreNuevo)!=null){
         
         return "El nombre esta repetido "  ;
           }
           }
        buscarPorNombre.setNombreSolicitante(nombreNuevo);
        buscarPorNombre.setCantidad(cantidad);
        buscarPorNombre.setTipoCalculadora(tipoCalculadora);
       
       
       
        
        return null;
    }

    public String eliminar(String nombreSolicitante) {
         if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
        return "El nombre del solicitante de la persona no puede estar vacío";
    } 
       if (repository.buscarPorNombreSolicitante(nombreSolicitante)==null){
       return "La persona no existe";
   }
    repository.eliminarPorNombreSolicitante(nombreSolicitante);
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


