import com.ndhzs.hotfix.handler.suffix.jar.JarEntrance
import kotlinx.coroutines.CoroutineScope
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.contact.MemberPermission
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.Listener
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import org.mozilla.javascript.Context
import org.mozilla.javascript.ScriptableObject
import team.redrock.makiko.Makiko
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * JsShell
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/12 14:37
 */
class JsShell : JarEntrance {

    private val codeRunner by lazy { CodeRunner(Makiko) }

    override fun CommandSender.onFixLoad() {
        codeRunner.init()
    }

    override fun CommandSender.onFixUnload(): Boolean {
        codeRunner.unregister()
        return true
    }
}

class CodeRunner(private val plugin: KotlinPlugin) : CoroutineScope by plugin {

    private var scope: ScriptableObject? = null
    private var context: Context? = null

    private var currentUser: User? = null

    private var listener: Listener<GroupMessageEvent>? = null

    private val singleThreadPool = Executors.newSingleThreadExecutor()

    private suspend inline fun <T> runOnScriptThread(crossinline func: () -> T): T = suspendCoroutine {
        singleThreadPool.submit {
            try {
                it.resume(func())
            } catch (t: Throwable) {
                it.resumeWithException(t)
            }
        }
    }

    fun init() {
        listener = GlobalEventChannel.parentScope(plugin).subscribeAlways {
            if (sender.permission >= MemberPermission.ADMINISTRATOR) {
                val msg = message.contentToString()
                if (msg == "/shell") {
                    if (scope == null) {
                        context = runOnScriptThread {
                            Context.enter().apply {
                                optimizationLevel = -1
                                languageVersion = Context.VERSION_ES6
                                scope = initStandardObjects()
                            }
                        }
                        currentUser = sender
                        group.sendMessage(message.quote() + "已进入JavaScript Shell")
                    } else {
                        runOnScriptThread {
                            Context.exit()
                        }
                        context = null
                        scope = null
                        currentUser = null
                        group.sendMessage(message.quote() + "已退出JavaScript Shell")
                    }
                    return@subscribeAlways
                }
                if (sender == currentUser) {
                    kotlin.runCatching {
                        val res = runOnScriptThread {
                            context!!.evaluateString(scope, msg, null, 1, null)
                        }
                        group.sendMessage("返回值: $res")
                    }.onFailure {
                        group.sendMessage("脚本执行异常: " + it.javaClass.name + ": ${it.message}")
                    }
                }
            }
        }
    }

    fun unregister() {
        listener?.complete()
    }
}
