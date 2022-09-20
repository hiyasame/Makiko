package interceptors.common

import interceptors.AtMessageContainsInterceptor
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain

/**
 * interceptors.common.AndroidIntercepter
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/14 13:16
 */
class AndroidInterceptor : AtMessageContainsInterceptor("android", "安卓") {
    override val reply: MessageChain = buildMessageChain {
        append(
            """
            Android是移动开发部从事安卓系统APP研发的子部门。他们利用Java和Kotlin来创造无限的可能，想在手机拥有一个自己创造的APP吗？加入Android吧！
            """.trimIndent()
        )
    }
}