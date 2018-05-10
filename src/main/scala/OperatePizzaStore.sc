object OperatePizzaStore {
  import scala.collection.mutable.ArrayBuffer
  abstract class PizzaStore {
    def orderPizza(pizzaType: String) {
      val pizza = createPizza(pizzaType)
      pizza.prepare()
      pizza.bake()
      pizza.cut()
      pizza.box()
      pizza
    }

    def createPizza(pizzaType: String): Pizza
  }

  abstract class Pizza {
    val name: String
    val dough: String
    val sauce: String
    val toppings = new ArrayBuffer[String]()

    def prepare() {
      println("Preparing " + name)
      println("Tossing dough...: " + dough)
      println("Adding sauce...: " + sauce)
      println("Adding toppings: " + toppings.mkString(","))
    }

    def bake() { println("Bake for 25 minutes at 350") }
    def cut() { println("Cutting the pizza into 8 slices") }
    def box() { println("Place pizza in official PizzaStore box") }
    def getName(): String = name
  }

  class NYStypeCheesePizza() extends Pizza {
    val name = "NY Style Sauce and Cheese Pizza"
    val dough = "Thin Crust Dough"
    val sauce = "Marinara Sauce"
    toppings += "Grated Reggiano Cheese"
  }

  class NYStypeVeggiePizza() extends Pizza {
    val name = "NY Style Veggie Pizza"
    val dough = "Thin Crust Dough"
    val sauce = "Tomato Sauce"

    toppings += "Grated Reggiano Cheese"
  }

  class NYPizzaStore extends PizzaStore {
    def createPizza(pizzaType: String): Pizza = {
      var pizza: Pizza = null
      if (pizzaType == "cheese") {
        pizza = new NYStypeCheesePizza
      } else if (pizzaType == "veggie") {
        pizza = new NYStypeVeggiePizza
      }
      pizza
    }
  }
  val nyStore = new NYPizzaStore()                //> nyStore  : OperatePizzaStore.NYPizzaStore = OperatePizzaStore$NYPizzaStore@
                                                  //| 5d6f64b1
  nyStore.orderPizza("cheese")                    //> Preparing NY Style Sauce and Cheese Pizza
                                                  //| Tossing dough...: Thin Crust Dough
                                                  //| Adding sauce...: Marinara Sauce
                                                  //| Adding toppings: Grated Reggiano Cheese
                                                  //| Bake for 25 minutes at 350
                                                  //| Cutting the pizza into 8 slices
                                                  //| Place pizza in official PizzaStore box
}