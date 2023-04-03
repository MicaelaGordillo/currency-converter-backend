package arquitectura.software.demo.api

import arquitectura.software.demo.bl.VueloBl
import arquitectura.software.demo.dto.VueloDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/vuelo")
class VueloApi @Autowired constructor(private val vueloBl: VueloBl) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(VueloApi::class.java)
    }

    //Lista de vuelos por origen, destino y fecha
    @GetMapping("/list/{origen}/{destino}/{fecha}")
    fun listByOriginDestinyAndDate(@PathVariable origen: String,
                                   @PathVariable destino: String,
                                   @PathVariable fecha: String): List<VueloDto>? {
        LOGGER.info("Iniciando peticion para listar vuelos por origen, destino y fecha")
        val result = vueloBl.getVuelos(origen, destino, fecha)
        return result
    }
}