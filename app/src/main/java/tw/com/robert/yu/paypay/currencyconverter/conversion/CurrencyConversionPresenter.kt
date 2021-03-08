package tw.com.robert.yu.paypay.currencyconverter.conversion

import android.text.Editable
import tw.com.robert.yu.paypay.currencyconverter.conversion.data.CurrencyExchangeData
import tw.com.robert.yu.paypay.currencyconverter.conversion.data.CurrencyInfo
import tw.com.robert.yu.paypay.currencyconverter.dao.DataAccessCallback
import tw.com.robert.yu.paypay.currencyconverter.dao.IDAO
import tw.com.robert.yu.paypay.currencyconverter.dao.exchage.ExchangeRateListDAO
import tw.com.robert.yu.paypay.currencyconverter.dao.exchage.ExchangeRateListData
import tw.com.robert.yu.paypay.currencyconverter.dao.list.CurrencyListDAO
import tw.com.robert.yu.paypay.currencyconverter.dao.list.CurrencyListData
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class CurrencyConversionPresenter(val mView: CurrencyConversionConcat.View) :
    CurrencyConversionConcat.Presenter {

    private val mHelper = CurrencyExchangeHelper();
    private val mCurrencyListDAO: IDAO<CurrencyListData>
    private val mExchangeRateListDao: IDAO<ExchangeRateListData>;

    private val mDateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    private var mCurrencyExchangeRateList = listOf<CurrencyExchangeData>();
    private var mInput: BigDecimal = BigDecimal.ZERO


    init {
        mCurrencyListDAO = CurrencyListDAO();
        mExchangeRateListDao = ExchangeRateListDAO();
    }

    override fun onRefresh() {
        mView.showLoading()
        requestCurrencyList();
    }

    private fun requestCurrencyList() {
        mCurrencyListDAO.getData(object : DataAccessCallback<CurrencyListData> {
            override fun onSuccess(data: CurrencyListData) {
                data.currencies?.apply {
                    mHelper.mCurrencyNameMap = this;
                };
                mView.showCurrencyList(createCurrencyInfoList(data))
                requestExchangeRateList();
            }

            override fun onError(e: Exception) {
                mView.showError(e.localizedMessage);
            }
        })
    }

    private fun createCurrencyInfoList(listData: CurrencyListData): List<CurrencyInfo> {
        return mutableListOf<CurrencyInfo>().apply {
            listData.currencies?.forEach { (key, value) ->
                if (mHelper.mBaseCurrencyID.isEmpty()) mHelper.mBaseCurrencyID = key;
                add(CurrencyInfo(key, value));
            }
        }
    }

    private fun requestExchangeRateList() {
        mExchangeRateListDao.getData(object : DataAccessCallback<ExchangeRateListData> {
            override fun onSuccess(data: ExchangeRateListData) {

                mHelper.initExchangeRateMap(data);
                mCurrencyExchangeRateList =
                    createCurrencyDataList(mHelper.mExchangeRateMap)

                mView.showContent(mCurrencyExchangeRateList);
                mView.showLastUpdate(mDateFormatter.format((data.timestamp ?: 0) * 1000));
            }

            override fun onError(e: Exception) {
                mView.showError(e.localizedMessage);
            }
        })
    }

    private fun createCurrencyDataList(map: Map<String, Double>): List<CurrencyExchangeData> {
        return mutableListOf<CurrencyExchangeData>().apply {
            map.forEach { (key, _) ->
                add(CurrencyExchangeData().apply {
                    currencyID = key;
                    name = mHelper.getCurrencyNation(currencyID);
                    exchangeRate = mHelper.getExchangeRate(currencyID);
                    input = mInput;
                })
            }
        }
    };

    override fun onBaseCurrencyChange(currencyID: String) {
        mHelper.mBaseCurrencyID = currencyID;
        mCurrencyExchangeRateList.forEach {
            it.exchangeRate = mHelper.getExchangeRate(it.currencyID);
        }

        mView.showContent(mCurrencyExchangeRateList);
    }

    override fun afterInputChange(text: Editable?) {
        mInput = if (text == null) BigDecimal.ZERO;
        else mHelper.getValidInput(text.toString());

        mCurrencyExchangeRateList.forEach {
            it.input = mInput
        }

        mView.showContent(mCurrencyExchangeRateList);
    }

}