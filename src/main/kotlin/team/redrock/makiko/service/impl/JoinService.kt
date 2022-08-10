package team.redrock.makiko.service.impl

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupEvent
import net.mamoe.mirai.event.events.MemberJoinEvent
import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.message.code.MiraiCode.deserializeMiraiCode
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.at
import net.mamoe.mirai.message.data.buildMessageChain
import team.redrock.makiko.service.Service
import team.redrock.makiko.utils.getPic
import java.io.File

/**
 * team.redrock.makiko.service.impl.JoinService
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 15:08
 */
object JoinService : Service {

    override fun init(plugin: KotlinPlugin) {
        GlobalEventChannel.parentScope(plugin).subscribeAlways<MemberJoinEvent> {
            plugin.launch {
                delay(200)
                val msg = buildMessageChain {
                    append(member.at())
                    append("欢迎到来！这里是红岩网校工作站的新生群，有什么问题都可以问我哦！没有头绪就试试对我发送help吧。")
                    append("[mirai:image:{B043013D-64E6-5A5B-7939-D60089AFB9FE}.mirai]".deserializeMiraiCode())
                }
                group.sendMessage(msg)
            }
        }
    }
}