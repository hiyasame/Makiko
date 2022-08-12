import com.ndhzs.hotfix.handler.suffix.jar.JarEntrance
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.message.code.MiraiCode.deserializeMiraiCode
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain
import team.redrock.makiko.Makiko
import team.redrock.makiko.utils.doPermissionAction
import team.redrock.makiko.utils.theBot
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * team.redrock.makiko.service.impl.CleanMemberService
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 15:24
 */
class CleanMemberService : JarEntrance {

    private var job: Job? = null
    private var executedFlag = false

    // 21-Android-寒雨
    // 21 Android 寒雨
    // Android-寒雨
    // Android 寒雨
    private val regex = "(\\d+(\\s|-))?\\S+(\\s|-)\\S+".toRegex()

    override suspend fun CommandSender.onFixLoad() {
        job = Makiko.launch {
            while (true) {
                // 一小时检查一次
                delay(10000)
                handleRemind()
                handleClean()
                delay(TimeUnit.HOURS.toMillis(1))
            }
        }
    }

    override suspend fun CommandSender.onFixUnload(): Boolean {
        job?.cancel()
        job = null
        return true
    }

    private suspend fun handleClean() {
        if (checkTime(3, 0) || checkTime(7, 0)) {
            theBot.groups.forEach { group ->
                if (group.id != 904552771L) {
                    return@forEach
                }
                group.members.forEach { member ->
                    if (!regex.matches(member.nameCard)) {
                        if (group.botPermission > member.permission) {
                            member.kick("昵称不合规范")
                            delay(5000)
                        }
                    }
                }
            }
        }
    }

    private suspend fun handleRemind() {
        if (checkTime(2, 8) || checkTime(6, 8)) {
            theBot.groups.forEach {
                it.sendMessage(getRemindMsg())
            }
        }
    }

    private suspend fun getRemindMsg(): MessageChain {
        return buildMessageChain {
            append(
                "今天晚上12点卷娘可要清理掉不按规矩改名的同学了哦！"
            )
            append(
                "[mirai:image:{C2B58DC7-E3F7-6A91-27E2-DE6DD2AE8873}.jpg]".deserializeMiraiCode()
            )
        }
    }

    /**
     * 检查时间
     *
     * @param day 这里从Sunday开始为1，Saturday为7
     * @param hour
     * @return
     */
    private fun checkTime(day: Int? = null, hour: Int? = null): Boolean {
        val calendar = Calendar.getInstance()
        return (day == null || day == calendar.get(Calendar.DAY_OF_WEEK)) && (hour == null || hour == calendar.get(
            Calendar.HOUR_OF_DAY
        ))
    }

}