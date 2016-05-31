package First;

import java.io.Serializable;

public enum Stage implements Serializable {

    EXPOSE{
        @Override
        public String toString(){
            return "Expose Generals";
        }

    },

    ALLOCATE{
        @Override
        public String toString(){
            return "Allocate Regiments";
        }

    },

    MOVEFIGHTERS{
        @Override
        public String toString(){
            return "Move Generals";
        }

    },

    BATTLE{
        @Override
        public String toString(){
            return "Fight";
        }

    },

    MOVECOS{
        @Override
        public String toString(){
            return "Move Chief";
        }

    },

    SETASSIST {
        @Override
        public String toString(){
            return "Set Defense";
        }

    },

    IDLE{

    },
}
