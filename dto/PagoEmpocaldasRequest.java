/*
 * Archivo que contiene el código de
 * la clase PagoEmpocaldasRequest
 * 
 * Copyright (c) CODESA. Todos Los Derechos Reservados.
 *
 * NO MODIFICAR O ELIMINAR AVISOS COPYRIGHT O ESTE ENCABEZADO DEL ARCHIVO.
 *
 * Este código es software propietario, no puede redistribuirlo y / o modificarlo
 * sin previo permiso de CODESA.

 * Póngase en contacto con CODESA. o visite www.codesa.com.co si necesita
 * información adicional o tiene alguna pregunta. 
 * Codesa
 *
 * @date 22/07/2022
 */
package com.datacenter.seta.sw.recaudos.terceros.empocaldas.dto;

import java.util.ArrayList;

/**
 * Clase PagoEmpocaldasRequest
 *
 * @author Crhistian Jimenez
 * @version 1.0 Creación de la Clase
 *
 */
public class PagoEmpocaldasRequest {
	
	private Integer IdComercio;
	private String Password;
	private String IdCliente;
	private String IdFactura;
	ArrayList<Facturas> Facturas = new ArrayList<Facturas>();
	private Integer EstadoPago;
	private Integer ValorTotalPagado;
	private String FechaPago;
	private Integer IdPago;
	private Integer CodigoBanco;
	/**
	 * @return el atributo idComercio
	 */
	public Integer getIdComercio() {
		return IdComercio;
	}
	/**
	 * Actualiza el valor del atributo idComercio
	 * @param idComercio valor a ser actualizado
	 */
	public void setIdComercio(Integer idComercio) {
		IdComercio = idComercio;
	}
	/**
	 * @return el atributo password
	 */
	public String getPassword() {
		return Password;
	}
	/**
	 * Actualiza el valor del atributo password
	 * @param password valor a ser actualizado
	 */
	public void setPassword(String password) {
		Password = password;
	}

	/**
	 * @return el atributo facturas
	 */
	public ArrayList<Facturas> getFacturas() {
		return Facturas;
	}
	/**
	 * Actualiza el valor del atributo facturas
	 * @param facturas valor a ser actualizado
	 */
	public void setFacturas(ArrayList<Facturas> facturas) {
		Facturas = facturas;
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
	 * @return el atributo estadoPago
	 */
	public Integer getEstadoPago() {
		return EstadoPago;
	}
	/**
	 * Actualiza el valor del atributo estadoPago
	 * @param estadoPago valor a ser actualizado
	 */
	public void setEstadoPago(Integer estadoPago) {
		EstadoPago = estadoPago;
	}
	/**
	 * @return el atributo valorTotalPagado
	 */
	public Integer getValorTotalPagado() {
		return ValorTotalPagado;
	}
	/**
	 * Actualiza el valor del atributo valorTotalPagado
	 * @param valorTotalPagado valor a ser actualizado
	 */
	public void setValorTotalPagado(Integer valorTotalPagado) {
		ValorTotalPagado = valorTotalPagado;
	}
	/**
	 * @return el atributo fechaPago
	 */
	public String getFechaPago() {
		return FechaPago;
	}
	/**
	 * Actualiza el valor del atributo fechaPago
	 * @param fechaPago valor a ser actualizado
	 */
	public void setFechaPago(String fechaPago) {
		FechaPago = fechaPago;
	}
	/**
	 * @return el atributo idPago
	 */
	public Integer getIdPago() {
		return IdPago;
	}
	/**
	 * Actualiza el valor del atributo idPago
	 * @param idPago valor a ser actualizado
	 */
	public void setIdPago(Integer idPago) {
		IdPago = idPago;
	}
	/**
	 * @return el atributo codigoBanco
	 */
	public Integer getCodigoBanco() {
		return CodigoBanco;
	}
	/**
	 * Actualiza el valor del atributo codigoBanco
	 * @param codigoBanco valor a ser actualizado
	 */
	public void setCodigoBanco(Integer codigoBanco) {
		CodigoBanco = codigoBanco;
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
}
