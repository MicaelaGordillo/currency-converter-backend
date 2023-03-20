package arquitectura.software.demo.api

import arquitectura.software.demo.bl.CurrencyBl
import arquitectura.software.demo.dto.CurrencyDto
import arquitectura.software.demo.dto.ResponseServiceDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal


@RequestMapping("/api/currency")
@RestController
class CurrencyApi @Autowired constructor(private val currencyBl: CurrencyBl) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(CurrencyApi::class.java)
    }

    @GetMapping("/exchange")
    fun exchangeRate(@RequestParam to: String,
                     @RequestParam from: String,
                     @RequestParam amount: BigDecimal): ResponseServiceDto {
        LOGGER.info("Iniciando peticion para convertir divisas de $from a $to con un monto de $amount")
        val result = currencyBl.exchangeRate(to, from, amount)
        return result
    }

    //Lista de registros
    @GetMapping("/list")
    fun list(): List<CurrencyDto>? {
        LOGGER.info("Iniciando peticion para listar registros")
        val result = currencyBl.list()
        return result
    }

    //Lista de registros por moneda origen
    @GetMapping("/list/from/{from}")
    fun listByFrom(@PathVariable from: String): List<CurrencyDto>? {
        LOGGER.info("Iniciando peticion para listar registros por moneda origen")
        val result = currencyBl.listByFrom(from)
        return result
    }

    //Lista de registros por moneda destino
    @GetMapping("/list/to/{to}")
    fun listByTo(@PathVariable to: String): List<CurrencyDto>? {
        LOGGER.info("Iniciando peticion para listar registros por moneda destino")
        val result = currencyBl.listByTo(to)
        return result
    }

    //Lista de registros ordenada de forma ascendente por monto
    @GetMapping("/list/asc")
    fun listAsc(): List<CurrencyDto>? {
        LOGGER.info("Iniciando peticion para listar registros ordenada de forma ascendente por monto")
        val result = currencyBl.listByAmountAsc()
        return result
    }

    //Lista de registros ordenada de forma descendente por monto
    @GetMapping("/list/desc")
    fun listDesc(): List<CurrencyDto>? {
        LOGGER.info("Iniciando peticion para listar registros ordenada de forma descendente por monto")
        val result = currencyBl.listByAmountDesc()
        return result
    }

    //Lista de registros en un rango - paginacion
    @GetMapping("/list/range/{from}/{to}")
    fun listRange(@PathVariable from: Int, @PathVariable to: Int): List<CurrencyDto>? {
        LOGGER.info("Iniciando peticion para listar registros en un rango")
        val result = currencyBl.listByRange(from, to)
        return result
    }

}