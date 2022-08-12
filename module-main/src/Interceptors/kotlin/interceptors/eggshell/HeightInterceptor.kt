package interceptors.eggshell

import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain
import interceptors.AtMessageContainsInterceptor
import java.io.File

/**
 * team.redrock.makiko.interceptors.eggshell.HeightInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 14:53
 */
class HeightInterceptor : AtMessageContainsInterceptor("身高", withPic = "[mirai:image:{5693464F-1FD9-A92F-E1C4-74003F76D383}.gif]") {
    override val reply: MessageChain = buildMessageChain {
        append(
            """
            不会吧，不会吧，不会真的有人要问女生的身高吧。
        """.trimIndent()
        )
    }
}