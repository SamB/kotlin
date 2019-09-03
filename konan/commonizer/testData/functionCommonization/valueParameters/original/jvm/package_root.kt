fun functionNoParameters() {}
fun functionSingleParameter(i: Int) {}
fun functionTwoParameters(i: Int, s: String) {}
fun functionThreeParameters(i: Int, s: String, l: List<Double>) {}

fun functionMismatchedParameterCount1(i: Int, s: String, l: List<Double>) {}
fun functionMismatchedParameterCount2(i: Int, s: String) {}

fun functionMismatchedParameterNames1(i1: Int, s: String) {}
fun functionMismatchedParameterNames2(i: Int, s1: String) {}

fun functionMismatchedParameterTypes1(i: Short, s: String) {}
fun functionMismatchedParameterTypes2(i: Int, s: CharSequence) {}

fun functionDefaultValues1(i: Int = 1, s: String) {}
fun functionDefaultValues2(i: Int, s: String = "hello") {}

inline fun inlineFunction1(lazyMessage: () -> String) {}
inline fun inlineFunction2(crossinline lazyMessage: () -> String) {}
inline fun inlineFunction3(crossinline lazyMessage: () -> String) {}
inline fun inlineFunction4(noinline lazyMessage: () -> String) {}
inline fun inlineFunction5(noinline lazyMessage: () -> String) {}

fun functionWithVararg1(vararg numbers: Int) {}
fun functionWithVararg2(numbers: Array<Int>) {}
fun functionWithVararg3(numbers: Array<out Int>) {}
//fun functionWithVararg4(numbers: Array<out Int>) {}
fun functionWithVararg5(numbers: Array<out Int>) {}
