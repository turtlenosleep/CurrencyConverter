package tw.com.robert.yu.paypay.currencyconverter.dao.list

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tw.com.robert.yu.paypay.currencyconverter.dao.ErrorData

@JsonClass(generateAdapter = true)
data class CurrencyListData(
    @field:Json(name = "currencies") val currencies: Map<String, String>?,
    @field:Json(name = "privacy") val privacy: String?,
    @field:Json(name = "success") val success: Boolean,
    @field:Json(name = "terms") val terms: String?,
    @field:Json(name = "error") val error: ErrorData?

)
