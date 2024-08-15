import java.math.BigInteger

fun main() {
    val a = readln().toBigInteger()
    val b = readln().toBigInteger()
    val large = (a + b + (a - b).abs()) / BigInteger.TWO
    println(large)

}