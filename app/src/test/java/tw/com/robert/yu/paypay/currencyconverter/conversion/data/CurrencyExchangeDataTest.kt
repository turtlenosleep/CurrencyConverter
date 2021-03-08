package tw.com.robert.yu.paypay.currencyconverter.conversion.data

import junit.framework.TestCase
import java.math.BigDecimal

class CurrencyExchangeDataTest : TestCase() {

    private val mExchangeData = CurrencyExchangeData();


    fun testSetExchangeRate() {

        mExchangeData.input = BigDecimal.ONE

        mExchangeData.exchangeRate = 2.56785.toBigDecimal()
        assertEquals(mExchangeData.conversionText, "2.5679")


        mExchangeData.exchangeRate = 2.56784.toBigDecimal()
        assertEquals(mExchangeData.conversionText, "2.5678")

        mExchangeData.exchangeRate = 2.56784555.toBigDecimal()
        assertEquals(mExchangeData.conversionText, "2.5678")
    }

    fun testSetInput() {

        mExchangeData.exchangeRate = BigDecimal.ONE

        mExchangeData.input = 2.56785.toBigDecimal()
        assertEquals(mExchangeData.conversionText, "2.5679")


        mExchangeData.input = 2.56784.toBigDecimal()
        assertEquals(mExchangeData.conversionText, "2.5678")

        mExchangeData.input = 2.56784555.toBigDecimal()
        assertEquals(mExchangeData.conversionText, "2.5678")
    }
}