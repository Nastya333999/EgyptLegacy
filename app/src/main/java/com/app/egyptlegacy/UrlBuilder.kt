package com.app.egyptlegacy

import android.content.res.Resources
import android.net.Uri.encode
import java.util.*

class UrlBuilder {
    companion object {

        fun buildUrl(
            res: Resources, baseFileData: String,
            gadid: String, apps: MutableMap<String, Any>?,
            deep: String, uid: String?
        ): String {
            var url = baseFileData + "?"
            url += createPArametr(
                res.getString(R.string.qws),
                res.getString(R.string.zxc)
            ) + "&"

            url += createPArametr(
                res.getString(R.string.nbh),
                TimeZone.getDefault().id
            ) + "&"

            url += createPArametr(
                res.getString(R.string.kjsdnv_r), gadid
            ) + "&"

            url += createPArametr(
                res.getString(R.string.ngbmvcx), deep
            ) + "&"


            url += createPArametr(
                res.getString(R.string.svdbfngmhjk),
                if (deep != "null") "deeplink" else apps?.get("media_source").toString()
            ) + "&"

            url += createPArametr(
                res.getString(R.string.u4567h),
                uid
            ) + "&"

            url += createPArametr(
                res.getString(R.string.cvbdnfv6),
                apps?.get("adset_id").toString()
            ) + "&"


            url += createPArametr(
                res.getString(R.string.qwsdfcvghyu7),
                apps?.get("campaign_id").toString()
            ) + "&"

            url += createPArametr(
                res.getString(R.string.sdfvgbh7),
                apps?.get("campaign").toString()
            ) + "&"
            url += createPArametr(
                res.getString(R.string.sdfghj8),
                apps?.get("adset").toString()
            ) + "&"
            url += createPArametr(
                res.getString(R.string.dfvbhjki9),
                apps?.get("adgroup").toString()
            ) + "&"

            url += createPArametr(
                res.getString(R.string.dcvghjk9),
                apps?.get("orig_cost").toString()
            ) + "&"

            url += createPArametr(
                res.getString(R.string.qawsdxcfg5),
                apps?.get("af_siteid").toString()
            )

            return F.PR + url
        }

        private fun createPArametr(key: String?, value: String?): String {

            return (encode(key, null).toString() + "="
                    + encode(value, null))
        }


    }
}