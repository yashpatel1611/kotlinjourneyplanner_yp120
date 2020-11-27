package journeyplan

// Add your code for modelling public transport networks in this file.

class Station(val stationName: String) {
    override fun toString(): String {
        return stationName
    }
}

class Line(val lineName: String) {
    override fun toString(): String {
        return "$lineName Line"
    }
}

class Segment(val firstStation: Station, val secondStation: Station, val line: Line, val journeyTime: Int) {
    override fun toString(): String {
        return "$firstStation to $secondStation via $line \n"
    }
}
