import com.sun.source.tree.IfTree
import kotlin.math.sqrt
import kotlin.math.pow
import kotlin.random.Random

fun throwDart(circleRadius: Double = 1.0, squareSize: Double = 2.0): Double {
    val x = Random.nextDouble(0.0, squareSize/2)
    val y = Random.nextDouble(0.0, squareSize/2)
    val h = sqrt(x.pow(2) + y.pow(2))
    val r = sqrt(circleRadius.pow(2) - h.pow(2))
    return if (h > circleRadius || r == Double.NaN) 0.0 else r
}

val results = mutableListOf<Int>()
repeat(100000) {
    var radius: Double = 1.0
    val radiusList = mutableListOf<Double>()
    var i = 0
    radiusList.add(radius)
    while (radius > 0) {
        radius = throwDart(radius)
        if (radius > 0) {
            i++
            radiusList.add(radius)
        }
    }
    println("${it+1}: $i $radiusList")
    results.add(i)
}

val entrys = mutableListOf<Int>()

results.forEach {
    if (it > entrys.size) {
        repeat(it-entrys.size) {
            entrys.add(0)
        }
    }
    if (it < 1) return@forEach
    entrys[it-1] = entrys[it-1] + 1
}

val entrysReversed = entrys.asReversed()
repeat(entrys.size) {
    if (it < 1) return@repeat
    entrysReversed[it] = entrysReversed[it] + entrysReversed[it-1]
}

println()
println("====================================")
entrys.forEachIndexed { i, e ->
    println("${i+1}: $e (${(e.toDouble()/results.size.toDouble())*100}%)")
}
println("====================================")
println("average hits: ${results.sum().toDouble()/results.size.toDouble()}")
println("====================================")
