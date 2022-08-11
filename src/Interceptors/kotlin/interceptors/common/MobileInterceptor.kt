package interceptors.common

import interceptors.AtMessageContainsInterceptor
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain

/**
 * team.redrock.makiko.interceptors.common.MobileInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 13:43
 */
class MobileInterceptor : AtMessageContainsInterceptor("移动") {
    override val reply: MessageChain = buildMessageChain {
        append(
            """
            移动开发部全是APP的开发者，想“解锁APP的无限可能”吗？点这里了解更多：https://sourl.cn/UXwvrk
        """.trimIndent()
        )
    }
}