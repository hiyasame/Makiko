package team.redrock.makiko.interceptors.common

import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.buildMessageChain
import team.redrock.makiko.interceptors.AtMessageEventInterceptor
import team.redrock.makiko.utils.chain
import team.redrock.makiko.utils.requestImage

/**
 * team.redrock.makiko.interceptors.common.InformationSecurityInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/10 17:10
 */
object InformationSecurityInterceptor : AtMessageEventInterceptor {

    private val keys = listOf(
        "信安协会",
        "信息安全协会"
    )

    private var image: Image? = null

    override suspend fun intercept(event: GroupMessageEvent, at: List<At>, atBot: Boolean): Boolean {
        if (atBot) {
            val message = event.message
                .filterNot { it is At }
                .chain()
                .contentToString()
            if (keys.any { message.contains(it) }) {
                event.group.sendMessage(buildMessageChain {
                    append(image ?: requestImage(
                        "https://persecution-1301196908.cos.ap-chongqing.myqcloud.com/image_bed/信安.jpg",
                        event.group
                    ).also { image = it })
                })
                return true
            }
        }
        return false
    }
}