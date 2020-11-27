package journeyplan

// Add your code for the route planner in this file.
fun main() {
}

// SubwayMap class takes in list of segments which outline the connections in the subway
// routesFrom takes in two stations and finds all possible routes between them
class SubwayMap(private val segmentList: List<Segment>) {

    // routesFrom utilises a helper function to allow it to search for sub-routes by recursion
    fun routesFrom(origin: Station, destination: Station): List<Route> {
        val stationsVisited: MutableList<Station> = mutableListOf()
        val finalRoutes: MutableList<Route> = mutableListOf()
        val route = mutableListOf<Segment>()

        fun helper(currentStation: Station) {

            if (currentStation != destination) {
                stationsVisited.add(currentStation)
            }

            val unvisitedLink = segmentList.filter { it.firstStation == currentStation && it.secondStation !in stationsVisited }

            if (unvisitedLink.isEmpty()) {
                finalRoutes.add(Route(route.toMutableList()))
                route.removeLast()
            }

            for (segment in unvisitedLink) {
                route.add(segment)
                if (segment.secondStation != destination) {
                    helper(segment.secondStation)
                } else {
                    finalRoutes.add(Route(route.toMutableList()))
                    route.clear()
                }
            }
        }

        helper(origin)
        return finalRoutes.filter { it.segments.last().secondStation == destination }
    }
}
// Route class takes in a list of segments, which outline a path between stations
// numChanges will take a route and calculate the total number of line changes
// duration will take a route and total the time for whole journey
// toString override function will print the route changes and stations
// toString also allows for transitive route changes (i.e. A -> B -> C on the same line is printed as A -> C)
class Route(val segments: List<Segment>) {
    fun numChanges(): Int {
        var currentLine = segments.first().line
        var changes = 0

        for (segment in segments) {
            if (segment.line != currentLine) changes++
            currentLine = segment.line
        }

        return changes
    }

    fun duration(): Int {
        var totalTime = 0
        segments.forEach { totalTime += it.journeyTime }

        return totalTime
    }

    override fun toString(): String {
        var output = "${segments.first().firstStation} to ${segments.last().secondStation} " +
            "- ${this.duration()} minutes, ${this.numChanges()} changes\n "

        output += "- ${segments.first().firstStation} to "

        var currentLine = segments.first().line
        var lastStation = segments.first().firstStation

        val segmentIterator: Iterator<Segment> = segments.iterator()

        while (segmentIterator.hasNext()) {
            val currentSegment = segmentIterator.next()

            if (!segmentIterator.hasNext()) {
                if (currentSegment.line == currentLine) {
                    output += "${currentSegment.secondStation} by ${currentSegment.line}"
                    continue
                } else {
                    output += "$lastStation by $currentLine\n - $currentSegment"
                    break
                }
            }

            if (currentSegment.line == currentLine) {
                lastStation = currentSegment.secondStation
                continue
            } else {
                output += "$lastStation by $currentLine\n - ${currentSegment.firstStation} to "
                lastStation = currentSegment.secondStation
            }
            currentLine = currentSegment.line
        }
        return output
    }
}
