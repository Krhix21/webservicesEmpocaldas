/*
 * Archivo que contiene el código de
 * la clase ConsultaEmpocaldasRequest
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
 * @date 19/07/2022
 */
package com.datacenter.seta.sw.recaudos.terceros.empocaldas.dto;

/**
 * Clase ConsultaEmpocaldasRequest
 *
 * @author Crhistian Jimenez
 * @version 1.0 Creación de la Clase
 *
 */
public class ConsultaEmpocaldasRequest {
	
	  private Integer IdComercio;
	  private String Password;
	  private String IdFactura;
	  private String IdCliente;
	  private String ClaveConsulta;
	  private String ForzarParamConsulta;
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
	 * @return el atributo claveConsulta
	 */
	public String getClaveConsulta() {
		return ClaveConsulta;
	}
	/**
	 * Actualiza el valor del atributo claveConsulta
	 * @param claveConsulta valor a ser actualizado
	 */
	public void setClaveConsulta(String claveConsulta) {
		ClaveConsulta = claveConsulta;
	}
	/**
	 * @return el atributo forzarParamConsulta
	 */
	public String getForzarParamConsulta() {
		return ForzarParamConsulta;
	}
	/**
	 * Actualiza el valor del atributo forzarParamConsulta
	 * @param forzarParamConsulta valor a ser actualizado
	 */
	public void setForzarParamConsulta(String forzarParamConsulta) {
		ForzarParamConsulta = forzarParamConsulta;
	}
	  
	

}
