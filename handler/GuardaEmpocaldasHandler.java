/*
 * Archivo que contiene el código de
 * la clase GuardaEmpocaldasHandler
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
package com.datacenter.seta.sw.recaudos.terceros.empocaldas.handler;

import java.net.ConnectException;
import java.util.List;

import com.datacenter.core.seta.sw.core.context.ServerContext;
import com.datacenter.seta.sw.core.api.annotations.Code;
import com.datacenter.seta.sw.core.dto.SubProducto;
import com.datacenter.seta.sw.core.exceptions.ServiceException;
import com.datacenter.seta.sw.core.message.BaseMessage;
import com.datacenter.seta.sw.recaudos.terceros.dto.BitacoraRecaudo;
import com.datacenter.seta.sw.recaudos.terceros.dto.Recaudo;
import com.datacenter.seta.sw.recaudos.terceros.dto.Servicio;
import com.datacenter.seta.sw.recaudos.terceros.empocaldas.dto.RecaudoEmpocaldas;
import com.datacenter.seta.sw.recaudos.terceros.empocaldas.service.provider.IEmpocaldasProviderService;
import com.datacenter.seta.sw.recaudos.terceros.handler.BaseGuardarRecaudo;
import com.datacenter.seta.sw.recaudos.terceros.message.RecaudoMessage;
import com.datacenter.seta.sw.recaudos.terceros.services.IRecaudoCodigoBarrasService;
import com.datacenter.seta.sw.terceros.service.ITerceroService;

/**
 * Clase GuardaEmpocaldasHandler
 * 
 * @author Crhistian Jimenez
 * @version 1.0 Creación de la Clase
 * 
 */
@Code(number = "0875004")
public class GuardaEmpocaldasHandler extends BaseGuardarRecaudo{
	
	/**
	 * Referencia hacia los servicios de empocaldasProviderService.
	 */
	private IEmpocaldasProviderService empocaldasProviderService;
	
	/**
	 * Referencia hacia los servicios de recaudos por códigos de barras.
	 */
	private IRecaudoCodigoBarrasService recaudoCodigoBarrasService;
	
	/**
	 * Referencia al servicio terceroService
	 */
	private ITerceroService terceroService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.datacenter.seta.sw.recaudos.terceros.handler.BaseGuardarRecaudo#
	 * transmitirRecaudoTercero
	 * (com.datacenter.seta.sw.recaudos.terceros.dto.Recaudo,
	 * com.datacenter.seta.sw.recaudos.terceros.dto.BitacoraRecaudo,
	 * com.datacenter.core.seta.sw.core.context.ServerContext)
	 */
	@Override
	public void transmitirRecaudoTercero(Recaudo recaudo, BitacoraRecaudo bitacora, ServerContext context)
			throws ServiceException, ConnectException {
		
		Servicio servicioEmpocaldas = recaudo.getListadoServicios().get(0);
		RecaudoEmpocaldas recaudoEmpocaldas = new RecaudoEmpocaldas();
		
		try {
			SubProducto subproducto = new SubProducto();

			subproducto = productoService.consultaSubproductoXId(recaudo.getSubProducto().getId(), context);
			String codigoSubproducto = subproducto.getCodigo();
			/****************************************************************
			 * Se validan los campos configurados para el recaudo y se obtiene
			 * su valor en el objeto recaudoEmpocaldas
			 ***************************************************************/
			recaudoEmpocaldas = empocaldasProviderService.obtenerInfoRecaudo(
					recaudo, servicioEmpocaldas, context);
			
			/******************************************
			 * Se registra la transacción en Empocaldas
			 ******************************************/
			empocaldasProviderService.registrarRecaudo(recaudoEmpocaldas, codigoSubproducto, bitacora, context);
			
			recaudo.setRecaudadoOffline(false);
		} catch (ConnectException ce) {
			throw ce;
		} catch (ServiceException se) {
			throw se;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.datacenter.seta.sw.recaudos.terceros.handler.BaseGuardarRecaudo#
	 * buscarCampos(com.datacenter.seta.sw.core.message.BaseMessage,
	 * com.datacenter.core.seta.sw.core.context.ServerContext)
	 */
	@Override
	public Recaudo buscarCampos(BaseMessage message, ServerContext context)
			throws ServiceException {

		RecaudoMessage recaudoMessage = (RecaudoMessage) message;
		return buscarCamposCodigoBarras(recaudoMessage.getRecaudo(), context);
	}
	
	/**
	 * Método que procesa el código de barras de un recaudo
	 * 
	 * @param recaudo
	 *            Información del recaudo
	 * @throws ServiceException
	 *             En caso de presentarse un inconveniente
	 */
	public Recaudo buscarCamposCodigoBarras(Recaudo recaudo,
			ServerContext context) throws ServiceException {
		List<Servicio> listadoServicios = null;

		recaudo.getListadoServicios().get(0)
				.setCodigoBarras(recaudo.getReferencia());
		recaudo.getListadoServicios().get(0).setValorTotal(0.0);

		/** Se procesa el código de barras */
		listadoServicios = recaudoCodigoBarrasService.procesaCodigoBarras(
				recaudo, context);

		listadoServicios.get(0).setCodigoBarras(recaudo.getReferencia());

		recaudo.setListadoServicios(listadoServicios);

		return recaudo;
	}

	/**
	 * @param empocaldasProviderService the empocaldasProviderService to set
	 */
	public void setEmpocaldasProviderService(IEmpocaldasProviderService empocaldasProviderService) {
		this.empocaldasProviderService = empocaldasProviderService;
	}

	/**
	 * @return the empocaldasProviderService
	 */
	public IEmpocaldasProviderService getEmpocaldasProviderService() {
		return empocaldasProviderService;
	}

	/**
	 * @return the recaudoCodigoBarrasService
	 */
	public IRecaudoCodigoBarrasService getRecaudoCodigoBarrasService() {
		return recaudoCodigoBarrasService;
	}

	/**
	 * @param recaudoCodigoBarrasService the recaudoCodigoBarrasService to set
	 */
	public void setRecaudoCodigoBarrasService(
			IRecaudoCodigoBarrasService recaudoCodigoBarrasService) {
		this.recaudoCodigoBarrasService = recaudoCodigoBarrasService;
	}

	/**
	 * @return el atributo terceroService
	 */
	public ITerceroService getTerceroService() {
		return terceroService;
	}

	/**
	 * Actualiza el valor del atributo terceroService
	 * @param terceroService valor a ser actualizado
	 */
	public void setTerceroService(ITerceroService terceroService) {
		this.terceroService = terceroService;
	}
	
	
}
