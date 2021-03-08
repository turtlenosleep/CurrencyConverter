package tw.com.robert.yu.paypay.currencyconverter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tw.com.robert.yu.paypay.currencyconverter.databinding.MainActivityBinding
import tw.com.robert.yu.paypay.currencyconverter.web.WebServiceRunner

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WebServiceRunner.cacheDir = cacheDir;
        binding = MainActivityBinding.inflate(layoutInflater);
        setContentView(binding.root);
    }

}