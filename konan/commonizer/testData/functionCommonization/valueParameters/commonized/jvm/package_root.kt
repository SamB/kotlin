actual fun functionNoParameters() {}
actual fun functionSingleParameter(i: Int) {}
actual fun functionTwoParameters(i: Int, s: String) {}
actual fun functionThreeParameters(i: Int, s: String, l: List<Double>) {}

fun functionMismatchedParameterCount1(i: Int, s: String, l: List<Double>) {}
fun functionMismatchedParameterCount2(i: Int, s: String) {}

fun functionMismatchedParameterNames1(i1: Int, s: String) {}
fun functionMismatchedParameterNames2(i: Int, s1: String) {}

fun functionMismatchedParameterTypes1(i: Short, s: String) {}
fun functionMismatchedParameterTypes2(i: Int, s: CharSequence) {}

fun functionDefaultValues1(i: Int = 1, s: String) {}
fun functionDefaultValues2(i: Int, s: String = "hello") {}

actual inline fun inlineFunction1(lazyMessage: () -> String) {}
actual inline fun inlineFunction2(crossinline lazyMessage: () -> String) {}
actual inline fun inlineFunction3(crossinline lazyMessage: () -> String) {}
actual inline fun inlineFunction4(noinline lazyMessage: () -> String) {}
actual inline fun inlineFunction5(noinline lazyMessage: () -> String) {}

actual fun functionWithVararg1(vararg numbers: Int) {}
fun functionWithVararg2(numbers: Array<Int>) {}
fun functionWithVararg3(numbers: Array<out Int>) {}
//fun functionWithVararg4(numbers: Array<out Int>) {}
actual fun functionWithVararg5(numbers: Array<out Int>) {}
