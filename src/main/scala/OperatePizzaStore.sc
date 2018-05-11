object OperatePizzaStore {
  import scala.collection.mutable.ArrayBuffer

  abstract class PizzaStore {
    def orderPizza(pizzaType: String) {
      val pizza = createPizza(pizzaType)
      pizza.prepare()
      pizza.bake()
      pizza.cut()
      pizza.box()
    }

    def createPizza(pizzaType: String): Pizza
  }

  abstract class Pizza {
    val dough: Dough
    val sauce: Sauce
    val cheese: Cheese
    var name = "Pizza"
    var veggies = List[Veggie]()

    // we make this abstract, as the ingredients will be taken care by Factory
    def prepare()

    def bake() { println("Bake for 25 minutes at 350") }
    def cut() { println("Cutting the pizza into 8 slices") }
    def box() { println("Place pizza in official PizzaStore box") }
    def getName(): String = name
    def setName(newName: String) { name = newName }
  }

  /**
   * All pizzas are made from the same components,  but each region has a different
   * implementation of those components
   */
  trait PizzaIngredientFactory {
    def createDough(): Dough
    def createSauce(): Sauce
    def createCheese(): Cheese
    def createVeggies(): List[Veggie]
  }

  trait Dough {
    def toString(): String
  }
  trait Sauce {
    def toString(): String
  }
  trait Cheese {
    def toString(): String
  }
  trait Veggie {
    def toString(): String
  }

  class NYPizzaIngredientFactory extends PizzaIngredientFactory {
    def createDough() = new ThinCrustDough()
    def createSauce() = new MarinaraSauce()
    def createCheese() = new ReggianoCheese()
    def createVeggies() = List(new Onion(), new Mushroom())
  }

  class ThinCrustDough extends Dough { override def toString() = "thin crust dough" }
  class MarinaraSauce extends Sauce { override def toString() = "marinara sauce" }
  class ReggianoCheese extends Cheese { override def toString() = "reggiano cheese" }
  class Onion extends Veggie { override def toString() = "onion" }
  class Mushroom extends Veggie { override def toString() = "mushroom" }

  class CheesePizza(val ingredientFactory: PizzaIngredientFactory) extends Pizza {
    val dough = ingredientFactory.createDough()
    val sauce = ingredientFactory.createSauce()
    val cheese = ingredientFactory.createCheese()

    def prepare() {
      println("Preparing " + name)
      println("...Tossing " + dough)
      println("...Adding " + sauce)
    }
  }

  class VeggiePizza(val ingredientFactory: PizzaIngredientFactory) extends Pizza {
    val dough = ingredientFactory.createDough()
    val sauce = ingredientFactory.createSauce()
    val cheese = ingredientFactory.createCheese()
    veggies = ingredientFactory.createVeggies()

    def prepare() {
      println("Preparing " + name)
      println("...Tossing " + dough)
      println("...Adding " + sauce)
      println("...Adding " + veggies.mkString(", "))
    }
  }

  class NYPizzaStore() extends PizzaStore {
    def createPizza(pizzaType: String): Pizza = {
      var pizza: Pizza = null
      val ingredientFactory = new NYPizzaIngredientFactory()

      if (pizzaType == "cheese") {
        pizza = new CheesePizza(ingredientFactory)
        pizza.setName("New York Style Cheese Pizza")
      } else if (pizzaType == "veggie") {
        pizza = new VeggiePizza(ingredientFactory)
        pizza.setName("New York Style Veggie Pizza")
      }
      pizza
    }
  }
  
  val nyStore = new NYPizzaStore()                //> nyStore  : OperatePizzaStore.NYPizzaStore = OperatePizzaStore$NYPizzaStore@
                                                  //| 5d6f64b1
  nyStore.orderPizza("cheese")                    //> Preparing New York Style Cheese Pizza
                                                  //| ...Tossing thin crust dough
                                                  //| ...Adding marinara sauce
                                                  //| Bake for 25 minutes at 350
                                                  //| Cutting the pizza into 8 slices
                                                  //| Place pizza in official PizzaStore box
  nyStore.orderPizza("veggie")                    //> Preparing New York Style Veggie Pizza
                                                  //| ...Tossing thin crust dough
                                                  //| ...Adding marinara sauce
                                                  //| ...Adding onion, mushroom
                                                  //| Bake for 25 minutes at 350
                                                  //| Cutting the pizza into 8 slices
                                                  //| Place pizza in official PizzaStore box
}