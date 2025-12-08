/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GimnacioClienteClase;

import GimnacioClienteClase.ClienteClase;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 *
 * @author Eduard Salas Murillo
 */
public class RepositorioClienteClaseMemoria implements RepositorioClienteClase {

    private static final List<ClienteClase> listaAsignaciones = new ArrayList<>();
    private static final AtomicLong contadorId = new AtomicLong(1L);

    @Override
    public long crear(ClienteClase clienteClase) {
        clienteClase.setId(contadorId.getAndIncrement());
        listaAsignaciones.add(clienteClase);
        return clienteClase.getId();
    }

    @Override
    public List<ClienteClase> buscarPorCedulaCliente(String cedulaCliente) {
        return listaAsignaciones.stream()
                .filter(cc -> cc.getCedulaCliente().equals(cedulaCliente))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClienteClase> buscarPorId(long id) {
        return listaAsignaciones.stream()
                .filter(cc -> cc.getId() != null && cc.getId() == id)
                .findFirst();
    }

    @Override
    public Optional<ClienteClase> buscarPorCedulaYClase(String cedulaCliente, int codigoClase) {
        return listaAsignaciones.stream()
                .filter(cc -> cc.getCedulaCliente().equals(cedulaCliente) && cc.getCodigoClase() == codigoClase)
                .findFirst();
    }
}
