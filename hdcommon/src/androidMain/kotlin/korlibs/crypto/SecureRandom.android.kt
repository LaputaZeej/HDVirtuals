//package korlibs.crypto
//
//import java.security.SecureRandom
//
//private val jrandom by lazy {
//    PRNGFixes.apply()
//    SecureRandom()
//}
//
//actual fun fillRandomBytes(array: ByteArray) {
//    jrandom.nextBytes(array)
//}
//
//actual fun seedExtraRandomBytes(array: ByteArray) {
//    jrandom.setSeed(array)
//}