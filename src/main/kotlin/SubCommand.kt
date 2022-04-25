import java.lang.RuntimeException


class AimingSubmarine {
    var depth = 0
    var x = 0
    var aim = 0

    fun forwards(x: Int){
        this.x += x
        this.depth += this.aim * x
    }

    fun applyAim(x: Int){
        this.aim += x
    }

    override fun toString(): String {
        return "AimingSubmarine(depth=$depth, x=$x, aim=$aim)"
    }


}

class SimpleSubmarine {
    var depth = 0
    var x = 0
    var y = 0

    fun sink(x: Int){
        this.depth += x
    }

    fun move(x:Int, y:Int){
        this.x += x
        this.y += y
    }

    override fun toString(): String {
        return "Submarine(depth=$depth, x=$x, y=$y)"
    }
}

enum class SubCommandType {
    DOWN{
        override fun apply(sub: SimpleSubmarine, value: Int) {
            sub.sink(value)
        }

        override fun apply(sub: AimingSubmarine, value: Int) {
            sub.applyAim(value)
        }
    },
    FORWARD{
        override fun apply(sub: SimpleSubmarine, value: Int) {
            sub.move(value, 0)
        }

        override fun apply(sub: AimingSubmarine, value: Int) {
            sub.forwards(value)
        }
    },
    UP {
        override fun apply(sub: SimpleSubmarine, value: Int) {
            sub.sink(-value)
        }

        override fun apply(sub: AimingSubmarine, value: Int) {
            sub.applyAim(-value)
        }
    };
    abstract fun apply(sub: SimpleSubmarine, value: Int)
    abstract fun apply(sub: AimingSubmarine, value: Int)

    companion object{
        fun stringToType(string:String): SubCommandType{
            return when(string){
                "down" -> DOWN
                "forward" -> FORWARD
                "up" -> UP
                else -> {
                    throw RuntimeException("Illegal command: $string")
                }
            }
        }
    }
}

class SubCommand(val type: SubCommandType, val value: Int){
    companion object{
        fun stringToSubCommand(string: String): SubCommand{
            val parts = string.split(" ")
            val (command, value) = Pair(parts[0], parts[1].toInt())
            return SubCommand(SubCommandType.stringToType(command), value)
        }
    }

    fun apply(sub: SimpleSubmarine){
        type.apply(sub, value)
    }

    fun apply(sub: AimingSubmarine){
        type.apply(sub, value)
    }
}

