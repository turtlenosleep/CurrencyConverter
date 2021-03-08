package tw.com.robert.yu.paypay.currencyconverter.dao

interface IDAO<T> {

    fun getData(callback: DataAccessCallback<T>);
}