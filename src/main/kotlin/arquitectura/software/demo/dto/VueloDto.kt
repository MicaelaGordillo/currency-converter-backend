package arquitectura.software.demo.dto

import java.math.BigDecimal

class VueloDto (
    var id: Long,
    var origen: String,
    var destino: String,
    var fecha: String,
    var hora: String,
    var precio: BigDecimal
)