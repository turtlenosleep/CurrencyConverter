package tw.com.robert.yu.paypay.currencyconverter.dao


interface DataAccessCallback<T> {

    fun onSuccess(data:T);

    fun onError(e: Exception);

}