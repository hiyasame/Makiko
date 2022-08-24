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
> 不知道怎么获得第三方库的依赖？  
> 1、点击 idea 右侧的 `External Libraries`  
> 2、找到你依赖的第三方库  
> 3、右键点击并选择 `Open Library Settings`，然后你可以在打开的窗口中看到该 jar 包的位置

## Js平台

> 本质上是使用rhino引擎实现js调用mirai java api
>
> 支持大部分es6语法，不过还是建议使用babel之类的编译器编译到es5
>
> 考虑到对没有写过java的人不太友好，我会简单的写一层js兼容层

### 生命周期入口

> Makiko主体读取脚本后将在指定的生命周期回调js中定义的生命周期函数
>
> 每一个js插件中都必须要有onLoad，onUnload两个函数供插件回调

~~~js
function onLoad() {
    print("插件已加载")
}

function onUnload() {
    print("插件已卸载")
}
~~~

> 在这两个函数中我们可以注册/注销我们的事件监听器

~~~js
const { GlobalEventChannel }  = net.mamoe.mirai.event
const { GroupMessageEvent } = net.mamoe.mirai.event.events

let listener;

function onLoad() {
    listener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent, function (event) {
        if (event.getMessage().contentEquals("test", false)) {
            event.getGroup().sendMessage("test")
        }
    })
}

function onUnload() {
    listener.cancel()
}
~~~

### 如何在js中调用Java api

#### 导包

> 推荐使用es6解构语法调包
>
> 感觉这样更贴近js的模块化机制

~~~js
const { Thread, Runnable } = java.lang

new Runnable({
    run: () => {
        print("hello world")
    }
}).run()
~~~

> 也可以调用导包方法

~~~js
importPackage(java.lang)
// 之后java.lang包下的类都可以直接调用，不用写全称

const r = new Runnable({
    run: () => {
        print("hello world")
    }
})

new Thread(r).start()
~~~

#### 实现匿名类

传一个带有其所有需要实现的方法的object就好

~~~js
const r = new Runnable({
    run: () => {
        print("hello world")
    }
})
~~~

#### lambda支持

在java所有适用lambda表达式的场景，都可以直接使用es6的lambda表达式，而不用实现这个函数式接口

~~~js
new Thread(new Runnable({
    run: () => {
        print("hello world")
    }
})).start()
// equals
new Thread(() => { print("hello world") }).start()
~~~

#### 多模块 & Typescript

可以使用`webpack`将多个模块打包在一起，建议配合`babel`，`core-js`来取得更好的语法兼容

详情可以参考模板项目: [makiko-ts-plugin-template](https://github.com/ColdRain-Moro/makiko-ts-plugin-template/)

#### 兼容层

> 可参考 [makiko-ts-plugin-template](https://github.com/ColdRain-Moro/makiko-ts-plugin-template/)

直接使用`yarn add makiko-api`即可引入项目，注意打包的时候要一起打进去

目前只简单的封装了一个订阅事件的函数，还有两种事件类型

有什么希望我兼容的可以跟我提

~~~ts
import { GroupMessageEvent, Listener, subscribeEvent } from "makiko-api"

let listener: Listener

/**
 * 插件加载
 */
// @ts-ignore
globalThis.onLoad = () => {
    console.log("Hello World");
    // 订阅事件
    listener = subscribeEvent<GroupMessageEvent>(GroupMessageEvent, (e) => {
        if (e.getMessage().contentToString() == "3G") {
            e.getGroup().sendMessage("重现3g荣光, 我辈义不容辞！")
        }
    })
}

/**
 * 插件卸载
 */
// @ts-ignore
globalThis.onUnload = () => {
    console.log("Goodbye World");
    // 取消订阅
    listener.complete()
}
~~~

### 注意事项

#### 关闭Webpack的函数名称混淆

不关的话生命周期入口函数的名称将被混淆，java层无法获取

#### 不支持CommonJs/ESModule等模块化规范

如你所见，最终的js文件中不允许以任何方式导入/导出模块

~~~js
Object.defineProperty(exports, "__esModule", { value: true });

export {  }
~~~

但你可以使用webpack等打包工具进行模块的合并

#### 测试环境

> 使用mirai-console加载release中的jar包插件，第一次运行会在根目录生成hotfix/makiko文件夹，将你的js文件放入这个文件夹
>
> 在控制台使用指令`fixmakiko reload`加载js文件
> 
> 随便找个qq群测试功能
> 
> 嫌麻烦就算了，直接想当然写，我会帮你们测试并修改一些写错的地方。

### 简单示例

模板项目中的ts代码

由于webpack打包是整个打在一个闭包里，直接声明函数的话java层无法访问，故将函数挂载到`globalThis`

~~~ts
import { GroupMessageEvent, Listener, subscribeEvent } from "makiko-api"

let listener: Listener

/**
 * 插件加载
 */
// @ts-ignore
globalThis.onLoad = () => {
    console.log("Hello World");
    listener = subscribeEvent<GroupMessageEvent>(GroupMessageEvent, (e) => {
        if (e.getMessage().contentToString() == "3G") {
            e.getGroup().sendMessage("重现3g荣光, 我辈义不容辞！")
        }
    })
}

/**
 * 插件卸载
 */
// @ts-ignore
globalThis.onUnload = () => {
    console.log("Goodbye World");
    listener.complete()
}
~~~

效果是我在群里发个阿巴阿巴，机器人就会回我一个歪比巴卜