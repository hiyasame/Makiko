package interceptors.common

import interceptors.AtMessageEventInterceptor
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.at
import net.mamoe.mirai.message.data.buildMessageChain

/**
 * team.redrock.makiko.interceptors.common.EmptyMessageInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/10 14:22
 */
object UnknownMessageInterceptor : AtMessageEventInterceptor {
    override suspend fun intercept(event: GroupMessageEvent, at: List<At>, atBot: Boolean): Boolean {
        if (atBot) {
            event.group.sendMessage(buildMessageChain {
                append(event.sender.at())
                append(" 不知道你要干什么，试试对我发送help吧!")
            })
            return true
        }
        return false
    }
}