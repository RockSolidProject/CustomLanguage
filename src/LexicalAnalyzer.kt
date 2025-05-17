import java.io.File

class LexicalAnalyzer(
    var stateTable : Array<Pair<Array<Int?>, String?>>? = null,
    val filters: MutableList<String> = mutableListOf(),
    var ignore : HashSet<String> = hashSetOf()
){
    init {
        ignore.add("whitespace")

        val numbers = "0123456789"
        var letters = "abcdefghijlkmnoprqstuvxyvwz"
        letters += letters.uppercase()
        val operators = "+-*/"
        var lettersToF = "abcdef"
        lettersToF += lettersToF.uppercase()
        val binOperators = "&|"
        val separators = "()"
        val whitespaces = "\t\n\r "

        filters.add(numbers)
        filters.add(letters)
        filters.add("#")
        filters.add(lettersToF)
        filters.add(operators)
        filters.add(binOperators)
        filters.add(separators)
        filters.add(whitespaces)

        stateTable = arrayOf(
            Pair(arrayOf(    1,    2,    3, null,    5,    6,    7,    8), "STANJE0"),
            Pair(arrayOf(    1, null, null, null, null, null, null, null), "int"),
            Pair(arrayOf(    2,    2, null, null, null, null, null, null), "var"),
            Pair(arrayOf(    4, null, null,    4, null, null, null, null), null),
            Pair(arrayOf(    4, null, null,    4, null, null, null, null), "hex"),
            Pair(arrayOf( null, null, null, null, null, null, null, null), "operator"),
            Pair(arrayOf( null, null, null, null, null, null, null, null), "bop"),
            Pair(arrayOf( null, null, null, null, null, null, null, null), "Sep"),
            Pair(arrayOf( null, null, null, null, null, null, null, null), "whitespace")
        )
    }

    private fun analyze(str:String):List<Pair<String,String>>{
        for (row in stateTable!!) {
            if (row.first.size != filters.size)
                throw IllegalStateException("Each row of state table must have the same number of elements as filters table.")
        }

        val out : MutableList<Pair<String,String>> = mutableListOf()
        var state = 0
        var currentString = ""

        var i = 0
        while (i < str.length){
            if (stateTable!!.size <= state || state < 0) throw IllegalStateException("Current state ($state) does not exist in map (invalid preset)")

            var currFilter = ""
            var nextState = 0

            for (j in 0..<filters.size) {
                //stateTable!![state].first
                if (stateTable!![state].first[j] != null) {
                    if (filters[j].contains(str[i])){
                        currFilter = filters[j]
                        nextState = stateTable!![state].first[j]!!
                        break
                    }
                }
            }
            if (currFilter != "") {
                currentString += str[i].toString()
                state = nextState

                if (i+1 == str.length){
                    if (!ignore.contains(stateTable!![state].second!!))
                        out.add(Pair(currentString,stateTable!![state].second!!))
                    currentString = ""
                }
            }
            else {
                if(stateTable!![state].second != null){
                    i--
                    if (!ignore.contains(stateTable!![state].second!!))
                        out.add(Pair(currentString,stateTable!![state].second!!))
                    currentString=""
                    state = 0
                }
                else {
                    throw IllegalArgumentException("Invalid input for this preset")
                }
            }
            i++
        }
        return out
    }
    fun analyzeFile(path: String) : List<Pair<String,String>>? {
        try {
            return analyze(File(path).readText()) //can throw an error
        }
        catch (e:Exception){
            println("Analyzing failed: ${e.message}")
            return null
        }

    }
}