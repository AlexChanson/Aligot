import son.SoundPlayer

class TestsSon {
    static void main(String[] args){
        println "test"
        SoundPlayer.play("loop_test")
        Thread.sleep(15500)
        println "test"
        SoundPlayer.play("loop_test")
    }
}
