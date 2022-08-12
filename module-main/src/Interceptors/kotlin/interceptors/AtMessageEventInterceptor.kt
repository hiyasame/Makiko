package interceptors

import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.At

/**
 * team.redrock.makiko.interceptors.AtMessageEventInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 11:30
 */
interface AtMessageEventInterceptor {
    suspend fun intercept(event: GroupMessageEvent, at: List<At>, atBot: Boolean): Boolean
}