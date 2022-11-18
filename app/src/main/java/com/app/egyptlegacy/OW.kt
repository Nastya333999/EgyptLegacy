package com.app.egyptlegacy

import android.content.Context
import com.onesignal.OneSignal

class OW(context: Context, uid: String) {
    init {
        OneSignal.initWithContext(context)
        OneSignal.setAppId(ID)
        OneSignal.setExternalUserId(uid)
    }

    fun send(campaign: String, deep: String) {
        when {
            campaign == "null" && deep == "null" -> {
                OneSignal.sendTag("key2", "organic")
            }
            deep != "null" -> {
                OneSignal.sendTag("key2", deep.replace("myapp://", "").substringBefore("/"))
            }
            campaign != "null" -> {
                OneSignal.sendTag("key2", campaign.substringBefore("_"))
            }
        }
    }

    companion object {
        private const val ID = "53ff3d13-6350-4be3-93f0-c47ae73d2d4d"
    }
}