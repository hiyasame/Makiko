import com.ndhzs.hotfix.handler.suffix.jar.JarEntrance
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.contact.NormalMember
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.Listener
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.code.MiraiCode.deserializeMiraiCode
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageSource.Key.recall
import net.mamoe.mirai.message.data.buildMessageChain

/**
 * team.redrock.makiko.service.impl.DirtyWordService
 * makiko
 * 脏话 & 政治 & 广告
 *
 * @author 寒雨
 * @since 2022/8/9 18:27
 */
class DirtyWordService : JarEntrance {

    private val politicsKeys = listOf(
        "台湾",
        "台独",
        "两个中国"
    )

    private val ad = listOf(
        "买卡",
        "卖卡",
        "卖移动卡",
        "卖电信卡",
        "买校园卡",
        "卖校园卡",
        "买被子",
        "卖被子",
        "招聘",
        "+v",
        "加v",
        "+V",
        "加V"
    )

    private val dirtyWords = listOf(
                "傻逼",
                "sb",
                "SB",
                "贱货",
                "狗娘养的",
                "鸡巴"
    )
    
    private var mListener: Listener<GroupMessageEvent>? = null
    
    override fun CommandSender.onFixLoad() {
       mListener =  GlobalEventChannel.subscribeAlways {
            val msg = message.contentToString()
            if (politicsKeys.any { msg.contains(it) }) {
                message.recall()
                group.sendMessage(buildMessageChain {
                    append(
                        At(sender) + "我们的群聊，还不支持这么高深话题的讨论哦！" + "[mirai:image:{A4907EFF-A325-7A58-D0B0-A473EE30C0F4}.mirai]".deserializeMiraiCode()
                    )
                })
                return@subscribeAlways
            }
            if (ad.any { msg.contains(it) }) {
                message.recall()
                group.sendMessage(buildMessageChain {
                    append(
                        At(sender)
                          + "打广告是不允许的，哼！"
                          + "[mirai:image:{5DFC77C4-A1C2-8734-8958-30B0C17385BD}.mirai]".deserializeMiraiCode()
                    )
                })
                return@subscribeAlways
            }
            if (dirtyWords.any { msg.contains(it) }) {
                message.recall()
                if (sender is NormalMember) {
                    (sender as NormalMember).kick("不许说脏话")
                }
                return@subscribeAlways
            }
        }
    }
    
    override fun CommandSender.onFixUnload(): Boolean {
        mListener?.complete()
        mListener = null
        return true
    }
}