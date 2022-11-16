/*
 * Archivo que contiene el código de
 * la clase EmpocaldasProviderService
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
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import org.jboss.netty.handler.timeout.ReadTimeoutException;

import com.datacenter.core.seta.sw.core.context.ServerContext;
import com.datacenter.core.seta.sw.core.services.provider.AbstractProviderService;
import com.datacenter.core.util.ErrorUtils;
import com.datacenter.seta.sw.core.exceptions.ServiceException;
import com.datacenter.seta.sw.core.utils.FechaUtils;
import com.datacenter.seta.sw.products.general.dto.TransactionControl;
import com.datacenter.seta.sw.protocols.rest.Rest;
import com.datacenter.seta.sw.protocols.rest.RestResponseMessage;
import com.datacenter.seta.sw.protocols.tls.TLSSocketConnectionFactory;
import com.datacenter.seta.sw.recaudos.terceros.dto.BitacoraRecaudo;
import com.datacenter.seta.sw.recaudos.terceros.dto.Campo;
import com.datacenter.seta.sw.recaudos.terceros.dto.Recaudo;
import com.datacenter.seta.sw.recaudos.terceros.dto.Servicio;
import com.datacenter.seta.sw.recaudos.terceros.empocaldas.dto.ConsultaEmpocaldasRequest;
import com.datacenter.seta.sw.recaudos.terceros.empocaldas.dto.ConsultaEmpocaldasResponse;
import com.datacenter.seta.sw.recaudos.terceros.empocaldas.dto.Facturas;
import com.datacenter.seta.sw.recaudos.terceros.empocaldas.dto.PagoEmpocaldasRequest;
import com.datacenter.seta.sw.recaudos.terceros.empocaldas.dto.PagoEmpocaldasResponse;
import com.datacenter.seta.sw.recaudos.terceros.empocaldas.dto.RecaudoEmpocaldas;
import com.datacenter.seta.sw.recaudos.terceros.utils.ConstantesRecaudosTerceros;
import com.google.gson.Gson;

/**
 * Clase EmpocaldasProviderService
 * 
 * @author Crhistian Jimenez
 * @version 1.0 Creación de la Clase
 * 
 */
public class EmpocaldasProviderService extends AbstractProviderService
		implements IEmpocaldasProviderService {
	/*
	 * (non-Javadoc)
	 * @see com.datacenter.seta.sw.recaudos.terceros.empocaldas.service.provider.IEmpocaldasProviderService#consultar(java.lang.String, com.datacenter.core.seta.sw.core.context.ServerContext)
	 */
	@Override
	public ConsultaEmpocaldasResponse consultar(String idFactura, String codigoSubproducto,
			ServerContext context) throws ServiceException, ConnectException,
			ReadTimeoutException {

		Rest rest = new Rest();
		String tramaEnvioJson = null;
		String tramaRecepcionJson = null;
		RestResponseMessage respuesta = null;
		ConsultaEmpocaldasRequest request = new ConsultaEmpocaldasRequest();
		ConsultaEmpocaldasResponse respuestaEmpocaldas = null;

		try {
			String url = EmpocaldasProperties
					.getStringProperty(EmpocaldasProperties.URL_WS_EMPOCALDAS);
			String metodoConsulta = EmpocaldasProperties
					.getStringProperty(EmpocaldasProperties.METODO_CONSULTAR);
			Integer timeout = EmpocaldasProperties
					.getIntegerProperty(EmpocaldasProperties.PROPIEDAD_TIMEOUT);

			/***************************************
			 * Se evalua si es Empocaldas o Empocaldas otros conceptos
			 ***************************************/
			if (codigoSubproducto.equals(EmpocaldasProperties.CODIGO_SUBPRODUCTO_EMPOCALDAS)){
				request.setIdComercio(EmpocaldasProperties
						.getIntegerProperty(EmpocaldasProperties.IDCOMERCIO_EMPOCALDAS));
				request.setPassword(EmpocaldasProperties
						.getStringProperty(EmpocaldasProperties.PASSWORD_EMPOCALDAS));
			}else{
				request.setIdComercio(EmpocaldasProperties
						.getIntegerProperty(EmpocaldasProperties.IDCOMERCIO_EMPOCALDAS_OTROS_CONCEPTOS));
				request.setPassword(EmpocaldasProperties
						.getStringProperty(EmpocaldasProperties.PASSWORD_EMPOCALDAS_OTROS_CONCEPTOS));
			}
			
			request.setIdFactura(idFactura);
			
			/***************************************
			 * Se convierte la peticion en JSON
			 ***************************************/
			Gson gsonRequest = new Gson();
			tramaEnvioJson = gsonRequest.toJson(request,
					ConsultaEmpocaldasRequest.class);

			/***************************************
			 * Se inicializa la conexion
			 ***************************************/
			URL urlPago = new URL(url + metodoConsulta);

			log.info("********************************************************");
			log.info("Clase: EmpocaldasProviderService");
			log.info("Metodo: Consulta");
			log.info("URL: " + url + metodoConsulta);
			log.info("timeOut: " + timeout);
			log.info("Peticion JSON: " + tramaEnvioJson);
			log.info("********************************************************");

			HttpsURLConnection connection = (HttpsURLConnection) urlPago
					.openConnection();
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			connection.setSSLSocketFactory(new TLSSocketConnectionFactory());
			
			respuesta = rest.sendConSSL(connection, tramaEnvioJson);

			tramaRecepcionJson = respuesta.getMensajeRespuesta();
			
			/**
			 * Se realiza replace ya que al momento de realizar pruebas la fecha de vencimiento
			 * estaba llegando con el formato  erroneo DD\/MM\/YYYY y deberia llegar con el 
			 * formato DD\MM\YYYY
			 */
			tramaRecepcionJson = tramaRecepcionJson.replace("\\", "");
	
			log.info("********************************************************");
			log.info("Respuesta JSON: " + tramaRecepcionJson);
			log.info("********************************************************");

			/********************************************
			 * Se convierte la respuesta JSON a Objeto
			 *******************************************/
			Gson gsonResponse = new Gson();
			respuestaEmpocaldas = gsonResponse.fromJson(tramaRecepcionJson,
					ConsultaEmpocaldasResponse.class);

			if (!EmpocaldasProperties.RESPUESTA_OK_EMPOCALDAS
					.equals(respuestaEmpocaldas.getCodRespuesta().toString())) {
				throw new ServiceException(
						"Ocurrió un error consultando el Recaudo Empocaldas: "
								+ respuestaEmpocaldas.getCodRespuesta()
								+ " - descripción error: "
								+ respuestaEmpocaldas.getDescripcion(),
						getCodeErrors().getError("087506",
								respuestaEmpocaldas.getDescripcion()));
			}
			
			return respuestaEmpocaldas;

		} catch (ServiceException se) {
			throw se;
		} catch (ConnectException ce) {
			log.error("Error con Empocaldas: " + ErrorUtils.getStackTrace(ce));
			throw ce;
		} catch (ReadTimeoutException re) {
			log.error("Error con Empocaldas: " + ErrorUtils.getStackTrace(re));
			throw new ConnectException(ErrorUtils.getStackTrace(re));
		} catch (Exception e) {
			log.error("Error con Empocaldas: " + ErrorUtils.getStackTrace(e));
			throw new ServiceException("Traza: " + ErrorUtils.getStackTrace(e),
					getCodeErrors().getError("087507"));
		}

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.datacenter.seta.sw.recaudos.terceros.empocaldas.service.provider.
	 * IEmpocaldasProviderService
	 * #registrarRecaudo(com.datacenter.seta.sw.recaudos
	 * .terceros.dto.BitacoraRecaudo,
	 * com.datacenter.core.seta.sw.core.context.ServerContext)
	 */
	@Override
	public void registrarRecaudo(RecaudoEmpocaldas recaudoEmpocaldas, String codigoSubproducto, BitacoraRecaudo bitacora, ServerContext context)
			throws ServiceException, ConnectException, ReadTimeoutException {
		
		Rest rest = new Rest();
		String tramaEnvioJson = null;
		String tramaRecepcionJson = null;
		PagoEmpocaldasRequest request = new PagoEmpocaldasRequest();
		PagoEmpocaldasResponse pagoEmpocaldasResponse = new PagoEmpocaldasResponse();
		RestResponseMessage respuesta = null;
		try{
			String url = EmpocaldasProperties
					.getStringProperty(EmpocaldasProperties.URL_WS_EMPOCALDAS);
			String metodoNotificarPago = EmpocaldasProperties
					.getStringProperty(EmpocaldasProperties.METODO_NOTIFICAR);
			Integer timeout = EmpocaldasProperties
					.getIntegerProperty(EmpocaldasProperties.PROPIEDAD_TIMEOUT);	
			
			
			/***************************************
			 * Se crea la peticion de pago
			 ***************************************/
			/***************************************
			 * Se evalua si es Empocaldas o Empocaldas otros conceptos
			 ***************************************/
			if (codigoSubproducto.equals(EmpocaldasProperties.CODIGO_SUBPRODUCTO_EMPOCALDAS)){
				request.setIdComercio(EmpocaldasProperties
						.getIntegerProperty(EmpocaldasProperties.IDCOMERCIO_EMPOCALDAS));
				request.setPassword(EmpocaldasProperties
						.getStringProperty(EmpocaldasProperties.PASSWORD_EMPOCALDAS));
			}else{
				request.setIdComercio(EmpocaldasProperties
						.getIntegerProperty(EmpocaldasProperties.IDCOMERCIO_EMPOCALDAS_OTROS_CONCEPTOS));
				request.setPassword(EmpocaldasProperties
						.getStringProperty(EmpocaldasProperties.PASSWORD_EMPOCALDAS_OTROS_CONCEPTOS));
			}
			request.setIdCliente(recaudoEmpocaldas.getIdCliente());
			request.setFacturas(recaudoEmpocaldas.getFacturas());
			request.setCodigoBanco(recaudoEmpocaldas.getCodigoBanco());
			request.setEstadoPago(EmpocaldasProperties.ESTADO_PAGO);
			request.setValorTotalPagado((int) recaudoEmpocaldas.getFacturas().get(0).getTotalFactura());
			request.setFechaPago(FechaUtils.fechaConHoraString(context.getFecha()));
			request.setIdPago(Integer.valueOf(recaudoEmpocaldas.getTransaccionId()));
			request.setCodigoBanco(recaudoEmpocaldas.getCodigoBanco());
			
			/***************************************
			 * Se convierte la peticion en JSON
			 ***************************************/
			Gson gsonRequest = new Gson();
			tramaEnvioJson = gsonRequest.toJson(request, PagoEmpocaldasRequest.class);
			
			/*******************************************************************
			 * Se inicializa la información de la transacción para registrar en
			 * la bitácora
			 ******************************************************************/
			bitacora.setTransactionControl(beginTransactionControl(
					bitacora.getTransactionControl(), context));
			bitacora.getTransactionControl().setUrlEnviada(url + metodoNotificarPago);
			bitacora.getTransactionControl().setTramaEnvio(tramaEnvioJson);
			
			/***************************************
			 * Se inicializa la conexion
			 ***************************************/
			URL urlPago = new URL(url + metodoNotificarPago);			

			log.info("********************************************************");
			log.info("Clase: EmpocaldasProviderService");
			log.info("Metodo: NotificarPago");
			log.info("URL: " + url + metodoNotificarPago);
			log.info("timeOut: " + timeout);
			log.info("Peticion JSON: " + tramaEnvioJson);
			log.info("********************************************************");
			
			HttpsURLConnection connection = (HttpsURLConnection) urlPago
					.openConnection();
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			connection.setSSLSocketFactory(new TLSSocketConnectionFactory());

			respuesta = rest.sendConSSL(connection, tramaEnvioJson);

			tramaRecepcionJson = respuesta.getMensajeRespuesta();
			
			log.info("********************************************************");
			log.info("Respuesta JSON: " + tramaRecepcionJson);
			log.info("********************************************************");
			
			/********************************************
			 * Se convierte la respuesta JSON a Objeto
			 *******************************************/
			Gson gsonResponse = new Gson();
			pagoEmpocaldasResponse = gsonResponse.fromJson(tramaRecepcionJson, PagoEmpocaldasResponse.class);
			bitacora.getTransactionControl().setTramaRecepcion(tramaRecepcionJson);
			bitacora.setTransactionControl(endTransactionControl(
					bitacora.getTransactionControl(), context));
			
			if(!EmpocaldasProperties.RESPUESTA_OK_EMPOCALDAS.equals(pagoEmpocaldasResponse.getCodEstado().toString())) {
				throw new ServiceException(
						"Ocurrió un error registrando el Recaudo Empocaldas: "
								+ pagoEmpocaldasResponse.getCodEstado()
								+ " - descripción error: "
								+ pagoEmpocaldasResponse.getCodEstado(),
						getCodeErrors().getError("087506",
								pagoEmpocaldasResponse.getDescripcionEstado()));
			}
		} catch (ServiceException se) {
			throw se;
		} catch (ConnectException ce) {
			log.error("Error con Empocaldas: " + ErrorUtils.getStackTrace(ce));
			throw ce;
		} catch (ReadTimeoutException re) {
			log.error("Error con Empocaldas: " + ErrorUtils.getStackTrace(re));
			throw new ConnectException(ErrorUtils.getStackTrace(re));
		} catch (Exception e) {
			log.error("Error con Empocaldas: " + ErrorUtils.getStackTrace(e));
			throw new ServiceException("Traza: " + ErrorUtils.getStackTrace(e),
					getCodeErrors().getError("087508"));
		}

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.datacenter.seta.sw.recaudos.terceros.empocaldas.service.provider.
	 * IEmpocaldasProviderService
	 * #obtenerInfoRecaudo(com.datacenter.seta.sw.recaudos.terceros.dto.Recaudo,
	 * com.datacenter.seta.sw.recaudos.terceros.dto.Servicio,
	 * com.datacenter.core.seta.sw.core.context.ServerContext)
	 */
	@Override
	public RecaudoEmpocaldas obtenerInfoRecaudo(Recaudo recaudo, Servicio servicio,
			ServerContext context) throws ServiceException {
		RecaudoEmpocaldas recaudoEmpocaldas = new RecaudoEmpocaldas();
		
		Campo idFactura = servicio
				.getCampoPorTipoCampo(ConstantesRecaudosTerceros.TIPO_CAMPO_REFERENCIA);
		Campo idCliente = servicio
				.getCampoPorTipoCampo(ConstantesRecaudosTerceros.TIPO_CAMPO_SUSCRIPTOR);
		Campo valor = servicio
				.getCampoPorTipoCampo(ConstantesRecaudosTerceros.TIPO_CAMPO_VALOR);
		Campo fechaVencimiento = servicio
				.getCampoPorTipoCampo(ConstantesRecaudosTerceros.TIPO_CAMPO_FECHA_VENCIMIENTO);
		Campo codigoBanco = servicio
				.getCampoPorTipoCampo(ConstantesRecaudosTerceros.TIPO_CAMPO_OTRO);
		
		if (idFactura == null) {
			throw new ServiceException(
					"El recaudo debe tener configurado el campo "
							+ ConstantesRecaudosTerceros.TIPO_CAMPO_REFERENCIA,
					getCodeErrors().getError("080105"));
		}
		
		if (idCliente == null) {
			throw new ServiceException(
					"El recaudo debe tener configurado el campo "
							+ ConstantesRecaudosTerceros.TIPO_CAMPO_SUSCRIPTOR,
					getCodeErrors().getError("080105"));
		}
		
		if (valor == null) {
			throw new ServiceException(
					"El recaudo debe tener configurado el campo "
							+ ConstantesRecaudosTerceros.TIPO_CAMPO_VALOR,
					getCodeErrors().getError("080105"));
		}
		
		if (fechaVencimiento == null) {
			throw new ServiceException(
					"El recaudo debe tener configurado el campo "
							+ ConstantesRecaudosTerceros.TIPO_CAMPO_FECHA_VENCIMIENTO,
					getCodeErrors().getError("080105"));
		}
		
		if (codigoBanco == null) {
			throw new ServiceException(
					"Ocurrió un problema con el codigoBanco"
							+ ConstantesRecaudosTerceros.TIPO_CAMPO_OTRO,
					getCodeErrors().getError("080105"));
		}

		/****************************************************************
		 * Se setea la información necesaria para el registro del recaudo
		 ***************************************************************/
		Facturas factura = new Facturas();
		factura.setFechaVencimiento(fechaVencimiento.getValor());
		factura.setIdFactura(idFactura.getValor());
		factura.setValorPagado(valor.getValor());
		recaudoEmpocaldas.getFacturas().add(factura);
		recaudoEmpocaldas.setIdCliente(idCliente.getValor());
		recaudoEmpocaldas.setTransaccionId(String.valueOf(context.getServerTransactionId()));
		recaudoEmpocaldas.setCodigoBanco(Integer.valueOf(codigoBanco.getValor()));
		return recaudoEmpocaldas;

		

	}
	
	/**
	 * Metodo que inicializa los tiempos para el envio de transacciones
	 * 
	 * @param transactionControl
	 *            Objeto para el control de las transacciones
	 * @param context
	 *            Contexto del servidor
	 * @return TransactionControl Objeto con toda la información de control
	 *         asociada a la comunicación con el tercero
	 */
	public TransactionControl beginTransactionControl(
			TransactionControl transactionControl, ServerContext context) {
		transactionControl.setFechaEnvio(context.getFecha());
		transactionControl.setHoraEnvio(FechaUtils.horaString(context
				.getFecha()));
		transactionControl.setTiempoInicio(System.currentTimeMillis());
		return transactionControl;
	}
	
	/**
	 * Metodo que termina los tiempos para cuando se recibe las transacciones
	 * 
	 * @param transactionControl
	 *            Objeto para el control de las transacciones
	 * @param context
	 *            Contexto del servidor
	 * @return TransactionControl Objeto con toda la información de control
	 *         asociada a la comunicación con el tercero
	 */
	public TransactionControl endTransactionControl(
			TransactionControl transactionControl, ServerContext context) {
		transactionControl.setFechaRecepcion(context.getFecha());
		transactionControl.setHoraRecepcion(FechaUtils.horaString(context
				.getFecha()));
		transactionControl.setTiempoFinal(System.currentTimeMillis());
		transactionControl.setTiempoEspera(transactionControl.getTiempoFinal()
				- transactionControl.getTiempoInicio());
		return transactionControl;
	}

}
