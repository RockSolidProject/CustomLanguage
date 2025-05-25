import java.math.BigDecimal

/*class Root(override val size: Int) : MutableList<Feature> {

}*/
abstract class Feature() {
    abstract fun toGeoJSON() : String
}

class Features(
    val featuresList : MutableList<Feature> = mutableListOf()
){
    fun toGeoJSON() : String {
        var out = "{\n" +
                "  \"type\": \"FeatureCollection\",\n" +
                "  \"features\": [\n"
        out += featuresList.joinToString(",\n") {feature -> feature.toGeoJSON() }
        out += "\n  ]\n" +
                "}"
        return out
    }
}

class Door(
    val x1 : BigDecimal,
    val y1 : BigDecimal,
    val x2 : BigDecimal,
    val y2 : BigDecimal
) : Feature (){
    override fun toGeoJSON(): String {
        return """
            {
            "type": "Feature",
              "properties": {
                "name": "Door",
                "stroke": "#ff0000",
                "stroke-width": 4
              },
              "geometry": {
                "type": "LineString",
                "coordinates": [
                  [ $x1, $y1 ],
                  [ $x2, $y2 ]
                ]
              }
            }""".trimIndent().prependIndent("    ")
    }
}

class Window(
    val x1 : BigDecimal,
    val y1 : BigDecimal,
    val x2 : BigDecimal,
    val y2 : BigDecimal
) : Feature (){
    override fun toGeoJSON(): String {
        return """
            {
            "type": "Feature",
              "properties": {
                "name": "Window",
                "stroke": "#0000ff",
                "stroke-width": 2
              },
              "geometry": {
                "type": "LineString",
                "coordinates": [
                  [ $x1, $y1 ],
                  [ $x2, $y2 ]
                ]
              }
            }""".trimIndent().prependIndent("    ")
    }
}

class Wall(
    val x1 : BigDecimal,
    val y1 : BigDecimal,
    val x2 : BigDecimal,
    val y2 : BigDecimal
) : Feature (){
    override fun toGeoJSON(): String {
        return """
            {
            "type": "Feature",
              "properties": {
                "name": "Wall",
                "stroke": "#000000",
                "stroke-width": 4
              },
              "geometry": {
                "type": "LineString",
                "coordinates": [
                  [ $x1, $y1 ],
                  [ $x2, $y2 ]
                ]
              }
            }""".trimIndent().prependIndent("    ")
    }
}
