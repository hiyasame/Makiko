package interceptors.common

import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain
import interceptors.AtMessageContainsInterceptor

/**
 * team.redrock.makiko.interceptors.common.SREInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 13:47
 */
class SREInterceptor : AtMessageContainsInterceptor("运维", "sre") {
    override val reply: MessageChain = buildMessageChain {
        append(
            """
            运维安全部的成员技术高超，多亏了他们的存在，网校的系统才维持着安全稳定！点这里了解更多：https://redrock.team/s/qa/sre
        """.trimIndent()
        )
    }
}