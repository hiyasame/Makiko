package interceptors.common

import interceptors.AtMessageContainsInterceptor
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain

class JoinInterceptor : AtMessageContainsInterceptor("加入", "报名", "进入",
    withPic = "[mirai:image:{ED21FC6E-073B-3162-5D1D-2C492691C16D}.jpg]") {
    override val reply: MessageChain
        get() = buildMessageChain {
            append("""
                扫描下方二维码, 进入'青春邮约', 选择红岩网校工作站, 让redrocker成为你最骄傲的自称吧~~
            """.trimIndent())
        }
}