object WeatherStation {
  import scala.collection.mutable.ListBuffer
  trait Subject {
    def registerObserver(o: Observer)
    def removeObserver(o: Observer)
    def notifyObservers
  }

  trait Observer {
    def update(temp: Double, humidity: Double, pressure: Double)
  }

  trait DisplayElement {
    def display
  }

  class WeatherData extends Subject {
    var observers = new ListBuffer[Observer]()
    var temp = 0.0
    var humidity = 0.0
    var pressure = 0.0

    def registerObserver(o: Observer) {
      observers += o
    }

    def removeObserver(o: Observer) {
      observers -= o
    }

    def notifyObservers {
      observers.foreach(_.update(temp, humidity, pressure))
    }

    def measurementsChanged = notifyObservers

    // This method is used for internal testing
    def setMeasurements(t: Double, h: Double, p: Double) {
      this.temp = t
      this.humidity = h
      this.pressure = p
      measurementsChanged
    }
  }

  class CurrentConditionDisplay(var subWeather: Subject) extends Observer with DisplayElement {
    var temp = 0.0
    var humidity = 0.0

    subWeather.registerObserver(this)

    def display {
      println(s"Current conditions: $temp F degrees and $humidity % humidity")
    }
    def update(t: Double, h: Double, p: Double) {
      this.temp = t
      this.humidity = h
      display
    }
  };import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(1389); 

  val w = new WeatherData();System.out.println("""w  : WeatherStation.WeatherData = """ + $show(w ));$skip(46); 
  val testCC = new CurrentConditionDisplay(w);System.out.println("""testCC  : WeatherStation.CurrentConditionDisplay = """ + $show(testCC ));$skip(32); 
  w.setMeasurements(60, 40, 30)}

}
