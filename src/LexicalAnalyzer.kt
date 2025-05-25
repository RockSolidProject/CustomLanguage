import java.io.File
class LexicalAnalyzer(
    var stateTable : Array<Pair<Array<Int?>, TokenType>>? = null,
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
        filters.add("{")
        filters.add("}")
        filters.add(",")
        filters.add("[")
        filters.add("]")
        //println(filters)

        stateTable = arrayOf(
            Pair(arrayOf(1,	null,	4,	47,	6,	8,	9,	10,	11,	12,	13,	15,	17,	18,	19,	20,	22,	24,	26,	28,	null,	null,	null,	null,	34,	36,	null,	null,	41,	null,	33,	null,	null, 48, 49, 50, 51, 52), TokenType.NULL),
            Pair(arrayOf(1,	2,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.NUMBER),
            Pair(arrayOf(3,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.NULL),
            Pair(arrayOf(3,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.NUMBER),
            Pair(arrayOf(null,	null,	5,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	4,	null,	null,	null,	null,	null), TokenType.NULL),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.STRING),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	7,	null,	null,	null,	null,	null,	null,	null), TokenType.CALCULATE),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	7,	null,	null,	null,	null,	null,	null), TokenType.VARIABLE),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.PLUS),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.MINUS),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.MULTIPLY),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.DIVIDE),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.MOD),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	14,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.BWAND),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.AND),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	16,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.BWOR),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.OR),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.LPAREN),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.RPAREN),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.SEMI),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	21,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.ASSIGN),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.EQ),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	23,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.GT),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.GE),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	25,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.LT),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.LE),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	27,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.NEGATION),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.NE),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	29,	null,	31,	null,	null,	null,	null,	null,	null,	null,	null,	33,	null,	null,	null,	null,	null,	null), TokenType.FUN_NAME),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	30,	null,	null,	null,	null,	null,	null,	null,	null,	null,	33,	null,	null,	null,	null,	null,	null), TokenType.FUN_NAME),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.FUNCTION),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	32,	null,	null,	null,	null,	null,	null,	null,	33,	null,	null,	null,	null,	null,	null), TokenType.FUN_NAME),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.FOR),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	33,	null,	null,	null,	null,	null,	null), TokenType.FUN_NAME),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	35,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	33,	null,	null,	null,	null,	null,	null), TokenType.FUN_NAME),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.IF),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	37,	null,	null,	null,	null,	33,	null,	null,	null,	null,	null,	null), TokenType.FUN_NAME),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	38,	null,	null,	null,	null,	null,	null,	33,	null,	null,	null,	null,	null,	null), TokenType.FUN_NAME),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	39,	null,	null,	null,	33,	null,	null,	null,	null,	null,	null), TokenType.FUN_NAME),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	40,	null,	null,	33,	null,	null,	null,	null,	null,	null), TokenType.FUN_NAME),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.WHILE),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	42,	null,	null,	null,	33,	null,	null,	null,	null,	null,	null), TokenType.FUN_NAME),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	43,	null,	null,	null,	null,	45,	null,	33,	null,	null,	null,	null,	null,	null), TokenType.FUN_NAME),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	44,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	33,	null,	null,	null,	null,	null,	null), TokenType.FUN_NAME),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.ELIF),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	46,	null,	null,	33,	null,	null,	null,	null,	null,	null), TokenType.FUN_NAME),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.ELSE),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.WHITESPACE),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.LCURL),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.RCURL),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.COMMA),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.LSQUARE),
            Pair(arrayOf(null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null,	null), TokenType.RSQUARE)
        )
    }

    private fun analyze(str:String):List<Pair<String,TokenType>>{
        for (row in stateTable!!) {
            if (row.first.size != filters.size)
                throw IllegalStateException("Each row of state table must have the same number of elements as filters table.")
        }

        val out : MutableList<Pair<String,TokenType>> = mutableListOf()
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
                    if (stateTable!![state].second == TokenType.NULL)
                        throw IllegalArgumentException("Program ends in a non valid way.")
                    if (!ignore.contains(stateTable!![state].second!!.name.lowercase())){
                        var second = stateTable!![state].second!!
                        //TODO TMP
                        if (second == TokenType.FUN_NAME && currentString == "import") {
                            out.add(Pair(currentString, TokenType.IMPORT))
                        } else if (second == TokenType.FUN_NAME && currentString == "write") {
                            out.add(Pair(currentString, TokenType.WRITE))
                        } else if (second == TokenType.FUN_NAME && currentString == "print") {
                            out.add(Pair(currentString, TokenType.PRINT))
                        } else if (second == TokenType.FUN_NAME && currentString == "return") {
                            out.add(Pair(currentString, TokenType.RETURN))
                        } else if (second == TokenType.STRING){
                            out.add(Pair(currentString.removeSurrounding("\""), TokenType.STRING))
                        } else if (second == TokenType.VARIABLE){
                            out.add(Pair(currentString.removePrefix("$"), TokenType.VARIABLE))
                        } else {
                            out.add(Pair(currentString, second))
                        }
                    }
                    currentString = ""
                }
            }
            else {
                if(stateTable!![state].second != TokenType.NULL){
                    i--
                    if (!ignore.contains(stateTable!![state].second!!.name.lowercase())){
                        var second = stateTable!![state].second!!
                        //TODO TMP
                        if (second == TokenType.FUN_NAME && currentString == "import") {
                            out.add(Pair(currentString, TokenType.IMPORT))
                        } else if (second == TokenType.FUN_NAME && currentString == "write") {
                            out.add(Pair(currentString, TokenType.WRITE))
                        } else if (second == TokenType.FUN_NAME && currentString == "print") {
                            out.add(Pair(currentString, TokenType.PRINT))
                        } else if (second == TokenType.FUN_NAME && currentString == "return") {
                            out.add(Pair(currentString, TokenType.RETURN))
                        } else if (second == TokenType.STRING){
                            out.add(Pair(currentString.removeSurrounding("\""), TokenType.STRING))
                        } else if (second == TokenType.VARIABLE){
                            out.add(Pair(currentString.removePrefix("$"), TokenType.VARIABLE))
                        } else {
                            out.add(Pair(currentString, second))
                        }
                    }

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
    fun analyzeFile(path: String) : List<Pair<String,TokenType>>? {
        try {
            return analyze(File(path).readText())
        }
        catch (e:Exception){
            println("Analyzing failed: ${e.message}")
            return null
        }

    }
}