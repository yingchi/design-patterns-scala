import scala.collection.mutable.ArrayBuffer

object MenuMerge {
  class MenuItem(val name: String, val desc: String, val veggie: Boolean, val price: Double) {
    def getName() = name
    def getDesc() = desc
    def getPrice() = price
    def isVeggie() = veggie
  }

  class PancakeMenu {
    val menuItems = ArrayBuffer[MenuItem]()
    addItem("Regular Pancake Breakfast", "Pancake with fried eggs, sausage", false, 2.99)
    addItem("Blueberry Pancakes", "Pancakes made with fresh blueberryies", true, 3.49)
    def addItem(name: String, desc: String, veg: Boolean, price: Double) {
      menuItems += new MenuItem(name, desc, veg, price)
    }
    def getMenuItems() = menuItems
    def createIterator() = menuItems.iterator
  }

  class DinerMenu {
    val MAX_ITEMS = 6
    var numItems = 0
    val menuItems = new Array[MenuItem](MAX_ITEMS)
    addItem("Vegetarian BLT", "Fake bacon with lettuce & tomato on whole wheat", true, 2.99)
    addItem("Hotdog", "A hot dog, with saurkraut, relish, onions, topped with cheese", false, 3.05)
    def addItem(name: String, desc: String, veg: Boolean, price: Double) {
      if (numItems >= MAX_ITEMS) {
        println("Sorry, menu is full! Can only hold " + MAX_ITEMS + " items")
      } else {
        menuItems(numItems) = new MenuItem(name, desc, veg, price)
        numItems += 1
      }
    }
    def getMenuItems() = menuItems
    def createIterator() = menuItems.take(numItems).iterator
  }

  class Waitress(pMenu: PancakeMenu, dMenu: DinerMenu) {
    def printMenu() {
      val pIterator = pMenu.createIterator()
      val dIterator = dMenu.createIterator()
      println("Menu\n----\nBreakfast")
      printMenu(pIterator)
      println("\nLunch")
      printMenu(dIterator)
    }

    def printMenu(iterator: Iterator[MenuItem]) {
      while (iterator.hasNext) {
        val menuItem = iterator.next()
        print(menuItem.getName() + ", ")
        print(menuItem.getPrice() + " -- ")
        println(menuItem.getDesc())
      }
    }
  }

  val pMenu = new PancakeMenu()                   //> pMenu  : MenuMerge.PancakeMenu = MenuMerge$PancakeMenu@1175e2db
  val dMenu = new DinerMenu()                     //> dMenu  : MenuMerge.DinerMenu = MenuMerge$DinerMenu@30c7da1e
  val waitress = new Waitress(pMenu, dMenu)       //> waitress  : MenuMerge.Waitress = MenuMerge$Waitress@5b464ce8
  waitress.printMenu()                            //> Menu
                                                  //| ----
                                                  //| Breakfast
                                                  //| Regular Pancake Breakfast, 2.99 -- Pancake with fried eggs, sausage
                                                  //| Blueberry Pancakes, 3.49 -- Pancakes made with fresh blueberryies
                                                  //| 
                                                  //| Lunch
                                                  //| Vegetarian BLT, 2.99 -- Fake bacon with lettuce & tomato on whole wheat
                                                  //| Hotdog, 3.05 -- A hot dog, with saurkraut, relish, onions, topped with chee
                                                  //| se
}