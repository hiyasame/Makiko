package team.redrock.makiko.interceptors.common

import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain
import team.redrock.makiko.interceptors.AtMessageContainsInterceptor

/**
 * team.redrock.makiko.interceptors.common.StudyInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 13:48
 */
object StudyInterceptor : AtMessageContainsInterceptor("学习", "收获") {
    override val reply: MessageChain = buildMessageChain {
        append(
            """
            扎实的技术，互联网前沿知识，独一无二的项目经验，志同道合的朋友，都在红岩网校等着你呢！快来提升你的自学能力和沟通能力，强化你的自驱和自制力吧！点这里了解更多：https://sourl.cn/3LnuB5
        """.trimIndent()
        )
    }
}