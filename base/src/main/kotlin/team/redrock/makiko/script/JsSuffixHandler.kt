package team.redrock.makiko.script

import com.ndhzs.hotfix.handler.suffix.IHotfixSuffixHandler
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.rootDir
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import org.mozilla.javascript.Context
import org.mozilla.javascript.Function
import org.mozilla.javascript.ScriptableObject
import java.io.File

/**
 * team.redrock.makiko.script.JSSuffixHandler
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/11 17:18
 */
object JsSuffixHandler : IHotfixSuffixHandler {
    override val typeSuffix: String
        get() = "js"

    private lateinit var scope: ScriptableObject

    override fun CommandSender.onFixLoad(file: File, pluginClassLoader: ClassLoader) {
        Context.enter().apply {
            optimizationLevel = -1
            languageVersion = Context.VERSION_ES6
            scope = initStandardObjects()
            evaluateReader(scope, file.reader(), null, 1, null)

            (scope.get("onLoad", scope) as Function).call(this, scope, scope, arrayOf())
        }
    }

    override fun CommandSender.onFixUnload(file: File): Boolean {
        try {
            Context.getCurrentContext().apply {
                (scope.get("onUnload", scope) as Function).call(this, scope, scope, arrayOf())
            }
        } catch (t: Throwable) {
            return false
        }
        return true
    }
}