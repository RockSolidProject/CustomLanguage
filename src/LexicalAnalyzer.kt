import java.io.File

class LexicalAnalyzer(
    var stateTable : Array<Pair<Array<Int?>, String?>>? = null,
    val filters: MutableList<String> = mutableListOf(),
    var ignore : HashSet<String> = hashSetOf()
){
    init {
        ignore.add("whitespace")

        filters.add(('0'..'9').joinToString(""))
        filters.add(".")
        filters.add("\"")
        filters.add("\t\r\n ")
        filters.add("$")
        filters.add("+")
        filters.add("-")
        filters.add("*")
        filters.add("/")
        filters.add("%")
        filters.add("&")
        filters.add("|")
        filters.add("(")
        filters.add(")")
        filters.add(";")
        filters.add("=")
        filters.add(">")
        filters.add("<")
        filters.add("!")
        filters.add("f")
        filters.add("u")
        filters.add("n")
        filters.add("o")
        filters.add("r")
        filters.add("i")
        filters.add("w")
        filters.add("h")
        filters.add("l")
        filters.add("e")
        filters.add("s")
        filters.add(('a'..'z').joinToString("")+('A'..'Z').joinToString("") + "_")
        filters.add(('a'..'z').joinToString("")+('A'..'Z').joinToString("") + "_" + ('0'..'9').joinToString(""))
        filters.add((' '..'!').joinToString("")+('#'..'~').joinToString(""))
        //println(filters)

        stateTable = arrayOf(
            Pair(arrayOf(1,	null,	4,	47,	6,	8,	9,	10,	11,	12,	13,	15,	17,	18,	19,	20,	22,	24,	26,	28,	null,	null,	null,	null,	34,	36,	null,	null,	41,	null,	33,	null,	null), null),
            Pair(arrayOf(1,	2,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "number"),
            Pair(arrayOf(3,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,),null),
            Pair(arrayOf(3,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "number"),
            Pair(arrayOf(null,	null,	5,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	4), null),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "string"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	7,	null,	null), "calculate"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	7,	null,), "variable"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "plus"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "minus"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "multiply"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "divide"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "mod"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	14,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "bwand"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "and"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	16,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "bwor"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "or"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "lparen"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "rparen"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "semi"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	21,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "assign"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "eq"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	23,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "gt"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "ge"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	25,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "lt"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "le"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	27,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "negation"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "ne"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	29,	null,	31,	null,	null,	null,	null,	null,	null,	null,	null,	33,	null), "fun_name"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	30,	null,	null,	null,	null,	null,	null,	null,	null,	null,	33,	null), "fun_name"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "function"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	32,	null,	null,	null,	null,	null,	null,	null,	33,	null), "fun_name"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "for"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	33,	null), "fun_name"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	35,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	33,	null), "fun_name"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "if"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	37,	null,	null,	null,	null,	33,	null), "fun_name"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	38,	null,	null,	null,	null,	null,	null,	33,	null), "fun_name"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	39,	null,	null,	null,	33,	null), "fun_name"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	40,	null,	null,	33,	null), "fun_name"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "while"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	42,	null,	null,	null,	33,	null), "fun_name"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	43,	null,	null,	null,	null,	45,	null,	33,	null), "fun_name"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	44,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	33,	null), "fun_name"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "elif"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	46,	null,	null,	33,	null), "fun_name"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "else"),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), "whitespace")
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
                    if (stateTable!![state].second == null)
                        throw IllegalArgumentException("Program ends in a non valid way.")
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