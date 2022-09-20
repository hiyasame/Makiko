package interceptors.common

import interceptors.AtMessageContainsInterceptor
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain

/**
 * interceptors.common.IOSInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/14 13:18
 */
class IOSInterceptor : AtMessageContainsInterceptor("ios") {
    override val reply: MessageChain = buildMessageChain {
        append(
            """
                iOS是移动开发部从事苹果系统APP研发的子部门，拥有着强大的苹果生态与无限的可能。想从App Store上看到自己的应用吗？想成为下一个Apple Developer吗？来iOS吧！
            """.trimIndent()
        )
    }
}