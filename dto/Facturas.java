/*
 * Archivo que contiene el código de
 * la clase Facturas
 * 
 * Copyright (c) 2011, DATACENTER S.A. Todos Los Derechos Reservados.
 *
 * NO MODIFICAR O ELIMINAR AVISOS COPYRIGHT O ESTE ENCABEZADO DEL ARCHIVO.
 *
 * Este código es software propietario, no puede redistribuirlo y / o modificarlo
 * sin previo permiso de DATACENTER S.A.

 * Póngase en contacto con DATACENTER S.A. o visite www.datacenter.com.co si necesita
 * información adicional o tiene alguna pregunta. 
 * Dat@center
 *
 * @date 18/07/2022
 */
package com.datacenter.seta.sw.recaudos.terceros.empocaldas.dto;


/**
 * Clase Facturas
 *
 * @author Crhistian Jimenez
 * @version 1.0 Creación de la Clase
 *
 */
public class Facturas {
	
	  private String IdFactura;
	  private String Concepto;
	  private double TotalFactura;
	  private double TotalIVA;
	  private double Saldo;
	  private String FechaVencimiento;
	  private Integer Orden;
	  private String TipoIdCliente;
	  private String IdCliente;
	  private String Nombre;
	  private String Apellido;
	  private String Email;
	  private String Telefono;
	  private String ValorPagado;
	  private String CampoAdicional1;
	  private String CampoAdicional2;
	  private String CampoAdicional3;
	  
	/**
	 * @return el atributo valorPagado
	 */
	public String getValorPagado() {
		return ValorPagado;
	}
	/**
	 * Actualiza el valor del atributo valorPagado
	 * @param valorPagado valor a ser actualizado
	 */
	public void setValorPagado(String valorPagado) {
		ValorPagado = valorPagado;
	}
	/**
	 * @return el atributo idFactura
	 */
	public String getIdFactura() {
		return IdFactura;
	}
	/**
	 * Actualiza el valor del atributo idFactura
	 * @param idFactura valor a ser actualizado
	 */
	public void setIdFactura(String idFactura) {
		IdFactura = idFactura;
	}
	/**
	 * @return el atributo concepto
	 */
	public String getConcepto() {
		return Concepto;
	}
	/**
	 * Actualiza el valor del atributo concepto
	 * @param concepto valor a ser actualizado
	 */
	public void setConcepto(String concepto) {
		Concepto = concepto;
	}
	/**
	 * @return el atributo totalFactura
	 */
	public double getTotalFactura() {
		return TotalFactura;
	}
	/**
	 * Actualiza el valor del atributo totalFactura
	 * @param totalFactura valor a ser actualizado
	 */
	public void setTotalFactura(double totalFactura) {
		TotalFactura = totalFactura;
	}
	/**
	 * @return el atributo totalIVA
	 */
	public double getTotalIVA() {
		return TotalIVA;
	}
	/**
	 * Actualiza el valor del atributo totalIVA
	 * @param totalIVA valor a ser actualizado
	 */
	public void setTotalIVA(double totalIVA) {
		TotalIVA = totalIVA;
	}
	/**
	 * @return el atributo saldo
	 */
	public double getSaldo() {
		return Saldo;
	}
	/**
	 * Actualiza el valor del atributo saldo
	 * @param saldo valor a ser actualizado
	 */
	public void setSaldo(double saldo) {
		Saldo = saldo;
	}
	
	/**
	 * @return el atributo orden
	 */
	public Integer getOrden() {
		return Orden;
	}
	/**
	 * Actualiza el valor del atributo orden
	 * @param orden valor a ser actualizado
	 */
	public void setOrden(Integer orden) {
		Orden = orden;
	}
	/**
	 * @return el atributo tipoIdCliente
	 */
	public String getTipoIdCliente() {
		return TipoIdCliente;
	}
	/**
	 * Actualiza el valor del atributo tipoIdCliente
	 * @param tipoIdCliente valor a ser actualizado
	 */
	public void setTipoIdCliente(String tipoIdCliente) {
		TipoIdCliente = tipoIdCliente;
	}
	/**
	 * @return el atributo idCliente
	 */
	public String getIdCliente() {
		return IdCliente;
	}
	/**
	 * Actualiza el valor del atributo idCliente
	 * @param idCliente valor a ser actualizado
	 */
	public void setIdCliente(String idCliente) {
		IdCliente = idCliente;
	}
	/**
	 * @return el atributo nombre
	 */
	public String getNombre() {
		return Nombre;
	}
	/**
	 * Actualiza el valor del atributo nombre
	 * @param nombre valor a ser actualizado
	 */
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	/**
	 * @return el atributo apellido
	 */
	public String getApellido() {
		return Apellido;
	}
	/**
	 * Actualiza el valor del atributo apellido
	 * @param apellido valor a ser actualizado
	 */
	public void setApellido(String apellido) {
		Apellido = apellido;
	}
	/**
	 * @return el atributo email
	 */
	public String getEmail() {
		return Email;
	}
	/**
	 * Actualiza el valor del atributo email
	 * @param email valor a ser actualizado
	 */
	public void setEmail(String email) {
		Email = email;
	}
	/**
	 * @return el atributo telefono
	 */
	public String getTelefono() {
		return Telefono;
	}
	/**
	 * Actualiza el valor del atributo telefono
	 * @param telefono valor a ser actualizado
	 */
	public void setTelefono(String telefono) {
		Telefono = telefono;
	}
	/**
	 * @return el atributo campoAdicional1
	 */
	public String getCampoAdicional1() {
		return CampoAdicional1;
	}
	/**
	 * Actualiza el valor del atributo campoAdicional1
	 * @param campoAdicional1 valor a ser actualizado
	 */
	public void setCampoAdicional1(String campoAdicional1) {
		CampoAdicional1 = campoAdicional1;
	}
	/**
	 * @return el atributo campoAdicional2
	 */
	public String getCampoAdicional2() {
		return CampoAdicional2;
	}
	/**
	 * Actualiza el valor del atributo campoAdicional2
	 * @param campoAdicional2 valor a ser actualizado
	 */
	public void setCampoAdicional2(String campoAdicional2) {
		CampoAdicional2 = campoAdicional2;
	}
	/**
	 * @return el atributo campoAdicional3
	 */
	public String getCampoAdicional3() {
		return CampoAdicional3;
	}
	/**
	 * Actualiza el valor del atributo campoAdicional3
	 * @param campoAdicional3 valor a ser actualizado
	 */
	public void setCampoAdicional3(String campoAdicional3) {
		CampoAdicional3 = campoAdicional3;
	}
	/**
	 * @return el atributo fechaVencimiento
	 */
	public String getFechaVencimiento() {
		return FechaVencimiento;
	}
	/**
	 * Actualiza el valor del atributo fechaVencimiento
	 * @param fechaVencimiento valor a ser actualizado
	 */
	public void setFechaVencimiento(String fechaVencimiento) {
		FechaVencimiento = fechaVencimiento;
	}
	
	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "FacturaEmpocaldasResponse [IdFactura=" + IdFactura
                + ", Concepto=" + Concepto + ", TotalFactura=" + TotalFactura
                + ", TotalIva=" + TotalIVA + ", Saldo=" + Saldo
                + ", FechaVencimiento=" + FechaVencimiento + ", TipoIdCliente="
                + TipoIdCliente + ", IdCliente=" + IdCliente + ", Nombre="
                + Nombre + ", Apellido=" + Apellido + ", Email=" + Email
                + ", Telefono=" + Telefono + ", CampoAdicional1="
                + CampoAdicional1 + ", CampoAdicional2=" + CampoAdicional2
                + ", CampoAdicional3=" + CampoAdicional3 + "]";
    } 

}
