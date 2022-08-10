package team.redrock.makiko.interceptors.common

import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain
import team.redrock.makiko.interceptors.AtMessageContainsInterceptor

/**
 * team.redrock.makiko.interceptors.common.FrontEndInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 13:41
 */
object FrontEndInterceptor : AtMessageContainsInterceptor("前端") {
    override val reply: MessageChain = buildMessageChain {
        append(
            """
            前端顾名思义就是前端工程师，“打造最完美的前端界面”全靠他们！点这里了解更多：https://sourl.cn/LABysT
            """.trimIndent()
        )
    }
}