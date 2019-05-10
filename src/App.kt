import kotlin.math.ceil
import kotlin.math.max

fun main  (args : Array<String>) {

    println ("Flasche und Gläser")

    val glas1 = Glas (80, 250)
    val glas2 = Glas (50)
    val glas3 = Glas (100 )
    val glas4 = Glas (90 )
    val glas5 = Glas (0.3)  // Glas mit 30% Füllung

    // Beispiel-Ausgaben
    glas1.liesVor()
    glas5.liesVor()

    // Flasche und Tisch mit leerer Liste von Gläsern erzeugen:
    val flasche = Flasche(700)
    val ikeaTisch = Tisch ( mutableListOf<Glas>() )

    // Solange noch inhalt in der Flasche ist...einschenken!
    while ( flasche.inhalt > 0 ) {
        val neuesGlas = Glas (0)          // leeres Glas erzeugen
        flasche.umfuellen(neuesGlas)      // ins Glas einschenken
        ikeaTisch.glaeser.add(neuesGlas)  // Glas auf den Tisch stellen
    }


    // Und der Benutzer darf noch Gläser dazustellen
    do {
        println("Was soll geschehen: einschenken oder stopp")
        val aktion = readLine()  // Von der Konsole einlesen
        if (aktion == "einschenken") {
            val neuesGlas = Glas (180)
            ikeaTisch.glaeser.add(neuesGlas)
        }
    } while (aktion != "stopp")


}

fun umfuellMengeBerechnen (maxInhalt : Int,
                           aktuellerInhalt : Int,
                           inhaltDerFlasche : Int) : Int {

    val verfuegbarerPlatz =  maxInhalt - aktuellerInhalt
    if (verfuegbarerPlatz <= inhaltDerFlasche) {
        return verfuegbarerPlatz
    } else {
        return inhaltDerFlasche
    }

}

fun umfuellen ( flasche : Flasche , glas : Glas) {

    val umfuellmenge = umfuellMengeBerechnen( glas.maxInhalt,
            glas.inhalt, flasche.inhalt)

    glas.inhalt += umfuellmenge
    flasche.inhalt -= umfuellmenge



}

// Klassen-Kopf mit integriertem primären Konstruktor mit einem Paramter und einer Eigenschaft
class Glas (  inhalt_param : Int ,  val maxInhalt: Int = 200  ) {

    // Sekundärer Konstruktor
    constructor( ratio : Double , maxInhalt: Int = 200) : this ( (maxInhalt * ratio).toInt() ,maxInhalt ) {
        // Zusätzlicher Code falls gewünscht
    }

    // Zusätziche Eigenschaft mit speziellem setter
    var getraenk = "WASSER"
    set (value) {
        field = value.toUpperCase()
    }

    // Initialisierung der Eigenschaft inhalt durch inhalt_param
    // Defintion eines setters, um Setzen der Werte zu kontrollieren
    var inhalt = inhalt_param
    set (wert) {
        // Der eigentliche Werte der Eigenschaft wird in field gespeichert
        // Negativen Werte erlauben:
        if ( wert < 0 ) field = 0
        // Keine zu großen Werte erlauben:
        if ( wert > maxInhalt) field = maxInhalt
        // Wert liegt im gültigen Beriech
        if ( wert >= 0 && wert <=maxInhalt) field = wert
    }

    // Initialisierung der Eigenschaft innerhalb des Wertebereichs
    // erfolgt im init-Block. Dieser wird beim Erstellen des Objekts
    // ausgeführt
    init {
        inhalt = inhalt_param
    }

    // Eigenschaften ohne Datenfeld.
    // Es ist eine unveränderliche Eigenschaft. D.h. man kann
    // diese Eigenschaft nicht setzen. Der Wert ist jedoch
    // nicht konstant, sondern wird bei jedem Zugriff berechnet.
    val freierPlatz : Int
    get () {
        return maxInhalt - inhalt
    }

    val leer: Boolean
    get() {
        return inhalt == 0
    }

    // Eigenschaft ohne eigenes Datenfeld. Beim Setzen
    // werden andere Eigenschaftenn verändert
    var voll : Boolean
    set (value) {
       if (value) {
           inhalt = maxInhalt
       } else {
           inhalt = maxInhalt - 1
       }
    }
    get () {
        return maxInhalt == inhalt
    }

    // Methoden der Klasse
    fun verfuegbaerPlatz () : Int {
        return maxInhalt - inhalt
    }

    fun liesVor () : Unit {
        println ("Es sind noch $inhalt Milliliter ${getraenk} im Glas vorhanden.")
        println ("Maximal passen $maxInhalt ml. ins Glas.")
    }


}

class Flasche (var inhalt : Int ) {
    fun umfuellen ( glas : Glas) {

        // Umfüllmenge berechnen:
        var umfuellMenge = glas.verfuegbaerPlatz()
        if (umfuellMenge > this.inhalt) {
            umfuellMenge = this.inhalt
        }

        // Umfüllen:
        this.inhalt -= umfuellMenge
        glas.inhalt += umfuellMenge
    }
}

class Tisch ( val glaeser : MutableList<Glas>)

class Wasserhahn

class Eimer {
    var inhalt = 0
}