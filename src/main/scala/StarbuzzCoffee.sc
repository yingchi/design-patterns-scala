object StarbuzzCoffee {
  trait Beverage {
    var description = "Unknown Beverage"
    def getDescription(): String = description
    def cost(): Double
  }
  
  trait CondimentDecorator extends Beverage {
    def getDescription(): String
  }
  
  class Espresso() extends Beverage {
    description = "Espresso"
    def cost(): Double = 1.99
  }
  
  class Mocha(val beverage: Beverage) extends CondimentDecorator {
    override def getDescription() = beverage.getDescription() + ", Mocha"
    def cost(): Double = 0.2 + beverage.cost()
  }
  
  val bev1 = new Espresso()                       //> bev1  : StarbuzzCoffee.Espresso = StarbuzzCoffee$Espresso@5d6f64b1
  println(bev1.getDescription()+" $" + bev1.cost())
                                                  //> Espresso $1.99
  val bev1Mocha = new Mocha(bev1)                 //> bev1Mocha  : StarbuzzCoffee.Mocha = StarbuzzCoffee$Mocha@2133c8f8
  println(bev1Mocha.getDescription()+" $" + bev1Mocha.cost())
                                                  //> Espresso, Mocha $2.19
}