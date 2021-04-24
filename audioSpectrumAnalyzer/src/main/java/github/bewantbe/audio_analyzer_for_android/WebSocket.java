package github.bewantbe.audio_analyzer_for_android;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class WebSocket {
    private static int id_player = 0;
    private static Socket signalSocket;
    private final AnalyzerActivity activity;
    public static int getId_player(){return id_player;}
    public static Socket getInstance() {
        return signalSocket;
    }
    public WebSocket(int id_player, AnalyzerActivity _activity) {
         activity  = _activity;
         this.id_player = id_player;
        try {
            signalSocket = IO.socket("http://144.126.216.255:3011");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        signalSocket.connect();
        signalSocket.emit("joinRoom",id_player);
        signalSocket.on("message", onMessage);
        signalSocket.on("success", onSuccess);
        signalSocket.on("welcome", onWelcome);

    }
    private Emitter.Listener onMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String message;
                    try {
                        message = data.getString("message");
                        System.out.println(message);
                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };
    private Emitter.Listener onWelcome = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String message;
                    try {
                        message = data.getString("message");
                        System.out.println(message);
                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };
    private Emitter.Listener onSuccess = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String message;
                    try {
                        message = data.getString("message");
                        System.out.println(message);
                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };


}
