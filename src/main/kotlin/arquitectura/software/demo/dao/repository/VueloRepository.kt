package arquitectura.software.demo.dao.repository

import arquitectura.software.demo.dao.Vuelo
import org.springframework.data.repository.CrudRepository

interface VueloRepository: CrudRepository<Vuelo, Long> {
}