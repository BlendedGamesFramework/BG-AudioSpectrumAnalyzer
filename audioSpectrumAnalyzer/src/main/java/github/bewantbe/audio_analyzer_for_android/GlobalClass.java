package github.bewantbe.audio_analyzer_for_android;

public class GlobalClass{
    private int id_player;
    private static final GlobalClass ourInstance = new GlobalClass();
    public static GlobalClass getInstance() {
        return ourInstance;
    }
    private GlobalClass() {
    }
    public int getId_player(){
        return id_player;
    }
    public void setId_player(int id){
        id_player = id;
    }
}
