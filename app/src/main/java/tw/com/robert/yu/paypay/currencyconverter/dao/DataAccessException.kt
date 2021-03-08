package tw.com.robert.yu.paypay.currencyconverter.dao

class DataAccessException(e: Exception) : Exception(e) {
    constructor(e: String) : this(Exception(e))
}