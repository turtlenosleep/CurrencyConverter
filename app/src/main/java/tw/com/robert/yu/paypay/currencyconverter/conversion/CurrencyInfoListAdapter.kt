package tw.com.robert.yu.paypay.currencyconverter.conversion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SpinnerAdapter
import tw.com.robert.yu.paypay.currencyconverter.conversion.data.CurrencyInfo
import tw.com.robert.yu.paypay.currencyconverter.databinding.CurrencyInfoItemBinding

class CurrencyInfoListAdapter : BaseAdapter(), SpinnerAdapter {

    var mCurrencyInfoList = listOf<CurrencyInfo>();

    override fun getCount(): Int = mCurrencyInfoList.size

    override fun getItem(index: Int): CurrencyInfo = mCurrencyInfoList[index]

    override fun getItemId(index: Int): Long = index.toLong();

    override fun getView(index: Int, convertView: View?, parent: ViewGroup): View {

        val viewHolder: CurrencyInfoViewHolder =
            convertView?.run {
                convertView.tag as CurrencyInfoViewHolder
            } ?: CurrencyInfoViewHolder(
                CurrencyInfoItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            );


        viewHolder.bind(getItem(index));
        viewHolder.showSelection();
        return viewHolder.getView();

    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = super.getDropDownView(position, convertView, parent)
        (view.tag as CurrencyInfoViewHolder).showDropDown();
        return view;
    }

    class CurrencyInfoViewHolder(val binding: CurrencyInfoItemBinding) {

        init {
            binding.root.tag = this;
        }

        fun bind(item: CurrencyInfo) {
            binding.nameTextView.text = item.name;
        }

        fun getView() = binding.root;

        fun showSelection() {
            binding.divider.visibility = View.GONE;
            binding.arrowImageView.visibility = View.VISIBLE
        }

        fun showDropDown() {
            binding.divider.visibility = View.VISIBLE;
            binding.arrowImageView.visibility = View.GONE
        }
    }
}