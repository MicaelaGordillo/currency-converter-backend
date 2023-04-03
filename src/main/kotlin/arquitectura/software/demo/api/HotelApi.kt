package arquitectura.software.demo.api

import arquitectura.software.demo.bl.HotelBl
import arquitectura.software.demo.dto.HotelDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/hotel")
class HotelApi @Autowired constructor(private val hotelBl: HotelBl) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(CurrencyApi::class.java)
    }

    //Lista de registros por ciudad
    @GetMapping("/list/{destination}")
    fun listByDestination(@PathVariable destination: String): List<HotelDto>? {
        LOGGER.info("Iniciando peticion para listar registros por ciudad")
        val result = hotelBl.getHotelsByDestination(destination)
        return result
    }
}