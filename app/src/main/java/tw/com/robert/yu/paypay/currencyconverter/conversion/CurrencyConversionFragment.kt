package tw.com.robert.yu.paypay.currencyconverter.conversion

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import tw.com.robert.yu.paypay.currencyconverter.conversion.data.CurrencyExchangeData
import tw.com.robert.yu.paypay.currencyconverter.conversion.data.CurrencyInfo
import tw.com.robert.yu.paypay.currencyconverter.databinding.CurrencyConversionFragementBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CurrencyConversionFragment : NavHostFragment(), CurrencyConversionConcat.View {

    private lateinit var mBinding: CurrencyConversionFragementBinding;
    private lateinit var mPresenter: CurrencyConversionConcat.Presenter;

    private val mListAdapter = CurrencyExchangeListAdapter();
    private val mSpinnerAdapter = CurrencyInfoListAdapter();

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = CurrencyConversionFragementBinding.inflate(inflater, container, false);
        return mBinding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter = CurrencyConversionPresenter(this);
        buildInputEditText(mBinding);
        buildSelectCurrencySpinner(mBinding);
        buildRecyclerView(mBinding);
        buildStatusLayout(mBinding);
    }

    private fun buildInputEditText(binding: CurrencyConversionFragementBinding) {
        binding.inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(text: Editable?) {
                mPresenter.afterInputChange(text);
            }
        })
        binding.clearTextImageView.setOnClickListener {
            binding.inputEditText.text = null;
        }
    }

    private fun buildSelectCurrencySpinner(binding: CurrencyConversionFragementBinding) {
        binding.selectCurrencySpinner.adapter = mSpinnerAdapter;
        binding.selectCurrencySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, index: Int, p3: Long) {
                    mPresenter.onBaseCurrencyChange(mSpinnerAdapter.getItem(index).currencyID);
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
    }

    private fun buildRecyclerView(binding: CurrencyConversionFragementBinding) {
        binding.currencyRecyclerView.layoutManager = GridLayoutManager(context, 2);
        binding.currencyRecyclerView.adapter = mListAdapter;
    }

    private fun buildStatusLayout(binding: CurrencyConversionFragementBinding) {
        binding.statusLayout.refreshTextView.setOnClickListener {
            mPresenter.onRefresh()
        }
    }


    override fun onResume() {
        super.onResume()
        mPresenter.onRefresh()
    }

    private fun runOnUiThread(runnable: () -> Unit) {
        activity?.runOnUiThread(runnable);
    }

    override fun showLoading() {
        runOnUiThread {
            mBinding.statusLayout.root.visibility = View.GONE
            mBinding.loadingLayout.root.visibility = View.VISIBLE
        }
    }

    override fun showCurrencyList(currencyInfoList: List<CurrencyInfo>) {
        runOnUiThread {
            mSpinnerAdapter.mCurrencyInfoList = currencyInfoList;
            mSpinnerAdapter.notifyDataSetChanged();
        }
    }

    override fun showContent(exchangeRateList: List<CurrencyExchangeData>) {
        runOnUiThread {
            mListAdapter.mExchangeRateList = exchangeRateList;
            mListAdapter.notifyDataSetChanged();

            mBinding.statusLayout.root.visibility = View.GONE
            mBinding.loadingLayout.root.visibility = View.GONE
            mBinding.currencyRecyclerView.visibility = View.VISIBLE;
        }
    }

    override fun showLastUpdate(lastUpdateText: String?) {
        runOnUiThread {
            mBinding.lastUpdateTextView.text = "Last Update:${lastUpdateText}";
        }
    }

    override fun showError(errorMsg: String?) {
        runOnUiThread {
            mBinding.statusLayout.statusTextView.text = errorMsg;
            mBinding.statusLayout.root.visibility = View.VISIBLE
            mBinding.loadingLayout.root.visibility = View.GONE
            mBinding.currencyRecyclerView.visibility = View.GONE;
        }
    }

}