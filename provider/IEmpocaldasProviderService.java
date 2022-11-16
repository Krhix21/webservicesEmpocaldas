/*
 * Archivo que contiene el código de
 * la clase IEmpocaldasProviderService
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
package com.datacenter.seta.sw.recaudos.terceros.empocaldas.service.provider;

import java.net.ConnectException;

import org.jboss.netty.handler.timeout.ReadTimeoutException;

import com.datacenter.core.seta.sw.core.context.ServerContext;
import com.datacenter.seta.sw.core.exceptions.ServiceException;
import com.datacenter.seta.sw.recaudos.terceros.dto.BitacoraRecaudo;
import com.datacenter.seta.sw.recaudos.terceros.dto.Recaudo;
import com.datacenter.seta.sw.recaudos.terceros.dto.Servicio;
import com.datacenter.seta.sw.recaudos.terceros.empocaldas.dto.ConsultaEmpocaldasResponse;
import com.datacenter.seta.sw.recaudos.terceros.empocaldas.dto.RecaudoEmpocaldas;

/**
 * Clase IEmpocaldasProviderService
 *
 * @author Crhistian Jimenez
 * @version 1.0 Creación de la Clase
 *
 */
public interface IEmpocaldasProviderService {

	/**
	 * @param idFactura
	 * @param context
	 * @return
	 * @throws ServiceException
	 * @throws ConnectException
	 * @throws ReadTimeoutException
	 */
	ConsultaEmpocaldasResponse consultar(String idFactura, String codigoSubproducto, ServerContext context)
			throws ServiceException, ConnectException, ReadTimeoutException;

	/**
	 * Método registrarRecaudo para permitir realizar el ingreso del pago del recaudo.
	 * 
	 * @param recaudoEmpocaldas
	 *            Con información de recaudo
	 * @param bitacora
	 *            Con información de la bitácora	 
	 * @param context
	 *            Contexto de la transacción
	 * @return Objeto con información del recaudo realizado
	 * @throws ServiceException
	 *             Si ocurre algún error durante el proceso
	 * @throws ConnectException
	 *             Si ocurre algún error durante el proceso
	 * @throws ReadTimeoutException
	 *             Si ocurre algún error durante el proceso
	 */
	public void registrarRecaudo(RecaudoEmpocaldas recaudoEmpocaldas, String codigoSubproducto, BitacoraRecaudo bitacora, ServerContext context)
			throws ServiceException, ConnectException, ReadTimeoutException;

	/**
	 * Método encargado de obtener la información relacionada con el recaudo de Empocaldas
	 * 
	 * @param recaudo
	 * 			  Con información del recaudo
	 * @param servicio
	 *            Servicio de recaudo con la información de los campos
	 * @param context
	 *            Contexto de la transacción
	 * @return Objeto con la información del recaudo de Empocaldas
	 * @throws ServiceException
	 *             Si ocurre algún error durante el proceso
	 */
	public abstract RecaudoEmpocaldas obtenerInfoRecaudo(Recaudo recaudo,
			Servicio servicio, ServerContext context) throws ServiceException;
}
