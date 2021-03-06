object SimUDuck {
  abstract class Duck(val name: String) {
    def fly
    def quack
    def display = println(s"I look like $name")
  }

  trait FlyBehavior {
    def fly
  }

  trait FlyWithWings extends FlyBehavior {
    def fly = println("I can fly!")
  }

  trait FlyNoWay extends FlyBehavior {
    def fly = println("I can't fly")
  }

  trait QuackBehavior {
    def quack
  }

  trait Quack extends QuackBehavior {
    def quack = println("It is quacking!")
  }

  trait Squeak extends QuackBehavior {
    def quack = println("Rubber duck can only squeak!")
  }

  trait MallardDuck extends Duck with FlyWithWings with Quack {
  }
  
  val m = new Duck("Mallard") with MallardDuck    //> m  : SimUDuck.Duck with SimUDuck.MallardDuck = SimUDuck$$anonfun$main$1$$ano
                                                  //| n$1@5d6f64b1
  m.fly                                           //> I can fly!
  m.quack                                         //> It is quacking!
  m.display                                       //> I look like Mallard

}