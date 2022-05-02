fun readLineSegments(path: String): List<LineSegment> {
    return readInput(path) {
        it.split("->").map { s -> s.split(',').map { s -> s.replace(" ", "").toInt() } }
            .map { it -> Pair(it[0], it[1]) }
    }.map { Pair(it[0], it[1]) }.map { pair -> LineSegment(pair) }
}


class LineSegment(pair: Pair<Pair<Int, Int>, Pair<Int, Int>>) {
    val A = pair.first
    val B = pair.second

    val direction = (B `-` A)
    val normalisedDirection = normalise(direction)

    private fun normalise(direction: Pair<Int, Int>): Pair<Int,Int> {
        return Pair(
            pseudoNormalize(direction.x),
            pseudoNormalize(direction.y)
        )


//        return if (isHorizontalOrVertical()) {
//            if (direction.x == 0) {
//                if (direction.y < 0) Pair(0,-1) else Pair(0, 1)
//            } else {
//                if (direction.x < 0) Pair(-1,0) else Pair(1, 0)
//            }
//        } else {
//            println("ignoring line")
//            direction
//        }
    }

    private fun pseudoNormalize(x: Int): Int {
        return if(x == 0) 0 else if(x>0) 1 else -1
    }

    fun getPointsBetween(): List<Pair<Int,Int>>{
        val results = mutableListOf<Pair<Int,Int>>()
        var point = A
        while(point != B){
            results.add(point)
            point = point `+` normalisedDirection
        }
        results.add(point)
        return results
    }

    fun isHorizontalOrVertical(): Boolean{
        return direction.x == 0 || direction.y == 0
    }


}

private infix fun Pair<Int,Int>.`+`(other: Pair<Int,Int>): Pair<Int,Int> {
    return Pair(this.x + other.x, this.y + other.y)
}

private val <A, B> Pair<A, B>.y: B
    get() {return second}

private val <A, B> Pair<A, B>.x: A
    get() {return first}

private infix fun Pair<Int, Int>.`-`(b: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.x - b.x, this.y - b.y)
}





