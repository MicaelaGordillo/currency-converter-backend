package arquitectura.software.demo.dao

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "hotel")
class Hotel (
    var ciudad: String,
    var nombre: String,
    var precio: BigDecimal,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,
) {
    constructor() : this("","", BigDecimal.ZERO) {
    }
}