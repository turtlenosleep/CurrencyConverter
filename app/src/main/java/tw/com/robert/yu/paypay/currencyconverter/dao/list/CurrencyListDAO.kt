package tw.com.robert.yu.paypay.currencyconverter.dao.list

import com.squareup.moshi.Moshi
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import tw.com.robert.yu.paypay.currencyconverter.dao.CommonWebDao
import tw.com.robert.yu.paypay.currencyconverter.dao.DataAccessCallback
import tw.com.robert.yu.paypay.currencyconverter.dao.DataAccessException
import tw.com.robert.yu.paypay.currencyconverter.web.WebServiceRunner
import java.io.IOException

class CurrencyListDAO : CommonWebDao<CurrencyListData>() {

    private val mJsonAdapter = Moshi.Builder().build().adapter(CurrencyListData::class.java);

    override fun getData(callback: DataAccessCallback<CurrencyListData>) {
        val request = createBaseRequestBuilder()
            .url(
                createBaseUrlBuilder()
                    .addPathSegment("list")
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
                    val currencyListData = mJsonAdapter.fromJson(jsonString!!);

                    currencyListData?.apply {
                        if (currencyListData.success) {
                            callback.onSuccess(this);

                        } else {
                            callback.onError(
                                DataAccessException(
                                    currencyListData.error?.info ?: "request error"
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