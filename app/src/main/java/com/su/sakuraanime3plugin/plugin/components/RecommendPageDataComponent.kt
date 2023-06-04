package com.su.sakuraanime3plugin.plugin.components

import android.util.Log
import com.su.mediabox.pluginapi.action.DetailAction
import com.su.mediabox.pluginapi.components.ICustomPageDataComponent
import com.su.mediabox.pluginapi.data.BaseData
import com.su.mediabox.pluginapi.data.MediaInfo1Data
import com.su.mediabox.pluginapi.data.MediaInfo2Data
import com.su.mediabox.pluginapi.data.TagData
import com.su.sakuraanime3plugin.plugin.components.Const.host
import com.su.sakuraanime3plugin.plugin.util.JsoupUtil

/**
 * FileName: RecommendPageDataComponent
 * Founder: Jiang Houren
 * Create Date: 2023/4/18 10:22
 * Profile: 每日推荐
 */
class RecommendPageDataComponent : ICustomPageDataComponent {
    override val pageName: String
        get() = "每日推荐"

    override suspend fun getData(page: Int): List<BaseData> {
        val hostUrl = Const.host + "/recommend/?pagesize=24&pageindex=${page-1}"
        val document = JsoupUtil.getDocument(hostUrl)

        val data = mutableListOf<BaseData>()

        val li = document.select(".lpic").select("li")
        for (liE in li){
            val title =  liE.select("h2").select("a").text()
            val cover = liE.select("img").attr("src")
            val url = liE.select("h2").select("a").attr("href")
            val episode = liE.select("span")[0].text()
            val describe = liE.select("p").text()
            val tag = liE.select("span")[1].text().split("：")[1].split(" ")
            val tags = mutableListOf<TagData>()
            for (type in tag)
                tags.add(TagData(type))
            data.add(MediaInfo2Data(
                    title, cover, host + url, episode, describe, tags
            ).apply {
                action = DetailAction.obtain(url)
            })
        }
        return data
    }

}