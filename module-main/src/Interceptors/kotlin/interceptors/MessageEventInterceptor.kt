package interceptors

import net.mamoe.mirai.event.events.GroupMessageEvent

/**
 * team.redrock.makiko.interceptors.MessageEventInterceptor
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 11:32
 */
interface MessageEventInterceptor {
    /**
     * 拦截消息事件
     *
     * @param event 群消息事件
     * @return 是否消费，消费之后不会向下传递
     */
    suspend fun intercept(event: GroupMessageEvent): Boolean
}