object RemoteControl {
  trait Command {
    def execute()
    def undo()
  }
  class NoCommand() extends Command {
    def execute() {}
    def undo() {}
  }
  class LightOnCommand(val light: Light) extends Command {
    def execute() { light.on() }
    def undo() { light.off() }
  }
  class LightOffCommand(val light: Light) extends Command {
    def execute() { light.off() }
    def undo() { light.on() }
  }
  class Light() {
    def on() { println("Light on!") }
    def off() { println("Light off!") }
  }

  class StereoOnCommand(val stereo: Stereo) extends Command {
    def execute() { stereo.on() }
    def undo() { stereo.off() }
  }
  class StereoOffCommand(val stereo: Stereo) extends Command {
    def execute() { stereo.off() }
    def undo() { stereo.on() }
  }
  class Stereo(val loc: String) {
    def on() { println("Stereo at " + loc + " is on!") }
    def off() { println("Stereo at " + loc + " is off!") }
  }
  class TvOnCommand(val tv: Tv) extends Command {
    def execute() { tv.on() }
    def undo() { tv.off() }
  }
  class TvOffCommand(val tv: Tv) extends Command {
    def execute() { tv.off() }
    def undo() { tv.on() }
  }
  class Tv(val loc: String) {
    def on() { println("TV at " + loc + " is on!") }
    def off() { println("TV at " + loc + " is off!") }
  }

  class RemoteControl() {
    val num = 7
    val onCommands = new Array[Command](num)
    val offCommands = new Array[Command](num)
    val noCommand = new NoCommand()
    var undoCommand: Command = noCommand

    for (i <- 0 until num) {
      onCommands(i) = noCommand
      offCommands(i) = noCommand
    }

    def setCommand(slot: Int, onCommand: Command, offCommand: Command) {
      onCommands(slot) = onCommand
      offCommands(slot) = offCommand
    }
    def onButtonWasPressed(slot: Int) {
      onCommands(slot).execute()
      undoCommand = onCommands(slot)
    }
    def offButtonWasPressed(slot: Int) {
      offCommands(slot).execute()
      undoCommand = offCommands(slot)
    }
    def undoButtonWasPressed() {
      undoCommand.undo()
      undoCommand = noCommand
    }

    override def toString(): String = {
      val strBuffer = new StringBuilder()
      strBuffer ++= "\n------ Remote Control ------\n"
      for (i <- 0 until num)
        strBuffer ++= ("[slot " + i + "]" +
          onCommands(i).getClass().getName() + " " +
          offCommands(i).getClass().getName() + "\n")
      strBuffer.toString
    }
  }

  class CeilingFan(val loc: String) {
    val HIGH = 3
    val MEDIUM = 2
    val LOW = 1
    val OFF = 0
    var speed = OFF

    def high() { speed = HIGH; println("Speed -> " + HIGH) }
    def medium() { speed = MEDIUM; println("Speed -> " + MEDIUM) }
    def low() { speed = LOW; println("Speed -> " + LOW) }
    def off() { speed = OFF; println("Speed -> " + OFF) }
    def getSpeed() = speed
  }

  class CeilingFanHighCommand(val cf: CeilingFan) extends Command {
    var prevState = -1
    def execute() {
      prevState = cf.getSpeed()
      cf.high()
    }
    def undo() {
      if (prevState == cf.HIGH) cf.high()
      else if (prevState == cf.MEDIUM) cf.medium()
      else if (prevState == cf.LOW) cf.low()
      else if (prevState == cf.OFF) cf.off()
    }
  }

  class CeilingFanMediumCommand(val cf: CeilingFan) extends Command {
    var prevState = -1
    def execute() {
      prevState = cf.getSpeed()
      cf.medium()
    }
    def undo() {
      if (prevState == cf.HIGH) cf.high()
      else if (prevState == cf.MEDIUM) cf.medium()
      else if (prevState == cf.LOW) cf.low()
      else if (prevState == cf.OFF) cf.off()
    }
  }

  class CeilingFanOffCommand(val cf: CeilingFan) extends Command {
    var prevState = -1
    def execute() {
      prevState = cf.getSpeed()
      cf.off()
    }
    def undo() {
      if (prevState == cf.HIGH) cf.high()
      else if (prevState == cf.MEDIUM) cf.medium()
      else if (prevState == cf.LOW) cf.low()
      else if (prevState == cf.OFF) cf.off()
    }
  }

  class MacroCommand(comms: Array[Command]) extends Command {
    def execute() {
      for (i <- 0 until comms.size) comms(i).execute()
    }
    def undo() {
      for (i <- 0 until comms.size) comms(i).undo()
    }
  }

  val ceilingFan = new CeilingFan("Living Room")  //> ceilingFan  : RemoteControl.CeilingFan = RemoteControl$CeilingFan@5d6f64b1
  val ceilingFanHigh = new CeilingFanHighCommand(ceilingFan)
                                                  //> ceilingFanHigh  : RemoteControl.CeilingFanHighCommand = RemoteControl$Ceili
                                                  //| ngFanHighCommand@2133c8f8
  val ceilingFanMedium = new CeilingFanMediumCommand(ceilingFan);
                                                  //> ceilingFanMedium  : RemoteControl.CeilingFanMediumCommand = RemoteControl$C
                                                  //| eilingFanMediumCommand@43a25848
  val ceilingFanOff = new CeilingFanOffCommand(ceilingFan);
                                                  //> ceilingFanOff  : RemoteControl.CeilingFanOffCommand = RemoteControl$Ceiling
                                                  //| FanOffCommand@3ac3fd8b
  val light = new Light()                         //> light  : RemoteControl.Light = RemoteControl$Light@5594a1b5
  val lightOnComm = new LightOnCommand(light)     //> lightOnComm  : RemoteControl.LightOnCommand = RemoteControl$LightOnCommand@
                                                  //| 6a5fc7f7
  val lightOffComm = new LightOffCommand(light)   //> lightOffComm  : RemoteControl.LightOffCommand = RemoteControl$LightOffComma
                                                  //| nd@3b6eb2ec

  val stereo = new Stereo("Living Room")          //> stereo  : RemoteControl.Stereo = RemoteControl$Stereo@1e643faf
  val stereoOnComm = new StereoOnCommand(stereo)  //> stereoOnComm  : RemoteControl.StereoOnCommand = RemoteControl$StereoOnComma
                                                  //| nd@6e8dacdf
  val stereoOffComm = new StereoOffCommand(stereo)//> stereoOffComm  : RemoteControl.StereoOffCommand = RemoteControl$StereoOffCo
                                                  //| mmand@7a79be86
  val tv = new Tv("Living Room")                  //> tv  : RemoteControl.Tv = RemoteControl$Tv@34ce8af7
  val tvOnComm = new TvOnCommand(tv)              //> tvOnComm  : RemoteControl.TvOnCommand = RemoteControl$TvOnCommand@b684286
  val tvOffComm = new TvOffCommand(tv)            //> tvOffComm  : RemoteControl.TvOffCommand = RemoteControl$TvOffCommand@880ec6
                                                  //| 0
  val partyOn = Array(lightOnComm, stereoOnComm, tvOnComm)
                                                  //> partyOn  : Array[RemoteControl.Command] = Array(RemoteControl$LightOnComman
                                                  //| d@6a5fc7f7, RemoteControl$StereoOnCommand@6e8dacdf, RemoteControl$TvOnComma
                                                  //| nd@b684286)
  val partyOff = Array(lightOffComm, stereoOffComm, tvOffComm)
                                                  //> partyOff  : Array[RemoteControl.Command] = Array(RemoteControl$LightOffComm
                                                  //| and@3b6eb2ec, RemoteControl$StereoOffCommand@7a79be86, RemoteControl$TvOffC
                                                  //| ommand@880ec60)
  val partyOnMacro = new MacroCommand(partyOn)    //> partyOnMacro  : RemoteControl.MacroCommand = RemoteControl$MacroCommand@edf
                                                  //| 4efb
  val partyOffMacro = new MacroCommand(partyOff)  //> partyOffMacro  : RemoteControl.MacroCommand = RemoteControl$MacroCommand@2f
                                                  //| 7a2457

  val remote = new RemoteControl                  //> remote  : RemoteControl.RemoteControl = 
                                                  //| ------ Remote Control ------
                                                  //| [slot 0]RemoteControl$NoCommand RemoteControl$NoCommand
                                                  //| [slot 1]RemoteControl$NoCommand RemoteControl$NoCommand
                                                  //| [slot 2]RemoteControl$NoCommand RemoteControl$NoCommand
                                                  //| [slot 3]RemoteControl$NoCommand RemoteControl$NoCommand
                                                  //| [slot 4]RemoteControl$NoCommand RemoteControl$NoCommand
                                                  //| [slot 5]RemoteControl$NoCommand RemoteControl$NoCommand
                                                  //| [slot 6]RemoteControl$NoCommand RemoteControl$NoCommand
                                                  //| 
  remote.setCommand(0, lightOnComm, lightOffComm)
  remote.setCommand(1, ceilingFanHigh, ceilingFanOff)
  remote.setCommand(2, ceilingFanMedium, ceilingFanOff)

  remote.onButtonWasPressed(0)                    //> Light on!
  remote.onButtonWasPressed(2)                    //> Speed -> 2
  remote.offButtonWasPressed(2)                   //> Speed -> 0
  remote.undoButtonWasPressed()                   //> Speed -> 2
  remote.onButtonWasPressed(1)                    //> Speed -> 3
  println(remote)                                 //> 
                                                  //| ------ Remote Control ------
                                                  //| [slot 0]RemoteControl$LightOnCommand RemoteControl$LightOffCommand
                                                  //| [slot 1]RemoteControl$CeilingFanHighCommand RemoteControl$CeilingFanOffComm
                                                  //| and
                                                  //| [slot 2]RemoteControl$CeilingFanMediumCommand RemoteControl$CeilingFanOffCo
                                                  //| mmand
                                                  //| [slot 3]RemoteControl$NoCommand RemoteControl$NoCommand
                                                  //| [slot 4]RemoteControl$NoCommand RemoteControl$NoCommand
                                                  //| [slot 5]RemoteControl$NoCommand RemoteControl$NoCommand
                                                  //| [slot 6]RemoteControl$NoCommand RemoteControl$NoCommand
                                                  //| 
  remote.undoButtonWasPressed()                   //> Speed -> 2
  remote.setCommand(3, partyOnMacro, partyOffMacro)
  println(remote)                                 //> 
                                                  //| ------ Remote Control ------
                                                  //| [slot 0]RemoteControl$LightOnCommand RemoteControl$LightOffCommand
                                                  //| [slot 1]RemoteControl$CeilingFanHighCommand RemoteControl$CeilingFanOffComm
                                                  //| and
                                                  //| [slot 2]RemoteControl$CeilingFanMediumCommand RemoteControl$CeilingFanOffCo
                                                  //| mmand
                                                  //| [slot 3]RemoteControl$MacroCommand RemoteControl$MacroCommand
                                                  //| [slot 4]RemoteControl$NoCommand RemoteControl$NoCommand
                                                  //| [slot 5]RemoteControl$NoCommand RemoteControl$NoCommand
                                                  //| [slot 6]RemoteControl$NoCommand RemoteControl$NoCommand
                                                  //| 
  println("--- Pushing Macro On---")              //> --- Pushing Macro On---
  remote.onButtonWasPressed(3)                    //> Light on!
                                                  //| Stereo at Living Room is on!
                                                  //| TV at Living Room is on!
  println("--- Pushing Macro Off---")             //> --- Pushing Macro Off---
  remote.offButtonWasPressed(3)                   //> Light off!
                                                  //| Stereo at Living Room is off!
                                                  //| TV at Living Room is off!
}