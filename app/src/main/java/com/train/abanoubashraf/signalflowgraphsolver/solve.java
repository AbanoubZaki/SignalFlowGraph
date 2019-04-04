package com.train.abanoubashraf.signalflowgraphsolver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class solve extends AppCompatActivity {

    TextView paths, loops, nonTouchingLoops, answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);

        paths = (TextView)findViewById(R.id.paths);
        loops = (TextView)findViewById(R.id.loops);
        nonTouchingLoops = (TextView)findViewById(R.id.nonTouchingLoops);
        answer = (TextView)findViewById(R.id.answer);

        SFG.getInstance().getForwardPaths();
        SFG.getInstance().getIndividualLoops();
        SFG.getInstance().getNonTouchedLoops();

        String temp = new String("");

        if (SFG.getInstance().getForwardPaths().size() > 0) {
            temp = "Forward Paths :\n";
            for (int i = 0; i < SFG.getInstance().getForwardPaths().size(); i++) {
                for (int j = 0; j < SFG.getInstance().getForwardPaths().get(i).size(); j++) {
                    temp += SFG.getInstance().getForwardPaths().get(i).get(j).label;
                    temp += " ";
                }
                temp += "\n";
            }
            paths.setText(temp);
        }

        if (SFG.getInstance().getIndividualLoops().size() > 0) {
            temp = "Individual Loops :\n";
            for (int i = 0; i < SFG.getInstance().getIndividualLoops().size(); i++) {
                for (int j = 0; j < SFG.getInstance().getIndividualLoops().get(i).size(); j++) {
                    temp += SFG.getInstance().getIndividualLoops().get(i).get(j).label;
                    temp += " ";
                }
                temp += "\n";
            }
            loops.setText(temp);
        }

        if (SFG.getInstance().getNonTouchedLoops().size() > 0) {
            temp = "Non-Touching Loops :\n";
            int n = SFG.getInstance().getNonTouchedLoops().size();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < SFG.getInstance().getNonTouchedLoops().get(i).size(); j++) {
                    for (int k = 0; k < SFG.getInstance().getNonTouchedLoops().get(i).get(j).size(); k++) {
                        temp += SFG.getInstance().getNonTouchedLoops().get(i).get(j).get(k).label;
                        temp += " ";
                    }
                    if (j != (SFG.getInstance().getNonTouchedLoops().get(i).size() - 1)) {
                        temp += ", ";
                    }
                }
                temp += "\n";
            }
            nonTouchingLoops.setText(temp);
        }

        temp = "Solution = " + SFG.getInstance().getNumerator() + " / " + SFG.getInstance().getDelta() + "\n";
        answer.setText(temp);

    }
}
