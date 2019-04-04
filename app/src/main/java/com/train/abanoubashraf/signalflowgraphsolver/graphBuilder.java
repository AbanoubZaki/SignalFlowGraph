package com.train.abanoubashraf.signalflowgraphsolver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class graphBuilder extends AppCompatActivity {

    Button btnAddEdge, btnDeleteEdge, btnSolve, btnDrawGraph, btnClearGraph;
    EditText from, to, gain, deleteFrom, deleteTo, input, output;
    View graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_builder);

        btnAddEdge = (Button)findViewById(R.id.btnAddEdge);
        btnDeleteEdge = (Button)findViewById(R.id.btnRemoveEdge);
        btnSolve = (Button)findViewById(R.id.btnSolve);
        btnDrawGraph = (Button)findViewById(R.id.btnDrawGraph);
        btnClearGraph = (Button)findViewById(R.id.btnClearGraph);

        from = (EditText)findViewById(R.id.from);
        to = (EditText)findViewById(R.id.to);
        gain = (EditText)findViewById(R.id.gain);
        deleteFrom = (EditText)findViewById(R.id.deleteFrom);
        deleteTo = (EditText)findViewById(R.id.deleteTo);
        input = (EditText)findViewById(R.id.input);
        output = (EditText)findViewById(R.id.output);

//        graph = (View)findViewById(R.id.graphView);

        btnAddEdge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from.getText().toString().length() == 0) {
                    from.setError("Enter 1st node please!");
                } else if (to.getText().toString().length() == 0) {
                    to.setError("Enter 2nd node please!");
                } else if (gain.length() == 0) {
                    gain.setError("Enter a numeric gain please!");
                } else {
                    SFG.getInstance().getGraph().addEdge(new Vertex<String>(from.getText().toString().
                                    replaceAll(" ", "")), new Vertex<String>(to.getText().
                                    toString().replaceAll(" ", "")), Double.parseDouble(gain.
                            getText().toString()));
                    from.setText("");
                    to.setText("");
                    gain.setText("");
                }
            }
        });

        btnDeleteEdge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteFrom.getText().toString().length() == 0) {
                    deleteFrom.setError("Enter 1st node please!");
                } else if (deleteTo.getText().toString().length() == 0) {
                    deleteTo.setError("Enter 2nd node please!");
                } else {
                    Vertex <String> from = new Vertex<>(deleteFrom.getText().toString());
                    Vertex <String> to = new Vertex<>(deleteTo.getText().toString());
                    if (SFG.getInstance().getGraph().removeEdge(from, to)) {
                        Toast.makeText(getApplicationContext(), "Edge removed successfully!"
                                , Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed, Please make sure of " +
                                        "vertices labels!", Toast.LENGTH_SHORT).show();
                    }

                    deleteFrom.setText("");
                    deleteTo.setText("");
                }
            }
        });

        btnDrawGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), draw.class));

//                if (input.getText().toString().length() == 0) {
//                    input.setError("Enter input node label please!");
//                } else if (output.getText().toString().length() == 0) {
//                    output.setError("Enter output node label please!");
//                } else {
//                    Vertex <String> start = new Vertex<>(input.getText().toString());
//                    Vertex <String> end = new Vertex<>(output.getText().toString());
//                    if (SFG.getInstance().getGraph().isVertexExist(start)
//                            && SFG.getInstance().getGraph().isVertexExist(end)) {
//                        SFG.getInstance().setInputOutPutVertices(start, end);
//                        startActivity(new Intent(getApplicationContext(), draw.class));
//                    }
//                }
            }
        });

        btnSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.getText().toString().length() == 0) {
                    input.setError("Enter input node label please!");
                } else if (output.getText().toString().length() == 0) {
                    output.setError("Enter output node label please!");
                } else {
                    Vertex <String> start = new Vertex<>(input.getText().toString());
                    Vertex <String> end = new Vertex<>(output.getText().toString());
                    if (SFG.getInstance().getGraph().isVertexExist(start)
                            && SFG.getInstance().getGraph().isVertexExist(end)) {
                        SFG.getInstance().setInputOutPutVertices(start, end);
                        startActivity(new Intent(getApplicationContext(), solve.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed, Please make sure of " +
                                "vertices labels!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnClearGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SFG.getInstance().getGraph().clearGraph();
                Toast.makeText(getApplicationContext(), "Graph cleared successfully!",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
