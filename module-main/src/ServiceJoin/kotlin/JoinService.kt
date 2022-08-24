import com.ndhzs.hotfix.handler.suffix.jar.JarEntrance
import kotlinx.coroutines.delay
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.Listener
import net.mamoe.mirai.event.events.MemberJoinEvent
import net.mamoe.mirai.message.code.MiraiCode.deserializeMiraiCode
import net.mamoe.mirai.message.data.at
import net.mamoe.mirai.message.data.buildMessageChain

/**
 * team.redrock.makiko.service.impl.JoinService
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 15:08
 */
class JoinService : JarEntrance {

    private var mListener: Listener<MemberJoinEvent>? = null
    
    override fun CommandSender.onFixLoad() {
        mListener = GlobalEventChannel.subscribeAlways {
            delay(200)
            val msg = buildMessageChain {
                append(member.at())
                append("""
                    欢迎到来！这里是红岩网校工作站的新生群，有什么问题都可以问我哦！没有头绪就试试对我发送help吧。
                    记得修改群昵称哦! (年级-学院-姓名)
                    """.trimIndent())
                append("[mirai:image:{027F9B67-10EF-A3FD-B1B2-5EF132D5926E}.jpg]".deserializeMiraiCode())
            }
            group.sendMessage(msg)
        }
    }
    
    override fun CommandSender.onFixUnload(): Boolean {
        mListener?.complete()
        mListener = null
        return true
    }
}