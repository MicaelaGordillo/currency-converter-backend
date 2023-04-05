package arquitectura.software.demo.bl

import arquitectura.software.demo.dao.repository.CurrencyRepository
import arquitectura.software.demo.exception.ServiceException
import arquitectura.software.demo.dto.ErrorServiceDto
import arquitectura.software.demo.dto.ResponseServiceDto
import arquitectura.software.demo.dao.Currency
import arquitectura.software.demo.dto.CurrencyDto
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.math.BigDecimal
import org.springframework.beans.factory.annotation.Value
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@Service
class CurrencyBl @Autowired constructor(private val currencyRepository: CurrencyRepository) {

    companion object {
        val objectMapper = jacksonObjectMapper()
        val LOGGER: Logger = LoggerFactory.getLogger(CurrencyBl::class.java)
    }

    //Se obtienen los valores de las variables de entorno
    //Declaramos la url de la api
    @Value("\${api.url}")
    lateinit var apiUrl: String
    //Declaramos la api key
    @Value("\${api.key}")
    lateinit var apiKey: String

    //Método para convertir divisas
    fun exchangeRate(to: String, from: String, amount: BigDecimal): ResponseServiceDto {
        LOGGER.error("Iniciando lógica para convertir divisas")
        if (amount < BigDecimal.ZERO) {
            LOGGER.error("El monto no puede ser negativo")
            throw IllegalArgumentException("El monto no puede ser negativo")
        }
        val response = invokeApi("$apiUrl?to=$to&from=$from&amount=$amount")
        val responseServiceDto = parseResponse(response)
        val currency = Currency()
        currency.currencyFrom = from
        currency.currencyTo = to
        currency.amount = amount
        currency.date = Date()
        currency.result = responseServiceDto.result
        currencyRepository.save(currency)
        return responseServiceDto
    }

    //Método para invocar el servicio de conversión de divisas
    fun invokeApi(endpoint: String): Response {
        LOGGER.info("Invocando servicio de conversión de monedas")
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(endpoint)
            .addHeader("apikey", apiKey)
            .build()
        try {
            return client.newCall(request).execute()
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Error en el servicio de conversión de monedas")
        }
    }

    //Método para parsear la respuesta del servicio de conversión de divisas
    fun parseResponse(response: Response): ResponseServiceDto {
        LOGGER.info("Parseando respuesta del servicio de conversión de monedas")
        val body = response.body().string()
        LOGGER.info("El servicio de conversión de monedas retorno => $body")
        if(response.isSuccessful) {
            LOGGER.info("El servicio de conversión de monedas fue exitoso")
            return objectMapper.readValue(body)
        }
        LOGGER.info("El servicio de conversión de monedas fue fallido")
        val errorService = objectMapper.readValue<ErrorServiceDto>(body)
        throw ServiceException("Code: ${errorService.error.code}, message: ${errorService.error.message}")
    }

    //Método realizado para probar la conexión a la BBDD
    //Lista de registros
    fun list(): List<CurrencyDto>? {
        LOGGER.info("Iniciando peticion para listar registros")
        val result = currencyRepository.findAll()
        val list = result.map { currency -> CurrencyDto(
            currency.id,
            currency.currencyFrom,
            currency.currencyTo,
            currency.amount,
            currency.result,
            currency.date) }
        return list
    }

    //Lista de registros por moneda origen
    fun listByFrom(currencyFrom: String): List<CurrencyDto> {
        LOGGER.info("Iniciando peticion para listar registros por moneda origen")
        val list = list()
        if (list != null) {
            return list.filter { currency -> currency.currencyFrom == currencyFrom }
        }
        return emptyList()
    }

    //Lista de registros por moneda destino
    fun listByTo(currencyTo: String): List<CurrencyDto> {
        LOGGER.info("Iniciando peticion para listar registros por moneda destino")
        val list = list()
        if (list != null) {
            return list.filter { currency -> currency.currencyTo == currencyTo }
        }
        return emptyList()
    }

    //Lista de registros ordenada de forma ascendente por monto
    fun listByAmountAsc(): List<CurrencyDto> {
        LOGGER.info("Iniciando peticion para listar registros ordenados por monto de forma ascendente")
        val list = list()
        if (list != null) {
            return list.sortedBy { currency -> currency.amount }
        }
        return emptyList()
    }

    //Lista de registros ordenada de forma descendente por monto
    fun listByAmountDesc(): List<CurrencyDto> {
        LOGGER.info("Iniciando peticion para listar registros ordenados por monto de forma descendente")
        val list = list()
        if (list != null) {
            return list.sortedByDescending { currency -> currency.amount }
        }
        return emptyList()
    }

    //Lista de registros en un rango - paginacion
    fun listByRange(from: Int, to: Int): List<CurrencyDto> {
        LOGGER.info("Iniciando petición para listar registros en un rango de $from a $to")
        val result = currencyRepository.findAll()
        var list = result.map { currency ->
            CurrencyDto(
                currency.id,
                currency.currencyFrom,
                currency.currencyTo,
                currency.amount,
                currency.result,
                currency.date
            )
        }
        val listSize = list.size
        if (from > listSize) {
            println("El rango no puede ser mayor al tamaño de la lista")
        } else if (to > listSize) {
            println("El rango es mayor al tamaño de la lista")
            list = list.subList(from, listSize)
        } else if (listSize == 0){
            println("La lista esta vacia")
        } else {
            list = list.subList(from, to)
        }
        return list
    }

}