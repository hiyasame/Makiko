package team.redrock.makiko

import com.ndhzs.hotfix.HotfixKotlinPlugin
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.utils.info

object Makiko : HotfixKotlinPlugin(
    JvmPluginDescription(
        id = "team.redrock.makiko",
        name = "Makiko",
        version = "0.1.0",
    ) {
        author("Rain")
        info("""卷娘可爱捏""")
    }
) {
    override fun onHotfixEnable() {
        logger.info { "Plugin loaded" }
    }
}