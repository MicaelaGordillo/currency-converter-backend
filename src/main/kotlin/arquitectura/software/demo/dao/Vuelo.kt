package arquitectura.software.demo.dao

import java.math.BigDecimal
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "vuelo")
class Vuelo (
    var origen: String,
    var destino: String,
    var fecha: Date,
    var hora: String,
    var precio: BigDecimal,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,
) {
    constructor() : this("","", Date(), "", BigDecimal.ZERO) {
    }
}