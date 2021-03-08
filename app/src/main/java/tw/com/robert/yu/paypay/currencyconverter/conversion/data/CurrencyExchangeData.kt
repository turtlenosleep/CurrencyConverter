package tw.com.robert.yu.paypay.currencyconverter.conversion.data

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.properties.Delegates


class CurrencyExchangeData {
    lateinit var currencyID: String;
    lateinit var name: String;

    var exchangeRate: BigDecimal by Delegates.observable(BigDecimal.ZERO) { _, _, newValue ->
        conversionText = (input * newValue)
            .setScale(4, RoundingMode.HALF_UP)
            .toPlainString();
    }

    var input: BigDecimal by Delegates.observable(BigDecimal.ZERO) { _, _, newValue ->
        conversionText = (newValue * exchangeRate)
            .setScale(4, RoundingMode.HALF_UP)
            .toPlainString();
    }

    var conversionText: String = "";

}
