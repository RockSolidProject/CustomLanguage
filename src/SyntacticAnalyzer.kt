class SyntacticAnalyzer(var tokens: List<Pair<String, TokenType>>?) {
    private var tokenIndex = 0
    private var currentToken: Pair<String, TokenType>? = null
    private var currentTokenType: TokenType? = null
    private var currentTokenValue: String? = null

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

    fun parse(): Boolean {

        return true
    }


    private fun args() {
        TODO("Not yet implemented")
    }



    private fun if0(){
        return if (currentTokenType == TokenType.IF) {
            if1()
            elif1()
            else0()
        } else {
            throw Exception("Expected 'if'")
        }
    }
    private fun if1(){
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
                            throw Exception("Expected '}'")
                        }
                    } else {
                        throw Exception("Expected '{'")
                    }
                } else {
                    throw Exception("Expected ')'")
                }
            } else {
                throw Exception("Expected '('")
            }
        } else {

        }
    }
    private fun elif(){
        if (currentTokenType == TokenType.ELIF) {
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
                            throw Exception("Expected '}'")
                        }
                    } else {
                        throw Exception("Expected '{'")
                    }
                } else {
                    throw Exception("Expected ')'")
                }
            } else {
                throw Exception("Expected '('")
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
                    throw Exception("Expected '}'")
                }
            } else {
                throw Exception("Expected '{'")
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
                                    throw Exception("Expected '}'")
                                }
                            } else {
                                throw Exception("Expected '{'")
                            }
                        } else {
                            throw Exception("Expected ')'")
                        }
                    } else {
                        throw Exception("Expected ';'")
                    }
                } else {
                    throw Exception("Expected ';'")
                }
            } else {
                throw Exception("Expected '('")
            }
        } else {
            throw Exception("Expected 'for'")
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
                            throw Exception("Expected '}'")
                        }
                    } else {
                        throw Exception("Expected '{'")
                    }
                } else {
                    throw Exception("Expected ')'")
                }
            } else {
                throw Exception("Expected '('")
            }
        } else {
            throw Exception("Expected 'while'")
        }
    }

    private fun assign() {
        return if (currentTokenType == TokenType.VARIABLE) {
            incrementToken()
            if (currentTokenType == TokenType.EQ) {
                incrementToken()
                expression()
            } else {
                throw Exception("Expected '='")
            }
        } else {
            throw Exception("Expected variable")
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
                    throw Exception("Expected ')'")
                }
            } else {
                throw Exception("Expected '('")
            }
        } else {
            throw Exception("Expected 'print'")
        }
    }

    private fun return0() { // ni slo return
        return if (currentTokenType == TokenType.RETURN) {
            incrementToken()
            expression()
        } else {
            throw Exception("Expected 'return'")
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
                    throw Exception("Expected ')'")
                }
            } else {
                throw Exception("Expected '('")
            }
        } else {
            throw Exception("Expected 'write'")
        }
    }

    private fun expression() {
        return if (currentTokenType == TokenType.CALCULATE) {
            incrementToken()
            if (currentTokenType == TokenType.LPAREN) {
                incrementToken()
                compare()
                if (currentTokenType != TokenType.RPAREN) {
                    incrementToken()
                } else {
                    throw Exception("Expected ')'")
                }
            } else {
                throw Exception("Expected '('")
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
        return when (currentTokenType) {
            TokenType.PLUS -> {
                incrementToken()
                concat()
            }

            else -> {}
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
                throw Exception("Expected ']'")
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
        return when (currentTokenType) {
            TokenType.MOD -> {
                incrementToken()
                mod()
            }

            else -> {}
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
                        throw Exception("Expected ')'")
                    }
                } else {
                    throw Exception("Expected '('")
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

            else -> throw Exception("Unexpected token: $currentTokenValue")
        }
    }
}