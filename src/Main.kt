fun main(){
    val lexicalAnalyzer = LexicalAnalyzer()
    val analyzerOutput = lexicalAnalyzer.analyzeFile("input2.txt")
    if (analyzerOutput == null) {
        println("Analyzing failed.")
        return
    }
    /*analyzerOutput.forEach {
        println("Content: \"${it.first}\", type: ${it.second}")
    }*/
    val semanticAnalyzer = SemanticAnalyzer(analyzerOutput)
    println(semanticAnalyzer.testParse())
}
