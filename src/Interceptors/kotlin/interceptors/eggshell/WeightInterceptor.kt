package interceptors.eggshell

import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain
import interceptors.AtMessageContainsInterceptor
import java.io.File

/**
 * team.redrock.makiko.interceptors.eggshell.WeightInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 14:55
 */
object WeightInterceptor : interceptors.AtMessageContainsInterceptor("体重", withPic = "[mirai:image:{AFFAE4A0-C12E-DE38-EF5B-D3303C84B8D4}.gif]") {
    override val reply: MessageChain = buildMessageChain {
        append(
            """
            emmmm你要不问问别的？
        """.trimIndent()
        )
    }

}