package tw.com.robert.yu.paypay.currencyconverter.conversion

import android.text.SpannableStringBuilder
import junit.framework.TestCase
import tw.com.robert.yu.paypay.currencyconverter.dao.exchage.ExchangeRateListData
import java.math.BigDecimal

class CurrencyExchangeHelperTest : TestCase() {

    private val mHelper = CurrencyExchangeHelper();
    private lateinit var mExchangeRateListData: ExchangeRateListData;
    private val mCurrencyNameMap = mutableMapOf(
        "USD" to "United States Dollar",
        "EUR" to "Euro",
        "CAD" to "Canadian Dollar"
    )

    public override fun setUp() {
        super.setUp()
        mExchangeRateListData = ExchangeRateListData(
            privacy = "privacy link",
            source = "test data",
            terms = "terms link",
            success = true,
            timestamp = 0L,
            quotes = mutableMapOf(
                "USDUSD" to 1.0,
                "USDEUR" to 0.5,
                "USDCAD" to 1.5
            ),
            error = null
        )

        mHelper.initExchangeRateMap(mExchangeRateListData)
        mHelper.mCurrencyNameMap = mCurrencyNameMap;
    }

    fun testExchangeMapKey() {
        assertTrue(mHelper.mExchangeRateMap.containsKey("USD"))
        assertTrue(mHelper.mExchangeRateMap.containsKey("EUR"))
        assertTrue(mHelper.mExchangeRateMap.containsKey("CAD"))
    }

    fun testGetExchangeRate() {
        mHelper.mBaseCurrencyID = "USD"
        assertEquals(mHelper.mBaseRate, mHelper.mExchangeRateMap["USD"])

        mHelper.mExchangeRateMap.forEach { (key, value) ->
            assertEquals(
                mHelper.getExchangeRate(key),
                (value / mHelper.mBaseRate).toBigDecimal()
            )
        }

        mHelper.mBaseCurrencyID = "EUR"
        assertEquals(mHelper.mBaseRate, mHelper.mExchangeRateMap["EUR"])

        mHelper.mExchangeRateMap.forEach { (key, value) ->
            assertEquals(
                mHelper.getExchangeRate(key),
                (value / mHelper.mBaseRate).toBigDecimal()
            )
        }


        mHelper.mBaseCurrencyID = "CAD"
        assertEquals(mHelper.mBaseRate, mHelper.mExchangeRateMap["CAD"])

        mHelper.mExchangeRateMap.forEach { (key, value) ->
            assertEquals(
                mHelper.getExchangeRate(key),
                (value / mHelper.mBaseRate).toBigDecimal()
            )
        }
    }

    fun testGetCurrencyName() {
        mCurrencyNameMap.forEach { (key, value) ->
            assertEquals(mHelper.getCurrencyNation(key), value)
        }
    }

    fun testInput() {
        var text = "";
        assertEquals(mHelper.getValidInput(text), BigDecimal.ZERO);

        text = "xyz123"
        assertEquals(mHelper.getValidInput(text), BigDecimal.ZERO);

        text = "-123.456"
        assertEquals(mHelper.getValidInput(text), BigDecimal.ZERO);

        text = "123.456"
        assertEquals(mHelper.getValidInput(text), 123.456.toBigDecimal());

    }
}