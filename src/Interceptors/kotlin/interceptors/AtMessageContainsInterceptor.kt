package interceptors

import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.code.MiraiCode.deserializeMiraiCode
import net.mamoe.mirai.message.data.*
import utils.chain

/**
 * team.redrock.makiko.interceptors.AtMessageContainsInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 13:28
 */
abstract class AtMessageContainsInterceptor(vararg key: String, private val withPic: String? = null) :
    interceptors.AtMessageEventInterceptor {

    private var key: Array<out String>

    init {
        this.key = key
    }

    abstract val reply: MessageChain

    override suspend fun intercept(event: GroupMessageEvent, at: List<At>, atBot: Boolean): Boolean {
        val intercept = event.message
            .filterNot { it is At }
            .chain()
            .contentToString()
            .run { key.any { contains(it) } } && atBot
        if (intercept) {
            var msg = At(event.sender.id) + reply
            if (withPic != null) {
                msg += withPic.deserializeMiraiCode()
            }
            event.group.sendMessage(msg)
        }
        return intercept
    }

}