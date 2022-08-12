package interceptors.common

import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain
import interceptors.AtMessageContainsInterceptor

/**
 * team.redrock.makiko.interceptors.common.AchievementInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 13:57
 */
class AchievementInterceptor : AtMessageContainsInterceptor("成果") {
    override val reply: MessageChain = buildMessageChain {
        append(
            """
            嘿嘿，红岩网校的成果有这些：重邮帮小程序，”重邮小帮手“公众号，“掌上重邮”APP……
            """.trimIndent()
        )
    }
}