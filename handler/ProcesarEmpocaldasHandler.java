/*
 * Archivo que contiene el código de
 * la clase ProcesarCodigoBarrasEmpocaldas
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

import java.util.List;

import com.datacenter.core.seta.sw.core.context.ServerContext;
import com.datacenter.seta.sw.core.api.annotations.Code;
import com.datacenter.seta.sw.core.dto.SubProducto;
import com.datacenter.seta.sw.core.exceptions.HandlerException;
import com.datacenter.seta.sw.core.exceptions.ServiceException;
import com.datacenter.seta.sw.core.message.BaseMessage;
import com.datacenter.seta.sw.core.utils.ErrorUtils;
import com.datacenter.seta.sw.core.utils.StringUtils;
import com.datacenter.seta.sw.products.general.services.IProductoService;
import com.datacenter.seta.sw.recaudos.terceros.distribuidores.utils.ConstantesRecaudosTercerosDistribuidor;
import com.datacenter.seta.sw.recaudos.terceros.dto.Campo;
import com.datacenter.seta.sw.recaudos.terceros.dto.Recaudo;
import com.datacenter.seta.sw.recaudos.terceros.dto.Servicio;
import com.datacenter.seta.sw.recaudos.terceros.empocaldas.dto.ConsultaEmpocaldasResponse;
import com.datacenter.seta.sw.recaudos.terceros.empocaldas.service.provider.IEmpocaldasProviderService;
import com.datacenter.seta.sw.recaudos.terceros.handler.BaseProcesarRecaudoHandler;
import com.datacenter.seta.sw.recaudos.terceros.message.RecaudoMessage;
import com.datacenter.seta.sw.recaudos.terceros.services.IRecaudoCodigoBarrasService;
import com.datacenter.seta.sw.recaudos.terceros.services.IRecaudoService;
import com.datacenter.seta.sw.recaudos.terceros.utils.ConstantesRecaudosTerceros;

/**
 * Clase ProcesarEmpocaldas
 * 
 * @author Crhistian Jimenez 
 * @version 1.0 Creación de la Clase
 * 
 */
@Code(number = "0875003")
public class ProcesarEmpocaldasHandler extends BaseProcesarRecaudoHandler {

	/**
	 * Referencia hacia los servicios de recaudos.
	 */
	private IRecaudoService recaudoService;

	/**
	 * Referencia hacia los servicios de recaudos X código de barras.
	 */
	private IRecaudoCodigoBarrasService recaudoCodigoBarrasService;
	
	/**
	 * Referencia hacia los servicios de empocaldasProviderService.
	 */
	private IEmpocaldasProviderService empocaldasProviderService;
	
	/**
	 * Referencia al servicio de productos.
	 */
	private IProductoService productoService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.datacenter.seta.sw.recaudos.terceros.handler.BaseProcesarRecaudoHandler
	 * #onProcess(com.datacenter.seta.sw.core.message.BaseMessage,
	 * com.datacenter.core.seta.sw.core.context.ServerContext)
	 */
	@Override
	public BaseMessage onProcess(BaseMessage message, ServerContext context)
			throws HandlerException {
		RecaudoMessage recaudoMessage = (RecaudoMessage) message;
		List<Servicio> listadoServicios = null;
		String idFactura = null;
		String seccional = null;
		try {
			/** Se procesa el código de barras */
			listadoServicios = recaudoCodigoBarrasService.procesaCodigoBarras(
					recaudoMessage.getRecaudo(), context);
			recaudoMessage.setEstadoRespuesta(BaseMessage.RESPUESTA_EXITOSA);

			Recaudo recaudoNew = new Recaudo();

			recaudoNew = recaudoService.consultarRecaudoPorId(
					recaudoMessage.getRecaudo(), context);

			SubProducto subproducto = new SubProducto();
			subproducto = productoService.consultaSubproductoXId(recaudoNew
					.getSubProducto().getId(), context);
			String codigoSubproducto = subproducto.getCodigo();

			idFactura = listadoServicios
					.get(0)
					.getCampoPorTipoCampo(
							ConstantesRecaudosTerceros.TIPO_CAMPO_REFERENCIA)
					.getValor();
			seccional = listadoServicios
					.get(0)
					.getCampoPorTipoCampo(
							ConstantesRecaudosTerceros.TIPO_CAMPO_VALIDA_PUNTO)
					.getValor();

			idFactura = idFactura + seccional;
			ConsultaEmpocaldasResponse respuesta = new ConsultaEmpocaldasResponse();
			respuesta = empocaldasProviderService.consultar(idFactura,
					codigoSubproducto, context);
			
			listadoServicios
					.get(0)
					.getCampoPorTipoCampo(
							ConstantesRecaudosTerceros.TIPO_CAMPO_SUSCRIPTOR)
					.setValor(respuesta.getFacturas().get(0).getIdCliente());
			
			listadoServicios
					.get(0)
					.getCampoPorTipoCampo(
							ConstantesRecaudosTerceros.TIPO_CAMPO_REFERENCIA)
					.setValor(idFactura);
			
			listadoServicios
					.get(0)
					.getCampoPorTipoCampo(
							ConstantesRecaudosTerceros.TIPO_CAMPO_VALOR)
					.setValor(
							StringUtils.doubleToStringNoDecimal(respuesta.getFacturas().get(0)
									.getTotalFactura()));
			
			listadoServicios
					.get(0)
					.getCampoPorTipoCampo(
							ConstantesRecaudosTerceros.TIPO_CAMPO_FECHA_VENCIMIENTO)
					.setValor(
							respuesta.getFacturas().get(0)
									.getFechaVencimiento());
			listadoServicios
					.get(0)
					.getCampoPorTipoCampo(
							ConstantesRecaudosTerceros.TIPO_CAMPO_OTRO)
					.setValor(
							respuesta.getFacturas().get(0).getCampoAdicional3());
			
			recaudoMessage.getRecaudo().setListadoServicios(listadoServicios);
			return recaudoMessage;
		} catch (ServiceException se) {
			recaudoMessage.setEstadoRespuesta(BaseMessage.RESPEUSTA_FALLIDA);
			throw new HandlerException(
					"Traza: " + ErrorUtils.getStackTrace(se), se.getError());
		} catch (Exception ex) {
			recaudoMessage.setEstadoRespuesta(BaseMessage.RESPEUSTA_FALLIDA);
			throw new HandlerException("Traza:" + ErrorUtils.getStackTrace(ex),
					getCodeErrors().getError("000000"));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.datacenter.seta.sw.recaudos.terceros.handler.BaseProcesarRecaudoHandler
	 * #getValoresProcesar(com.datacenter.seta.sw.core.message.BaseMessage,
	 * com.datacenter.core.seta.sw.core.context.ServerContext)
	 */
	@Override
	public BaseMessage getValoresProcesar(BaseMessage message,
			ServerContext context) throws HandlerException {
		RecaudoMessage recaudoMessage = (RecaudoMessage) message;
		Recaudo recaudo;

		try {
			recaudo = procesarCamposCodigoBarras(recaudoMessage.getRecaudo(),
					context);

			recaudoMessage.setRecaudo(recaudo);
			recaudoMessage.setEstadoRespuesta(BaseMessage.RESPUESTA_EXITOSA);
			return recaudoMessage;
		} catch (ServiceException se) {
			recaudoMessage.setEstadoRespuesta(BaseMessage.RESPEUSTA_FALLIDA);
			throw new HandlerException(
					"Traza: " + ErrorUtils.getStackTrace(se), se.getError());
		} catch (Exception ex) {
			recaudoMessage.setEstadoRespuesta(BaseMessage.RESPEUSTA_FALLIDA);
			throw new HandlerException("Traza:" + ErrorUtils.getStackTrace(ex),
					getCodeErrors().getError("000000"));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.datacenter.seta.sw.recaudos.terceros.handler.BaseProcesarRecaudoHandler
	 * #procesarCampos(com.datacenter.seta.sw.recaudos.terceros.dto.Recaudo,
	 * com.datacenter.core.seta.sw.core.context.ServerContext)
	 */
	@Override
	public Recaudo procesarCampos(Recaudo recaudo, ServerContext context)
			throws ServiceException {
		recaudo = procesarCamposCodigoBarras(recaudo, context);
		return recaudo;
	}

	/**
	 * Método que procesa el código de barras de un recaudo
	 * 
	 * @param recaudo
	 *            Información del recaudo
	 * @throws ServiceException
	 *             En caso de presentarse un inconveniente
	 */
	public Recaudo procesarCamposCodigoBarras(Recaudo recaudo,
			ServerContext context) throws ServiceException {
		List<Servicio> listadoServicios = null;
		boolean encontrado = false;

		/****************************************************************
		 * Se agregan los parámetros que son enviados a través de la trama del
		 * WS distribuidores
		 ***************************************************************/
		for (Servicio servicioTemp : recaudo.getListadoServicios()) {
			servicioTemp.setCodigoBarras(recaudo.getReferencia());
			servicioTemp.setValorTotal(0.0);
		}

		/****************************************************************
		 * Se procesa el código de barras
		 ***************************************************************/
		listadoServicios = recaudoCodigoBarrasService.procesaCodigoBarras(
				recaudo, context);

		/*************************************************************
		 * Se busca el valor.
		 *************************************************************/
		encontrado = false;
		for (Campo campo : listadoServicios.get(0).getListadoCampos()) {
			if (campo
					.getTipoCampo()
					.getCodigo()
					.equals(ConstantesRecaudosTercerosDistribuidor.PARAMETRO_VALOR)) {
				recaudo.setValorTotal(Double.parseDouble(campo.getValor()));
				encontrado = true;
			}
		}
		if (!encontrado) {
			throw new ServiceException(
					"No se tiene configurado ningún campo Valor para el recaudo.",
					getCodeErrors().getError("085002"));
		}

		recaudo.setListadoServicios(listadoServicios);

		return recaudo;
	}

	/**
	 * Actualiza el valor del atributo recaudoService
	 * 
	 * @param recaudoService
	 *            valor a ser actualizado
	 */
	public void setRecaudoService(IRecaudoService recaudoService) {
		this.recaudoService = recaudoService;
	}

	/**
	 * @return el atributo recaudoService
	 */
	public IRecaudoService getRecaudoService() {
		return recaudoService;
	}

	/**
	 * Retorna el valor de recaudoCodigoBarrasService
	 * 
	 * @return valor de recaudoCodigoBarrasService
	 */
	public IRecaudoCodigoBarrasService getRecaudoCodigoBarrasService() {
		return recaudoCodigoBarrasService;
	}

	/**
	 * Actualiza el valor de recaudoCodigoBarrasService
	 * 
	 * @param recaudoCodigoBarrasService
	 *            - Nuevo valor para el campo recaudoCodigoBarrasService
	 */
	public void setRecaudoCodigoBarrasService(
			IRecaudoCodigoBarrasService recaudoCodigoBarrasService) {
		this.recaudoCodigoBarrasService = recaudoCodigoBarrasService;
	}

	/**
	 * @return el atributo empocaldasProviderService
	 */
	public IEmpocaldasProviderService getEmpocaldasProviderService() {
		return empocaldasProviderService;
	}

	/**
	 * Actualiza el valor del atributo empocaldasProviderService
	 * @param empocaldasProviderService valor a ser actualizado
	 */
	public void setEmpocaldasProviderService(
			IEmpocaldasProviderService empocaldasProviderService) {
		this.empocaldasProviderService = empocaldasProviderService;
	}

	/**
	 * @return el atributo productoService
	 */
	public IProductoService getProductoService() {
		return productoService;
	}

	/**
	 * Actualiza el valor del atributo productoService
	 * @param productoService valor a ser actualizado
	 */
	public void setProductoService(IProductoService productoService) {
		this.productoService = productoService;
	}
	

}
