import scala.collection.mutable.ArrayBuffer

object MenuComposite {
  abstract class MenuComponent {
    def add(menuComponent: MenuComponent) {
      throw new UnsupportedOperationException()
    }
    def remove(menuComponent: MenuComponent) {
      throw new UnsupportedOperationException()
    }
    def getChild(i: Int): MenuComponent = {
      throw new UnsupportedOperationException()
    }
    def getName(): String = {
      throw new UnsupportedOperationException()
    }
    def getDescription(): String = {
      throw new UnsupportedOperationException()
    }
    def getPrice(): Double = {
      throw new UnsupportedOperationException()
    }
    def print() {
      throw new UnsupportedOperationException()
    }
  }

  class MenuItem(val name: String, val desc: String, val veggie: Boolean, val price: Double)
      extends MenuComponent {
    override def getName() = name
    override def getDescription() = desc
    override def getPrice() = price
    def isVeggie() = veggie
    override def print() {
      Console.print("  " + getName())
      if (isVeggie()) {
        Console.print("(v)")
      }
      println(", " + getPrice())
      println(" -- " + getDescription())
    }
  }

  class Menu(val name: String, val desc: String) extends MenuComponent {
    val menuComponents = new ArrayBuffer[MenuComponent]()
    override def add(menuComponent: MenuComponent) {
      menuComponents += menuComponent
    }
    override def remove(menuComponent: MenuComponent) {
      menuComponents -= menuComponent
    }
    override def getChild(i: Int) = {
      menuComponents(i)
    }
    override def getName() = name
    override def getDescription() = desc
    override def print() {
      Console.print("\n" + getName())
      println(", " + getDescription())
      println("---------------------")
      val iter = menuComponents.iterator
      while (iter.hasNext) {
        val menuComponent = iter.next()
        menuComponent.print()
      }
    }
  }

  class Waitress(allMenus: MenuComponent) {
    def printMenu() {
      allMenus.print()
    }
  }

  val pancakeHouseMenu = new Menu("PANCAKE HOUSE MENU", "Breakfast")
                                                  //> pancakeHouseMenu  : MenuComposite.Menu = MenuComposite$Menu@2b05039f
  val dinerMenu = new Menu("DINER MENU", "Lunch") //> dinerMenu  : MenuComposite.Menu = MenuComposite$Menu@30c7da1e
  val dessertMenu = new Menu("DESSERT MENU", "Dessert")
                                                  //> dessertMenu  : MenuComposite.Menu = MenuComposite$Menu@5b464ce8

  val allMenus = new Menu("ALL MENUS", "All menus combined")
                                                  //> allMenus  : MenuComposite.Menu = MenuComposite$Menu@57829d67
  allMenus.add(pancakeHouseMenu)
  allMenus.add(dinerMenu)

  dinerMenu.add(new MenuItem("Pasta", "Spaghetti with Marinara Sauce", true, 3.89))
  dinerMenu.add(dessertMenu)
  dessertMenu.add(new MenuItem("Ice Cream", "Vanilla ice cream", true, 1.79))
  val waitress = new Waitress(allMenus)           //> waitress  : MenuComposite.Waitress = MenuComposite$Waitress@19dfb72a
  waitress.printMenu()                            //> 
                                                  //| ALL MENUS, All menus combined
                                                  //| ---------------------
                                                  //| 
                                                  //| PANCAKE HOUSE MENU, Breakfast
                                                  //| ---------------------
                                                  //| 
                                                  //| DINER MENU, Lunch
                                                  //| ---------------------
                                                  //|   Pasta(v), 3.89
                                                  //|  -- Spaghetti with Marinara Sauce
                                                  //| 
                                                  //| DESSERT MENU, Dessert
                                                  //| ---------------------
                                                  //|   Ice Cream(v), 1.79
                                                  //|  -- Vanilla ice cream
}