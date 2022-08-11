package utils

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.withLock
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.messageChainOf
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import okhttp3.*
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * team.redrock.makiko.utils.Utils
 * makiko
 *
 * @author 寒雨
 * @since 2022/8/9 12:08
 */
fun List<Message>.chain(): MessageChain {
    return messageChainOf(*this.toTypedArray())
}

private val okHttpClient = OkHttpClient()

suspend fun getPic(pic: String, contact: Contact): Image = coroutineScope {
    return@coroutineScope this::class.java.classLoader.getResourceAsStream(pic)
            ?.toExternalResource()
            ?.uploadAsImage(contact) ?: error("图片读取/上传失败")
}

suspend fun requestImage(url: String, contact: Contact): Image {
    val body = suspendCoroutine<ResponseBody> {
        okHttpClient.newCall(
            Request.Builder()
                .url(url)
                .build()
        ).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                it.resumeWithException(e)
            }

            override fun onResponse(call: Call, response: Response) {
                it.resume(response.body!!)
            }
        })
    }
    return body.byteStream().uploadAsImage(contact)
}

suspend fun NormalMember.tryKick(msg: String, permission: MemberPermission = MemberPermission.ADMINISTRATOR) {
    kotlin.runCatching {
        if (permission > this.permission) {
            kick(msg)
        }
    }.onFailure {
        it.printStackTrace()
    }
}

val theBot: Bot
    get() = Bot.instances[0]

inline fun Group.doPermissionAction(perm: MemberPermission = MemberPermission.ADMINISTRATOR, func: () -> Unit) {
    if (botPermission >= perm) func()
}