

// aoc1
fun countStepdowns(steps: List<Int>):Int{
    var prevStep: Int? = null
    var downSteps = 0
    for (step: Int  in steps){
        if(prevStep != null && step > prevStep){
            downSteps += 1
        }
        prevStep = step
    }
    return downSteps
}

fun <T> createSublists(list: List<T>, sublistSize: Int): List<List<T>>{
    val res = emptyList<List<T>>().toMutableList()
    for((index, _) in list.withIndex()) {
        if(index <= list.size - sublistSize)
        res.add(list.subList(index, index + sublistSize))
    }
    return res.toList()
}

