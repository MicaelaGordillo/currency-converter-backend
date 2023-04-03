package arquitectura.software.demo.api

import arquitectura.software.demo.bl.HotelBl
import arquitectura.software.demo.bl.VueloBl
import arquitectura.software.demo.dto.FacadeDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/facade")
class FacadeApi @Autowired constructor(private val hotelBl: HotelBl, private val vueloBl: VueloBl) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(FacadeApi::class.java)
    }

    //Lista de la informacion para el viaje
    @GetMapping("/list/{origen}/{destino}/{fechaIda}/{fechaRegreso}")
    fun list(@PathVariable("origen") origen: String,
             @PathVariable("destino") destino: String,
             @PathVariable("fechaIda") fechaIda: String,
             @PathVariable("fechaRegreso") fechaRegreso: String): FacadeDto {
        LOGGER.info("Lista de la informacion para el viaje")
        val vuelosIda = vueloBl.getVuelos(origen, destino, fechaIda)
        val vuelosRegreso = vueloBl.getVuelos(destino, origen, fechaRegreso)
        val hoteles = hotelBl.getHotelsByDestination(destino)
        return FacadeDto(vuelosIda, vuelosRegreso, hoteles)
    }

}