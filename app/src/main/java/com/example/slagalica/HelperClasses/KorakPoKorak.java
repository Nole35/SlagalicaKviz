package com.example.slagalica.HelperClasses;

import java.util.ArrayList;
import java.util.List;

public class KorakPoKorak {

    private String mainSolution;
    private Column column;

    public KorakPoKorak(Column columnn) {
        mainSolution = "";
        this.column = columnn;
    }

    public String getMainSolution() {
        return mainSolution;
    }

    public void setMainSolution(String mainSolution) {
        this.mainSolution = mainSolution;
    }

    public Column getColumn() {
        return column;
    }

}
