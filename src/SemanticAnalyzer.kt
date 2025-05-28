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
    init {
        initFunctionMap["door"] = Funct(4,lambda@{ args,functions, vars ->
            println("Creating a door")
            features.featuresList.add(Door(
                args[0].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[1].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[2].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[3].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers.")
            ))
            return@lambda ""
        })

        initFunctionMap["climbingWall"] = Funct(4,lambda@{ args,functions, vars ->
            println("Creating a climbing wall")
            features.featuresList.add(ClimbingWall(
                args[0].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[1].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[2].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[3].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers.")
            ))
            return@lambda ""
        })

        initFunctionMap["sprayWall"] = Funct(4,lambda@{ args,functions, vars ->
            println("Creating a spray wall")
            features.featuresList.add(SprayWall(
                args[0].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[1].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[2].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[3].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers.")
            ))
            return@lambda ""
        })

        initFunctionMap["moonboard"] = Funct(4,lambda@{ args,functions, vars ->
            println("Creating a moonboard")
            features.featuresList.add(Moonboard(
                args[0].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[1].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[2].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[3].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers.")
            ))
            return@lambda ""
        })

        initFunctionMap["window"] = Funct(4,lambda@{ args,functions, vars ->
            println("Creating a window")
            features.featuresList.add(Window(
                args[0].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[1].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[2].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[3].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers.")
            ))
            return@lambda ""
        })
        initFunctionMap["wall"] = Funct(4,lambda@{ args,functions, vars ->
            println("Creating a wall")
            features.featuresList.add(Wall(
                args[0].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[1].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[2].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[3].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers.")
            ))
            return@lambda ""
        })

        initFunctionMap["chillOutSpot"] = Funct(8,lambda@{ args,functions, vars ->
            println("Creating a chill out spot")
            features.featuresList.add(ChillOutSpot(
                args[0].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[1].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[2].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[3].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[4].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[5].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[6].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[7].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers.")
            ))
            return@lambda ""
        })

        initFunctionMap["trainingSpace"] = Funct(8,lambda@{ args,functions, vars ->
            println("Creating a training space.")
            features.featuresList.add(TrainingSpace(
                args[0].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[1].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[2].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[3].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[4].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[5].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[6].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[7].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers.")
            ))
            return@lambda ""
        })

        initFunctionMap["wardrobe"] = Funct(8,lambda@{ args,functions, vars ->
            println("Creating a wardrobe.")
            features.featuresList.add(Wardrobe(
                args[0].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[1].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[2].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[3].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[4].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[5].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[6].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[7].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers.")
            ))
            return@lambda ""
        })

        initFunctionMap["reception"] = Funct(8,lambda@{ args,functions, vars ->
            println("Creating a reception.")
            features.featuresList.add(Reception(
                args[0].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[1].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[2].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[3].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[4].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[5].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[6].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[7].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers.")
            ))
            return@lambda ""
        })

        initFunctionMap["toilet"] = Funct(8,lambda@{ args,functions, vars ->
            println("Creating a toilet.")
            features.featuresList.add(Toilet(
                args[0].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[1].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[2].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[3].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[4].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[5].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[6].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers."),
                args[7].toBigDecimalOrNull() ?: throw Exception("All arguments for doors must be convertible to numbers.")
            ))
            return@lambda ""
        })
    }

    private var tokenIndex = 0
    private var currentToken: Pair<String, TokenType>? = null
    private var currentTokenType: TokenType? = null
    private var currentTokenValue: String? = null
    private var checkCondition =
        { str: String -> str != "" && (str.toBigDecimalOrNull() == null || str.toBigDecimal() != BigDecimal.ZERO) }

    companion object {
        var initFunctionMap = mutableMapOf<String, Funct>()
        var initVarMap = mutableMapOf<String, String>()
        val file = File("output.txt")
        val fileGeo = File("geoOutput.txt")
        val features = Features()
    }

    fun testParse(): Boolean {
        file.writeText("")

        init()
        program()

        if (currentTokenType != null) {
            throw Exception("Unexpected token: $currentTokenValue").also { printErrorContext() }
            //println("Unexpected token: $currentTokenValue")
            return false
        }
        fileGeo.writeText(features.toGeoJSON())
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
        //println("new token : $currentTokenType")
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

    fun program() {
        val functions = initFunctionMap.toMutableMap()
        functions.putAll(function())
        val varMapCopy = initVarMap.toMutableMap()
        functions["main"]!!.body(mutableListOf(), functions, varMapCopy)
    }

    private fun function(): MutableMap<String, Funct> {
        if (currentTokenType == TokenType.FUNCTION) {
            val func = function1()
            val funcMap = function2(func)
            return funcMap
        } else {
            throw Exception("Unexpected token type: $currentTokenType").also { printErrorContext() }
        }

    }

    private fun function1(): MutableMap<String, Funct> {
        if (currentTokenType != TokenType.FUNCTION) return mutableMapOf()
        incrementToken()
        if (currentTokenType != TokenType.FUN_NAME) throw Exception("Expected function name").also { printErrorContext() }

        val name = currentTokenValue!!

        incrementToken()
        if (currentTokenType != TokenType.LPAREN) throw Exception("Expected '('").also { printErrorContext() }
        incrementToken()
        val args = args()
        if (currentTokenType != TokenType.RPAREN) throw Exception("Expected ')'").also { printErrorContext() }
        incrementToken()
        if (currentTokenType != TokenType.LCURL) throw Exception("Expected '{'").also { printErrorContext() }
        incrementToken()

        val act = action()

        if (currentTokenType != TokenType.RCURL) throw Exception("Expected '}'").also { printErrorContext() }

        incrementToken()
        val numArgs = args.size
        val body = lambda@{ functionArguments: MutableList<String>, functions: MutableMap<String, Funct>, variables: MutableMap<String, String> ->
            if (functionArguments.size != numArgs) throw Exception("Expected number of arguments: $numArgs, given: ${functionArguments.size}")
            val vars = mutableMapOf<String, String>()
            for (i in 0 until numArgs) {
                vars[args[i]] = functionArguments[i]
            }
            val ret = act(functions, vars)
            if (ret is String) return@lambda ret as String else return@lambda ""
        }
        return mutableMapOf(Pair(name, Funct(numArgs, body)))
    }
    fun function2(inherited: MutableMap<String, Funct>): MutableMap<String, Funct> {
        if(currentTokenType != TokenType.FUNCTION) return inherited
        inherited.putAll(function1())
        function2(inherited)
        return inherited
    }

    private fun args(): MutableList<String> {
        return when (currentTokenType) {
            TokenType.VARIABLE -> {
                val name = currentTokenValue!!
                incrementToken()
                val list = mutableListOf(name)
                args1(list)
            }

            TokenType.RPAREN -> {
                mutableListOf() // no arguments
            }

            else -> {
                throw Exception("Expected variable").also { printErrorContext() }
            }
        }
    }

    private fun args1(inherited: MutableList<String>): MutableList<String> {
        return if (currentTokenType == TokenType.COMMA) {
            incrementToken()
            if (currentTokenType == TokenType.VARIABLE) {
                val name = currentTokenValue
                inherited.add(name!!) // <-- fix: add the variable name
                incrementToken()
                args1(inherited) // recursively check for more
            } else {
                throw Exception("Expected variable").also { printErrorContext() }
            }
        } else {
            inherited // done parsing arguments
        }
    }

    private fun functionArgs(): MutableList<LambdaToString> {
        return when (currentTokenType) {
            TokenType.RPAREN -> {
                mutableListOf() // No arguments
            }

            else -> {
                val expr = expression()
                val list = mutableListOf(expr)
                return functionArgs1(list)
            }
        }
    }


    private fun functionArgs1(inherited: MutableList<LambdaToString>): MutableList<LambdaToString> {
        return if (currentTokenType == TokenType.COMMA) {
            incrementToken()
            val expr = expression()
            inherited.add(expr)
            functionArgs1(inherited)
        } else {
            inherited // End of argument list
        }
    }


    private fun action(): LambdaToAny {
        val validTokens =
            setOf(TokenType.VARIABLE, TokenType.PRINT, TokenType.RETURN, TokenType.WRITE, TokenType.FUN_NAME)

        return if (currentTokenType in validTokens) {
            val firstAction = action2()
            if (currentTokenType == TokenType.SEMI) {
                incrementToken()
                return action1(firstAction)
            } else {
                throw Exception("Expected ';'").also { printErrorContext() }
            }
        } else {
            val stmt = statement() // e.g., if/while/for
            return action1(stmt)
        }
    }


    private fun action1(inherited: LambdaToAny): LambdaToAny {
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

        if (currentTokenType in validTokens) {
            val nextAction = action()

            return lambda@{ functions, vars ->
                val result = inherited(functions, vars)
                if (result is String) {
                    return@lambda result // stop early if return/exit occurred
                } else {
                    return@lambda nextAction(functions, vars)
                }
            }
        } else {
            return inherited
        }
    }

    private fun action2(): LambdaToAny { //Action'' ::= Assign | Print | Return | Write | id ( Args )
        if (currentTokenType == TokenType.VARIABLE) {
            return assign()
        } else if (currentTokenType == TokenType.PRINT) {
            return print()
        } else if (currentTokenType == TokenType.RETURN) {
            return return0()
        } else if (currentTokenType == TokenType.WRITE) {
            return write()
        } else if (currentTokenType == TokenType.FUN_NAME) {
            val name = currentTokenValue!!
            incrementToken()
            if (currentTokenType != TokenType.LPAREN) throw Exception("Expected '('").also { printErrorContext() }
            incrementToken()
            val arguments = functionArgs()
            if (currentTokenType != TokenType.RPAREN) throw Exception("Expected ')'").also { printErrorContext() }
            incrementToken()
            return lambda@{ functions, vars ->
                val argValues = mutableListOf<String>()
                for(arg in arguments) argValues.add(arg(functions, vars))
                val varsCopy = initVarMap.toMutableMap()
                val response = functions[name]!!.body(argValues, functions, varsCopy)
                return@lambda Unit
            }


        } else {
            throw throw Exception("Unexpected token: $currentTokenValue").also { printErrorContext() }
        }
    }

    private fun statement(): LambdaToAny {
        when (currentTokenType) {
            TokenType.IF -> {
                return if0()
            }

            TokenType.FOR -> {
                return for0()
            }

            TokenType.WHILE -> {
                return while0()
            }

            else -> throw throw Exception("Unexpected token: $currentTokenValue").also { printErrorContext() }
        }
    }

    private fun if0(): LambdaToAny {
        if (currentTokenType != TokenType.IF) throw Exception("Expected 'if'").also { printErrorContext() }
        val first = if1()
        val second = elif(first)
        val third = else0()
        return lambda@{ functions, vars ->
            val firstOut: Pair<Boolean, String?> = first(functions, vars) as Pair<Boolean, String?>
            if (firstOut.first) return@lambda firstOut.second ?: Unit
            val secondOut: Pair<Boolean, String?> = second(functions, vars) as Pair<Boolean, String?>
            if (secondOut.first) return@lambda secondOut.second ?: Unit
            val thirdOut: String? = third(functions, vars) as String?
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

        val act = action()
        if (currentTokenType != TokenType.RCURL) {
            throw Exception("Expected '}'").also { printErrorContext() }
        }
        incrementToken()
        return lambda@{ functions, vars ->
            if (checkCondition(expr(functions, vars))) {
                val res = act(functions, vars)
                if (res is String) return@lambda Pair<Boolean, String?>(true, res)
                else return@lambda Pair<Boolean, String?>(true, null)
            } else return@lambda Pair<Boolean, String?>(false, null)
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

        val block = action() //if supporting multiple statements

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
            val block = action()
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

        val act = action()

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
                val bodyResult = act(fCopy, vCopy)
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

        val act = action()

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
                val ret = act(fCopy, vCopy)
                if (ret is String) {
                    functions.forEach { key, value ->
                        functions[key] = fCopy[key]!!
                    }
                    variables.forEach { key, value ->
                        variables[key] = vCopy[key]!!
                    }
                    return@lambda ret
                }
                exRes = expr(fCopy, vCopy)
                checker =
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
                val name = currentTokenValue ?: throw Exception("Expected function name")
                incrementToken()

                if (currentTokenType != TokenType.LPAREN) {
                    throw Exception("Expected '('").also { printErrorContext() }
                }
                incrementToken()

                val args = functionArgs()

                if (currentTokenType != TokenType.RPAREN) {
                    throw Exception("Expected ')'").also { printErrorContext() }
                }
                incrementToken()

                return let@{ functions, vars ->
                    val funct = functions[name] ?: throw Exception("Function $name not defined: only defined ${functions}")
                    val evaluatedArgs = args.map { it(functions, vars) }.toMutableList()
                    var varsCopy = initVarMap.toMutableMap()
                    return@let funct.body(evaluatedArgs, functions, varsCopy)
                }
            }

            TokenType.LPAREN -> {

                incrementToken()

                val expr = expression()

                if (currentTokenType != TokenType.RPAREN) {
                    throw Exception("Expected ')'").also { printErrorContext() }
                }
                incrementToken()
                return expr
            }

            else -> throw Exception("Unexpected token: $currentTokenValue").also { printErrorContext() }
        }

    }
}
