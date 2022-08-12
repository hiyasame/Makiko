# Makiko

红岩网校招新Bot & 吉祥物

> 卷娘可爱捏

## 如何参与？
因为本项目使用了非官方的 mirai 热修插件，
详细用法可以参考该项目：[mirai-hotfix](https://github.com/985892345/mirai-hotfix)，
本文只介绍如何快速参与，以下步骤分为 [Jvm 平台](#Jvm平台)和 [Js 平台](#Js平台)

## Jvm平台
### 一、前提
首先需要你有一定的 java 或 kotlin 基础，并了解过 [mirai](https://github.com/mamoe/mirai) 的写法
> 若不是很了解的话，建议先看一下 mirai 的官方文档：https://github.com/mamoe/mirai  
> 如果你只是想简单提个 pr，加个彩蛋，可以直接看官方的 api 文档：https://github.com/mamoe/mirai/blob/dev/docs/CoreAPI.md

### 二、添加依赖
版本号：[![](https://jitpack.io/v/ColdRain-Moro/Makiko.svg)](https://jitpack.io/#ColdRain-Moro/Makiko)  
使用 idea 新建一个 kotlin 项目，然后在你的项目中添加如下设置：
````kotlin
repositories {
  maven("https://jitpack.io")
}

dependencies { 
  // mirai 基础包
  implementation("com.github.ColdRain-Moro:Makiko:base-0.1-alpha1") // 版本号请看上方 jitpack 标签
}
````
如果你已成功编译，那么就可以开始开发了

### 三、书写代码
因为使用了 mirai-hotfix，每个项目的需要遵守以下写法才能成功被加载：

1、在 `java` 或者 `kotlin` **根目录**下创建一个类，并实现 `JarEntrance` 接口
````kotlin
// 注意：该类作为 jar 的启动类，需要写在 java 或 kotlin 包的根目录下
// 这是 kotlin 写法，java 写法类似，kotlin 100% 兼容 java
class Test : JarEntrance {
  override fun CommandSender.onFixLoad() {
    // 这是 jar 被加载时的回调
  }
  
  override fun CommandSender.onFixUnload(): Boolean {
    // 这是 jar 需要被卸载时的回调
    // 返回 true 表示能被卸载，false 则反之
    // 为了在出问题时能及时修护，请一定要在这里清理所有会影响卸载的对象引用！
    // 注意：请不要使用静态变量和 object 单例类，防止无法被回收
  }
}
````
2、接下来就与 mirai 官方的写法完全一致，你可以在这里学习：https://github.com/mamoe/mirai/blob/dev/docs/CoreAPI.md  
3、你可以获取 `Makiko` 这个全局单例，他是已经自带的一个 mirai 插件
> 例子：  
> 比如你想回复一些关键词  
> ````kotlin
> class Test : JarEntrance {
> 
>   private var mListener: Listener<GroupMessageEvent>? = null
> 
>   override fun CommandSender.onFixLoad() {
>     mListener = GlobalEventChannel
>       .filter {
>         it is GroupMessageEvent
>           && it.group === Makiko.groupEnrollOrTest // 该群会自动判断是否是测试群还是招生大群，方便测试
>       }.subscribeAlways {
>         if (message.contentEquals("3g")) {
>           sendMessage("重现3g荣光，我辈义不容辞！")
>       }
>     }
>   }
> 
>   override fun CommandSender.onFixUnload(): Boolean {
>     // 这里需要取消监听，并 return true，不然会导致 jar 包无法被卸载
>     mListener?.complete()
>     return true
>   }
> }
> ````
更多用法请参考 mirai 官网 [api 文档](https://github.com/mamoe/mirai/blob/dev/docs/CoreAPI.md)

### 四、打包发送
使用 gradle 自带的 jar 命令即可打包，然后在 build/libs 下，将打包好的 jar 包私发给我们即可

**注意:** 如果你使用了其他第三方依赖，请将该第三方依赖的 jar 包一起发给我们
> 不知道怎们获得第三方库的依赖？  
> 1、点击 idea 右侧的 `External Libraries`  
> 2、找到你依赖的第三方库  
> 3、右键点击并选择 `Open Library Settings`，然后你可以在打开的窗口中看到该 jar 包的位置

## Js平台


