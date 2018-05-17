object Adapter {
  trait Duck {
    def quack()
    def fly()
  }
  class MallardDuck() extends Duck {
    def quack() { println("Quack") }
    def fly() { println("I'm flying") }
  }

  trait Turkey {
    def gobble()
    def fly() { println("I'm flying a short distance!") }
  }
  class WildTurkey() extends Turkey {
    def gobble() { println("Gobble") }
  }
  class TurkeyAdapter(val tk: Turkey) extends Duck {
    def quack() { tk.gobble() }
    def fly() {
      for (i <- 0 until 5)
        tk.fly()
    }
  }

  val tk = new WildTurkey()                       //> tk  : Adapter.WildTurkey = Adapter$WildTurkey@5d6f64b1
  val mdDuck = new MallardDuck()                  //> mdDuck  : Adapter.MallardDuck = Adapter$MallardDuck@2133c8f8
  val tkAdpter = new TurkeyAdapter(tk)            //> tkAdpter  : Adapter.TurkeyAdapter = Adapter$TurkeyAdapter@43a25848

  println("The Turkey says ...")                  //> The Turkey says ...
  tk.gobble()                                     //> Gobble
  tk.fly()                                        //> I'm flying a short distance!

  println("\nThe Duck says ...")                  //> 
                                                  //| The Duck says ...
  mdDuck.quack()                                  //> Quack
  mdDuck.fly()                                    //> I'm flying

  println("\nThe TurkeyAdapter says ...")         //> 
                                                  //| The TurkeyAdapter says ...
  tkAdpter.quack()                                //> Gobble
  tkAdpter.fly()                                  //> I'm flying a short distance!
                                                  //| I'm flying a short distance!
                                                  //| I'm flying a short distance!
                                                  //| I'm flying a short distance!
                                                  //| I'm flying a short distance!
}