package team.redrock.makiko.interceptors.eggshell

import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain
import team.redrock.makiko.interceptors.AtMessageContainsInterceptor
import java.io.File

/**
 * team.redrock.makiko.interceptors.eggshell.AgeInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 14:34
 */
object AgeInterceptor : AtMessageContainsInterceptor(
    "年龄", "年纪", "芳龄",
    withPic = " [mirai:image:{946779CA-A297-0475-5876-A5E78A692B52}.mirai]"
) {

    override val reply: MessageChain = buildMessageChain {
        append(
            """
            随便问女生年龄可真是不礼貌！
        """.trimIndent()
        )
    }

}