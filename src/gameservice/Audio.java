package gameservice;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public enum Audio {
    BGM(Constants.BGMFileName),

    ;

    private String fileName;

    Audio(String name) {
        fileName = name;
    }

    public void play() {
        Thread playerThread = null;
        Audio thisName = this;
        playerThread = new Thread() {
            @Override
            public void run() {
                super.run();
                do {
                    File file = new File(fileName);
                    Player player = null;
                    FileInputStream stream;
                    try {
                        stream = new FileInputStream(file);
                        player = new Player(stream);
                        player.play();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                } while (thisName == BGM);            //仅BGM循环播放
            }
        };
        playerThread.start();
    }
}
