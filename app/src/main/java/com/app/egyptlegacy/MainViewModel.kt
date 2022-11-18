package com.app.egyptlegacy

import android.app.Application
import android.provider.Settings
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.egyptlegacy.first.FirstActivity
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainViewModel(private val app: Application) : AndroidViewModel(app) {

    val isNotADB = MutableLiveData<String>()

    private val _data = MutableSharedFlow<String>()
    val data = _data.asSharedFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            isNotADB.value = isNotADB().toString()
        }
    }

    fun init(activity: FirstActivity) {
        viewModelScope.launch(Dispatchers.IO) {
            if ((app as App).gf.exists()) {
                _data.emit((app).gf.readD())
            } else {
                val apps = getAppsflyer(activity)
                val deep = deepFlow(activity)
                val adId = AdvertisingIdClient.getAdvertisingIdInfo(activity).id.toString()
                val uId = AppsFlyerLib.getInstance().getAppsFlyerUID(activity)!!

                OW(app, adId).send(apps?.get("campaign").toString(), deep)

                val urlTest = UrlBuilder.buildUrl(
                    res = app.resources,
                    baseFileData = F.BAU,
                    gadid = adId,
                    apps = if (deep == "null") apps else null,
                    deep = deep,
                    uid = if (deep == "null") uId else null
                )
                _data.emit(urlTest)
            }
        }
    }

    private suspend fun getAppsflyer(activity: FirstActivity): MutableMap<String, Any>? =
        suspendCoroutine { coroutine ->
            val callback = object : AW {
                override fun onConversionDataSuccess(convData: MutableMap<String, Any>?) {
                    coroutine.resume(convData)
                }

                override fun onConversionDataFail(p0: String?) {
                    coroutine.resume(null)
                }
            }
            AppsFlyerLib.getInstance().init(APPS_FLYER_KEY, callback, activity)
            AppsFlyerLib.getInstance().start(activity)
        }

    private suspend fun deepFlow(activity: FirstActivity): String =
        suspendCoroutine { coroutine ->
            val callback = AppLinkData.CompletionHandler {
                coroutine.resume(it?.targetUri.toString())
            }
            AppLinkData.fetchDeferredAppLinkData(activity, callback)
        }

    private fun isNotADB(): Boolean =
        Settings.Global.getString(
            app.contentResolver,
            Settings.Global.ADB_ENABLED
        ) != "1"

    // ------------- GameActivity part -------------------


    val listGameItems = MutableLiveData<MutableList<CardItem>>(mutableListOf())
    private var activeItems = mutableListOf<CardItem>()

    fun startGame() {
        viewModelScope.launch {
            val cardItemsList = mutableListOf(
                CardItem(R.drawable.p1, FlipState.BACK),
                CardItem(R.drawable.p2, FlipState.BACK),
                CardItem(R.drawable.p3, FlipState.BACK),
                CardItem(R.drawable.p4, FlipState.BACK),
                CardItem(R.drawable.p5, FlipState.BACK),
                CardItem(R.drawable.p6, FlipState.BACK),
                CardItem(R.drawable.p1, FlipState.BACK),
                CardItem(R.drawable.p2, FlipState.BACK),
                CardItem(R.drawable.p3, FlipState.BACK),
                CardItem(R.drawable.p4, FlipState.BACK),
                CardItem(R.drawable.p5, FlipState.BACK),
                CardItem(R.drawable.p6, FlipState.BACK)
            )

            cardItemsList.shuffle()
            listGameItems.postValue(cardItemsList)

            delay(2500)

            cardItemsList.forEachIndexed { index, item ->
                cardItemsList[index] = item.copy(flipState = FlipState.FRONT)
            }

            listGameItems.postValue(cardItemsList)
        }
    }

    fun itemFlipped(it: CardItem) {
        viewModelScope.launch {
            activeItems.add(it)
            if (activeItems.size == 2) {
                delay(300)
                if (activeItems[0].resId == activeItems[1].resId) {
                    val firstIndex = listGameItems.value!!.indexOf(activeItems[0])
                    val secondIndex = listGameItems.value!!.indexOf(activeItems[1])
                    listGameItems.value!![firstIndex] =
                        activeItems[0].copy(flipState = FlipState.INVISIBLE)

                    listGameItems.value!![secondIndex] =
                        activeItems[1].copy(flipState = FlipState.INVISIBLE)
                    listGameItems.postValue(listGameItems.value)
                } else {
                    listGameItems.postValue(listGameItems.value)
                }
                activeItems.clear()
                if (listGameItems.value!!.none { it.flipState != FlipState.INVISIBLE }) {
                    delay(250)
                    startGame()
                }
            }
        }
    }


    companion object {
        val APPS_FLYER_KEY = "5TpDN42YE5C8vyDSVvTRKg"
    }


}