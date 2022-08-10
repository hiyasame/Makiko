package team.redrock.makiko.service

import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import team.redrock.makiko.service.impl.CleanMemberService
import team.redrock.makiko.service.impl.DirtyWordService
import team.redrock.makiko.service.impl.JoinService
import team.redrock.makiko.service.impl.RepeatMessageService

/**
 * team.redrock.makiko.feature.FeatureHandler
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 15:04
 */
object ServiceManager {

    private val services = listOf(
        CleanMemberService,
        JoinService,
        RepeatMessageService,
        DirtyWordService
    )

    fun init(plugin: KotlinPlugin) {
        services.forEach { it.init(plugin) }
    }
}