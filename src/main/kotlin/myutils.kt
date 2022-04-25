import java.io.File



fun readIntList(path: String): List<Int>{
    return readInput<Int>(path){it.toInt()}
}

fun readStringList(path: String): List<String>{
    return readInput<String>(path){it}
}

fun <T> readInput(path: String, mappingFunction: (String)-> T): List<T>{
    val result = emptyList<T>().toMutableList()
    File(path).forEachLine {
        result.add(mappingFunction(it))
    }
    return result.toList()
}



