package interceptors.common

import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain
import interceptors.AtMessageContainsInterceptor

/**
 * team.redrock.makiko.interceptors.common.JoinInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 13:53
 */
class HowToJoinInterceptor : AtMessageContainsInterceptor(
    "招新",
) {
    override val reply: MessageChain = buildMessageChain {
        append(
            """
            网校的招新活动已经开始火热筹备！届时会开设招新宣讲会, 帮助你进一步的了解红岩网校。会上我们还准备了精美的礼品等你来拿, 时刻关注群消息, 一定不要错过哦！

            点这里了解更多：https://redrock.team/s/qa/redrock
        """.trimIndent()
        )
    }
}