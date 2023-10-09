package com.android.cookbook.KotlinBasic

fun main() {

    //var定义变量，val定义常量
    val v1 = Animal("dog",1);
//    var v2 = Animal("cat",2)
    val v4 = Animal();

    var v3 = v1;
    if (v1 == v3) {
        println("v1 is equals v3")
    }else {
        println("v1 is not equals v3")
    }
    //调用实例属性
    println("name: ${v1.name}")
    //修改实例属性
    v1.id = 5

    //调用实例方法
    v1.eat()
    v4.eat()

    //模拟调用类的方法与属性
    println("static value: ${Animal.staticValue}")
    Animal.staticFunc("static function ")
    Animal.Companion.breath()

    //继承的示例代码
    //创建子类的对象
    var dog = Dog("my dog",2)
    //调用子类的方法体会一下重写与重载的不同
    dog.eat()
    dog.eat(food = "fork")
    dog.guardDoor()


//多态的示例程序
    var fatherObj = Animal("Animal",1)
    var sonObj = Dog("My dog",2)

    //调用父类的eat方法
    fatherObj.eat()

    //判断是否可以转换
    if(sonObj is Animal) {
        fatherObj = sonObj
        //调用子类的eat方法
        fatherObj.eat()
    }

    var sonDog: Dog? = fatherObj as? Dog

    if (sonDog != null ) {
        sonDog.guardDoor()
    }else {
        println("can't translate")
    }

    //特殊类示例程序
    var dataBean1 = AnimalBean("AnimalBean",1)
    var dataBean2 = AnimalBean("AnimalBean",1)
    var dataBean3 = Animal("AnimalBean",5)
    var dataBean4 = Animal("AnimalBean",5)


    //data类重写了equals方法，equals和==运算符都是对实例的内容进行比较，实例内容相同则实例相同
    if(dataBean1.equals(dataBean2)) {
        println("dataBean1 equals dataBean2")
    } else {
        println("dataBean1 don't equals dataBean2")
    }

    if(dataBean1 == dataBean2) {
        println("dataBean1 == dataBean2")
    } else {
        println("dataBean1 != dataBean2")
    }

    //Animal类没有重写equals方法，equals和==运算符都是对实例的引用进行比较，引用相同则实例相同
    if(dataBean3.equals(dataBean4)) {
        println("dataBean3 equals dataBean4")
    } else {
        println("dataBean3 don't equals dataBean4")
    }

    if(dataBean3 == dataBean4) {
        println("dataBean3 == dataBean4")
    } else {
        println("dataBean3 != dataBean4")
    }

    //可以像使用静态方法一样调用object实例对象的方法
    //object声明在性能上比伴生对象差一些
    AnimalSingle.doSomething()
    AnimalSingle.hide()

    //创建实现接口的类的实例
    var bird = Bird("My bird",1)
    var fish = Fish()
    //通过实例调用接口中的方法
    bird.eatFood()
    fish.eatFood()

    //通过多态调用接口中的方法
    fun callingEatFood(param: AnimalInterface) {
        param.eatFood()
    }
    callingEatFood(bird)
    callingEatFood(fish)
}

fun sum(item1:Int,item2:Int):Int {
    return item1+item2
}

//传统的if/else语句具有条件判断功能
fun maxValue(v1:Int,v2:Int):Int{
    if(v1 > v2) {
        return v1
    } else {
        return v2
    }
}
//现代语言的if/else语句具有返回值，类似表达式
fun maxValueModern(v1:Int,v2:Int):Int{
    return if(v1 > v2) {
        v1
    } else {
        v2
    }
}

//现代语言的if/else语句具有返回值，类似表达式
//对代码做了优化，使用赋值操作更加能体现出返回值
fun maxValueModernNew(v1:Int,v2:Int) = if(v1 > v2) v1 else v2

fun switchCase(value:Int) = when (value) {
    1 -> "One"
    2 -> "Two"
    3 -> "Three"
    else -> "unKnow"
}

//传统的while循环，语法没有变化
fun whileLoop() {
    var i = 0
    while (i++ < 3) println(i)
}

//现代语言中的循环:for-each语法
fun foreachLoop() {
    var i = 0
    //两个点表示区间语法，until则不包含边界值，使用step关键字控制步进
//    for(i in 1..3 step 1) println(i)
    for(i in 1..3 ) println(i)
    for(i in 0 until 3 step 1) println(i)
    //区间默认是升序，使用downTo关键字控制区间的顺序为降序
    for(i in 3 downTo 1) println(i)
}

//类的构造方法和类名绑定在一起,构造方法的参数在类名后面，构造方法的实现在init块中
//和类名绑定的是主构造方法，constructor定义的是次构造方法，次构造方法必须调用主构造方法
open class Animal(n:String,i:Int) {
    constructor() : this("default",0)
    //类的成员变量,默认生成getter/setter
    var name:String
    var id:Int
    init {
        this.name = n
        this.id = i
    }

    //类的成员方法
    open fun eat() {
        println("$name is eating.")
    }
    //类不支持static关键字，也没有静态概念，使用伴生类来模拟静态成员或者方法
    companion object { //Object是伴生对象的名字
        var staticValue = 0
        fun staticFunc(param:String) {
            println("this is the static function, params: $param")
        }
        fun breath() {
            println("Animal must be breath")
        }
    }
}

//继承时需要调用父类的构造函数来初始化类的成员属性，构造函数可以是主构造函数或者次构造函数
class Dog (n:String,i:Int) : Animal(n,i) {
    //重写父类的方法，注意父类的方法默认final修饰，需要使用open修饰才能被重写
    override fun eat() {
        println("Dog class $name is eating")
        //在子类中调用父类的方法
        super.eat()
    }

    //重载父类的eat方法，注意它们的参数不同
    fun eat(food:String) {
        println("Dog class $name is eating $food")
    }
    //Dog自己定义的方法
    fun guardDoor() {
        println("Dog on guard")
    }

}

class Cat : Animal{
    constructor(n:String,i:Int):super(n,i)
}

//定义data类
data class AnimalBean (val name: String,val id:Int)

//定义object声明，主要用来做单例
object AnimalSingle {
    fun doSomething() {
        println("this is the single Animal class")
    }
    fun hide () = println("this is the hide func of AnimalSingle")
}


//定义接口
interface AnimalInterface {
    fun sleep() = println("Animal is eating")
    fun eatFood()
}

//子类同时继承类和接口
class Bird(name: String,id: Int):Animal(name,id),AnimalInterface {
    override fun eatFood() {
        println("$name is eating food")
    }
}

class Fish:AnimalInterface {
    override fun eatFood() {
        println("Fish is eating food")
    }
}

