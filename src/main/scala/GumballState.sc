object GumballState {
  trait State {
    def insertQuarter()
    def ejectQuarter()
    def turnCrank()
    def dispense()
  }

  class NoQuarterState(val machine: GumballMachine) extends State {
    def insertQuarter() {
      println("You inserted a quarter")
      machine.setState(machine.hasQuarterState)
    }
    def ejectQuarter() {
      println("You haven't inserted a quarter")
    }
    def turnCrank() {
      println("You turned, but there's no quarter")
    }
    def dispense() { println("You need to pay first") }
  }

  class HasQuarterState(val machine: GumballMachine) extends State {
    val r = new scala.util.Random(System.currentTimeMillis())
    
    def insertQuarter() {
      println("You can't insert another quarter")
    }
    def ejectQuarter() {
      println("Quarter returned")
      machine.setState(machine.noQuarterState)
    }
    def turnCrank() {
      println("You turned...")
      val winner = r.nextInt(10)
      if (winner == 0 & machine.count > 0) machine.setState(machine.winnerState)
      else machine.setState(machine.soldState)
    }
    def dispense() { println("No gumball dispensed") }
  }

  class SoldState(val machine: GumballMachine) extends State {
    def insertQuarter() {
      println("Please wait, we're already giving you a gumball")
    }
    def ejectQuarter() {
      println("Sorry, you already turned the crank")
    }
    def turnCrank() {
      println("Turning twice doesn't get you another gumball!")
    }
    def dispense() {
      machine.releaseBall()
      if (machine.count > 0) machine.setState(machine.noQuarterState)
      else {
        println("Oops, out of gumballs!")
        machine.setState(machine.soldOutState)
      }
    }
  }
  
  class WinnerState(val machine: GumballMachine) extends State {
    def insertQuarter() {
      println("Please wait, we're already giving you the gumballs")
    }
    def ejectQuarter() {
      println("Sorry, you already turned the crank")
    }
    def turnCrank() {
      println("Turning twice doesn't get you another gumball!")
    }
    def dispense() {
      println("---*****----\nYOU WIN! Releasing two gumballs to you!")
      machine.releaseBall()
      if (machine.count > 0) {
        machine.releaseBall()
        machine.setState(machine.noQuarterState)
      }
      else {
        println("Oops, out of gumballs!")
        machine.setState(machine.soldOutState)
      }
    }
  }

  class SoldOutState(val machine: GumballMachine) extends State {
    def insertQuarter() {
      println("You can't insert a quarter, the machine is sold out")
    }
    def ejectQuarter() {
      println("You can't eject, you haven't inserted a quarter yet")
    }
    def turnCrank() {
      println("You turned, but there are no gumballs")
    }
    def dispense() { println("No gumball dispensed") }
  }

  class GumballMachine(var count: Int) {
    val soldOutState = new SoldOutState(this)
    val noQuarterState = new NoQuarterState(this)
    val hasQuarterState = new HasQuarterState(this)
    val soldState = new SoldState(this)
    val winnerState = new WinnerState(this)
    var state = if (count > 0) noQuarterState else soldOutState

    def insertQuarter() { state.insertQuarter() }
    def ejectQuarter() { state.ejectQuarter() }
    def turnCrank() { state.turnCrank(); state.dispense() }
    def setState(newState: State) { this.state = newState }
    def releaseBall() {
      println("A gumball comes rolling out the slot...")
      if (count != 0) count -= 1
    }
    
    override def toString() = "\nThere is " + count +
    " gumballs left.\nAnd the state is " + state.getClass().getSimpleName
  }

  val gumballMachine = new GumballMachine(5)      //> gumballMachine  : GumballState.GumballMachine = 
                                                  //| There is 5 gumballs left.
                                                  //| And the state is NoQuarterState
  gumballMachine.insertQuarter()                  //> You inserted a quarter
  gumballMachine.turnCrank()                      //> You turned...
                                                  //| ---*****----
                                                  //| YOU WIN! Releasing two gumballs to you!
                                                  //| A gumball comes rolling out the slot...
                                                  //| A gumball comes rolling out the slot...
  println(gumballMachine)                         //> 
                                                  //| There is 3 gumballs left.
                                                  //| And the state is NoQuarterState
  
  gumballMachine.insertQuarter()                  //> You inserted a quarter
  gumballMachine.ejectQuarter()                   //> Quarter returned
  gumballMachine.turnCrank()                      //> You turned, but there's no quarter
                                                  //| You need to pay first
  gumballMachine.insertQuarter()                  //> You inserted a quarter
  gumballMachine.insertQuarter()                  //> You can't insert another quarter
  gumballMachine.turnCrank()                      //> You turned...
                                                  //| ---*****----
                                                  //| YOU WIN! Releasing two gumballs to you!
                                                  //| A gumball comes rolling out the slot...
                                                  //| A gumball comes rolling out the slot...
  gumballMachine.insertQuarter()                  //> You inserted a quarter
  gumballMachine.turnCrank()                      //> You turned...
                                                  //| A gumball comes rolling out the slot...
                                                  //| Oops, out of gumballs!
  gumballMachine.insertQuarter()                  //> You can't insert a quarter, the machine is sold out
  gumballMachine.turnCrank()                      //> You turned, but there are no gumballs
                                                  //| No gumball dispensed
  
  gumballMachine.insertQuarter()                  //> You can't insert a quarter, the machine is sold out
  gumballMachine.turnCrank()                      //> You turned, but there are no gumballs
                                                  //| No gumball dispensed
  println(gumballMachine)                         //> 
                                                  //| There is 0 gumballs left.
                                                  //| And the state is SoldOutState
  
  
}