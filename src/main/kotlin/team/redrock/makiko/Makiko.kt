package team.redrock.makiko

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.utils.info
import team.redrock.makiko.interceptors.InterceptorHandler
import team.redrock.makiko.service.ServiceManager

object Makiko : KotlinPlugin(
    JvmPluginDescription(
        id = "team.redrock.makiko",
        name = "Makiko",
        version = "0.1.0",
    ) {
        author("Rain")
        info("""卷娘可爱捏""")
    }
) {
    override fun onEnable() {
        logger.info { "Plugin loaded" }
        InterceptorHandler.init(this)
        ServiceManager.init(this)
    }
}