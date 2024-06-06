package org.harolrodriguez.bean;

import java.sql.Time;
import java.time.LocalDate;


public class Facturas {
    private int facturaId;
    private int CodigoCliente;
    private int empleadoId;
    private LocalDate facha;
    private  Time hora ;
    private Double total;

    public Facturas() {
        
    }

    public Facturas(int facturaId, int CodigoCliente, int empleadoId, LocalDate facha, Time hora, Double total) {
        this.facturaId = facturaId;
        this.CodigoCliente = CodigoCliente;
        this.empleadoId = empleadoId;
        this.facha = facha;
        this.hora = hora;
        this.total = total;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public int getCodigoCliente() {
        return CodigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.CodigoCliente = codigoCliente;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public LocalDate getFacha() {
        return facha;
    }

    public void setFacha(LocalDate facha) {
        this.facha = facha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return " - " + getFacturaId();
    }

    
    
}
