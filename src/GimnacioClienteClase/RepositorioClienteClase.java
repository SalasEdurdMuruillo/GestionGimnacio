/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package GimnacioClienteClase;

import GimnacioClienteClase.ClienteClase;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Eduard Salas Murillo
 */
public interface RepositorioClienteClase {

    long crear(ClienteClase clienteClase);

    List<ClienteClase> buscarPorCedulaCliente(String cedulaCliente);

    Optional<ClienteClase> buscarPorId(long id);

    Optional<ClienteClase> buscarPorCedulaYClase(String cedulaCliente, int codigoClase);

}
