package interceptors.common

import interceptors.AtMessageContainsInterceptor
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain

/**
 * team.redrock.makiko.interceptors.common.RedrockInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 13:27
 */
class RedrockInterceptor : AtMessageContainsInterceptor("红岩网校介绍") {
    override val reply: MessageChain = buildMessageChain {
        append(
            """
        我们红岩网校是重邮团委旗下唯一一个从事互联网开发运营的学生组织，不仅培育出了一大批优秀的人才，还荣获多个荣誉奖项，深受大企青睐！点这里了解更多：https://sourl.cn/gnwtjW
        """.trimIndent()
        )
    }
}