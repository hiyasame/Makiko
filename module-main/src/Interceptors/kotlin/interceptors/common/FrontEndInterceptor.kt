package interceptors.common

import interceptors.AtMessageContainsInterceptor
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain

/**
 * team.redrock.makiko.interceptors.common.FrontEndInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 13:41
 */
class FrontEndInterceptor : AtMessageContainsInterceptor("前端") {
    override val reply: MessageChain = buildMessageChain {
        append(
            """
            他们是网站前台的画家。小程序、网站、webApp里都有他们的影子。打造完美的网页是前端的宗旨！ 点这里了解更多：https://redrock.team/s/qa/frontend
            """.trimIndent()
        )
    }
}