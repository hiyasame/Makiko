# Makiko

红岩网校招新Bot & 吉祥物

> 卷娘可爱捏

## 使用Javascript进行热修插件开发

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

### 兼容层

如果你要使用，使用`useCompatibilityLayer()`获取兼容层，通过解构赋值拿到你需要使用的函数

兼容层提供`d.ts`类型定义，为ts/js提供补全能力

~~~js
const { subscribeEvent, GroupMessageEvent } = useCompatibilityLayer()
~~~

