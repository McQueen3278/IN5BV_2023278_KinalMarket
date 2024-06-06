package org.harolrodriguez.bean;

import java.time.LocalDate;



public class Compras {
    private int compraId;
    private LocalDate fechaCompra;
    private String totalCompra;

    public Compras() {
        
    }

    public Compras(int compraId, LocalDate fechaCompra, String totalCompra) {
        this.compraId = compraId;
        this.fechaCompra = fechaCompra;
        this.totalCompra = totalCompra;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(String totalCompra) {
        this.totalCompra = totalCompra;
    }

    @Override
    public String toString() {
        return " - " + getCompraId();
    }

    
    
    
   
    
    
}
