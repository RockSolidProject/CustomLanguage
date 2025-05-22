import java.math.BigDecimal
import java.math.RoundingMode

class SemanticAnalyzer(var tokens: List<Pair<String, TokenType>>?) {
    private var tokenIndex = 0
    private var currentToken: Pair<String, TokenType>? = null
    private var currentTokenType: TokenType? = null
    private var currentTokenValue: String? = null

    companion object {
        var initFunctionMap = mutableMapOf<String, Funct>()
        var initVarMap = mutableMapOf<String, String>(Pair("var", "3"))
    }


    fun testParse(): Boolean {
        init()
        println(bval()(initFunctionMap, initVarMap))
        if (currentTokenType != null) {
            throw Exception("Unexpected token: $currentTokenValue").also {  printErrorContext() }
        }
        return true
    }

    fun init() {
        if (tokens.isNullOrEmpty()) {
            throw Exception("Token list is empty or null")
        }
        tokenIndex = 0
        currentToken = tokens!![tokenIndex]
        currentTokenType = currentToken!!.second
        currentTokenValue = currentToken!!.first

    }

    private fun incrementToken() {
        tokenIndex++
        if (tokenIndex < tokens!!.size) {
            currentToken = tokens!![tokenIndex]
            currentTokenType = currentToken!!.second
            currentTokenValue = currentToken!!.first
        } else {
            currentToken = null
            currentTokenType = null
            currentTokenValue = null
        }
    }

    fun printErrorContext() {
        val contextSize = 3
        val start = maxOf(0, tokenIndex - contextSize)
        val end = minOf(tokens!!.size, tokenIndex + contextSize + 1)

        // Prikaži tokene pred, na, in za napako
        val context = tokens!!.subList(start, end).joinToString(" ") { it.first }
        println(context)

        // Izračunaj pozicijo napake
        val errorPosition = tokens!!.subList(start, tokenIndex).sumOf { it.first.length + 1 }
        println(" ".repeat(errorPosition) + "^")
    }

    /*fun parse(): Boolean {
        init()
        program()
        if (currentTokenType != null) {
            throw Exception("Unexpected token: $currentTokenValue").also {  printErrorContext() }
        }
        return true
    }*/


    /*private fun args() : (Funct) -> List<String>{
        return if (currentTokenType == TokenType.VARIABLE || currentTokenType == TokenType.RPAREN) {
            if(currentTokenType == TokenType.VARIABLE) {
                incrementToken()
                args1()
            } else{ // Torej je ')'

            }
        } else {
            throw Exception("Expected variable").also {  printErrorContext() }
        }
    }

    private fun program() {
        import()
        function1()
        function()
    }

    private fun import() {
        if (currentTokenType != TokenType.IMPORT) {
            return  // Or throw an error if 'IMPORT' is mandatory
        }
        incrementToken()

        if (currentTokenType != TokenType.LPAREN) {
            throw Exception("Expected '('").also { printErrorContext() }
        }
        incrementToken()

        if (currentTokenType != TokenType.STRING) {
            throw Exception("Expected string").also { printErrorContext() }
        }
        incrementToken()

        if (currentTokenType != TokenType.RPAREN) {
            throw Exception("Expected ')'").also { printErrorContext() }
        }
        incrementToken()

        import()
    }


    private fun function() {
        return if (currentTokenType == TokenType.FUNCTION) {
            function1()
            function()
        } else {

        }
    }

    private fun function1() {
        if (currentTokenType != TokenType.FUNCTION) {
            throw Exception("Expected 'function'").also { printErrorContext() }
        }
        incrementToken()

        if (currentTokenType != TokenType.FUN_NAME) {
            throw Exception("Expected function name").also { printErrorContext() }
        }
        incrementToken()

        if (currentTokenType != TokenType.LPAREN) {
            throw Exception("Expected '('").also { printErrorContext() }
        }
        incrementToken()

        args()

        if (currentTokenType != TokenType.RPAREN) {
            throw Exception("Expected ')'").also { printErrorContext() }
        }
        incrementToken()

        if (currentTokenType != TokenType.LCURL) {
            throw Exception("Expected '{'").also { printErrorContext() }
        }
        incrementToken()

        action()

        if (currentTokenType != TokenType.RCURL) {
            throw Exception("Expected '}'").also { printErrorContext() }
        }
        incrementToken()
    }



    private fun args1() {
        return if (currentTokenType == TokenType.COMMA) {
            incrementToken()
            if (currentTokenType == TokenType.VARIABLE) {
                incrementToken()
                args1()
            } else {
                throw Exception("Expected variable").also {  printErrorContext() }
            }
        } else {

        }
    }

    private fun action() {
        val validTokens =
            setOf(TokenType.VARIABLE, TokenType.PRINT, TokenType.RETURN, TokenType.WRITE, TokenType.FUN_NAME)
        return if (currentTokenType in validTokens) {
            action2()
            if (currentTokenType == TokenType.SEMI) {
                incrementToken()
                action1()
            } else {
                throw Exception("Expected ';'").also {  printErrorContext() }
            }
        } else {
            statement()
            action()
        }
    }

    private fun action1() {
        val validTokens = setOf(
            TokenType.VARIABLE,
            TokenType.PRINT,
            TokenType.RETURN,
            TokenType.WRITE,
            TokenType.FUN_NAME,
            TokenType.IF,
            TokenType.FOR,
            TokenType.WHILE
        )
        return if (currentTokenType in validTokens) {
            action()
        } else {

        }
    }

    private fun action2() { //Action'' ::= Assign | Print | Return | Write | id ( Args )
        return if (currentTokenType == TokenType.VARIABLE) {
            assign()
        } else if (currentTokenType == TokenType.PRINT) {
            print()
        } else if (currentTokenType == TokenType.RETURN) {
            return0()
        } else if (currentTokenType == TokenType.WRITE) {
            write()
        } else if (currentTokenType == TokenType.FUN_NAME) {
            incrementToken()
            if (currentTokenType == TokenType.LPAREN) {
                incrementToken()
                args()
                if (currentTokenType == TokenType.RPAREN) {
                    incrementToken()
                } else {
                    throw Exception("Expected ')'").also {  printErrorContext() }
                }
            } else {
                throw Exception("Expected '('").also {  printErrorContext() }
            }
        } else {
            throw throw Exception("Unexpected token: $currentTokenValue").also {  printErrorContext() }
        }
    }

    private fun statement() {
        return when (currentTokenType) {
            TokenType.IF -> {
                if0()
            }

            TokenType.FOR -> {
                for0()
            }

            TokenType.WHILE -> {
                while0()
            }

            else -> throw throw Exception("Unexpected token: $currentTokenValue").also {  printErrorContext() }
        }
    }

    private fun if0() {
        return if (currentTokenType == TokenType.IF) {
            if1()
            elif()
            else0()
        } else {
            throw Exception("Expected 'if'").also {  printErrorContext() }
        }
    }

    private fun if1() {
        if (currentTokenType != TokenType.IF) {
            return  // Or throw an error if 'if' is mandatory in this context
        }
        incrementToken()

        if (currentTokenType != TokenType.LPAREN) {
            throw Exception("Expected '('").also { printErrorContext() }
        }
        incrementToken()

        expression()

        if (currentTokenType != TokenType.RPAREN) {
            throw Exception("Expected ')'").also { printErrorContext() }
        }
        incrementToken()

        if (currentTokenType != TokenType.LCURL) {
            throw Exception("Expected '{'").also { printErrorContext() }
        }
        incrementToken()

        action()

        if (currentTokenType != TokenType.RCURL) {
            throw Exception("Expected '}'").also { printErrorContext() }
        }
        incrementToken()
    }


    private fun elif() {
        return if (currentTokenType == TokenType.ELIF) {
            elif1()
            elif()
        } else {

        }
    }

    private fun elif1() {
        if (currentTokenType != TokenType.ELIF) {
            return  // Or throw Exception("Expected 'elif'") if it's required
        }
        incrementToken()

        if (currentTokenType != TokenType.LPAREN) {
            throw Exception("Expected '('").also { printErrorContext() }
        }
        incrementToken()

        expression()

        if (currentTokenType != TokenType.RPAREN) {
            throw Exception("Expected ')'").also { printErrorContext() }
        }
        incrementToken()

        if (currentTokenType != TokenType.LCURL) {
            throw Exception("Expected '{'").also { printErrorContext() }
        }
        incrementToken()

        action()

        if (currentTokenType != TokenType.RCURL) {
            throw Exception("Expected '}'").also { printErrorContext() }
        }
        incrementToken()
    }


    private fun else0() {
        return if (currentTokenType == TokenType.ELSE) {
            incrementToken()
            if (currentTokenType == TokenType.LCURL) {
                incrementToken()
                action()
                if (currentTokenType == TokenType.RCURL) {
                    incrementToken()
                } else {
                    throw Exception("Expected '}'").also {  printErrorContext() }
                }
            } else {
                throw Exception("Expected '{'").also {  printErrorContext() }
            }
        } else {

        }
    }

    private fun for0() {
        if (currentTokenType != TokenType.FOR) {
            throw Exception("Expected 'for'").also { printErrorContext() }
        }
        incrementToken()

        if (currentTokenType != TokenType.LPAREN) {
            throw Exception("Expected '('").also { printErrorContext() }
        }
        incrementToken()

        assign()

        if (currentTokenType != TokenType.SEMI) {
            throw Exception("Expected ';'").also { printErrorContext() }
        }
        incrementToken()

        expression()

        if (currentTokenType != TokenType.SEMI) {
            throw Exception("Expected ';'").also { printErrorContext() }
        }
        incrementToken()

        assign()

        if (currentTokenType != TokenType.RPAREN) {
            throw Exception("Expected ')'").also { printErrorContext() }
        }
        incrementToken()

        if (currentTokenType != TokenType.LCURL) {
            throw Exception("Expected '{'").also { printErrorContext() }
        }
        incrementToken()

        action()

        if (currentTokenType != TokenType.RCURL) {
            throw Exception("Expected '}'").also { printErrorContext() }
        }
        incrementToken()
    }


    private fun while0() {
        if (currentTokenType != TokenType.WHILE) {
            throw Exception("Expected 'while'").also { printErrorContext() }
        }
        incrementToken()

        if (currentTokenType != TokenType.LPAREN) {
            throw Exception("Expected '('").also { printErrorContext() }
        }
        incrementToken()

        expression()

        if (currentTokenType != TokenType.RPAREN) {
            throw Exception("Expected ')'").also { printErrorContext() }
        }
        incrementToken()

        if (currentTokenType != TokenType.LCURL) {
            throw Exception("Expected '{'").also { printErrorContext() }
        }
        incrementToken()

        action()

        if (currentTokenType != TokenType.RCURL) {
            throw Exception("Expected '}'").also { printErrorContext() }
        }
        incrementToken()
    }


    private fun assign() {
        return if (currentTokenType == TokenType.VARIABLE) {
            incrementToken()
            if (currentTokenType == TokenType.ASSIGN) {
                incrementToken()
                expression()
            } else {
                throw Exception("Expected '='").also {  printErrorContext() }
            }
        } else {
            throw Exception("Expected variable").also {  printErrorContext() }
        }
    }

    private fun print() {
        if (currentTokenType != TokenType.PRINT) {
            throw Exception("Expected 'print'").also { printErrorContext() }
        }
        incrementToken()

        if (currentTokenType != TokenType.LPAREN) {
            throw Exception("Expected '('").also { printErrorContext() }
        }
        incrementToken()

        expression()

        if (currentTokenType != TokenType.RPAREN) {
            throw Exception("Expected ')'").also { printErrorContext() }
        }
        incrementToken()
    }


    private fun return0() { // ni slo return
        return if (currentTokenType == TokenType.RETURN) {
            incrementToken()
            expression()
        } else {
            throw Exception("Expected 'return'").also {  printErrorContext() }
        }
    }

    private fun write() {
        return if (currentTokenType == TokenType.WRITE) {
            incrementToken()
            if (currentTokenType == TokenType.LPAREN) {
                incrementToken()
                expression()
                if (currentTokenType == TokenType.RPAREN) {
                    incrementToken()
                } else {
                    throw Exception("Expected ')'").also {  printErrorContext() }
                }
            } else {
                throw Exception("Expected '('").also {  printErrorContext() }
            }
        } else {
            throw Exception("Expected 'write'").also {  printErrorContext() }
        }
    }

    private fun expression() : (Map<String, Funct>, Map<String, String>) -> String
    {
        if (currentTokenType == TokenType.CALCULATE) {
            incrementToken()
            if (currentTokenType == TokenType.LPAREN) {
                incrementToken()
                compare()
                if (currentTokenType == TokenType.RPAREN) {
                    incrementToken()
                } else {
                    throw Exception("Expected ')'").also {  printErrorContext() }
                }
            } else {
                throw Exception("Expected '('").also {  printErrorContext() }
            }
        } else {
            concat()
        }
    }


    private fun concat() {
        get()
        concat1()
    }

    private fun concat1() {
        if (currentTokenType == TokenType.PLUS) {
            incrementToken()
            if (currentTokenType in setOf(TokenType.STRING, TokenType.NUMBER, TokenType.VARIABLE, TokenType.FUN_NAME)) {
                concat()
            } else {
                throw Exception("Unexpected token after '+' operator: $currentTokenValue").also {  printErrorContext() }
            }
        }
    }

    private fun get() {
        unit()
        get1()
    }

    private fun get1() {
        if (currentTokenType == TokenType.LSQUARE) {
            incrementToken()
            unit()
            if (currentTokenType == TokenType.RSQUARE) {
                incrementToken()
            } else {
                throw Exception("Expected ']'").also {  printErrorContext() }
            }
        }
    }

    private fun compare() {
        bval()
        compare1()
    }

    private fun compare1() {
        return when (currentTokenType) {
            TokenType.EQ -> { // equal
                incrementToken()
                compare()
            }

            TokenType.LT -> { // less than
                incrementToken()
                compare()
            }

            TokenType.GT -> { // greater than
                incrementToken()
                compare()
            }

            TokenType.LE -> { // less than or equal
                incrementToken()
                compare()
            }

            TokenType.GE -> { // greater than or equal
                incrementToken()
                compare()
            }

            TokenType.NE -> { // not equal
                incrementToken()
                compare()
            }

            else -> {}
        }
    }*/

    private fun bval() : (Map<String, Funct>, Map<String, String>) -> String{
        if (currentTokenType == TokenType.NEGATION) {
            incrementToken()
            val bin = binary()
            return lambda@{ functions, vars ->
                val res = bin(functions, vars)
                if(res == "0" || res == "") return@lambda "1"
                else return@lambda "0"
            }
        } else {
            val bin = binary()
            return bin
        }
    }

    private fun binary(): (Map<String, Funct>, Map<String, String>) -> String {
        val bit = bitwise()
        return binary1(bit)
    }

    private fun binary1(inherited: (Map<String, Funct>, Map<String, String>) -> String) : (Map<String, Funct>, Map<String, String>) -> String {
        when (currentTokenType) {
            TokenType.OR -> {
                incrementToken()
                val bin = binary()
                return lambda@ { functions, vars ->
                    val inh = BigDecimal(inherited(functions, vars))
                    val b = BigDecimal(bin(functions, vars))
                    if(inh != BigDecimal.ZERO || b != BigDecimal.ZERO) {
                        return@lambda "1"
                    }else {
                        return@lambda "0"
                    }
                }
            }

            TokenType.AND -> {
                incrementToken()
                val bin = binary()
                return lambda@ { functions, vars ->
                    val inh = BigDecimal(inherited(functions, vars))
                    val b = BigDecimal(bin(functions, vars))
                    if(inh != BigDecimal.ZERO && b != BigDecimal.ZERO) {
                        return@lambda "1"
                    }else {
                        return@lambda "0"
                    }
                }
            }
            else -> {
                return inherited
            }
        }
    }

    private fun bitwise(): (Map<String, Funct>, Map<String, String>) -> String{
        val mod = mod()
        val bit = bitwise1(mod)
        return bit
    }

    private fun bitwise1(inherited: (Map<String, Funct>, Map<String, String>) -> String) : (Map<String, Funct>, Map<String, String>) -> String{
        when (currentTokenType) {
            TokenType.BWOR -> {
                incrementToken()
                val bit = bitwise()
                return lambda@ { functions, vars ->
                    val inh = BigDecimal(inherited(functions, vars)).toBigInteger()
                    val b = BigDecimal(bit(functions, vars)).toBigInteger()
                    return@lambda inh.or(b).toString()
                }
            }

            TokenType.BWAND -> {
                incrementToken()
                val bit = bitwise()
                return lambda@ { functions, vars ->
                    val inh = BigDecimal(inherited(functions, vars)).toBigInteger()
                    val b = BigDecimal(bit(functions, vars)).toBigInteger()
                    return@lambda inh.and(b).toString()
                }
            }

            else -> {
                return inherited
            }
        }
    }

    private fun mod(): (Map<String, Funct>, Map<String, String>) -> String {
        val add = additive()
        return mod1(add)
    }

    private fun mod1(inherited: (Map<String, Funct>, Map<String, String>) -> String) : (Map<String, Funct>, Map<String, String>) -> String{
        if (currentTokenType == TokenType.MOD) {
            incrementToken()
            val mod = mod()
            //incrementToken()
            return lambda@ { functions, vars ->
                val inh = BigDecimal(inherited(functions, vars)).remainder(BigDecimal(mod(functions, vars)))
                return@lambda inh.toPlainString()
            }

        }else {
            return inherited
        }
    }

    private fun additive() : (Map<String, Funct>, Map<String, String>) -> String {
        val mul = multiplicative()
        val add1 = additive1(mul)
        return add1
    }

    private fun additive1(inherited: (Map<String, Funct>, Map<String, String>) -> String) : (Map<String, Funct>, Map<String, String>) -> String {
        when (currentTokenType) {
            TokenType.PLUS -> {
                incrementToken()
                val add = additive()
                return lambda@ { functions, vars ->
                    val inh = BigDecimal(inherited(functions, vars)).plus(BigDecimal(add(functions, vars)))
                    return@lambda inh.toPlainString()
                }
            }

            TokenType.MINUS -> {
                incrementToken()
                val add = additive()
                return lambda@ { functions, vars ->
                    val inh = BigDecimal(inherited(functions, vars)).minus(BigDecimal(add(functions, vars)))
                    return@lambda inh.toPlainString()
                }
            }

            else -> {return inherited}
        }
    }

    private fun multiplicative() : (Map<String, Funct>, Map<String, String>) -> String {
        val un = unit()
        val out = multiplicative1(un)
        return out
    }


    private fun multiplicative1(inherited: (Map<String, Funct>, Map<String, String>) -> String) : (Map<String, Funct>, Map<String, String>) -> String {
        when (currentTokenType) {
            TokenType.MULTIPLY -> {
                incrementToken()
                val mul = multiplicative()
                return lambda@ { functions, vars ->
                    val inh = BigDecimal(inherited(functions, vars)).times(BigDecimal(mul(functions, vars)))
                    return@lambda inh.toPlainString()
                }
            }

            TokenType.DIVIDE -> {
                incrementToken()
                val mul = multiplicative()
                return lambda@ { functions, vars ->
                    val inh = BigDecimal(inherited(functions, vars)).divide(BigDecimal(mul(functions, vars)), 10, RoundingMode.HALF_UP)
                    return@lambda inh.toPlainString()
                }
            }

            else -> {
                return inherited
            }
        }
    }

    private fun unit() : (Map<String, Funct>, Map<String, String>) -> String {
        when (currentTokenType) {
            TokenType.PLUS -> {
                incrementToken()
                val expr = primary()
                return expr
            }

            TokenType.MINUS -> {
                incrementToken()
                val expr = primary()
                return lambda@{ functions, vars ->
                    val result = BigDecimal(expr(functions, vars)).negate()
                    return@lambda result.toPlainString()
                }
            }

            else -> {
                val expr = primary()
                return expr
            }
        }
    }

    private fun primary() : (Map<String, Funct>, Map<String, String>) -> String
    {
        when (currentTokenType) {
            TokenType.STRING,
            TokenType.NUMBER ->{
                val content = currentTokenValue
                incrementToken()
                return {functions, vars -> content ?: ""}
            }
            TokenType.VARIABLE -> {
                val currVal = currentTokenValue
                incrementToken()
                return {functions, vars ->
                    vars[currVal] ?: throw Error("variable name \"$currVal\" does not exist")
                }
            }

            TokenType.FUN_NAME -> {
                TODO()
                val name = currentTokenValue // storing function name
                incrementToken()

                if (currentTokenType != TokenType.LPAREN) {
                    throw Exception("Expected '('").also { printErrorContext() }
                }

                incrementToken()
                //var arguments = args() // storing function arguments

                if (currentTokenType != TokenType.RPAREN) {
                    throw Exception("Expected ')'").also { printErrorContext() }
                }
                incrementToken()
                return {functions, vars ->
                    TODO()
                }
            }

            TokenType.LPAREN -> {
                TODO()
                incrementToken()

                //val expr = expression()

                if (currentTokenType != TokenType.RPAREN) {
                    throw Exception("Expected ')'").also { printErrorContext() }
                }
                incrementToken()
                //return expr
            }

            else -> throw Exception("Unexpected token: $currentTokenValue").also { printErrorContext() }
        }

    }
}
