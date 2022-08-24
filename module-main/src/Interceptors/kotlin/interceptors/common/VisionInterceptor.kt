package interceptors.common

import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain
import interceptors.AtMessageContainsInterceptor

/**
 * team.redrock.makiko.interceptors.common.VisionInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 13:38
 */
class VisionInterceptor : AtMessageContainsInterceptor("视觉") {
    override val reply: MessageChain = buildMessageChain {
        append(
            """
            视觉设计部是创意设计者，用色彩渲染世界。点这里了解更多：https://redrock.team/s/qa/design
            """.trimIndent()
        )
    }
}