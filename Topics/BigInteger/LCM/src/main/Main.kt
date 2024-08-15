fun main() {
    val a = readln().toBigInteger()
    val b = readln().toBigInteger()
    val lcm = a * b / a.gcd(b)
    println(lcm)
}