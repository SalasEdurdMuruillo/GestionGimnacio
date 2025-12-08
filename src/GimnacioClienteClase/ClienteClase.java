/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GimnacioClienteClase;

import java.time.LocalDate;

/**
 *
 * @author Eduard Salas Murillo
 */
public class ClienteClase {

    private Long id;
    private String cedulaCliente;
    private int codigoClase;
    private LocalDate fechaInscripcion;

    public ClienteClase() {
    }

    public ClienteClase(String cedulaCliente, int codigoClase) {
        this.cedulaCliente = cedulaCliente;
        this.codigoClase = codigoClase;

        this.fechaInscripcion = LocalDate.now();
    }

    public ClienteClase(Long id, String cedulaCliente, int codigoClase, LocalDate fechaInscripcion) {
        this.id = id;
        this.cedulaCliente = cedulaCliente;
        this.codigoClase = codigoClase;
        this.fechaInscripcion = fechaInscripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(String cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public int getCodigoClase() {
        return codigoClase;
    }

    public void setCodigoClase(int codigoClase) {
        this.codigoClase = codigoClase;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
}
