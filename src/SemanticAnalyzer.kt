import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import java.io.File

typealias LambdaToString = (MutableMap<String, Funct>, MutableMap<String, String>) -> String
typealias LambdaToUnit = (MutableMap<String, Funct>, MutableMap<String, String>) -> Unit
typealias LambdaToAny = (MutableMap<String, Funct>, MutableMap<String, String>) -> Any?

private fun String.toBigDecimalOrNull(): BigDecimal? {
    return try {
        BigDecimal(this)
    } catch (e: NumberFormatException) {
        null
    }
}

class SemanticAnalyzer(var tokens: List<Pair<String, TokenType>>?) {
    private var tokenIndex = 0
    private var currentToken: Pair<String, TokenType>? = null
    private var currentTokenType: TokenType? = null
    private var currentTokenValue: String? = null
    private var checkCondition = {str:String -> str != "" && (str.toBigDecimalOrNull() == null || str.toBigDecimal() != BigDecimal.ZERO)}

    companion object {
        var initFunctionMap = mutableMapOf<String, Funct>()
        var initVarMap = mutableMapOf<String, String>(Pair("var", "3"))
        val file = File("output.txt")


    }


    fun testParse(): Boolean {
        file.createNewFile()
        init()
        println(if0()(initFunctionMap, initVarMap))
        if (currentTokenType != null) {
            throw Exception("Unexpected token: $currentTokenValue").also { printErrorContext() }
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
        println("new token : $currentTokenType")
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
    }*/

    private fun if0(): LambdaToAny {
        if (currentTokenType != TokenType.IF) throw Exception("Expected 'if'").also { printErrorContext() }
        val first = if1()
        val second = elif(first)
        val third = else0()
        return lambda@{ functions, vars ->
            val firstOut:Pair<Boolean, String?> = first(functions, vars) as Pair<Boolean,String?>
            if(firstOut.first) return@lambda firstOut.second ?: Unit
            val secondOut:Pair<Boolean, String?> = second(functions, vars) as Pair<Boolean,String?>
            if(secondOut.first) return@lambda secondOut.second ?: Unit
            val thirdOut:String? = third(functions, vars) as String?
            return@lambda thirdOut ?: Unit
        }
    }

    private fun if1(): LambdaToAny {
        if (currentTokenType != TokenType.IF) throw Exception("Expected 'if'").also { printErrorContext() }
        incrementToken()
        if (currentTokenType != TokenType.LPAREN) {
            throw Exception("Expected '('").also { printErrorContext() }
        }
        incrementToken()
        val expr = expression()
        if (currentTokenType != TokenType.RPAREN) {
            throw Exception("Expected ')'").also { printErrorContext() }
        }
        incrementToken()

        if (currentTokenType != TokenType.LCURL) {
            throw Exception("Expected '{'").also { printErrorContext() }
        }
        incrementToken()

        val act = expression()//action()
        if (currentTokenType != TokenType.RCURL) {
            throw Exception("Expected '}'").also { printErrorContext() }
        }
        incrementToken()
        return lambda@{ functions, vars ->
            if(checkCondition(expr(functions, vars))) {
                val res = act(functions, vars)
                if(res is String) return@lambda Pair<Boolean, String?>(true, res)
                else return@lambda Pair<Boolean, String?>(true, null)
            }
            else return@lambda Pair<Boolean, String?>(false, null)
        }
    }


    private fun elif(inherited: LambdaToAny): LambdaToAny {
        return if (currentTokenType == TokenType.ELIF) {
            val currentElif = elif1()
            val next = elif(inherited)
            return lambda@{ functions, vars ->
                val result = currentElif(functions, vars) as Pair<Boolean, String?>
                if (result.first) return@lambda result
                else return@lambda next(functions, vars)
            }
        } else {
            inherited
        }
    }


    private fun elif1(): LambdaToAny {
        incrementToken() // past 'elif'
        if (currentTokenType != TokenType.LPAREN) throw Exception("Expected '('").also { printErrorContext() }
        incrementToken()

        val expr = expression()

        if (currentTokenType != TokenType.RPAREN) throw Exception("Expected ')'").also { printErrorContext() }
        incrementToken()

        if (currentTokenType != TokenType.LCURL) throw Exception("Expected '{'").also { printErrorContext() }
        incrementToken()

        val block = expression() // use action() if supporting multiple statements

        if (currentTokenType != TokenType.RCURL) throw Exception("Expected '}'").also { printErrorContext() }
        incrementToken()

        return lambda@{ functions, vars ->
            if (checkCondition(expr(functions, vars))) {
                val res = block(functions, vars)
                return@lambda Pair(true, res as? String)
            } else {
                return@lambda Pair(false, null)
            }
        }
    }



    private fun else0(): LambdaToAny {
        if (currentTokenType == TokenType.ELSE) {
            incrementToken()
            if (currentTokenType != TokenType.LCURL) throw Exception("Expected '{'").also { printErrorContext() }
            incrementToken()
            val block = expression()
            if (currentTokenType != TokenType.RCURL) throw Exception("Expected '}'").also { printErrorContext() }
            incrementToken()

            return lambda@{ functions, vars ->
                return@lambda block(functions, vars) as? String
            }
        } else {
            return lambda@{ _, _ -> null } // no else block
        }
    }


    private fun for0(): LambdaToAny {
        if (currentTokenType != TokenType.FOR) {
            throw Exception("Expected 'for'").also { printErrorContext() }
        }
        incrementToken()

        if (currentTokenType != TokenType.LPAREN) {
            throw Exception("Expected '('").also { printErrorContext() }
        }
        incrementToken()

        val ass1 = assign()

        if (currentTokenType != TokenType.SEMI) {
            throw Exception("Expected ';'").also { printErrorContext() }
        }
        incrementToken()

        val expr = expression()

        if (currentTokenType != TokenType.SEMI) {
            throw Exception("Expected ';'").also { printErrorContext() }
        }
        incrementToken()

        var ass2 = assign()

        if (currentTokenType != TokenType.RPAREN) {
            throw Exception("Expected ')'").also { printErrorContext() }
        }
        incrementToken()

        if (currentTokenType != TokenType.LCURL) {
            throw Exception("Expected '{'").also { printErrorContext() }
        }
        incrementToken()

        val act = expression()//action()

        if (currentTokenType != TokenType.RCURL) {
            throw Exception("Expected '}'").also { printErrorContext() }
        }
        incrementToken()
        return lambda@{ functions, vars ->
            val fCopy = functions.toMutableMap()
            val vCopy = vars.toMutableMap()
            ass1(fCopy, vCopy)
            var exRes = expr(fCopy, vCopy)
            var checker = exRes != "" && (exRes.toBigDecimalOrNull() == null || exRes.toBigDecimal() != BigDecimal.ZERO)
            while (checker) {
                val bodyResult = act(functions, vars)
                if (bodyResult is String) {
                    functions.forEach { key, value ->
                        functions[key] = fCopy[key]!!
                    }
                    vars.forEach { key, value ->
                        vars[key] = vCopy[key]!!
                    }
                    return@lambda bodyResult
                }
                ass2(fCopy, vCopy)
                exRes = expr(fCopy, vCopy)
                checker = exRes != "" && (exRes.toBigDecimalOrNull() == null || exRes.toBigDecimal() != BigDecimal.ZERO)
            }
        }

    }


    private fun while0(): LambdaToAny {
        if (currentTokenType != TokenType.WHILE) {
            throw Exception("Expected 'while'").also { printErrorContext() }
        }
        incrementToken()
        if (currentTokenType != TokenType.LPAREN) {
            throw Exception("Expected '('").also { printErrorContext() }
        }
        incrementToken()
        val expr = expression()

        if (currentTokenType != TokenType.RPAREN) {
            throw Exception("Expected ')'").also { printErrorContext() }
        }
        incrementToken()

        if (currentTokenType != TokenType.LCURL) {
            throw Exception("Expected '{'").also { printErrorContext() }
        }
        incrementToken()

        val act = expression()//TODO()

        if (currentTokenType != TokenType.RCURL) {
            throw Exception("Expected '}'").also { printErrorContext() }
        }
        incrementToken()
        return lambda@{ functions, variables ->


            var isFalse = true
            val fCopy = functions.toMutableMap()
            val vCopy = variables.toMutableMap()
            var exRes = expr(fCopy, vCopy)
            var checker = exRes != "" && (exRes.toBigDecimalOrNull() == null || exRes.toBigDecimal() != BigDecimal.ZERO)

            while (checker) {
                val ret = act(functions, variables)
                if (ret is String) {
                    functions.forEach { key, value ->
                        functions[key] = fCopy[key]!!
                    }
                    variables.forEach { key, value ->
                        variables[key] = vCopy[key]!!
                    }
                    return@lambda ret
                }
                exRes = expr(functions, variables)
                var checker =
                    exRes != "" && (exRes.toBigDecimalOrNull() == null || exRes.toBigDecimal() != BigDecimal.ZERO)
            }
        }
    }


    private fun assign(): LambdaToUnit {
        if (currentTokenType != TokenType.VARIABLE) throw Exception("Expected 'print'").also { printErrorContext() }
        val varName = currentTokenValue
        incrementToken()
        if (currentTokenType != TokenType.ASSIGN) throw Exception("Expected '='").also { printErrorContext() }
        incrementToken()
        val expr = expression()
        return { functions, variables ->
            variables[varName!!] = expr(functions, variables)
        }


    }

    private fun print(): LambdaToUnit {
        if (currentTokenType != TokenType.PRINT) throw Exception("Expected 'write'").also { printErrorContext() }
        incrementToken()
        if (currentTokenType != TokenType.LPAREN) throw Exception("Expected '('").also { printErrorContext() }
        incrementToken()
        val expr = expression()
        if (currentTokenType != TokenType.RPAREN) throw Exception("Expected ')'").also { printErrorContext() }
        incrementToken()
        return { functions, vars ->
            println(expr(functions, vars))
        }
    }


    private fun return0(): LambdaToString { // ni slo return
        if (currentTokenType != TokenType.RETURN) throw Exception("Expected 'return'").also { printErrorContext() }
        incrementToken()
        val expr = expression()
        return expr
    }

    private fun write(): LambdaToUnit {
        if (currentTokenType != TokenType.WRITE) throw Exception("Expected 'write'").also { printErrorContext() }
        incrementToken()
        if (currentTokenType != TokenType.LPAREN) throw Exception("Expected '('").also { printErrorContext() }
        incrementToken()
        val expr = expression()
        if (currentTokenType != TokenType.RPAREN) throw Exception("Expected ')'").also { printErrorContext() }
        incrementToken()
        return { functions, vars ->
            file.appendText(expr(functions, vars))
        }


    }

    private fun expression(): LambdaToString {
        if (currentTokenType == TokenType.CALCULATE) {
            incrementToken()
            if (currentTokenType != TokenType.LPAREN) throw Exception("Expected '('").also { printErrorContext() }
            incrementToken()
            val comp = compare()
            if (currentTokenType != TokenType.RPAREN) throw Exception("Expected ')'").also { printErrorContext() }
            incrementToken()
            return comp
        } else {
            return concat()
        }
    }


    private fun concat(): LambdaToString {
        val get = get()
        return concat1(get)
    }

    private fun concat1(inherited: LambdaToString): LambdaToString {
        if (currentTokenType == TokenType.PLUS) {
            incrementToken()
            if (currentTokenType in setOf(TokenType.STRING, TokenType.NUMBER, TokenType.VARIABLE, TokenType.FUN_NAME)) {
                val get = get()
                val lambda = lambda@{ functs: MutableMap<String, Funct>, vars: MutableMap<String, String> ->
                    return@lambda inherited(functs, vars) + get(functs, vars)
                }
                return concat1(lambda)
            } else {
                throw Exception("Unexpected token after '+' operator: $currentTokenValue").also { printErrorContext() }
            }
        } else {
            return inherited
        }
    }

    private fun get(): LambdaToString {
        val prim = primary()
        return get1(prim)
    }

    private fun get1(inherited: LambdaToString): LambdaToString {
        if (currentTokenType != TokenType.LSQUARE) return inherited
        incrementToken()
        val prim = primary()
        if (currentTokenType != TokenType.RSQUARE) throw Exception("Expected ']'").also { printErrorContext() }
        incrementToken()
        return lambda@{ functions, vars ->
            return@lambda inherited(functions, vars)[BigInteger(prim(functions, vars)).toInt()].toString()
        }


    }

    private fun compare(): LambdaToString {
        val bv = bval()
        return compare1(bv)
    }

    private fun compare1(inherited: LambdaToString): LambdaToString {
        val comparison: ((BigDecimal, BigDecimal) -> Boolean)? = when (currentTokenType) {
            TokenType.EQ -> BigDecimal::equals
            TokenType.NE -> { a, b -> a != b }
            TokenType.LT -> { a, b -> a < b }
            TokenType.GT -> { a, b -> a > b }
            TokenType.LE -> { a, b -> a <= b }
            TokenType.GE -> { a, b -> a >= b }
            else -> null
        }

        return if (comparison != null) {
            incrementToken()
            val bv = bval()
            val lambda = lambda@{ functions: MutableMap<String, Funct>, vars: MutableMap<String, String> ->
                val first = BigDecimal(inherited(functions, vars))
                val second = BigDecimal(bv(functions, vars))
                return@lambda if (comparison(first, second)) "1" else "0"
            }
            compare1(lambda)
        } else {
            inherited
        }
    }

    private fun bval(): LambdaToString {
        if (currentTokenType == TokenType.NEGATION) {
            incrementToken()
            val bin = binary()
            return lambda@{ functions, vars ->
                val res = bin(functions, vars)
                if (res == "0" || res == "") return@lambda "1"
                else return@lambda "0"
            }
        } else {
            val bin = binary()
            return bin
        }
    }

    private fun binary(): LambdaToString {
        val bit = bitwise()
        return binary1(bit)
    }

    private fun binary1(inherited: LambdaToString): LambdaToString {
        when (currentTokenType) {
            TokenType.OR -> {
                incrementToken()
                val bitwise = bitwise()
                val lambda = lambda@{ functions: MutableMap<String, Funct>, vars: MutableMap<String, String> ->
                    val inh = BigDecimal(inherited(functions, vars))
                    val b = BigDecimal(bitwise(functions, vars))
                    if (inh != BigDecimal.ZERO || b != BigDecimal.ZERO) {
                        return@lambda "1"
                    } else {
                        return@lambda "0"
                    }
                }
                return binary1(lambda)
            }

            TokenType.AND -> {
                incrementToken()
                val bitwise = bitwise()
                val lambda = lambda@{ functions: MutableMap<String, Funct>, vars: MutableMap<String, String> ->
                    val inh = BigDecimal(inherited(functions, vars))
                    val b = BigDecimal(bitwise(functions, vars))
                    if (inh != BigDecimal.ZERO && b != BigDecimal.ZERO) {
                        return@lambda "1"
                    } else {
                        return@lambda "0"
                    }
                }
                return binary1(lambda)
            }

            else -> {
                return inherited
            }
        }
    }

    private fun bitwise(): LambdaToString {
        val mod = mod()
        val bit = bitwise1(mod)
        return bit
    }

    private fun bitwise1(inherited: LambdaToString): LambdaToString {
        when (currentTokenType) {
            TokenType.BWOR -> {
                incrementToken()
                val mod = mod()
                val lambda = lambda@{ functions: MutableMap<String, Funct>, vars: MutableMap<String, String> ->
                    val inh = BigDecimal(inherited(functions, vars)).toBigInteger()
                    val b = BigDecimal(mod(functions, vars)).toBigInteger()
                    return@lambda inh.or(b).toString()
                }
                return bitwise1(lambda)
            }

            TokenType.BWAND -> {
                incrementToken()
                val mod = mod()
                val lambda = lambda@{ functions: MutableMap<String, Funct>, vars: MutableMap<String, String> ->
                    val inh = BigDecimal(inherited(functions, vars)).toBigInteger()
                    val b = BigDecimal(mod(functions, vars)).toBigInteger()
                    return@lambda inh.and(b).toString()
                }
                return bitwise1(lambda)
            }

            else -> {
                return inherited
            }
        }
    }

    private fun mod(): LambdaToString {
        val add = additive()
        return mod1(add)
    }

    private fun mod1(inherited: LambdaToString): LambdaToString {
        if (currentTokenType == TokenType.MOD) {
            incrementToken()
            val add = additive()
            //incrementToken()
            val lambda = lambda@{ functions: MutableMap<String, Funct>, vars: MutableMap<String, String> ->
                val inh = BigDecimal(inherited(functions, vars)).remainder(BigDecimal(add(functions, vars)))
                return@lambda inh.toPlainString()
            }
            return mod1(lambda)

        } else {
            return inherited
        }
    }

    private fun additive(): LambdaToString {
        val mul = multiplicative()
        val add1 = additive1(mul)
        return add1
    }

    private fun additive1(inherited: LambdaToString): LambdaToString {
        when (currentTokenType) {
            TokenType.PLUS -> {
                incrementToken()
                val multiplicative = multiplicative()
                val lambda = lambda@{ functions: MutableMap<String, Funct>, vars: MutableMap<String, String> ->
                    val inh =
                        BigDecimal(inherited(functions, vars)).plus(BigDecimal(multiplicative(functions, vars)))
                    return@lambda inh.toPlainString()
                }
                return additive1(lambda)
            }

            TokenType.MINUS -> {
                incrementToken()
                val multiplicative = multiplicative()
                val lambda = lambda@{ functions: MutableMap<String, Funct>, vars: MutableMap<String, String> ->
                    val inh =
                        BigDecimal(inherited(functions, vars)).minus(BigDecimal(multiplicative(functions, vars)))
                    return@lambda inh.toPlainString()
                }
                return additive1(lambda)
            }

            else -> {
                return inherited
            }
        }
    }

    private fun multiplicative(): LambdaToString {
        val un = unit()
        val out = multiplicative1(un)
        return out
    }


    private fun multiplicative1(inherited: LambdaToString): LambdaToString {
        when (currentTokenType) {
            TokenType.MULTIPLY -> {
                incrementToken()
                val un = unit()
                val lambda = lambda@{ functions: MutableMap<String, Funct>, vars: MutableMap<String, String> ->
                    val inh = BigDecimal(inherited(functions, vars)).times(BigDecimal(un(functions, vars)))
                    return@lambda inh.toPlainString()
                }
                return multiplicative1(lambda)
            }

            TokenType.DIVIDE -> {
                incrementToken()
                val un = unit()
                val lambda = lambda@{ functions: MutableMap<String, Funct>, vars: MutableMap<String, String> ->
                    val inh = BigDecimal(inherited(functions, vars)).divide(
                        BigDecimal(un(functions, vars)),
                        10,
                        RoundingMode.HALF_UP
                    )
                    return@lambda inh.toPlainString()
                }
                return multiplicative1(lambda)
            }

            else -> {
                return inherited
            }
        }
    }

    private fun unit(): LambdaToString {
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

    private fun primary(): LambdaToString {
        when (currentTokenType) {
            TokenType.STRING,
            TokenType.NUMBER -> {
                val content = currentTokenValue
                incrementToken()
                return { functions, vars -> content ?: "" }
            }

            TokenType.VARIABLE -> {
                val currVal = currentTokenValue
                incrementToken()
                return { functions, vars ->
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
                return { functions, vars ->
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
