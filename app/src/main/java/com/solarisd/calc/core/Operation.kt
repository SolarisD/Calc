import java.lang.Exception
import java.math.BigDecimal

abstract class Operation {
    var a: BigDecimal? = null
    abstract val isInit: Boolean
    abstract val result: String
}

abstract class UnaryOperation(): Operation(){
    override val isInit: Boolean
        get() = a != null
    protected abstract fun equal(a: BigDecimal): BigDecimal
    override val result: String
        get() = try {
            if (isInit){
                equal(a!!).toString()
            } else {
                "Operation isn't initialized"
            }
        } catch (e: Exception){
            "Error"
        }
}

abstract class BinaryOperation(): Operation(){
    var b: BigDecimal? = null
    override val isInit: Boolean
        get() = a != null && b != null
    protected abstract fun equal(a: BigDecimal, b: BigDecimal): BigDecimal
    override val result: String
        get() = try {
            if (isInit){
                equal(a!!, b!!).toString()
            } else {
                "Operation isn't initialized"
            }
        } catch (e: Exception){
            "Error"
        }
}