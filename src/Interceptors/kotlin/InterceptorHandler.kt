import com.ndhzs.hotfix.handler.suffix.jar.JarEntrance
import interceptors.AtMessageEventInterceptor
import interceptors.MessageEventInterceptor
import interceptors.common.*
import interceptors.eggshell.AgeInterceptor
import interceptors.eggshell.HeightInterceptor
import interceptors.eggshell.WeightInterceptor
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.Listener
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.At

/**
 * team.redrock.makiko.interceptors.InterceptorHandler
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 11:42
 */
class InterceptorHandler : JarEntrance {

    private val atMessageEventInterceptors = arrayListOf<AtMessageEventInterceptor>()

    private val messageEventInterceptors = arrayListOf<MessageEventInterceptor>(AtMessageTrigger(this))

    private var listener: Listener<GroupMessageEvent>? = null

    override suspend fun CommandSender.onFixLoad() {
        println("test")
        listener = GlobalEventChannel.subscribeAlways {
            interceptMessage(it)
        }
        // init common
        registerAtMessageInterceptor(
            HelpInterceptor(),
            RedrockInterceptor(),
            ProductInterceptor(),
            VisionInterceptor(),
            FrontEndInterceptor(),
            BackEndInterceptor(),
            MobileInterceptor(),
            SREInterceptor(),
            StudyInterceptor(),
            JoinInterceptor(),
            AchievementInterceptor(),
            LiaoInterceptor(),
            InformationSecurityInterceptor()
        )
        // init eggshell
        registerAtMessageInterceptor(
            AgeInterceptor(),
            HeightInterceptor(),
            WeightInterceptor(),
        )

        registerAtMessageInterceptor(
            UnknownMessageInterceptor()
        )
    }

    override suspend fun CommandSender.onFixUnload(): Boolean {
        listener?.complete()
        listener = null
        return true
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

class AtMessageTrigger(private val handler: InterceptorHandler) : MessageEventInterceptor {
    override suspend fun intercept(event: GroupMessageEvent): Boolean {
        val at = event.message.filterIsInstance<At>()
        if (at.isNotEmpty()) {
            handler.interceptAtMessage(event, at)
        }
        return false
    }
}