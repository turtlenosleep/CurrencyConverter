package tw.com.robert.yu.paypay.currencyconverter.dao.exchage

import com.squareup.moshi.Moshi
import okhttp3.*
import tw.com.robert.yu.paypay.currencyconverter.dao.CommonWebDao
import tw.com.robert.yu.paypay.currencyconverter.dao.DataAccessCallback
import tw.com.robert.yu.paypay.currencyconverter.dao.DataAccessException
import tw.com.robert.yu.paypay.currencyconverter.web.WebServiceRunner
import java.io.IOException


class ExchangeRateListDAO : CommonWebDao<ExchangeRateListData>() {

    private val jsonAdapter = Moshi.Builder().build().adapter(ExchangeRateListData::class.java);

    override fun getData(callback: DataAccessCallback<ExchangeRateListData>) {
        val request = createBaseRequestBuilder()
            .url(
                createBaseUrlBuilder()
                    .addPathSegment("live")
                    .build()
            )
            .build();

        WebServiceRunner.mClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val jsonString = response.body?.string();
                    val exchangeRateListData = jsonAdapter.fromJson(jsonString!!);

                    exchangeRateListData?.apply {
                        if (exchangeRateListData.success) {
                            callback.onSuccess(this);

                        } else {
                            callback.onError(
                                DataAccessException(
                                    exchangeRateListData.error?.info ?: "request error"
                                )
                            )
                        }

                    } ?: callback.onError(DataAccessException("data null"))

                } else {
                    callback.onError(DataAccessException(response.toString()))
                }
            }
        })
    }

}