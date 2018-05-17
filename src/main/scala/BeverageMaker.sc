object BeverageMaker {
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
}