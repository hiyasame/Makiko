package interceptors.common

import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain
import interceptors.AtMessageContainsInterceptor

/**
 * team.redrock.makiko.interceptors.common.ProductInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 13:36
 */
class ProductInterceptor : AtMessageContainsInterceptor("产品") {
    override val reply: MessageChain = buildMessageChain {
        append(
            """
            产品策划及运营部是点子不冻港，“产品的设计蓝图都出自他们之手”点这里了解更多：https://redrock.team/s/qa/pm
        """.trimIndent()
        )
    }

}