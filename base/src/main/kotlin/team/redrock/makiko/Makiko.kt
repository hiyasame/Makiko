package team.redrock.makiko

import com.ndhzs.hotfix.HotfixKotlinPlugin
import com.ndhzs.hotfix.handler.suffix.jar.JarHotfixSuffixHandler
import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.utils.info
import team.redrock.makiko.script.JsSuffixHandler

object Makiko : HotfixKotlinPlugin(
    JvmPluginDescription(
        id = "team.redrock.makiko",
        name = "Makiko",
        version = "0.1.0",
    ) {
        author("Rain")
        info("""卷娘可爱捏""")
    },
    typeHandlers = arrayOf(JarHotfixSuffixHandler, JsSuffixHandler)
) {
    // 获取群聊
    val groupEnrollOrTest: Group
        get() {
            if (Bot.instances.size == 1) {
                val bot = Bot.instances[0]
                return when (bot.id) {
                    3534390965L -> bot.getGroupOrFail(904552771L) // 说明在内测群
                    237325281L -> bot.getGroupOrFail(611248395L) // 说明在内测群
                    else -> error("未登录")
                }
            } else {
                error("有多个账号时不允许使用")
            }
        }
    
    override fun onHotfixEnable() {
        logger.info { "Plugin loaded" }
    }
}