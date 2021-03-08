package tw.com.robert.yu.paypay.currencyconverter.conversion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.com.robert.yu.paypay.currencyconverter.conversion.data.CurrencyExchangeData
import tw.com.robert.yu.paypay.currencyconverter.databinding.CurrencyExchangeItemBinding

class CurrencyExchangeListAdapter :
    RecyclerView.Adapter<CurrencyExchangeListAdapter.CurrencyExchangeViewHolder>() {

    var mExchangeRateList = listOf<CurrencyExchangeData>();

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyExchangeViewHolder {
        return CurrencyExchangeViewHolder(
            CurrencyExchangeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CurrencyExchangeViewHolder, position: Int) {
        holder.bind(mExchangeRateList[position]);
    }

    override fun getItemCount(): Int = mExchangeRateList.size;

    class CurrencyExchangeViewHolder(private val binding: CurrencyExchangeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CurrencyExchangeData) {
            binding.nameTextView.text = data.name;
            binding.conversionTextView.text = data.conversionText;
            binding.exchangeRateTextView.text = data.exchangeRate.toString();
        }
    }




}