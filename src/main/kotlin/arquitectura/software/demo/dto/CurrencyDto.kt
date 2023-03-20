package arquitectura.software.demo.dto

import java.math.BigDecimal
import java.util.*

class CurrencyDto(
    var id: Long,
    var currencyFrom: String,
    var currencyTo: String,
    var amount: BigDecimal,
    var result: BigDecimal,
    var date: Date,
)