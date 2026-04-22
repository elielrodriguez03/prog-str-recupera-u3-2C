package com.example.prestamocalculadoras.repository;

import com.example.prestamocalculadoras.model.PrestamoCalculadora;

import java.util.ArrayList;
import java.util.List;

public class PrestamoCalculadoraRepository {

    private final List<PrestamoCalculadora> registros = new ArrayList<>();

    public List<PrestamoCalculadora> obtenerTodos() {
        return registros;
    }

    public void guardar(PrestamoCalculadora registro) {
        registros.add(registro);
    }

    public PrestamoCalculadora buscarPorNombreSolicitante(String nombreSolicitante) {
        for (int i = 0; i < registros.size(); i++) {
            PrestamoCalculadora actual = registros.get(i);
            if (actual.getNombreSolicitante().equalsIgnoreCase(nombreSolicitante)) {
                return actual;
            }
        }
        return null;
    }

    public PrestamoCalculadora eliminarPorNombreSolicitante(String nombreSolicitante) {
        for (int i = 0; i < registros.size(); i++) {
            PrestamoCalculadora actual = registros.get(i);
            if (actual.getNombreSolicitante().equalsIgnoreCase(nombreSolicitante)) {
                registros.remove(i);
                return actual;
            }
        }
        return null;
    }
}
