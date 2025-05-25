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

class ClimbingWall(
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
                "name": "Climbing wall",
                "stroke": "#0521f5",
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

class SprayWall(
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
                "name": "SprayWall",
                "stroke": "#4a407a",
                "stroke-width": 6
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

class Moonboard(
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
                "name": "Moonboard",
                "stroke": "#8a5a07",
                "stroke-width": 8
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
                "stroke": "#1cafed",
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


class ChillOutSpot(
    val x1 : BigDecimal,
    val y1 : BigDecimal,
    val x2 : BigDecimal,
    val y2 : BigDecimal,
    val x3 : BigDecimal,
    val y3 : BigDecimal,
    val x4 : BigDecimal,
    val y4 : BigDecimal
) : Feature (){
    override fun toGeoJSON(): String {
        return """
            {
            "type": "Feature",
              "properties": {
                "name": "ChillOutSpot",
                "stroke": "#00dd00",
                "stroke-width": 3,
                "fill": "#00aa00",   
                "fill-opacity": 0.5  
              },
              "geometry": {
                "type": "Polygon",
                "coordinates": [[
                  [ $x1, $y1 ],
                  [ $x2, $y2 ],
                  [ $x3, $y3 ],
                  [ $x4, $y4 ],
                  [ $x1, $y1 ]
                ]]
              }
            }""".trimIndent().prependIndent("    ")
    }
}

class TrainingSpace(
    val x1 : BigDecimal,
    val y1 : BigDecimal,
    val x2 : BigDecimal,
    val y2 : BigDecimal,
    val x3 : BigDecimal,
    val y3 : BigDecimal,
    val x4 : BigDecimal,
    val y4 : BigDecimal
) : Feature (){
    override fun toGeoJSON(): String {
        return """
            {
            "type": "Feature",
              "properties": {
                "name": "TrainingSpace",
                "stroke": "#e80770",
                "stroke-width": 3,
                "fill": "#910446",   
                "fill-opacity": 0.5  
              },
              "geometry": {
                "type": "Polygon",
                "coordinates": [[
                  [ $x1, $y1 ],
                  [ $x2, $y2 ],
                  [ $x3, $y3 ],
                  [ $x4, $y4 ],
                  [ $x1, $y1 ]
                ]]
              }
            }""".trimIndent().prependIndent("    ")
    }
}

class Wardrobe(
    val x1 : BigDecimal,
    val y1 : BigDecimal,
    val x2 : BigDecimal,
    val y2 : BigDecimal,
    val x3 : BigDecimal,
    val y3 : BigDecimal,
    val x4 : BigDecimal,
    val y4 : BigDecimal
) : Feature (){
    override fun toGeoJSON(): String {
        return """
            {
            "type": "Feature",
              "properties": {
                "name": "Wardrobe",
                "stroke": "#666666",
                "stroke-width": 3,
                "fill": "#a09da1",   
                "fill-opacity": 0.5  
              },
              "geometry": {
                "type": "Polygon",
                "coordinates": [[
                  [ $x1, $y1 ],
                  [ $x2, $y2 ],
                  [ $x3, $y3 ],
                  [ $x4, $y4 ],
                  [ $x1, $y1 ]
                ]]
              }
            }""".trimIndent().prependIndent("    ")
    }
}
class Reception(
    val x1 : BigDecimal,
    val y1 : BigDecimal,
    val x2 : BigDecimal,
    val y2 : BigDecimal,
    val x3 : BigDecimal,
    val y3 : BigDecimal,
    val x4 : BigDecimal,
    val y4 : BigDecimal
) : Feature (){
    override fun toGeoJSON(): String {
        return """
            {
            "type": "Feature",
              "properties": {
                "name": "Reception",
                "stroke": "#a045d1",
                "stroke-width": 3,
                "fill": "#8945d1",   
                "fill-opacity": 0.5  
              },
              "geometry": {
                "type": "Polygon",
                "coordinates": [[
                  [ $x1, $y1 ],
                  [ $x2, $y2 ],
                  [ $x3, $y3 ],
                  [ $x4, $y4 ],
                  [ $x1, $y1 ]
                ]]
              }
            }""".trimIndent().prependIndent("    ")
    }
}
class Toilet(
    val x1 : BigDecimal,
    val y1 : BigDecimal,
    val x2 : BigDecimal,
    val y2 : BigDecimal,
    val x3 : BigDecimal,
    val y3 : BigDecimal,
    val x4 : BigDecimal,
    val y4 : BigDecimal
) : Feature (){
    override fun toGeoJSON(): String {
        return """
            {
            "type": "Feature",
              "properties": {
                "name": "Toilet",
                "stroke": "#a09da1",
                "stroke-width": 3,
                "fill": "#666666",   
                "fill-opacity": 0.5  
              },
              "geometry": {
                "type": "Polygon",
                "coordinates": [[
                  [ $x1, $y1 ],
                  [ $x2, $y2 ],
                  [ $x3, $y3 ],
                  [ $x4, $y4 ],
                  [ $x1, $y1 ]
                ]]
              }
            }""".trimIndent().prependIndent("    ")
    }
}

