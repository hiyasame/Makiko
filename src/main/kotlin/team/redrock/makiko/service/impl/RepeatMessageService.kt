package team.redrock.makiko.service.impl

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.code.MiraiCode.deserializeMiraiCode
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import team.redrock.makiko.service.Service
import team.redrock.makiko.utils.*
import java.io.File
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/**
 * team.redrock.makiko.service.impl.RepeatMessageService
 * makiko
 * 图片刷屏 & 复读
 *
 * @author 寒雨
 * @since 2022/8/9 16:32
 */
object RepeatMessageService : Service {

    // 保存上三次聊天事件，一旦内容一致则发送提示信息并清栈
    private val groupLocals = mutableMapOf<Long, GroupLocals>()
    // 冷却，5秒内发送第二张图片则增加1次，满3次进行警告，满6次禁言
    private val timeBaffle = Baffle.of(5, TimeUnit.SECONDS)
    //
    private var imgRepeatMessageRemind: Image? = null

    override fun init(plugin: KotlinPlugin) {
        GlobalEventChannel
            .parentScope(plugin)
            .subscribeAlways<GroupMessageEvent> {
                handleRepeatMessage()
                handleRepeatImage()
        }
        plugin.launch {
            while (true) {
                // 5分钟清除一次标记
                delay(TimeUnit.MINUTES.toMillis(5))
                groupLocals.values.forEach { (_, counts) ->
                    counts.clear()
                }
            }
        }
    }

    /**
     * 重复图片刷屏逻辑处理
     */
    private suspend fun GroupMessageEvent.handleRepeatImage() = coroutineScope {
        if (message.findIsInstance<Image>() != null) {
            if (!timeBaffle.hasNext(sender.id.toString())) {
                groupLocals
                    .computeIfAbsent(group.id) { GroupLocals() }
                    .userImgSendCounts
                    .apply {
                        val count = computeIfAbsent(sender.id) { 0 }
                        put(sender.id, count + 1)
                        if (count >= 4) {
                            group.doPermissionAction {
                                group.sendMessage(getRepeatImageMutedRemind())
                                sender.mute(30)
                            }
                            put(sender.id, 0)
                        } else if (count == 2) {
                            group.sendMessage(getRepeatImageRemind(sender))
                        }
                    }
            }
        }
    }

    /**
     * 复读逻辑处理
     */
    private suspend fun GroupMessageEvent.handleRepeatMessage() {
        val stack = groupLocals.computeIfAbsent(group.id) { GroupLocals() }.eventStack
        stack.push(this)
        if (stack.size >= 3) {
            if (!stack.any { it.message.contains(Image) }
                && stack.all { it.message.contentToString() == stack[0].message.contentToString() }) {
                // 清栈
                stack.clear()
                // 提示信息
                group.sendMessage(getRepeatMessageRemind(group))
            } else {
                stack.removeFirst()
            }
        }
    }

    private fun getRepeatImageRemind(sender: Member): MessageChain {
        return buildMessageChain {
            append(At(sender))
            append("再发图刷屏我就把你禁言了喔！")
            append("[mirai:image:{4F42035D-B38A-D810-03AD-4F79FC433D3A}.gif]".deserializeMiraiCode())
        }
    }

    private fun getRepeatImageMutedRemind(): MessageChain {
        return buildMessageChain {
            append("呜呜，怎么就是有人不听卷娘的话呀？")
            append("[mirai:image:{54116638-CC61-C72D-3C15-7EC70AE26366}.jpg]".deserializeMiraiCode())
        }
    }

    private suspend fun getRepeatMessageRemind(group: Group): MessageChain {
        return buildMessageChain {
            append("咋又有复读机出现了啊")
            append(
                imgRepeatMessageRemind ?: requestImage(
                    "https://persecution-1301196908.cos.ap-chongqing.myqcloud.com/image_bed/repeat.jpg",
                    group
                ).also { imgRepeatMessageRemind = it }
            )
        }
    }

    data class GroupLocals(
        val eventStack: Stack<GroupMessageEvent> = Stack(),
        val userImgSendCounts: MutableMap<Long, Int> = ConcurrentHashMap()
    )
}