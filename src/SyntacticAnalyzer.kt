class SyntacticAnalyzer(var tokens: List<Pair<String, TokenType>>?) {
    private var tokenIndex = 0
    private var currentToken: Pair<String, TokenType>? = null
    private var currentTokenType: TokenType? = null
    private var currentTokenValue: String? = null

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

    fun parse(): Boolean {
        init()
        program()
        if (currentTokenType != null) {
            throw Exception("Unexpected token: $currentTokenValue").also {  printErrorContext() }
        }
        return true
    }


    private fun args() {
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
        function()
        function()
    }

    private fun import() {
        return if (currentTokenType == TokenType.IMPORT) {
            incrementToken()
            if (currentTokenType == TokenType.LPAREN) {
                incrementToken()
                if (currentTokenType == TokenType.STRING) {
                    incrementToken()
                    if (currentTokenType == TokenType.RPAREN) {
                        incrementToken()
                        import()
                    } else {
                        throw Exception("Expected ')'").also {  printErrorContext() }
                    }
                } else {
                    throw Exception("Expected string").also {  printErrorContext() }
                }
            } else {
                throw Exception("Expected '('").also {  printErrorContext() }
            }
        } else {

        }
    }

    private fun function() {
        return if (currentTokenType == TokenType.FUNCTION) {
            function1()
            function()
        } else {

        }
    }

    private fun function1() {
        return if (currentTokenType == TokenType.FUNCTION) {
            incrementToken()
            if (currentTokenType == TokenType.FUN_NAME) {
                incrementToken()
                if (currentTokenType == TokenType.LPAREN) {
                    incrementToken()
                    args()
                    if (currentTokenType == TokenType.RPAREN) {
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
                        throw Exception("Expected ')'").also {  printErrorContext() }
                    }
                } else {
                    throw Exception("Expected '('").also {  printErrorContext() }
                }
            } else {
                throw Exception("Expected function name").also {  printErrorContext() }
            }
        } else {
            throw Exception("Expected 'function'").also {  printErrorContext() }
        }
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
        return if (currentTokenType == TokenType.IF) {
            incrementToken()
            if (currentTokenType == TokenType.LPAREN) {
                incrementToken()
                expression()
                if (currentTokenType == TokenType.RPAREN) {
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
                    throw Exception("Expected ')'").also {  printErrorContext() }
                }
            } else {
                throw Exception("Expected '('").also {  printErrorContext() }
            }
        } else {

        }
    }

    private fun elif() {
        return if (currentTokenType == TokenType.ELIF) {
            elif1()
            elif()
        } else {

        }
    }

    private fun elif1() {
        return if (currentTokenType == TokenType.ELIF) {
            incrementToken()
            if (currentTokenType == TokenType.LPAREN) {
                incrementToken()
                expression()
                if (currentTokenType == TokenType.RPAREN) {
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
                    throw Exception("Expected ')'").also {  printErrorContext() }
                }
            } else {
                throw Exception("Expected '('").also {  printErrorContext() }
            }
        } else {

        }
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
        return if (currentTokenType == TokenType.FOR) {
            incrementToken()
            if (currentTokenType == TokenType.LPAREN) {
                incrementToken()
                assign()
                if (currentTokenType == TokenType.SEMI) {
                    incrementToken()
                    expression()
                    if (currentTokenType == TokenType.SEMI) {
                        incrementToken()
                        assign()
                        if (currentTokenType == TokenType.RPAREN) {
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
                            throw Exception("Expected ')'").also {  printErrorContext() }
                        }
                    } else {
                        throw Exception("Expected ';'").also {  printErrorContext() }
                    }
                } else {
                    throw Exception("Expected ';'").also {  printErrorContext() }
                }
            } else {
                throw Exception("Expected '('").also {  printErrorContext() }
            }
        } else {
            throw Exception("Expected 'for'").also {  printErrorContext() }
        }
    }

    private fun while0() {
        return if (currentTokenType == TokenType.WHILE) {
            incrementToken()
            if (currentTokenType == TokenType.LPAREN) {
                incrementToken()
                expression()
                if (currentTokenType == TokenType.RPAREN) {
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
                    throw Exception("Expected ')'").also {  printErrorContext() }
                }
            } else {
                throw Exception("Expected '('").also {  printErrorContext() }
            }
        } else {
            throw Exception("Expected 'while'").also {  printErrorContext() }
        }
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
        return if (currentTokenType == TokenType.PRINT) {
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
            throw Exception("Expected 'print'").also {  printErrorContext() }
        }
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

    private fun expression() {
        return if (currentTokenType == TokenType.CALCULATE) {
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
            if (currentTokenType in setOf(TokenType.STRING, TokenType.NUMBER, TokenType.VARIABLE)) {
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
    }

    private fun bval() {
        return if (currentTokenType == TokenType.NEGATION) {
            incrementToken()
            binary()
        } else {
            binary()
        }
    }

    private fun binary() {
        bitwise()
        binary1()
    }

    private fun binary1() {
        return when (currentTokenType) {
            TokenType.OR -> {
                incrementToken()
                binary()
            }

            TokenType.AND -> {
                incrementToken()
                binary()
            }
            else -> {}
        }
    }

    private fun bitwise() {
        mod()
        bitwise1()
    }

    private fun bitwise1() {
        return when (currentTokenType) {
            TokenType.BWOR -> {
                incrementToken()
                bitwise()
            }

            TokenType.BWAND -> {
                incrementToken()
                bitwise()
            }

            else -> {}
        }
    }

    private fun mod() {
        additive()
        mod1()
    }

    private fun mod1() {
        return if (currentTokenType == TokenType.MOD) {
            incrementToken()
            mod()

        }else {

        }
    }

    private fun additive() {
        multiplicative()
        additive1()
    }

    private fun additive1() {
        return when (currentTokenType) {
            TokenType.PLUS -> {
                incrementToken()
                additive()
            }

            TokenType.MINUS -> {
                incrementToken()
                additive()
            }

            else -> {}
        }
    }

    private fun multiplicative() {
        unit()
        multiplicative1()
    }


    private fun multiplicative1() {
        return when (currentTokenType) {
            TokenType.MULTIPLY -> {
                incrementToken()
                multiplicative()
            }

            TokenType.DIVIDE -> {
                incrementToken()
                multiplicative()
            }

            else -> {}
        }
    }

    private fun unit() {
        return when (currentTokenType) {
            TokenType.PLUS -> {
                incrementToken()
                primary()
            }

            TokenType.MINUS -> {
                incrementToken()
                primary()
            }

            else -> {
                primary()
            }
        }
    }

    private fun primary() {
        when (currentTokenType) {
            TokenType.STRING -> {
                incrementToken()
            }

            TokenType.NUMBER -> {
                incrementToken()
            }

            TokenType.VARIABLE -> {
                incrementToken()
            }

            TokenType.FUN_NAME -> {
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
            }

            TokenType.LPAREN -> {
                incrementToken()
                if (currentTokenType == TokenType.RPAREN) {
                    incrementToken()
                    expression()
                } else {
                    throw Exception("Expected ')'")
                }
            }

            else -> throw throw Exception("Unexpected token: $currentTokenValue").also {  printErrorContext() }
        }
    }
}