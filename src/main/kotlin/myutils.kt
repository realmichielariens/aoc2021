import java.io.File



fun <T> readInput(path: String, mappingFunction: (String)-> T): List<T>{
    val result = emptyList<T>().toMutableList()
    File(path).forEachLine {
        result.add(mappingFunction(it))
    }
    return result.toList()
}


fun triangular(i: Int): Int {
    return i*(i+1)/2
}

fun <T> List<T>.toPair(): Pair<T,T> {
    if(this.size != 2 ) throw java.lang.RuntimeException("toPair called on list not containing exactly two items")
    return Pair(this[0], this[1])
}