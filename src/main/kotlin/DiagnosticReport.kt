import java.lang.RuntimeException

class DiagnosticReport(val bins: List<Int>, val columns: Int) {

    val oneCounts = MutableList(columns) {col -> count1sInColumn(bins, col)}
    val zeroCounts = oneCounts.map { bins.size - it}

    val gammaList = oneCounts.zip(zeroCounts).map{(oneCount, zeroCount) -> oneCount >= zeroCount }
    val epsilonList = oneCounts.zip(zeroCounts).map{(oneCount, zeroCount) -> oneCount < zeroCount }

    val gamma = binListToInt(gammaList)
    val epsilon = binListToInt(epsilonList)

    fun getOxygenGeneratorRating(): Int{
        val filteredBins = bins.filter { bin -> ((bin shr this.columns - 1) and 1) == gammaList[columns - 1].toInt()}
        if(filteredBins.size == 1) return filteredBins[0]
        if(filteredBins.isEmpty() || columns == 0) throw RuntimeException("filtered to empty")
        val recDiagnosticReport = DiagnosticReport(filteredBins, columns - 1)
        return recDiagnosticReport.getOxygenGeneratorRating()
    }

    fun getCO2ScrubberRating(): Int {
        val filteredBins = bins.filter { bin -> ((bin shr this.columns - 1) and 1) == epsilonList[columns - 1].toInt()}
        if(filteredBins.size == 1) return filteredBins[0]
        if(filteredBins.isEmpty() || columns == 0) throw RuntimeException("filtered to empty")
        val recDiagnosticReport = DiagnosticReport(filteredBins, columns - 1)
        return recDiagnosticReport.getCO2ScrubberRating()
    }
}

private fun Boolean.toInt(): Int {
    return if(this) 1 else 0
}
