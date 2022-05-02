fun count1sInColumn(ints: List<Int>, column: Int):Int {
    return ints.map { (it shr column) and 1 }.reduce{ acc, i -> acc + i }
}

fun binListToInt(ints: List<Boolean>): Int{
    return ints.map{ if(it) 1 else 0}.reduceRight {i, acc -> i + (acc shl 1)}
}

fun IntBinListToInt(ints: List<Int>): Int{
    return ints.reduceRight {i, acc -> i + (acc shl 1)}
}