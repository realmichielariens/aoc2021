class FishEnvironment(){

    val fish = mutableListOf<LanternFish>()

    fun add(fish:LanternFish){
        this.fish.add(fish)
        fish.registerEnvironment(this)
    }

    fun tick(){
        for(fish in this.fish.toList()){
            fish.tick()
        }
    }

    fun population():Int{
        return this.fish.size
    }

    companion object{
        fun loadFromFile(path: String): FishEnvironment{
            val env = FishEnvironment()
            val fish = readInput(path){ s -> s.split(',').map { s ->  s.toInt() }}.flatten().map{i -> LanternFish(i) }
            fish.forEach { fish -> env.add(fish) }
            return env
        }
    }

}


class LanternFish(var nextReproduction: Int) {
    val FIRST_REPRODUCTION = 8
    val REPRODUCTION = 6

    private var environment: FishEnvironment? = null

    fun registerEnvironment(fishEnvironment: FishEnvironment) {
        this.environment = fishEnvironment
    }

    fun tick(){
        this.nextReproduction -= 1
        if(this.nextReproduction < 0 ){
            this.nextReproduction = REPRODUCTION
            spawn()
        }
    }

    private fun spawn() {
        this.environment?.add(LanternFish(FIRST_REPRODUCTION))
    }


}