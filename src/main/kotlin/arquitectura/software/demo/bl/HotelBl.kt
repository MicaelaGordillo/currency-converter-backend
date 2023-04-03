package arquitectura.software.demo.bl

import arquitectura.software.demo.dao.repository.HotelRepository
import arquitectura.software.demo.dto.HotelDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HotelBl @Autowired constructor(private val hotelRepository: HotelRepository) {

    //Método realizado para probar la conexión a la BBDD
    //Lista de hoteles por destino
    fun getHotelsByDestination(destination: String): List<HotelDto> {
        val hotels = hotelRepository.findAll()
        val list = hotels.map { hotel -> HotelDto(
            hotel.id,
            hotel.ciudad,
            hotel.nombre,
            hotel.precio)
        }
        return list.filter { hotel -> hotel.ciudad == destination }
    }

}