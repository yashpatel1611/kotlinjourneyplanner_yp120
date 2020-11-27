package journeyplan

// Add your code for modelling public transport networks in this file.

// Station class, takes in a string of station name
// override of toString to print out stationName
class Station(val stationName: String) {
    override fun toString(): String {
        return stationName
    }
}

// Line class, takes in line name
// override of toString to print out stationName and " Line"
class Line(val lineName: String) {
    override fun toString(): String {
        return "$lineName Line"
    }
}

// Segment class, links two stations together along with a journey time and which line connects them
// toString override to print out stations and line
class Segment(val firstStation: Station, val secondStation: Station, val line: Line, val journeyTime: Int) {
    override fun toString(): String {
        return "$firstStation to $secondStation via $line \n"
    }
}
