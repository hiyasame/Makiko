package interceptors.eggshell.extra

import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain
import interceptors.AtMessageContainsInterceptor

/**
 * team.redrock.makiko.interceptors.eggshell.extra.LiaoInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 20:19
 */
class LiaoInterceptor : AtMessageContainsInterceptor("廖老师") {
    override val reply: MessageChain = buildMessageChain {
        append("廖老师，400学妹的神！")
    }
}