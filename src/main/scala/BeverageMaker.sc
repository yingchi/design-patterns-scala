object BeverageMaker {
  import java.io.IOException
  abstract class CaffeineBeverage {
    final def prepareRecipe() {
      boilWater()
      brew()
      pourInCup()
      addCondiments()
    }

    def brew()
    def addCondiments()
    def boilWater() { println("Boiling water") }
    def pourInCup() { println("Pouring into cup") }
  }

  class Coffee() extends CaffeineBeverage {
    def brew() { println("Dripping coffee through filter") }
    def addCondiments() { println("Adding sugar and milk") }
  }

  class Tea() extends CaffeineBeverage {
    def brew() { println("Steeping in the tea") }
    def addCondiments() { println("Adding lemon") }
  }

  abstract class CaffeineBeverageWithHook {
    final def prepareRecipe() {
      boilWater()
      brew()
      pourInCup()
      if (customerWantsCondiments()) addCondiments()
    }

    def brew()
    def addCondiments()
    def boilWater() { println("Boiling water") }
    def pourInCup() { println("Pouring into cup") }
    def customerWantsCondiments(): Boolean = true
  }

  class TeaWithHook() extends CaffeineBeverageWithHook {
    def brew() { println("Steeping in the tea") }
    def addCondiments() { println("Adding lemon") }
    override def customerWantsCondiments(): Boolean = {
      val ans = getUserInput()
      ans.toLowerCase().startsWith("y")
    }
    def getUserInput(): String = {
      var ans: String = null
      println("Would you like lemon with your tea (y/n)? ")
      try {
        // ans = scala.io.StdIn.readLine()
        ans = "y"   // just for worksheet purpose
      } catch {
        case ioe: IOException => println("IO error trying to read your answer")
      }
      if (ans == null) "no"
      ans
    }
  }

  val coffee = new Coffee()                       //> coffee  : BeverageMaker.Coffee = BeverageMaker$Coffee@5d6f64b1
  val tea = new Tea()                             //> tea  : BeverageMaker.Tea = BeverageMaker$Tea@2133c8f8
  coffee.prepareRecipe()                          //> Boiling water
                                                  //| Dripping coffee through filter
                                                  //| Pouring into cup
                                                  //| Adding sugar and milk
  tea.prepareRecipe()                             //> Boiling water
                                                  //| Steeping in the tea
                                                  //| Pouring into cup
                                                  //| Adding lemon
  
  val teaHook = new TeaWithHook()                 //> teaHook  : BeverageMaker.TeaWithHook = BeverageMaker$TeaWithHook@43a25848
  println("\nMaking tea...")                      //> 
                                                  //| Making tea...
  teaHook.prepareRecipe()                         //> Boiling water
                                                  //| Steeping in the tea
                                                  //| Pouring into cup
                                                  //| Would you like lemon with your tea (y/n)? 
                                                  //| Adding lemon
}