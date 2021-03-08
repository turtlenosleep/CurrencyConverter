package tw.com.robert.yu.paypay.currencyconverter.web

import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File

object WebServiceRunner {

    lateinit var cacheDir: File;
    val mClient: OkHttpClient by lazy {
        OkHttpClient()
            .newBuilder()
            .cache(
                Cache(
                    directory = File(cacheDir, "http_cache"),
                    maxSize = 50L * 1024L * 1024L // 50 MiB
                )
            )
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BASIC) })
            .build();
    };

}