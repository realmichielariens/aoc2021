import java.io.File

fun readBingoConfig(path:String): Pair<List<Int>, List<BingoBoard>>{
    val inputStrings = File(path).readLines()
    val inputs = inputStrings[0].split(',').map { it.toInt() }
    val separatorIndices = inputStrings.mapIndexed{ index, s ->  if(s == "") index else 0 }.filter { it != 0 }
    val boardRawInputs = separatorIndices.subList(0, separatorIndices.size-2).zip(separatorIndices.subList(1, separatorIndices.size-1)).map { (from,to) -> inputStrings.subList(from + 1, to) }
    val boardInputs = boardRawInputs.map { //table
        it.map { s -> // line
            s.split(' ').filter { s -> s != "" }.map { s -> s.toInt() }.toIntArray()
        }.toTypedArray()
    }.map{arrayOfIntArrays -> BingoBoard(arrayOfIntArrays) }
    return Pair(inputs, boardInputs)
}



class BingoBoard (val board: Array<IntArray>) {
    val marks = board.map { ints -> ints.map { _ -> 0}.toIntArray() }.toTypedArray()
    val indexes = populateIndex()

    fun mark(value:Int){
        val index = indexes.get(value)
        if (index != null){
            marks[index.first][index.second] = 1
        }
    }

    fun checkWinner():Boolean{
        return checkWinningRows(marks) || checkWinningColumns(marks)
    }

    private fun checkWinningRows(marks: Array<IntArray>): Boolean{
        return marks.map { ints -> ints.all{i -> i == 1 }}.filter { b -> b }.isNotEmpty()
    }

    private fun checkWinningColumns(marks: Array<IntArray>): Boolean{
        return checkWinningRows(transpose(marks))
    }

    private fun transpose(marks: Array<IntArray>): Array<IntArray> {
        val transposed = Array(marks[0].size){IntArray(marks.size){0}}
        for(i in marks.indices){
            for(j in marks[0].indices){
                transposed[i][j] = marks[j][i]
            }
        }
        return transposed
    }

    private fun populateIndex(): Map<Int,Pair<Int,Int>>{
        val map = mutableMapOf<Int,Pair<Int,Int>>()
        board.mapIndexed{X, subarray ->
            subarray.mapIndexed{Y, value ->
                map[value] = Pair(X,Y)
            }
        }
        return map.toMap()
    }

    override fun toString(): String {
        return "BingoBoard(board=${board.contentDeepToString()}, marks=${marks.contentDeepToString()})"
    }

    fun calculateScore(): Int {
        return board.mapIndexed { X, ints ->
            ints.mapIndexed{ Y, value -> if(marks[X][Y] == 0) value else 0 }
        }.flatten().reduce { acc, i -> acc + i }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BingoBoard

        if (!board.contentDeepEquals(other.board)) return false

        return true
    }

    override fun hashCode(): Int {
        return board.contentDeepHashCode()
    }
}

