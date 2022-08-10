package team.redrock.makiko.interceptors.common

import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain
import team.redrock.makiko.interceptors.AtMessageContainsInterceptor

/**
 * team.redrock.makiko.interceptors.common.LiaoInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 20:19
 */
object LiaoInterceptor : AtMessageContainsInterceptor("廖老师") {
    override val reply: MessageChain = buildMessageChain {
        append("廖老师，400学妹的神！")
    }
}