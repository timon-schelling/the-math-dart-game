import java.io.File
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

val output = StringBuilder()

val outputFile: File? = if (args.size >= 2) File(args[1]) else null

fun print(s: String) {
    if (outputFile != null) output.append(s)
    kotlin.io.print(s)
}

fun println(s: String) = print("$s\n")
fun println() = println("")

fun throwDart(circleRadius: Double = 1.0, squareSize: Double = 2.0): Double {
    val x = Random.nextDouble(0.0, squareSize / 2)
    val y = Random.nextDouble(0.0, squareSize / 2)
    val h = sqrt(x.pow(2) + y.pow(2))
    val r = sqrt(circleRadius.pow(2) - h.pow(2))
    return if (h > circleRadius || r == Double.NaN) 0.0 else r
}

val results = mutableListOf<Int>()
repeat(args[0].toInt()) {
    var radius: Double = 1.0
    val radiusList = mutableListOf<Double>()
    var i = 0
    radiusList.add(radius)
    while (radius > 0) {
        radius = throwDart(radius)
        if (radius > 0) {
            radiusList.add(radius)
            i++
        }
    }
    println("${it + 1}: $i $radiusList")
    results.add(i)
}

val entrys = mutableListOf<Int>()

results.forEach {
    if (it > entrys.size) {
        repeat(it - entrys.size) {
            entrys.add(0)
        }
    }
    if (it < 1) return@forEach
    entrys[it - 1] = entrys[it - 1] + 1
}

val entrysReversed = entrys.asReversed()
repeat(entrys.size) {
    if (it < 1) return@repeat
    entrysReversed[it] = entrysReversed[it] + entrysReversed[it - 1]
}

val expectedRounds = run {
    fun fact(n: Int) = run { var factorial: Double = 1.0; (1..n).forEach { factorial *= it.toDouble() }; factorial }
    var sum: Double = 1.0
    repeat(100) {
        val i = it + 1
        sum += PI.pow(i.toDouble()) / fact(i) / 2.0.pow((i * 2).toDouble())
    }
    sum
}

println()
println("====================================")
entrys.forEachIndexed { i, e ->
    println("${i + 1}: $e (${(e.toDouble() / results.size.toDouble()) * 100}%)")
}
println("====================================")
println("average hits: ${results.sum().toDouble() / results.size.toDouble()}")
println("average rounds: ${(results.sum().toDouble() / results.size.toDouble()) + 1}")
println("expected rounds: $expectedRounds")
println("====================================")

if (outputFile != null) outputFile.writeText(output.toString())
