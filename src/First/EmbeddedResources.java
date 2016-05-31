package First;


import java.net.URL;

public class EmbeddedResources {

    public EmbeddedResources(){

    }

    public URL getBattleURL(){
        URL battleURL = getClass().getResource("/First/res/battle.png");
        return battleURL;
    }

    public URL getCrownURL(){
        URL crownURL = getClass().getResource("/First/res/crown.png");
        return crownURL;
    }

    public URL getNoTownsURL(){
        URL noTownsURL = getClass().getResource("/First/res/noTowns.jpg");
        return noTownsURL;
    }

    public URL getRulesv3URL(){
        URL Rulesv3URL = getClass().getResource("/First/res/Rulesv3.txt");
        return Rulesv3URL;
    }


}

