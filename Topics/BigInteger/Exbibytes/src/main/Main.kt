import java.math.BigInteger

fun main() {
    val exbibytes = readln().toBigInteger()
    val bits = exbibytes * BigInteger.TWO.pow(63)
    println(bits)
}