package tw.com.robert.yu.paypay.currencyconverter.dao

import okhttp3.CacheControl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import java.util.concurrent.TimeUnit

abstract class CommonWebDao<T> : IDAO<T> {
    private val mBaseUrl = "http://api.currencylayer.com"
    private val mAccessKey = "c7ae2f2c0aa3aea953fd509924db71bb"

    protected fun createBaseUrlBuilder() = mBaseUrl
        .toHttpUrl()
        .newBuilder()
        .addQueryParameter("access_key", mAccessKey)

    protected fun createBaseRequestBuilder() = Request.Builder()
        .cacheControl(
            CacheControl.Builder()
                .maxAge(30, TimeUnit.MINUTES)
                .maxStale(30, TimeUnit.MINUTES)
                .build()
        )

}