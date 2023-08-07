package net.misaka.bot.groupexport

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.PluginConfig
import net.mamoe.mirai.console.data.value
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.contact.nameCardOrNick
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.OtherClientMessageEvent
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.message.data.FileMessage
import net.mamoe.mirai.utils.error
import net.mamoe.mirai.utils.info

object GroupExportPluginConfig: AutoSavePluginConfig("GroupExportConfig") {
    var adminId by value(2749456652L)
}

object GroupExportPlugin : KotlinPlugin(
    JvmPluginDescription(
        id = "net.misaka.bot.groupexport",
        name = "GroupExportPlugin",
        version = "0.1.0",
    ) {
        author("SkyRain")
    }
) {
    override fun onEnable() {
        globalEventChannel().filter { it is FriendMessageEvent && it.friend.id == GroupExportPluginConfig.adminId && it.message.contentToString().trim().startsWith("/export", false) }.subscribeAlways<FriendMessageEvent> { event ->
            val messageAttr = message.contentToString()
            var groupId: Long? = null
            try {
                groupId = messageAttr.split(" ")[1].toLongOrNull()
            }catch (e: Exception){
                logger.error("Failed to read group id: $messageAttr", e)
            }
            if(groupId == null){
                event.friend.sendMessage("群号解析失败！")
                return@subscribeAlways
            }
            val resultMap = StringBuilder()
            bot.getGroupOrFail(groupId).members.forEach {
                resultMap.append(it.id)
                resultMap.append(" "+it.nameCardOrNick)
                resultMap.append("\n")
            }
            event.friend.sendMessage(resultMap.toString())
        }
    }
}