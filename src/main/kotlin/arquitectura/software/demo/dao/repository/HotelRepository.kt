package arquitectura.software.demo.dao.repository

import arquitectura.software.demo.dao.Hotel
import org.springframework.data.repository.CrudRepository

interface HotelRepository: CrudRepository<Hotel, Long> {
}