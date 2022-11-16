/*
 * Archivo que contiene el código de
 * la clase PagoEmpocaldasResponse
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

/**
 * Clase PagoEmpocaldasResponse
 *
 * @author Crhistian Jimenez
 * @version 1.0 Creación de la Clase
 *
 */
public class PagoEmpocaldasResponse {
	
	private Integer CodEstado;
	private String DescripcionEstado;
	/**
	 * @return el atributo codEstado
	 */
	public Integer getCodEstado() {
		return CodEstado;
	}
	/**
	 * Actualiza el valor del atributo codEstado
	 * @param codEstado valor a ser actualizado
	 */
	public void setCodEstado(Integer codEstado) {
		CodEstado = codEstado;
	}
	/**
	 * @return el atributo descripcionEstado
	 */
	public String getDescripcionEstado() {
		return DescripcionEstado;
	}
	/**
	 * Actualiza el valor del atributo descripcionEstado
	 * @param descripcionEstado valor a ser actualizado
	 */
	public void setDescripcionEstado(String descripcionEstado) {
		DescripcionEstado = descripcionEstado;
	}
	
	
}
