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
            Android是移动开发部从事安卓系统APP研发的同学，新兴的鸿蒙系统也支持安卓应用。Android开发仅需要普通的电脑即可。
            """.trimIndent()
        )
    }
}