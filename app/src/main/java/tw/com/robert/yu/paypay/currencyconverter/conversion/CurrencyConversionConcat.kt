package tw.com.robert.yu.paypay.currencyconverter.conversion

import android.text.Editable
import tw.com.robert.yu.paypay.currencyconverter.conversion.data.CurrencyExchangeData
import tw.com.robert.yu.paypay.currencyconverter.conversion.data.CurrencyInfo

class CurrencyConversionConcat {

    interface View {
        fun showLoading()

        fun showContent(exchangeRateList: List<CurrencyExchangeData>)

        fun showError(errorMsg: String?)

        fun showCurrencyList(currencyInfoList: List<CurrencyInfo>)

        fun showLastUpdate(lastUpdateText: String?)
    }

    interface Presenter {

        fun onRefresh();

        fun onBaseCurrencyChange(currencyID: String);

        fun afterInputChange(text: Editable?)
    }
}