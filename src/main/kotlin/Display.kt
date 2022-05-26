val defaultDisplayMap = mapOf(
    Pair(0, listOf("a", "b", "c", "e", "f")),
    Pair(1, listOf("c", "f")),
    Pair(2, listOf("a", "c", "d", "e", "g")),
    Pair(3, listOf("a", "c", "d", "f", "g")),
    Pair(4, listOf("b", "c", "d", "f")),
    Pair(5, listOf("a", "b", "d", "f", "g")),
    Pair(6, listOf("a", "b", "d", "e", "f", "g")),
    Pair(7, listOf("a", "c", "f")),
    Pair(8, listOf("a", "b", "c", "d", "e", "f", "g")),
    Pair(9, listOf("a", "b", "c", "d", "f", "g")),
)

val displayValueToCodeLengthMap = defaultDisplayMap.keys.map { key -> Pair(key, defaultDisplayMap[key]!!.size) }.toMap()
val codeLengthToDisplayValueMap = displayValueToCodeLengthMap.values.toSet().map { length -> Pair(length, displayValueToCodeLengthMap.toList().filter { (x,y) -> y == length }.map { (x,y) -> x }) }.toMap()

class DisplayDigit(
    val segments: List<String>){
}


class Display {
}
