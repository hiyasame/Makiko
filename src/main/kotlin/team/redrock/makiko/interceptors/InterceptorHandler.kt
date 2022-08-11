package team.redrock.makiko.interceptors

import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.Listener
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import team.redrock.makiko.interceptors.common.*
import team.redrock.makiko.interceptors.eggshell.AgeInterceptor
import team.redrock.makiko.interceptors.eggshell.HeightInterceptor
import team.redrock.makiko.interceptors.eggshell.WeightInterceptor

/**
 * team.redrock.makiko.interceptors.InterceptorHandler
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 11:42
 */
object InterceptorHandler {

    private val atMessageEventInterceptors = arrayListOf<AtMessageEventInterceptor>()

    private val messageEventInterceptors = arrayListOf<MessageEventInterceptor>(AtMessageTrigger)

    private lateinit var listener: Listener<GroupMessageEvent>

    fun init(plugin: KotlinPlugin) {
        listener = GlobalEventChannel.parentScope(plugin).subscribeAlways {
            interceptMessage(it)
        }
        // init common
        registerAtMessageInterceptor(
            HelpInterceptor,
            RedrockInterceptor,
            ProductInterceptor,
            VisionInterceptor,
            FrontEndInterceptor,
            BackEndInterceptor,
            MobileInterceptor,
            SREInterceptor,
            StudyInterceptor,
            JoinInterceptor,
            AchievementInterceptor,
            LiaoInterceptor,
            InformationSecurityInterceptor
        )
        // init eggshell
        registerAtMessageInterceptor(
            AgeInterceptor,
            HeightInterceptor,
            WeightInterceptor,
        )

        registerAtMessageInterceptor(
            UnknownMessageInterceptor
        )
    }

    private fun registerAtMessageInterceptor(vararg interceptor: AtMessageEventInterceptor) {
        interceptor.forEach {
            atMessageEventInterceptors.add(it)
        }
    }

    private fun registerMessageEventInterceptor(vararg interceptor: MessageEventInterceptor) {
        interceptor.forEach {
            messageEventInterceptors.add(it)
        }
    }

    suspend fun interceptAtMessage(event: GroupMessageEvent, at: List<At>) {
        atMessageEventInterceptors.any { it.intercept(event, at, at.any { a -> a.target == event.bot.id }) }
    }

    private suspend fun interceptMessage(event: GroupMessageEvent) {
        messageEventInterceptors.any { it.intercept(event) }
    }
}

object AtMessageTrigger : MessageEventInterceptor {
    override suspend fun intercept(event: GroupMessageEvent): Boolean {
        val at = event.message.filterIsInstance<At>()
        if (at.isNotEmpty()) {
            InterceptorHandler.interceptAtMessage(event, at)
        }
        return false
    }
}