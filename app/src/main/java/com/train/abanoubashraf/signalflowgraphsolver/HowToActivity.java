package com.train.abanoubashraf.signalflowgraphsolver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HowToActivity extends AppCompatActivity {

    TextView howTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);

        howTo = (TextView)findViewById(R.id.textView);

        String description = new String();
        description += "1- Enter all the graph in the form of vertices & edges.\n\n";
        description += "2- To enter an edge, you have to enter the label of" +
                " the starting node & the ending node and the gain of this edge then press the ADD EDGE button.\n\n";
        description += "3- After that you need to choose your input & output nodes.\n\n";
        description += "4- To draw the graph press the DRAW GRAPH button.\n\n";
        description += "5- To see the detailed solution of the SFG press the SOLVE button.\n\n";
        description += "For more accuracy enter forward paths first please.\n\n";

        howTo.setText(description);
    }
}
