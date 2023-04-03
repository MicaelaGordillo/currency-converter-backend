package arquitectura.software.demo.bl

import arquitectura.software.demo.dao.repository.VueloRepository
import arquitectura.software.demo.dto.VueloDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class VueloBl @Autowired constructor(private val vueloRepository: VueloRepository){

    //Método realizado para probar la conexión a la BBDD
    //Lista de vuelos en base al origen, al destino y la fecha
    fun getVuelos(origen: String, destino: String, fecha: String): List<VueloDto>? {
        val result = vueloRepository.findAll()
        val list = result.map { vuelo ->
            val formatoFecha = SimpleDateFormat("yyyy-MM-dd")
            val fechaString = formatoFecha.format(vuelo.fecha)
            VueloDto(vuelo.id, vuelo.origen, vuelo.destino, fechaString, vuelo.hora, vuelo.precio)
        }
        return list.filter { vuelo -> (vuelo.origen == origen) && (vuelo.destino == destino) && (vuelo.fecha == fecha) }
    }

}