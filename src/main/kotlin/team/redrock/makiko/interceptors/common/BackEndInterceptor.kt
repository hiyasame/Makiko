package team.redrock.makiko.interceptors.common

import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain
import team.redrock.makiko.interceptors.AtMessageContainsInterceptor

/**
 * team.redrock.makiko.interceptors.common.BackEndInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 13:42
 */
object BackEndInterceptor : AtMessageContainsInterceptor("后端") {
    override val reply: MessageChain = buildMessageChain {
        append(
            """
            后端是数据架构师，千万别小看它，这可是“网校背后最可靠的保障”。点这里了解更多：https://sourl.cn/gZ8rda
            """.trimIndent()
        )
    }
}