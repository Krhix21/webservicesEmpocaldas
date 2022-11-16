/*
 * Archivo que contiene el código de
 * la clase ConsultaEmpocaldasResponse
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

import java.util.ArrayList;

/**
 * Clase ConsultaEmpocaldasResponse
 *
 * @author Crhistian Jimenez
 * @version 1.0 Creación de la Clase
 *
 */
public class ConsultaEmpocaldasResponse {
	
	  private Integer CodRespuesta;
	  private String Descripcion;
	  ArrayList<Facturas> Facturas = new ArrayList<Facturas>();
	  private String CodServicioPpal;
	  private Integer CambiarClave;
	
	/**
	 * @return el atributo codRespuesta
	 */
	public Integer getCodRespuesta() {
		return CodRespuesta;
	}
	/**
	 * Actualiza el valor del atributo codRespuesta
	 * @param codRespuesta valor a ser actualizado
	 */
	public void setCodRespuesta(Integer codRespuesta) {
		CodRespuesta = codRespuesta;
	}
	/**
	 * @return el atributo descripcion
	 */
	public String getDescripcion() {
		return Descripcion;
	}
	/**
	 * Actualiza el valor del atributo descripcion
	 * @param descripcion valor a ser actualizado
	 */
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
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
	 * @return el atributo codServicioPpal
	 */
	public String getCodServicioPpal() {
		return CodServicioPpal;
	}
	/**
	 * Actualiza el valor del atributo codServicioPpal
	 * @param codServicioPpal valor a ser actualizado
	 */
	public void setCodServicioPpal(String codServicioPpal) {
		CodServicioPpal = codServicioPpal;
	}
	/**
	 * @return el atributo cambiarClave
	 */
	public Integer getCambiarClave() {
		return CambiarClave;
	}
	/**
	 * Actualiza el valor del atributo cambiarClave
	 * @param cambiarClave valor a ser actualizado
	 */
	public void setCambiarClave(Integer cambiarClave) {
		CambiarClave = cambiarClave;
	}
	
	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ConsultaEmpocaldasResponse [CodRespuesta=" + CodRespuesta
                + ", Descripcion=" + Descripcion + ", Facturas=" + Facturas
                + ", CodServicioPpal=" + CodServicioPpal + ", CambiarClave="
                + CambiarClave + "]";
    }

}
