package tw.com.robert.yu.paypay.currencyconverter.conversion

import tw.com.robert.yu.paypay.currencyconverter.dao.exchage.ExchangeRateListData
import java.math.BigDecimal
import kotlin.properties.Delegates

class CurrencyExchangeHelper {

    var mBaseCurrencyID: String by Delegates.observable("") { _, _, newValue ->
        mBaseRate = mExchangeRateMap[newValue] ?: 1.0
    };

    var mExchangeRateMap = mapOf<String, Double>();
    var mCurrencyNameMap = mapOf<String, String>();

    var mBaseRate = 1.0;

    fun initExchangeRateMap(data: ExchangeRateListData) {
        mExchangeRateMap =
            mutableMapOf<String, Double>().apply {
                data.quotes?.forEach { (key, value) ->
                    put(key.replaceFirst("USD", ""), value);
                }
            }
    }

    fun getExchangeRate(currencyID: String): BigDecimal =
        ((mExchangeRateMap[currencyID] ?: mBaseRate) / mBaseRate).toBigDecimal();

    fun getCurrencyNation(currencyID: String): String =
        mCurrencyNameMap[currencyID] ?: currencyID;


    fun getValidInput(text: String): BigDecimal {
        if (text.isBlank()) return BigDecimal.ZERO;

        val newValue: BigDecimal = text.toBigDecimalOrNull()
            ?: return BigDecimal.ZERO;

        return if (newValue < BigDecimal.ZERO) BigDecimal.ZERO else newValue;
    }

}