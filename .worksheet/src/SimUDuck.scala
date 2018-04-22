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
  };import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(689); 
  
  val m = new Duck("Mallard") with MallardDuck;System.out.println("""m  : SimUDuck.Duck with SimUDuck.MallardDuck = """ + $show(m ));$skip(8); 
  m.fly;$skip(10); 
  m.quack;$skip(12); 
  m.display}

}
